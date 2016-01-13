package com.olivermaerz.grido;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

public class GridFragment extends Fragment implements OnCompleted {


    public GridFragment() {
    }

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

    private static final String LOG_TAG = "GridFragment";

    private GridView gridView;
    //= (GridView) this.view.findViewById(R.id.gridview);
    private View view;

    private int imageWidth;

    private OnFragmentInteractionListener mListener;

    private Activity myActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */

        Log.v(LOG_TAG, "onCreate() called");





    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.myActivity = getActivity();

        /* set the number of columns for different devices */
        float scaleFactor = getResources().getDisplayMetrics().density * 150;

        DisplayMetrics displaymetrics = new DisplayMetrics();

        this.myActivity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;

        // calculate the number of columns based on screen width and scale factor
        int columns = (int) ((float) screenWidth / scaleFactor);
        // but always minimum 2 columns
        if (columns < 2) columns = 2;

        resetGrid();

        // set number of columns
        this.gridView.setNumColumns(columns);

        // calculate the image width to be set in image adapter
        this.imageWidth = screenWidth / columns;



        // check if there is a saved instance (so we do not reload the movie data on rotation
        // of the device
        if ( (savedInstanceState == null)
                || !(savedInstanceState.containsKey(STATE_MOVIES))
                || !(savedInstanceState.containsKey(STATE_CONFIG)) ) {
            // instantiate config object and set default config (sort order to popularity)
            config = new Config(POPULARITY);

            // no instance for movie date found - call movie db
            Log.v(LOG_TAG, "No saved instance - creating ArrayList and fetching movie data");
            movieListObj = new ArrayList<>();
            FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth, imageAdapter, this.myActivity, this );
            fetchMovies.execute();


        } else {
            // retrieve the config
            config = savedInstanceState.getParcelable(STATE_CONFIG);

            // data found; get parcelable and display data from saved instance
            Log.v(LOG_TAG, "Saved instance with name " + STATE_MOVIES + " found.");
            movieListObj = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            this.gridView.setAdapter(imageAdapter);
            imageAdapter.setWidth(this.imageWidth);
            for (Movies movie : movieListObj){
                imageAdapter.add(movie.getPosterUrl());
                Log.v(LOG_TAG, "Recovered MoviePoster: " + movie.getPosterUrl());

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

                mListener.onFragmentInteraction(movieListObj.get(position));

                // create intent and pass to detail activity
                /* TODO: Intent movieDetailIntent = new Intent(myActivity, DetailActivity.class)
                        .putExtra(Movies.EXTRA_MOVIE, movieListObj.get(position));
                startActivity(movieDetailIntent);*/

            }
        });



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(LOG_TAG, "onCreateView() called");
        // Inflate the layout for this fragment
        this.view  = inflater.inflate(R.layout.fragment_grid, container, false);




        // for access to toolbar
        setHasOptionsMenu(true);



        return this.view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_MOVIES, movieListObj);
        outState.putParcelable(STATE_CONFIG, config);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //myActivity.getMenuInflater().inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.v(LOG_TAG, "Menu item clicked: " + id + " R.id.change_sort_order would be " + R.id.change_sort_order);

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
            FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth, this.imageAdapter, this.myActivity, this);
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

    //public void onButtonPressed(Uri uri) {
    //    if (mListener != null) {
    //        mListener.onFragmentInteraction(uri);
    //    }
    //}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Movies movie);
    }

    private void resetGrid() {
        this.imageAdapter = new ImageAdapter(this.myActivity);
        this.gridView = null;
        this.gridView = (GridView) this.view.findViewById(R.id.grid_fragment);
        assert this.gridView != null;
        this.gridView.setAdapter(this.imageAdapter);
    }


}
