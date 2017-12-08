package com.example.android.brexitnews;

/**
 * Created by pkennedy on 12/8/17.
 */

public class NewsItem {

    private String mType, mSectionId, mWebTitle, mWebUrl, mWebPublicationDate;

    public NewsItem(String type, String sectionId, String webTitle, String webUrl, String webPublicationDate){
        mType = type;
        mSectionId = sectionId;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
        mWebPublicationDate = webPublicationDate;
    }

    public String getSectionType(){
        return mSectionId + " | " + mType;
    }

    public String getWebTitle(){
        return mWebTitle;
    }

    public String getWebUrl(){
        return mWebUrl;
    }

    public String getPubDate(){
        return mWebPublicationDate;
    }


}
