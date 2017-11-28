
package com.linecorp.example.weblogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class WebloginConfiguration
{
    @Autowired
    Environment env;

    @Bean(name="com.linecorp.channel_id")
    public String getChannelId()
    {
        String channel_id=System.getenv("CHANNEL_ID");
        if(channel_id==null) { channel_id=env.getProperty("com.linecorp.channel_id"); }
        System.out.println("WebloginConfiguration::getChannelId - " + channel_id);
        return channel_id;
    }

    @Bean(name="com.linecorp.channel_secret")
    public String getChannelSecret()
    {
        String channel_secret=System.getenv("CHANNEL_SECRET");
        if(channel_secret==null) { channel_secret=env.getProperty("com.linecorp.channel_secret"); }
        System.out.println("WebloginConfiguration::getChannelSecret - " + channel_secret);
        return channel_secret;
    }

    @Bean(name="com.linecorp.redirect_uri")
    public String getRedirectUri()
    {
      String redirect_uri=System.getenv("REDIRECT_URI");
      if(redirect_uri==null) { redirect_uri=env.getProperty("com.linecorp.redirect_uri"); }
      System.out.println("WebloginConfiguration::getRedirectUri - " + redirect_uri);
      return redirect_uri;
    }
};
