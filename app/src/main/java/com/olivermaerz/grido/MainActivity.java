package com.olivermaerz.grido;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnCompleted{


    public ImageAdapter imageAdapter;
    private ArrayList<Movies> movieListObj;

    // object to hold all configurations
    private Config config;

    // STATE_MOVIES: key in the parcelable to store movie list
    private static final String STATE_MOVIES ="gridoMovies" ;

    // default sort order
    private static final String POPULARITY = "popularity.desc";
    private static final String RATING = "vote_average.desc";

    // STATE_CONFIG: key in parcelable to store sort order
    private static final String STATE_CONFIG = "gridoConfig";

    private static final String LOG_TAG = "Main";

    private GridView gridView;

    private int imageWidth;

    private void resetGrid() {
        this.imageAdapter = new ImageAdapter(this);
        this.gridView = null;
        this.gridView = (GridView) findViewById(R.id.gridview);
        assert this.gridView != null;
        this.gridView.setAdapter(this.imageAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //imageAdapter = new ImageAdapter(this);

        //GridView gridview = (GridView) findViewById(R.id.gridview);
        //gridview.setAdapter(imageAdapter);

        resetGrid();

        /* set the number of columns for different devices */
        float scalefactor = getResources().getDisplayMetrics().density * 150;

        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        //int screenHeight = displaymetrics.heightPixels;

        // calculate the number of columns based on screen width and scale factor
        int columns = (int) ((float) screenWidth / scalefactor);
        // but always minimum 2 columns
        if (columns < 2) {
            columns = 2;
        }

        // set number of columns
        this.gridView.setNumColumns(columns);

        // calculate the image width to be set in image adapter
        this.imageWidth = screenWidth / columns;


        // check if there is a saved instance (so we do not reload the movie date on rotation
        // of the device
        if ((savedInstanceState == null) || !(savedInstanceState.containsKey(STATE_MOVIES)) || !(savedInstanceState.containsKey(STATE_CONFIG))) {
            // instatiate config object and set default config (sort order to popularity)
            config = new Config(POPULARITY);

            // no instance for movie date found - call movie db
            //Log.v(LOG_TAG, "No saved instance - creating ArrayList and fetching movie data");
            movieListObj = new ArrayList<Movies>();
            FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth, imageAdapter, MainActivity.this, this);
            fetchMovies.execute();


        } else {
            // retrieve the config
            config = savedInstanceState.getParcelable(STATE_CONFIG);

            // data found; get parcelable and display data from saved instance
            //Log.v(LOG_TAG, "Saved instance with name " + STATE_MOVIES + " found.");
            movieListObj = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            this.gridView.setAdapter(imageAdapter);
            imageAdapter.setWidth(this.imageWidth);
            for (Movies movie : movieListObj){
                imageAdapter.add(movie.getPosterUrl());
                //Log.v(LOG_TAG, "Recovered MoviePoster: " + movie.getPosterUrl());

            }

        }

        // listener if movie post is clicked to show the details page
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // get data from clicked movie poster and assign to 'movie'
                Movies movie = movieListObj.get(position);

                if (movie == null) {
                    return;
                }

                // create intent and pass to detail activity
                Intent movieDetailIntent = new Intent(MainActivity.this, DetailActivity.class)
                        .putExtra(Movies.EXTRA_MOVIE, movieListObj.get(position));
                startActivity(movieDetailIntent);

            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, movieListObj);
        outState.putParcelable(STATE_CONFIG, config);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_sort_order) {

            // clear the gridView
            resetGrid();

            if (config.sortOrder.equals(POPULARITY)) {
                // sort order was popularity before - now set it to rating:
                config.sortOrder = RATING;
                // change the text of the menu item
                item.setTitle(R.string.sort_by_popularity);
            } else {
                // set sort order to rating:
                config.sortOrder = POPULARITY;
                // change the text of the menu item
                item.setTitle(R.string.sort_by_rating);
            }
            // and reload the MovieDB data with the new sort order
            FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth, this.imageAdapter, MainActivity.this, this);
            fetchMovies.execute();

            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Receive the call back from FetchMovies with the updated movieListObject
     * that contains the list of movies etc.
     */
    @Override
    public void onTaskCompleted(ArrayList<Movies> movieListObj) {
        // callback from AsyncTask returns the movieListObj here
        this.movieListObj = movieListObj;

    }
}
