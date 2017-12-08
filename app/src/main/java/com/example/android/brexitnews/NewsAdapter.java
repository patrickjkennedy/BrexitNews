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

    public NewsAdapter(Activity context, List<NewsItem> newsItems) {
        super(context, 0, newsItems);
    }

    @SuppressLint("SetTextI18n")
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

        // Set the PubDate
        //TODO: Need to reformat this
        pubDate.setText(R.string.published + currentNewsItem.getPubDate());

        return listItemView;
    }
}
