
package com.linecorp.example.springempty;

import com.google.gson.Gson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/line")
public class Welcome
{
    @RequestMapping(value="/welcome", method=RequestMethod.GET)
    public ResponseEntity<String> world()
    {
        final String html = "<html><body><form action=\"https://access.line.me/dialog/oauth/weblogin\" method=\"GET\"><input type=\"hidden\" name = \"response_type\" value=\"code\" /><input type=\"hidden\" name = \"client_id\" value=\"1479418979\" /><input type=\"hidden\" name = \"state\" value=\"x\" /><input type=\"hidden\" name = \"redirect_uri\" value=\"http://10.56.43.47:8080/line/auth\" /><input type=\"submit\" /></form></body></html>";
        return new ResponseEntity<String>(html, HttpStatus.OK);
    }
};
