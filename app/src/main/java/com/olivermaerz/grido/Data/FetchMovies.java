package com.olivermaerz.grido.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.olivermaerz.grido.adapter.ImageAdapter;
import com.olivermaerz.grido.provider.favorite.FavoriteCursor;
import com.olivermaerz.grido.provider.favorite.FavoriteSelection;

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

            //Cursor favoriteCursor = context.getContentResolver().query(FavoriteColumns.CONTENT_URI,null,null,null,null);

            while (favoriteCursor.moveToNext()) {
                MdbMovie movie = new MdbMovie();
                movie.id = favoriteCursor.getMovieId();
                movie.originalTitle = favoriteCursor.getOriginaltitle();
                movie.description = favoriteCursor.getDescription();
                movie.posterUrl = favoriteCursor.getPoster();
                movie.rating = Double.parseDouble(favoriteCursor.getRated());
                movie.releaseDate = favoriteCursor.getReleasedate();

                movie.reviews = new ArrayList<>();
                movie.trailers = new ArrayList<>();

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
                    true,
                    null,
                    null);

            movie = getDetailsForMovie(jsonMovie.getString(MDB_ID), movie);

            mdbMovieList.add(movie);

        }

        Log.v(LOG_TAG, "mdbMovieList: " + mdbMovieList.toString());

        return mdbMovieList;

    }

    /**
     * Call MovieDB API and get reviews for movie
     */

    private MdbMovie getDetailsForMovie(String movieId, MdbMovie movie) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieDetailsJsonString;

        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(movieId)
                    //.appendPath("reviews")
                    .appendQueryParameter("api_key",
                            this.context.getResources().getString(string.movie_db_api_key))
                    .appendQueryParameter("append_to_response", "reviews,trailers");

            URL url = new URL(builder.build().toString());

            Log.v(LOG_TAG, "Built URI: " + builder.build().toString());

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
            movieDetailsJsonString = buffer.toString();

            movie = getMovieDetailsFromJson(movieDetailsJsonString, movie);
            Log.v(LOG_TAG, "- getDetailsForMovie: " + movieDetailsJsonString);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Internet Connection Error when connection to MovieDB ", e);
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, "JSONException Error when parsing movie details ", e);
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
        return movie;
    }


    /**
     * Process String with movie details in JSON Format and pull out the data like reviews, trailers
     */
    private MdbMovie getMovieDetailsFromJson(String jsonStr, MdbMovie movie) throws JSONException {

        Log.v(LOG_TAG, "- getMovieDetailsFromJson: " + jsonStr);

        // These are the names of the JSON objects that need to be extracted.
        final String REVIEW_TAG = "reviews";
        final String REVIEW_LIST = "results";
        final String REVIEW_ID = "id";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";

        final String TRAILER_LIST = "trailers";
        final String TRAILER_YOUTUBE = "youtube";
        final String TRAILER_NAME = "name";
        final String TRAILER_SIZE = "size";
        final String TRAILER_SOURCE = "source";

        JSONObject movieDetailsJsonObject = new JSONObject(jsonStr);
        // now get the child elements of the "reviews" element as JSONObject
        //JSONObject reviews = movieDetailsJsonObject.getJSONObject(REVIEW_TAG);
        JSONArray results = movieDetailsJsonObject.getJSONObject(REVIEW_TAG).getJSONArray(REVIEW_LIST);

        ArrayList<Review> reviewList = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {

            // Get the JSON object
            JSONObject jsonMovie = results.getJSONObject(i);

            Log.v(LOG_TAG, "while loop review: " + jsonMovie.getString(REVIEW_ID));

            reviewList.add(new Review(
                    jsonMovie.getString(REVIEW_ID),
                    jsonMovie.getString(REVIEW_AUTHOR),
                    jsonMovie.getString(REVIEW_CONTENT)));
        }



        Log.v(LOG_TAG, "reviewList: " + reviewList.toString());

        // now do the same for trailers (TODO: refactor into separate method that handles both)
        JSONArray trailerJsonArray = movieDetailsJsonObject.getJSONObject(TRAILER_LIST).getJSONArray(TRAILER_YOUTUBE);

        ArrayList<Trailer> trailerList = new ArrayList<>();
        for(int i = 0; i < trailerJsonArray.length(); i++) {

            // Get the JSON object
            JSONObject jsonMovie = trailerJsonArray.getJSONObject(i);

            trailerList.add(new Trailer(
                    jsonMovie.getString(TRAILER_NAME),
                    jsonMovie.getString(TRAILER_SIZE),
                    jsonMovie.getString(TRAILER_SOURCE)));

        }
        Log.v(LOG_TAG, "trailerList: " + trailerList.toString());

        movie.reviews = reviewList;
        movie.trailers = trailerList;

        return movie;

    }



}