
package com.linecorp.example.weblogin;

public class TokenInfo
{
    public String access_token;
    public String token_type;
    public long expires_in;
    public String refresh_token;
    public String scope;
    
    public TokenInfo(String aAccessToken, String aTokenType, long aExpiresIn,
        String aRefreshToken, String aScope)
    {
        access_token=aAccessToken;
        token_type=aTokenType;
        expires_in=aExpiresIn;
        refresh_token=aRefreshToken;
        scope=aScope;
    }
    
    public TokenInfo()
    {
    }
};
