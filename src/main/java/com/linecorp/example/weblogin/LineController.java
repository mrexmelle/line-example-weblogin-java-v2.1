
package com.linecorp.example.weblogin;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@RestController
@RequestMapping(value="/line")
public class LineController
{
	private final static String GRANT_TYPE="authorization_code";
	private final static String CHANNEL_ID="1479418979";
	private final static String CHANNEL_SECRET="a17951d01dd8719452544a5b57a22b85";
	private final static String REDIRECT_URI="http://localhost:8080/line/auth";

	private final static String POST_ACCESSTOKEN_URL="https://api.line.me/oauth2/v2.1/token";

    @RequestMapping(value="/auth", method=RequestMethod.GET)
    public ResponseEntity<String> auth(
        HttpSession aSession,
        @RequestParam(value="code", required=false) String aCode,
        @RequestParam(value="state", required=false) String aState,
        @RequestParam(value="errorCode", required=false) String aErrorCode,
        @RequestParam(value="errorMessage", required=false) String aErrorMessage)
    {
        if(aCode!=null)
        {
            System.out.println("LineController::auth - success with code: " + aCode + " and state: " + aState);
            System.out.println("LineController::auth - saved state: " + aSession.getAttribute("line_state"));
            if(!aState.equals(aSession.getAttribute("line_state")))
            {
                return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
            }
            try
            {
				// POST to get the access token
				HttpClient client= HttpClientBuilder.create().build();
				HttpPost post=new HttpPost(POST_ACCESSTOKEN_URL);
				post.setHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

				List<NameValuePair> urlParams=new ArrayList<NameValuePair>();
				urlParams.add(new BasicNameValuePair("grant_type", GRANT_TYPE));
				urlParams.add(new BasicNameValuePair("code", aCode));
				urlParams.add(new BasicNameValuePair("client_id", CHANNEL_ID));
				urlParams.add(new BasicNameValuePair("client_secret", CHANNEL_SECRET));
				urlParams.add(new BasicNameValuePair("redirect_uri", REDIRECT_URI));

				post.setEntity(new UrlEncodedFormEntity(urlParams));

				HttpResponse response=client.execute(post);

				BufferedReader br=new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				StringBuffer result=new StringBuffer();
				String line="";
				while((line=br.readLine())!=null)
				{
					result.append(line);
				}

                System.out.println("LineController::auth - get access token - response: " + result.toString());

				// Parsed the string result
				Gson g=new Gson();
				TokenInfo token=g.fromJson(result.toString(), TokenInfo.class);

                // decode id_token
                DecodedJWT jwt=null;
                try
                {
                    JWTVerifier v=JWT.require(Algorithm.HMAC256(CHANNEL_SECRET))
                        .withIssuer("https://access.line.me")
                        .withAudience(CHANNEL_ID)
                        .withClaim("nonce", (String)(aSession.getAttribute("line_nonce")))
                        .build();
                    jwt=v.verify(token.id_token);
                }
                catch(UnsupportedEncodingException e)
                {
                    System.out.println("LineController::auth - Unsupported encoding err: " + e.getMessage());
                    return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                catch(JWTVerificationException e)
                {
                    System.out.println("LineController::auth - JWT Verification err: " + e.getMessage());
                    return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
                
                System.out.println("LineController::auth - jwt-header: " + jwt.getHeader());
                System.out.println("LineController::auth - jwt-payload: " + jwt.getPayload());
                System.out.println("LineController::auth - jwt-signature: " + jwt.getSignature());
                
                Map<String, Claim> claims = jwt.getClaims();
                long i = 0;
                for (Map.Entry<String, Claim> pair : claims.entrySet())
                {
                    System.out.println("LineController::auth - (k,v) =(" + pair.getKey() + "," + pair.getValue().asString() + ")");
                }
                
                ProfileInfo profile=new ProfileInfo(jwt.getSubject(), jwt.getClaim("name").asString(), jwt.getClaim("picture").asString(), "status message");

				// show the HTML
				String html=String.format("<head>You are logged-in</head><body><p>Welcome %s!</p><br /><img src=\"%s\" /><br /><p>%s</p></body>",
					profile.displayName,
					profile.pictureUrl,
					profile.statusMessage);

                return new ResponseEntity<String>(html, HttpStatus.OK);

            }
            catch(Exception e)
            {
                System.out.println("LineController::auth - Exception raised: " + e.getMessage());
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else if(aErrorCode!=null && aErrorMessage!=null)
        {
            System.out.println("LineController::auth - failed with error_code: " + aErrorCode + " and error_message: " + aErrorMessage);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else
        {
            System.out.println("No params defined");
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
};
