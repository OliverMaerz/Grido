package com.olivermaerz.grido.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
 * AsyncTask to fetch additonal movie details from MovieDB
 *
 */
public class FetchMovieDetails extends AsyncTask<String, String, MdbMovie> {

    private static final String LOG_TAG = "Fetch Movie Details";
    private MdbMovie movieObj;
    private Context context;
    private OnMovieCompleted listener;


    public FetchMovieDetails(Context context, OnMovieCompleted listener, MdbMovie movie){
        this.context = context;
        this.listener=listener;
        this.movieObj = movie;

        Log.v(LOG_TAG, " FetchMovieDetails(Context context, OnMovieCompleted listener, MdbMovie movie) " + this.movieObj.toString());
    }

    /**
     * Call to MovieDB API is finished now pass the results
     * @param result
     */
    @Override
    protected void onPostExecute(MdbMovie result) {
        this.movieObj = result;
        if (this.movieObj != null){

            this.listener.onMovieDetailTaskCompleted(movieObj);
        } else {
            Toast.makeText(context,
                    "Could not connect to Movie Database. Please check your Internet connection.",
                    Toast.LENGTH_LONG).show();

        }
    }


    /**
     * Call the MovieDB API and get movie details
     * @param params
     * @return
     */
    @Override
    protected MdbMovie doInBackground(String... params) {


        Log.v(LOG_TAG, " doInBackground " + this.movieObj.toString());
        this.movieObj = getDetailsForMovie(this.movieObj);
        this.movieObj.detailsRetrieved = true;


        return this.movieObj;
    }

    protected void onProgressUpdate() {
        //setProgressPercent(progress[0]);
    }









    /**
     * Call MovieDB API and get reviews for movie
     */

    private MdbMovie getDetailsForMovie(MdbMovie movie) {

        String movieId = String.valueOf(movie.id);
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
                    //jsonMovie.getString(REVIEW_ID),
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
                    jsonMovie.getString(TRAILER_SOURCE)));

        }
        Log.v(LOG_TAG, "trailerList: " + trailerList.toString());

        movie.reviews = reviewList;
        movie.trailers = trailerList;

        return movie;

    }



}