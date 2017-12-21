package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cebuc on 12/20/2017.
 */

public class PopularMovie implements Parcelable {

    private String mTitle;
    private String mReleaseDate;
    private String mPosterFileName;
    private double mUserRating;
    private String mOverview;

    public PopularMovie(String title, String releaseDate, String posterFileName, double userRating, String overview) {
        mTitle = title;
        mReleaseDate = releaseDate;
        mPosterFileName = posterFileName;
        mUserRating = userRating;
        mOverview = overview;
    }

    private PopularMovie(Parcel in) {
        this.mTitle = in.readString();
        this.mReleaseDate = in.readString();
        this.mPosterFileName = in.readString();
        this.mUserRating = in.readDouble();
        this.mOverview = in.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }
    public String getImageURL() {
        if (mPosterFileName.contains("http:")) {
            return mPosterFileName;
        }
        return "http://image.tmdb.org/t/p/w185/" + mPosterFileName;
    }
    public double getUserRating() {
        return mUserRating;
    }
    public String getOverview() {
        return mOverview;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mPosterFileName);
        parcel.writeDouble(mUserRating);
        parcel.writeString(mOverview);
    }

    public static final Parcelable.Creator<PopularMovie> CREATOR = new Parcelable.Creator<PopularMovie>() {

        @Override
        public PopularMovie createFromParcel(Parcel in) {
            return new PopularMovie(in);
        }

        @Override
        public PopularMovie[] newArray(int size) {
            return new PopularMovie[size];
        }
    };


}
