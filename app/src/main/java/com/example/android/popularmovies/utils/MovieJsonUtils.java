package com.example.android.popularmovies.utils;

import android.text.TextUtils;

import com.example.android.popularmovies.data.PopularMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by cebuc on 12/16/2017.
 */

public class MovieJsonUtils {

    public static ArrayList<PopularMovie> getMovieTitlesFromJson(String jsonString) throws JSONException {

        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }

        ArrayList<PopularMovie> movies = new ArrayList<>();

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonString);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");


            for (int cntr = 0; cntr < resultsArray.length(); cntr++) {
                JSONObject movieDetailsObj = resultsArray.getJSONObject(cntr);
                String title = movieDetailsObj.getString("original_title");
                String releaseDate = movieDetailsObj.getString("release_date");
                String posterFileName = movieDetailsObj.getString("poster_path");
                double userRating = movieDetailsObj.getDouble("vote_average");
                String overview = movieDetailsObj.getString("overview");

                movies.add(new PopularMovie(title,releaseDate,posterFileName,userRating,overview));
            }
        }  catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

}
