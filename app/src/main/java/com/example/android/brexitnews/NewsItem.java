package com.example.android.brexitnews;

/**
 * Created by pkennedy on 12/8/17.
 */

public class NewsItem {

    private String mType, mSectionName, mWebTitle, mWebUrl, mWebPublicationDate;

    public NewsItem(String type, String sectionName, String webTitle, String webUrl, String webPublicationDate){
        mType = type;
        mSectionName = sectionName;
        mWebTitle = webTitle;
        mWebUrl = webUrl;
        mWebPublicationDate = webPublicationDate;
    }

    public String getSectionType(){
        return mSectionName + " | " + mType;
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
