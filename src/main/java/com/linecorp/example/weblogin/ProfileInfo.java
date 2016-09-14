
package com.linecorp.example.weblogin;

public class ProfileInfo
{
    public String mid;
    public String displayName;
    public String pictureUrl;
    public String statusMessage;

    public ProfileInfo(String aMid, String aDisplayName, String aPictureUrl, String aStatusMessage)
    {
        mid=aMid;
		displayName=aDisplayName;
		pictureUrl=aPictureUrl;
		statusMessage=aStatusMessage;
    }

    public ProfileInfo()
    {
    }
};
