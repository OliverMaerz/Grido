package com.olivermaerz.grido;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Package: com.olivermaerz.grido
 * Created by omaerz on 1/17/16.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {

    //private ArrayList<Review> review;

    private static final String LOG_TAG = ReviewAdapter.class.getSimpleName();
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the List is the data we want
     * to populate into the lists
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param reviews   A List of reviews objects to display in a list
     */
    public ReviewAdapter(Activity context, ArrayList<Review> reviews) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, reviews);
        //this.reviews = reviews;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate.
     *                    (search online for "android view recycling" to learn more)
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the Review object from the ArrayAdapter at the appropriate position
        Review review = getItem(position);

        // Adapters recycle views to AdapterViews.
        // If this is a new View object we're getting, then inflate the layout.
        // If not, this view already has the layout inflated from a previous call to getView,
        // and we modify the View widgets as usual.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_detail, parent, false);
        }

        TextView content = (TextView) convertView.findViewById(R.id.review_content);
        content.setText(review.content); //

        TextView author = (TextView) convertView.findViewById(R.id.review_author);
        author.setText(review.author);

        Log.v(LOG_TAG, " position " + position + " author: " + review.author);
        Log.v(LOG_TAG, " content: " + review.content);


        return convertView;
    }
}

