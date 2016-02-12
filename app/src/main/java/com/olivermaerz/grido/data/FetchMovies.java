package com.olivermaerz.grido.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.olivermaerz.grido.adapter.ImageAdapter;
import com.olivermaerz.grido.provider.favorite.FavoriteCursor;
import com.olivermaerz.grido.provider.favorite.FavoriteSelection;
import com.olivermaerz.grido.provider.review.ReviewCursor;
import com.olivermaerz.grido.provider.review.ReviewSelection;
import com.olivermaerz.grido.provider.trailer.TrailerCursor;
import com.olivermaerz.grido.provider.trailer.TrailerSelection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.olivermaerz.grido.R.string;

/**
 * Created by omaerz on 11/27/15.
 * AsyncTask to fetch movies from MovieDB and then pass the image url to imageAdapter
 * Store rest of the movie data like title, description etc. in movieListObj
 *
 */
public class FetchMovies extends AsyncTask<String, String, ArrayList<MdbMovie>> {

    private static final String LOG_TAG = "Fetch Movie";
    private int width;
    private ArrayList<MdbMovie> movieListObj;
    private ImageAdapter imageAdapter;
    private Context context;
    private OnCompleted listener;
    private String sortOrder;


    public FetchMovies(String sortOrder, int width, ImageAdapter imageAdapter, Context context, OnCompleted listener){
        this.width = width;
        this.imageAdapter = imageAdapter;
        this.context = context;
        this.listener=listener;
        this.sortOrder = sortOrder;
    }

    /**
     * Call to MovieDB API is finished now pass the results to ImageAdaptor and ArrayList with
     * movie info
     * @param result
     */
    @Override
    protected void onPostExecute(ArrayList<MdbMovie> result) {
        this.movieListObj = result;
        if (this.movieListObj != null){
            // update the movie list object in the mainactivity object
            //MainActivity.this.updateMovieListObj(this.movieListObj);
            this.imageAdapter.setWidth(width);
            for (MdbMovie movie : movieListObj){
                this.imageAdapter.add(movie.getPosterUrl());
                //Log.v(LOG_TAG, "MoviePoster: " + movie.getPosterUrl());

            }
            this.listener.onTaskCompleted(movieListObj);
        } else {
            Toast.makeText(context,
                    "Could not connect to Movie Database. Please check your Internet connection.",
                    Toast.LENGTH_LONG).show();

        }
    }


    /**
     * Call the MovieDB API or for favorites query content provider and get the list of movies
     * @param params
     * @return
     */
    @Override
    protected ArrayList<MdbMovie> doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.

        //String[] moviesArray = null;
        ArrayList<MdbMovie> mdbMovieList = new ArrayList<>();


