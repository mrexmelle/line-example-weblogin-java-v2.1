
package com.linecorp.example.weblogin;

import java.security.SecureRandom;
import java.util.Base64;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController
{
    public final static String NONCE="nonce";

    @Autowired
    @Qualifier("com.linecorp.channel_id")
    String CHANNEL_ID;

    @Autowired
    @Qualifier("com.linecorp.redirect_uri")
    String REDIRECT_URI;

    @GetMapping(value="/welcome")
    public ResponseEntity<String> welcome(HttpSession aSession)
    {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        aSession.setAttribute("line_state", token);
        aSession.setAttribute("line_nonce", NONCE);

        System.out.println("WelcomeController::welcome - channel id: " + CHANNEL_ID);

        final String html = "<html><head>"+"</head><body><form action=\"https://access.line.me/oauth2/v2.1/authorize\" method=\"GET\"><input type=\"hidden\" name = \"response_type\" value=\"code\" /><input type=\"hidden\" name = \"scope\" value=\"openid profile\" /><input type=\"hidden\" name = \"client_id\" value=\"" + CHANNEL_ID + "\" /><input type=\"hidden\" name = \"state\" value=\"" + token + "\" /><input type=\"hidden\" name = \"nonce\" value=\"" + NONCE + "\" /><input type=\"hidden\" name = \"redirect_uri\" value=\"" + REDIRECT_URI + "\" /><input type=\"submit\" /></form>"+"</body></html>";
        return new ResponseEntity<String>(html, HttpStatus.OK);
    }
};
