package com.olivermaerz.grido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // change title of the details activity and add back button
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle(R.string.movie_detail_title);
        // Add the back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mActionBarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Movies.EXTRA_MOVIE)) {
            //Movies movie = new Movies(intent.getExtras().getParcelable(Movies.EXTRA_MOVIE));
            Movies movie = intent.getParcelableExtra(Movies.EXTRA_MOVIE);

            // get the strings and double we need from the parcelable (title, rating, description)
            ((TextView)findViewById(R.id.movieTitle)).setText(movie.originalTitle);
            ((TextView)findViewById(R.id.rating)).setText(getString(R.string.rated) +
                    movie.rating + getString(R.string.of_10_points));
            ((TextView)findViewById(R.id.description)).setText(movie.description);

            // reformat the date so it looks a little nicer and is displayed localized
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(movie.releaseDate);
                DateFormat dateFormat =
                        android.text.format.DateFormat.getDateFormat(getApplicationContext());
                ((TextView)findViewById(R.id.releaseDate)).setText(getString(R.string.released) +
                        dateFormat.format(date));

            } catch (ParseException e) {
                //e.printStackTrace();
                // error parsing date - they must have change the format - just output string as
                // received from MovieDB
                ((TextView)findViewById(R.id.releaseDate)).setText(getString(R.string.released) +
                        getString(R.string.unknown));
            }


            // load the image from the poster url into the view (incl. caching, resizing etc.
            Picasso.with(this)
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.loading_image)
                    .into((ImageView)findViewById(R.id.imageView));


        }


    }

}
