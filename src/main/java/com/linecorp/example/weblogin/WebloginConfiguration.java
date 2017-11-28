
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
        return env.getProperty("com.linecorp.channel_id");
    }

    @Bean(name="com.linecorp.channel_secret")
    public String getChannelSecret()
    {
        return env.getProperty("com.linecorp.channel_secret");
    }
};
