package com.olivermaerz.grido;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by omaerz on 11/19/15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> listOfMovies;
    private int width;

    public ImageAdapter(Context c) {
        context = c;
        listOfMovies = new ArrayList<>();
    }

    public void add(String movie) {
        listOfMovies.add(movie);
        notifyDataSetChanged();
    }

    public void setWidth(int width) {
        this.width = width;
    }
    @Override
    public int getCount() {
        return listOfMovies.size();
    }

    @Override
    public Object getItem(int position) {
        if (position >= 0 && position < listOfMovies.size()) {
            return listOfMovies.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            int height = (int)(this.width * 1.5);
            imageView.setLayoutParams(new GridView.LayoutParams(this.width, height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }


        Picasso.with(context)
                .load(listOfMovies.get(position))
                .placeholder(R.drawable.loading_image)
                .into(imageView);
        return imageView;
    }


}