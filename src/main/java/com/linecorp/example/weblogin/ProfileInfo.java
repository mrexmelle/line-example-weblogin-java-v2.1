
package com.linecorp.example.weblogin;

public class ProfileInfo
{
    public String userId;
    public String displayName;
    public String pictureUrl;
    public String statusMessage;

    public ProfileInfo(String aUserId, String aDisplayName, String aPictureUrl, String aStatusMessage)
    {
        userId=aUserId;
		displayName=aDisplayName;
		pictureUrl=aPictureUrl;
		statusMessage=aStatusMessage;
    }

    public ProfileInfo()
    {
    }
};
