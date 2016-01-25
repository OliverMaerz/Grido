package com.olivermaerz.grido;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements GridFragment.OnFragmentInteractionListener {

    private static final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // check for tablets (min 600 dp) and load the detail fragment (dual pane)
            Configuration config = getResources().getConfiguration();
            if ( (config.smallestScreenWidthDp >= 600) && (findViewById(R.id.fragment_container_detail) != null) )  {
                // Create a new Fragment to be placed in the activity layout
                DetailFragment detailFragment = new DetailFragment();

                // In case this activity was started with special instructions from an
                // Intent, pass the Intent's extras to the fragment as arguments
                detailFragment.setArguments(getIntent().getExtras());

                // Add the fragment to the 'fragment_container' FrameLayout
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container_detail, detailFragment).commit();

                // check if in portrait mode and remove padding at the top
                Configuration deviceConfig = getResources().getConfiguration();
                if (deviceConfig.orientation != Configuration.ORIENTATION_LANDSCAPE) {
                    findViewById(R.id.fragment_container_detail).setPadding(0,0,0,0);

                }

                Log.v(LOG_TAG, "dual pane fragment added");

            }

            // Create a new Fragment to be placed in the activity layout
            GridFragment gridFragment = new GridFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            gridFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, gridFragment).commit();






        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }

    // interaction with the fragment ...
    @Override
    public void onFragmentInteraction(MdbMovie movie) {
        // create intent and pass to detail activity
        Intent movieDetailIntent = new Intent(MainActivity.this, DetailActivity.class)
                .putExtra(MdbMovie.EXTRA_MOVIE, movie);

        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp >= 600) {

            // Create a new Fragment to be placed in the activity layout
            DetailFragment detailFragment = new DetailFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            detailFragment.setArguments(movieDetailIntent.getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_detail, detailFragment).commit();

        } else {
            startActivity(movieDetailIntent);
        }



    }
}
