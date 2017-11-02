
package com.linecorp.example.weblogin;

public class TokenInfo
{
    public String access_token;
    public String token_type;
    public String id_token;
    public long expires_in;
    public String refresh_token;
    public String scope;
    
    public TokenInfo(String aAccessToken, String aTokenType, String aIdToken, long aExpiresIn,
        String aRefreshToken, String aScope)
    {
        access_token=aAccessToken;
        token_type=aTokenType;
        id_token=aIdToken;
        expires_in=aExpiresIn;
        refresh_token=aRefreshToken;
        scope=aScope;
    }
    
    public TokenInfo()
    {
    }
};