        Log.v(LOG_TAG, "doInBackground - sortorder = " + this.sortOrder);
        if (this.sortOrder.equals("favorites") ) {
            Log.v(LOG_TAG, "doInBackground - favorites selected");
            // user wants to see favorites ... query content provider
            FavoriteSelection favoriteSelection = new FavoriteSelection();
            FavoriteCursor favoriteCursor = favoriteSelection.query(context);

            while (favoriteCursor.moveToNext()) {
                MdbMovie movie = new MdbMovie();
                movie.id = favoriteCursor.getMovieId();
                movie.originalTitle = favoriteCursor.getOriginaltitle();
                movie.description = favoriteCursor.getDescription();
                movie.posterUrl = favoriteCursor.getPoster();
                movie.rating = Double.parseDouble(favoriteCursor.getRated());
                movie.releaseDate = favoriteCursor.getReleasedate();
                movie.detailsRetrieved = true; // always true for movies retrieved from db

                // retrieve the reviews
                movie.reviews = new ArrayList<>();
                ReviewSelection reviewSelection = new ReviewSelection();
                reviewSelection.favoriteMovieId(movie.id);
                ReviewCursor reviewCursor = reviewSelection.query(context);

                while (reviewCursor.moveToNext()) {
                    Review review = new Review(reviewCursor.getContent(),reviewCursor.getAuthor());
                    movie.reviews.add(review);
                }
                reviewCursor.close();

                // retrieve the trailers
                movie.trailers = new ArrayList<>();
                TrailerSelection trailerSelection = new TrailerSelection();
                trailerSelection.favoriteMovieId(movie.id);
                TrailerCursor trailerCursor = trailerSelection.query(context);

                while (trailerCursor.moveToNext()) {
                    Trailer trailer = new Trailer(trailerCursor.getName(),trailerCursor.getSource());
                    movie.trailers.add(trailer);
                }
                trailerCursor.close();

                mdbMovieList.add(movie);

            }
            favoriteCursor.close();


        } else {
            // user wants popular movies or best rated movies from MovieDB

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;



            // Will contain the raw JSON response as a string.
            String moviesJsonString = null;

            try {
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("sort_by", this.sortOrder)
                        .appendQueryParameter("vote_count.gte", "100")
                        .appendQueryParameter("api_key",
                                this.context.getResources().getString(string.movie_db_api_key));

                URL url = new URL(builder.build().toString());

                //Log.v(LOG_TAG, "Built movie URI: " + builder.build().toString());

                // Create the request to moviedb, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // add newlines for readability when debugging json
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonString = buffer.toString();

                //Log.v(LOG_TAG, "- movies: " + moviesJsonString);

                mdbMovieList = getMoviesFromJson(moviesJsonString);
            } catch (IOException | JSONException e) {
                //Log.e(LOG_TAG, "Internet Connection Error when connection to MovieDB ", e);

                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        //Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
        }
        return mdbMovieList;
    }

    protected void onProgressUpdate() {
        //setProgressPercent(progress[0]);
    }


    /**
     * Process String with movie list in JSON Format and pull out the data we need
     */
    private ArrayList<MdbMovie> getMoviesFromJson(String jsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MDB_ID = "id";
        final String MDB_LIST = "results";
        final String MDB_TITLE = "original_title";
        final String MDB_DESCRIPTION = "overview";
        final String MDB_POSTER_PATH = "poster_path";
        final String MDB_RELEASE_DATE = "release_date";
        final String MDB_RATING = "vote_average";

        /* ‘MDB_IMAGE_SIZE’, needs to be:
           "w92", "w154", "w185", "w342", "w500", "w780", or "original". */
        final String MDB_IMAGE_SIZE = "w185";

        JSONObject moviesJson = new JSONObject(jsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(MDB_LIST);


        //String[] resultStrs = new String[moviesArray.length()];
        ArrayList<MdbMovie> mdbMovieList = new ArrayList<>();
        for(int i = 0; i < moviesArray.length(); i++) {

            // Get the JSON object representing the day
            JSONObject jsonMovie = moviesArray.getJSONObject(i);


            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("image.tmdb.org")
                    .appendPath("t")
                    .appendPath("p")
                    .appendPath(MDB_IMAGE_SIZE)
                    //.appendPath("movie")
                    .appendEncodedPath(jsonMovie.getString(MDB_POSTER_PATH));

            //resultStrs[i] =  builder.build().toString();

            MdbMovie movie = new MdbMovie(
                    jsonMovie.getInt(MDB_ID),
                    jsonMovie.getString(MDB_TITLE),
                    builder.build().toString(),
                    jsonMovie.getString(MDB_DESCRIPTION),
                    jsonMovie.getString(MDB_RELEASE_DATE),
                    jsonMovie.getDouble(MDB_RATING),
                    false,
                    null,
                    null);

            //movie = getDetailsForMovie(jsonMovie.getString(MDB_ID), movie);

            mdbMovieList.add(movie);

        }

        Log.v(LOG_TAG, "mdbMovieList: " + mdbMovieList.toString());

        return mdbMovieList;
    }

}