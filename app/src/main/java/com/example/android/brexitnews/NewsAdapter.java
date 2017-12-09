package com.example.android.brexitnews;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pkennedy on 12/8/17.
 */

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    private static final String ISO8601_SEPARATOR = "T";

    public NewsAdapter(Activity context, List<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the NewsItem object located at this position in the list
        NewsItem currentNewsItem = getItem(position);

        // Get the SectionType TextView
        TextView sectionType = (TextView) listItemView.findViewById(R.id.sectionType);

        // Set the SectionType
        sectionType.setText(currentNewsItem.getSectionType());

        // Get the Title TextView
        TextView title = (TextView) listItemView.findViewById(R.id.title);

        // Set the Title
        title.setText(currentNewsItem.getWebTitle());

        // Get the PubDate TextView
        TextView pubDate = (TextView) listItemView.findViewById(R.id.pubDate);

        // Extract the date and time from ISO8601 string and construct the return string
        String date = getDateString(currentNewsItem.getPubDate());
        String time = getTimeString(currentNewsItem.getPubDate());
        String displayDateTime = getContext().getString(R.string.publishedString) + date + " | " + time;

        // Set the displayDateTime
        pubDate.setText(displayDateTime);

        return listItemView;
    }

    private String getDateString(String pubDate){
        if(pubDate.contains(ISO8601_SEPARATOR)){
            String[] dateTime = pubDate.split(ISO8601_SEPARATOR);
            return dateTime[0];
        }
        else return null;
    }

    private String getTimeString(String pubDate){
        if(pubDate.contains(ISO8601_SEPARATOR)){
            String[] dateTime = pubDate.split(ISO8601_SEPARATOR);
            String time = dateTime[1];
            return removeLastChar(time);
        }
        else return null;
    }

    /*https://stackoverflow.com/questions/7438612/how-to-remove-the-last-character-from-a-string*/
    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
    }

}
