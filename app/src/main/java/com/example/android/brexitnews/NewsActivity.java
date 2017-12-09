package com.example.android.brexitnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class NewsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsItem>>  {

    public static final String LOG_TAG = NewsActivity.class.getName();

    private TextView emptyTextView;

    private ListView listView;

    private ProgressBar progressBar;

    /**
     * Constant value for the news loader ID.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * Adapter for the list of news items
     */
    private NewsAdapter mAdapter;

    /**
     * URL to query the Guardian API for Brexit news
     */
    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Find the progress bar
        progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);

        // Find the list view
        listView = (ListView) findViewById(R.id.list);

        // Set the no data text view
        emptyTextView = (TextView) findViewById(R.id.empty);
        listView.setEmptyView(emptyTextView);

        // Check to see if we have an internet connection
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            progressBar.setVisibility(View.GONE);

            // Set empty state text to display "No internet connection."
            emptyTextView.setText(R.string.no_internet);
        }

    }

    /**
     * Update the screen to display earthquake information from USGS.
     */
    private void updateUi(final List<NewsItem> newsItems) {

        // Create an {@link EarthquakeAdapter}, whose data source is a list of {@link earthquake}s. The
        // adapter knows how to create list items for each item in the list.
        NewsAdapter adapter = new NewsAdapter(this, newsItems);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String url = newsItems.get(i).getWebUrl();
                Intent in = new Intent(Intent.ACTION_VIEW);
                in.setData(Uri.parse(url));
                startActivity(in);
            }
        });
    }


    @Override
    public Loader<List<NewsItem>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        String orderBy  = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("q", "brexit");
        uriBuilder.appendQueryParameter("api-key", "test");

        // Return the completed uri "http://content.guardianapis.com/search?order-by=newest&q=brexit&api-key=test"
        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<NewsItem>> loader, List<NewsItem> data) {
        // Hide the progress bar
        progressBar.setVisibility(View.GONE);

        // Set empty state text to display "No Brexit News Found"
        emptyTextView.setText(R.string.no_data);

        // Clear the adapter of previous news data
        if (mAdapter != null) {
            mAdapter.clear();
        }

        // If there is a valid list of {@link NewsItem}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            updateUi(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<NewsItem>> loader) {
        // Clear the adapter of previous news data
        if (mAdapter != null) {
            mAdapter.clear();
        }
    }

    @Override
    // This method initialize the contents of the Activity's options menu.
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
