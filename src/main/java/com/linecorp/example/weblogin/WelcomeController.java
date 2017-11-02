
package com.linecorp.example.weblogin;

import java.security.SecureRandom;
import java.util.Base64;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController
{
    private final static String CHANNEL_ID="1479418979";
    private final static String REDIRECT_URI="http://localhost:8080/line/auth";

    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public ResponseEntity<String> welcome(HttpSession aSession)
    {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        aSession.setAttribute("line_state", token);
        
        final String html = "<html><head>"+"</head><body><form action=\"https://access.line.me/dialog/oauth/weblogin\" method=\"GET\"><input type=\"hidden\" name = \"response_type\" value=\"code\" /><input type=\"hidden\" name = \"client_id\" value=\"" + CHANNEL_ID + "\" /><input type=\"hidden\" name = \"state\" value=\"" + token + "\" /><input type=\"hidden\" name = \"redirect_uri\" value=\"" + REDIRECT_URI + "\" /><input type=\"submit\" /></form>"+"</body></html>";
        return new ResponseEntity<String>(html, HttpStatus.OK);
    }
};
