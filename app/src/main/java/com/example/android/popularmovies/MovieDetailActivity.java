package com.example.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.PopularMovie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private TextView mDetailMovieTitle;
    private ImageView mDetailMovieImg;
    private TextView mDetailUserRating;
    private TextView mDetailOverview;
    private TextView mDetailReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mDetailMovieImg = (ImageView) findViewById(R.id.iv_detail_movie_img);
        mDetailMovieTitle = (TextView) findViewById(R.id.tv_detail_movie_title);
        mDetailUserRating = (TextView) findViewById(R.id.tv_detail_user_rating);
        mDetailReleaseDate = (TextView) findViewById(R.id.tv_detail_release_date);
        mDetailOverview = (TextView) findViewById(R.id.tv_detail_overview);

        Context context = this;
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity.hasExtra("parcel_data")) {
            PopularMovie movie = (PopularMovie) intentThatStartedThisActivity.getParcelableExtra("parcel_data");
            Picasso.with(context).load(movie.getImageURL()).into(mDetailMovieImg);
            mDetailMovieTitle.setText(movie.getTitle());
            mDetailUserRating.setText(Double.toString(movie.getUserRating()));
            mDetailReleaseDate.setText(movie.getReleaseDate());
            mDetailOverview.setText(movie.getOverview());
        }
    }
}
