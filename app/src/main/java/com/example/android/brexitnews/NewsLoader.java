package com.example.android.brexitnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pkennedy on 12/8/17.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsItem>> {

    /** Tag for log messages */
    private static final String LOG_TAG = NewsLoader.class.getName();

    /** Query URL */
    private String mUrl;

    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<NewsItem> loadInBackground() {

        if(mUrl == null){
            return null;
        }

        // Fetch the earthquake data (put in async task)
        String jsonResponse = QueryUtils.fetchNewsData(mUrl);

        // Create a list of earthquake data.
        ArrayList<NewsItem> newsItems = QueryUtils.extractNewsItems(jsonResponse);
        return newsItems;

    }
}
