package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.popularmovies.data.PopularMovie;
import com.example.android.popularmovies.utils.MovieJsonUtils;
import com.example.android.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;

    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessage;
    private ProgressBar mLoadingIndicator;
    private ArrayList<PopularMovie> mMovies;
    private String mSortOrder = "MostPopular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        int numberOfColumns = 2;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        /**
         * This checks the savedInstanceState on Activity screen change.  If movies is already loaded
         * then we dont want to call the API again we just want to use the movies from the parcalArraylist
         * to load the recyclerview.
         */
        if(savedInstanceState == null || !savedInstanceState.containsKey("Movies")) {
            mMovies = null;
            loadMovieData();
        } else {
            mMovies = savedInstanceState.getParcelableArrayList("Movies");
            showMovieData();
            mMovieAdapter.setMovies(mMovies);
        }
    }

    /**
     * This method calls showMovieData() then run the AsycnTask to load the data from the API.
     */
    private void loadMovieData() {
        showMovieData();
        new FetchMovieDataTask().execute();
    }

    //used to save the ArrayList on screen change so that we can just refresh the data instead of calling API.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("Movies", mMovies);
        super.onSaveInstanceState(outState);
    }

    /**
     * This method is called when a movie is selected from the parent screen.  This method uses
     * and Intent to call the detail activity to display information about a single movie.  It
     * uses parcelables to pass the movie data to the detail activity.
     * @param movie - object holding the information about the movie that was selected
     *
     *
     */
    @Override
    public void onClick(PopularMovie movie) {
        Context context = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToShowMovieDetails = new Intent(context, destinationClass);
        intentToShowMovieDetails.putExtra("parcel_data", movie);
        startActivity(intentToShowMovieDetails);

    }

    private void showMovieData() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    // This is the AsyncTask to call the Internet API and get the movie data.
    public class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<PopularMovie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected ArrayList<PopularMovie> doInBackground(String... params) {

            //This builds the URL needed to call the moviedb.org API
            URL movieRequestUrl = NetworkUtils.buildUrl(mSortOrder);

            //This grabs the jsonMovieresponse and then breakd down the data into an Arraylist of objects called mMovies.
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                mMovies = MovieJsonUtils.getMovieTitlesFromJson(jsonMovieResponse);
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        //Once the data is loaded it load the recyclerview adapter with the arraylist of movies.
        @Override
        protected void onPostExecute(ArrayList<PopularMovie> movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (mMovies != null) {
                showMovieData();
                mMovieAdapter.setMovies(mMovies);
            } else {
                showErrorMessage();
            }
        }
    }

    //This inflates the Menu to show the Sort Order Options
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }


    //Based on the selected Sort Order it reloads the movie data by calling loadMovieData() method.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.most_popular:
                mMovieAdapter.setMovies(null);
                mSortOrder = "MostPopular";
                loadMovieData();
                return true;
            case R.id.highest_rated:
                mMovieAdapter.setMovies(null);
                mSortOrder = "HighestRated";
                loadMovieData();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
