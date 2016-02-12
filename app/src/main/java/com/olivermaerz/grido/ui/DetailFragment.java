package com.olivermaerz.grido.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.olivermaerz.grido.data.Trailer;
import com.olivermaerz.grido.provider.favorite.FavoriteColumns;
import com.olivermaerz.grido.provider.favorite.FavoriteContentValues;
import com.olivermaerz.grido.provider.favorite.FavoriteSelection;
import com.olivermaerz.grido.provider.review.ReviewColumns;
import com.olivermaerz.grido.provider.review.ReviewContentValues;
import com.olivermaerz.grido.provider.trailer.TrailerColumns;
import com.olivermaerz.grido.provider.trailer.TrailerContentValues;
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
    private boolean isFavorite;
    private ShareActionProvider mShareActionProvider;
    private boolean shareTrailer;
    private String youtubeID;
    private final String LOG_TAG = "DetailFragment";
    private MdbMovie movieObj;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view  = inflater.inflate(R.layout.fragment_detail, container, false);

        // for access to toolbar
        setHasOptionsMenu(true);

        return this.view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem menuItem = menu.findItem(R.id.menu_item_share);

        // check if there is a trailer to share
        if (shareTrailer) {
            menuItem.setVisible(true);
            ShareActionProvider shareAction =
                    (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
            shareAction.setShareIntent(createShareIntent());
        } else {
            menuItem.setVisible(false);
        }



    }

    protected Intent createShareIntent() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.check_out_this_movie));
        intent.putExtra(Intent.EXTRA_TEXT, "http://www.youtube.com/watch?v=" + this.youtubeID);
        intent.setType("text/plain");

        return intent;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_share_action, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //Intent intent = getIntent();
        Bundle extras = getArguments();


        if (extras != null) {
            final MdbMovie movie = extras.getParcelable(MdbMovie.EXTRA_MOVIE);
            final ImageButton favoriteButton = (ImageButton) this.view.findViewById(R.id.favorite);


            if (movie != null) {
                // button is invisible by default - make visible
                favoriteButton.setVisibility(View.VISIBLE);

                // check if the movie is a favorite (display the heart icon as solid in that case
                FavoriteSelection where = new FavoriteSelection();
                where.movieId(movie.id);
                Cursor cursor = context.getContentResolver().query(FavoriteColumns.CONTENT_URI,
                        null, where.sel(), where.args(), null);

                if (!(cursor.moveToFirst()) || cursor.getCount() == 0) {
                    this.isFavorite = false;
                } else {
                    this.isFavorite = true;
                    favoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                }
                cursor.close();


                // set listener when favorites button is clicked
                favoriteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isFavorite) {
                            Snackbar.make(view, "Removed movie from favorites",
                                    Snackbar.LENGTH_LONG).setAction("Action", null).show();

                            removeFavoriteFromDatabase(movie.id);
                            // change the heart icon to border when movie is not a favorite
                            favoriteButton.setImageResource(
                                    R.drawable.ic_favorite_border_white_24dp);
                            isFavorite = false;
                        } else {
                            Snackbar.make(view, "Added movie to favorites", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                            saveFavoriteToDatabase(movie);
                            // change the heart icon to solid when movie is favorite
                            favoriteButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                            isFavorite = true;
                        }
                    }
                });

                // get the strings and double we need from the parcelable
                // (title, rating, description)
                ((TextView) this.view.findViewById(R.id.movieTitle)).setText(movie.originalTitle);
                ((TextView) this.view.findViewById(R.id.rating)).setText(getString(R.string.rated) +
                        movie.rating + getString(R.string.of_10_points));
                ((TextView) this.view.findViewById(R.id.description)).setText(movie.description);

                // reformat the date so it looks a little nicer and is displayed localized
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(movie.releaseDate);
                    DateFormat dateFormat =
                            android.text.format.DateFormat.getDateFormat(this.context);
                    ((TextView) this.view.findViewById(R.id.releaseDate))
                            .setText(getString(R.string.released) +
                            dateFormat.format(date));

                } catch (ParseException e) {
                    //e.printStackTrace();
                    // error parsing date - they must have change the format - just output string
                    // as received from MovieDB
                    ((TextView) this.view.findViewById(R.id.releaseDate))
                            .setText(getString(R.string.released) +
                            getString(R.string.unknown));
                }



                // load the image from the poster url into the view (incl. caching, resizing etc.
                Picasso.with(this.getActivity())
                        .load(movie.posterUrl)
                        .placeholder(R.drawable.loading_image)
                        .into((ImageView) this.view.findViewById(R.id.imageView));


                // listviews for reviews and trailers
                this.reviewListView = (NonScrollListView)
                        this.view.findViewById(R.id.reviews_listview);
                this.trailerListView = (NonScrollListView)
                        this.view.findViewById(R.id.trailers_listview);

                Log.v(LOG_TAG, " movie.reviews: " + movie.reviews);

                // instantiate adapters for reviews and trailers
                this.reviewAdapter = new ReviewAdapter(this.getActivity(), movie.reviews);
                this.trailerAdapter = new TrailerAdapter(this.getActivity(), movie.trailers);

                // if movie has trailers allow sharing the first video
                if (movie.trailers.size() > 0) {
                    this.youtubeID = movie.trailers.get(0).source;
                    this.shareTrailer = true;

                }


                // Assign adapters to ListView
                reviewListView.setAdapter(this.reviewAdapter);
                trailerListView.setAdapter(this.trailerAdapter);
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context = context;
    }

    /**
     * Save the movie to favorite table in favorites db
     *
     * @param movie
     */
    private void saveFavoriteToDatabase(MdbMovie movie) {
        // first insert the row for the movie data (title, description etc.)
        FavoriteContentValues favValues = new FavoriteContentValues();
        favValues.putOriginaltitle(movie.originalTitle)
                .putRated(movie.rating.toString())
                .putDescription(movie.description)
                .putReleasedate(movie.releaseDate)
                .putPoster(movie.posterUrl)
                .putMovieId(movie.id);

        // get the URI and the ID for the inserted record
        Uri favoriteUri = favValues.insert(context.getContentResolver());
        long favoriteId = ContentUris.parseId(favoriteUri);

        Log.v(LOG_TAG, "saveFavoriteToDatabase favoriteUri:" + favoriteUri );
        Log.v(LOG_TAG, "saveFavoriteToDatabase favoriteId:" + favoriteId );

        // insert the rows for the reviews
        ContentValues[] revValues = new ContentValues[movie.reviews.size()];
        int i = 0;
        for (Review review : movie.reviews) {
            ReviewContentValues revValue = new ReviewContentValues();
            revValues[i] = revValue.putContent(review.content).putAuthor(review.author)
                    .putFavoriteId(favoriteId).values();
            i++;
        }
        context.getContentResolver().bulkInsert(ReviewColumns.CONTENT_URI, revValues);

        // insert the rows for the trailers
        ContentValues[] trailerValues = new ContentValues[movie.trailers.size()];
        i = 0;
        for (Trailer trailer : movie.trailers) {
            TrailerContentValues trailerValue = new TrailerContentValues();
            trailerValues[i] = trailerValue.putName(trailer.name).putSource(trailer.source)
                    .putFavoriteId(favoriteId).values();
            i++;
        }
        context.getContentResolver().bulkInsert(TrailerColumns.CONTENT_URI, trailerValues);
    }

    /**
     * Remove movie with movieId from favorite table in favorites db
     *
     * @param movieId
     */
    private void removeFavoriteFromDatabase(long movieId) {
        FavoriteSelection where = new FavoriteSelection();
        where.movieId(movieId);
        context.getContentResolver().delete(FavoriteColumns.CONTENT_URI, where.sel(), where.args());

    }

}
