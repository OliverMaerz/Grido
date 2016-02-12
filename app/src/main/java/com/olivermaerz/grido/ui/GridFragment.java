package com.olivermaerz.grido.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.olivermaerz.grido.Config;
import com.olivermaerz.grido.R;
import com.olivermaerz.grido.adapter.ImageAdapter;
import com.olivermaerz.grido.data.FetchMovieDetails;
import com.olivermaerz.grido.data.FetchMovies;
import com.olivermaerz.grido.data.MdbMovie;
import com.olivermaerz.grido.data.OnCompleted;
import com.olivermaerz.grido.data.OnMovieCompleted;
import com.olivermaerz.grido.provider.favorite.FavoriteColumns;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GridFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

public class GridFragment extends Fragment implements OnCompleted, OnMovieCompleted {


    public ImageAdapter imageAdapter;
    private ArrayList<MdbMovie> movieListObj;

    // object to hold all configurations
    private Config config;

    // STATE_MOVIES: key in the parcelable to store movie list
    private static final String STATE_MOVIES ="gridoMovies" ;

    // default sort order
    private static final String POPULARITY = "popularity.desc";
    private static final String RATING = "vote_average.desc";
    private static final String FAVORITES = "favorites";

    // STATE_CONFIG: key in parcelable to store sort order
    private static final String STATE_CONFIG = "gridoConfig";

    private static final String LOG_TAG = "GridFragment";

    private GridView gridView;
    //= (GridView) this.view.findViewById(R.id.gridview);
    private View view;

    private int imageWidth;

    private OnFragmentInteractionListener mListener;

    private Activity myActivity;

    private int position;


    public GridFragment() {
    }

    public GridFragment outer(){
        return GridFragment.this;
    }

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

        int screenWidth;
        Configuration deviceConfig = getResources().getConfiguration();
        if ((deviceConfig.smallestScreenWidthDp >= 600) && (deviceConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)) {
            // device is tablet show dual pane with landscape width = 1/3 grid fragment and 2/3 detail fragment
            screenWidth = displaymetrics.widthPixels / 5 * 2;
        } else {
            screenWidth = displaymetrics.widthPixels;
        }
        resetGrid();

        //int viewWidth = this.gridView.getWidth();

        // calculate the number of columns based on screen width and scale factor
        int columns = (int) ((float) screenWidth / scaleFactor);
        // but always minimum 2 columns
        if (columns < 2) columns = 2;



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
            FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth,
                    imageAdapter, this.myActivity, this );
            fetchMovies.execute();


        } else {
            // retrieve the config
            config = savedInstanceState.getParcelable(STATE_CONFIG);

            // data found; get parcelable and display data from saved instance
            Log.v(LOG_TAG, "Saved instance with name " + STATE_MOVIES + " found.");
            movieListObj = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
            this.gridView.setAdapter(imageAdapter);
            imageAdapter.setWidth(this.imageWidth);
            for (MdbMovie movie : movieListObj){
                imageAdapter.add(movie.getPosterUrl());
                Log.v(LOG_TAG, "Recovered MoviePoster: " + movie.getPosterUrl());

            }

        }


        // listener if movie poster is clicked to show the details view
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id) {

                // get data from clicked movie poster and assign to 'movie'
                MdbMovie movie = movieListObj.get(pos);

                if (movie == null) {
                    return;
                }


                // check if movie details have been retrieved already

                if (movie.detailsRetrieved == true){
                    // moviedetails present ... show fragment for details
                    Log.v(LOG_TAG, "movie details are already present - call detail view");
                    if (mListener != null) {
                        mListener.onFragmentInteraction(movieListObj.get(pos));
                    }
                } else {
                    Log.v(LOG_TAG, "movie details need to be retrieved for position: " + pos);
                    //set current position
                    position = pos;

                    // need to retrieve movie details first
                    FetchMovieDetails fetchMovieDetails = new FetchMovieDetails(
                            myActivity, outer(), movie);
                    fetchMovieDetails.execute();


                }




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
        super.onCreateOptionsMenu(menu, inflater);

        // check what menu option is currently selected
        if (config.sortOrder.equals(FAVORITES)) {
            menu.findItem(R.id.show_favorites).setChecked(true);
        } else if (config.sortOrder.equals(RATING)) {
            menu.findItem(R.id.sort_by_rating).setChecked(true);
        } // most popular is the default in the menu resource - so no need to check for it here
    }








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_by_popularity) {
            if (!config.sortOrder.equals(POPULARITY)) {
                item.setChecked(true);
                resetGrid();
                config.sortOrder = POPULARITY;
                // reload the MovieDB data with the new sort order
                FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth,
                        this.imageAdapter, this.myActivity, this);
                fetchMovies.execute();
            }
        } else if (id == R.id.sort_by_rating) {
            if (!config.sortOrder.equals(RATING)) {
                item.setChecked(true);
                resetGrid();
                config.sortOrder = RATING;
                // reload the MovieDB data with the new sort order
                FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth,
                        this.imageAdapter, this.myActivity, this);
                fetchMovies.execute();
            }

        } else if (id == R.id.show_favorites) {
            if (!config.sortOrder.equals(FAVORITES)) {

                // check if there are favorites stored

                Cursor cursor = getContext().getContentResolver().query(
                        FavoriteColumns.CONTENT_URI, null, null, null, null);

                if (!(cursor.moveToFirst()) || cursor.getCount() ==0){
                    Toast.makeText(getActivity(), R.string.no_favorites_found,
                            Toast.LENGTH_LONG).show();
                } else {
                    // favorites exist
                    item.setChecked(true);
                    resetGrid();
                    config.sortOrder = FAVORITES;
                    FetchMovies fetchMovies = new FetchMovies(config.sortOrder, this.imageWidth,
                            this.imageAdapter, this.myActivity, this);
                    fetchMovies.execute();
                }
                cursor.close();


            }

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * Receive the call back from FetchMovies with the updated movieListObject
     * that contains the list of movies etc.
     */
    @Override
    public void onTaskCompleted(ArrayList<MdbMovie> movieListObj) {
        // callback from AsyncTask returns the movieListObj here
        this.movieListObj = movieListObj;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        if (context instanceof OnFragmentInteractionListener) {
            this.mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onMovieDetailTaskCompleted(MdbMovie movie) {


        movieListObj.set(this.position,movie);
        if (mListener != null) {
            mListener.onFragmentInteraction(movie);
        }

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
        void onFragmentInteraction(MdbMovie movie);
    }

    private void resetGrid() {
        this.imageAdapter = new ImageAdapter(this.myActivity);
        this.gridView = null;
        this.gridView = (GridView) this.view.findViewById(R.id.grid_fragment);
        assert this.gridView != null;
        this.gridView.setAdapter(this.imageAdapter);
    }


}
