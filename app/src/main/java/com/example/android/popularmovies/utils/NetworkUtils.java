package com.example.android.popularmovies.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by cebuc on 12/16/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIEDB_URL = "https://api.themoviedb.org/3/movie/";
    private static final String MOVIE_LOOKUP_POPLAR = "popular";
    private static final String MOVIE_LOOKUP_TOP_RATED = "top_rated";
    private static String mMovieBaseUrl;

    final static String API_PARAM = "api_key";
    final static String API_KEY = "????";  //REMOVE BEFORE SUBMIT //MUST SUPPLY YOUR OWN KEY

    public static URL buildUrl(String sortOrder) {

        if (sortOrder.equals("HighestRated")) {
            mMovieBaseUrl = MOVIEDB_URL + MOVIE_LOOKUP_TOP_RATED;
        } else {
            mMovieBaseUrl = MOVIEDB_URL + MOVIE_LOOKUP_POPLAR;
        }
        Uri builtUri = Uri.parse(mMovieBaseUrl).buildUpon()
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
