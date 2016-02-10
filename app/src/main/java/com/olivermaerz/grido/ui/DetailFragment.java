package com.olivermaerz.grido.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.olivermaerz.grido.R;
import com.olivermaerz.grido.adapter.ReviewAdapter;
import com.olivermaerz.grido.adapter.TrailerAdapter;
import com.olivermaerz.grido.data.MdbMovie;
import com.olivermaerz.grido.data.Review;
import com.olivermaerz.grido.provider.favorite.FavoriteContentValues;
import com.olivermaerz.grido.provider.review.ReviewContentValues;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Package: com.olivermaerz.grido
 * Created by omaerz on 1/13/16.
 */
public class DetailFragment extends Fragment {

    private View view;
    private NonScrollListView reviewListView;
    private NonScrollListView trailerListView;
    private Context context;
    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    private final String LOG_TAG = "DetailFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view  = inflater.inflate(R.layout.fragment_detail, container, false);

        // for access to toolbar
        //setHasOptionsMenu(true);

        return this.view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);




        //Intent intent = getIntent();
        Bundle extras = getArguments();

        if (extras != null) {
            final MdbMovie movie = extras.getParcelable(MdbMovie.EXTRA_MOVIE);
            final ImageButton favoriteButton = (ImageButton) this.view.findViewById(R.id.favorite);

            // button is invisible by default - make visible
            favoriteButton.setVisibility(View.VISIBLE);

            // set listener when favorites button is clicked
            favoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(view, "Added to favorites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    saveFavoriteToDatabase(movie);
                    // change the heart icon to solid when movie is favorite
                    favoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp);

                }
            });


            // get the strings and double we need from the parcelable (title, rating, description)
            ((TextView) this.view.findViewById(R.id.movieTitle)).setText(movie.originalTitle);
            ((TextView) this.view.findViewById(R.id.rating)).setText(getString(R.string.rated) +
                    movie.rating + getString(R.string.of_10_points));
            ((TextView) this.view.findViewById(R.id.description)).setText(movie.description);

            // reformat the date so it looks a little nicer and is displayed localized
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(movie.releaseDate);
                DateFormat dateFormat =
                        android.text.format.DateFormat.getDateFormat(this.context);
                ((TextView) this.view.findViewById(R.id.releaseDate)).setText(getString(R.string.released) +
                        dateFormat.format(date));

            } catch (ParseException e) {
                //e.printStackTrace();
                // error parsing date - they must have change the format - just output string as
                // received from MovieDB
                ((TextView) this.view.findViewById(R.id.releaseDate)).setText(getString(R.string.released) +
                        getString(R.string.unknown));
            }



            // load the image from the poster url into the view (incl. caching, resizing etc.
            Picasso.with(this.getActivity())
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.loading_image)
                    .into((ImageView) this.view.findViewById(R.id.imageView));

            // listviews for reviews and trailers
            this.reviewListView = (NonScrollListView) this.view.findViewById(R.id.reviews_listview);
            this.trailerListView = (NonScrollListView)
                    this.view.findViewById(R.id.trailers_listview);

            Log.v(LOG_TAG, " movie.reviews: " + movie.reviews);

            // instantiate adapters for reviews and trailers
            this.reviewAdapter = new ReviewAdapter(this.getActivity(), movie.reviews);
            this.trailerAdapter = new TrailerAdapter(this.getActivity(), movie.trailers);

            // Assign adapters to ListView
            reviewListView.setAdapter(this.reviewAdapter);
            trailerListView.setAdapter(this.trailerAdapter);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    private void saveFavoriteToDatabase(MdbMovie movie) {
        FavoriteContentValues favValues = new FavoriteContentValues();
        favValues.putOriginaltitle(movie.originalTitle)
                .putRated(movie.rating.toString())
                .putDescription(movie.description)
                .putReleasedate(movie.releaseDate)
                .putPoster(movie.posterUrl);
        context.getContentResolver().insert(favValues.uri(), favValues.values());

        ReviewContentValues[] revValues = new  ReviewContentValues[movie.reviews.size()];

        int i = 0;
        for (Review review: movie.reviews) {
            revValues[i].putContent(review.content).putAuthor(review.author);
            i++;
        }

        context.getContentResolver().bulkInsert(revValues[0].uri(), revValues.);


        context.getContentResolver().bulkInsert()



    }




}
