package com.example.android.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies.data.PopularMovie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cebuc on 12/16/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {

    private ArrayList<PopularMovie> mMovies;
    private Context mContext;


    private final MovieAdapterOnClickHandler mClickHander;

    public interface MovieAdapterOnClickHandler {
        void onClick(PopularMovie movie);
    }

    public MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHander = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMovieImageView;


        public MovieAdapterViewHolder(View view) {
            super(view);
            mMovieImageView = (ImageView) view.findViewById(R.id.iv_movie_img);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            PopularMovie movie = new PopularMovie(
                                mMovies.get(adapterPosition).getTitle(),
                                mMovies.get(adapterPosition).getReleaseDate(),
                                mMovies.get(adapterPosition).getImageURL(),
                                mMovies.get(adapterPosition).getUserRating(),
                                mMovies.get(adapterPosition).getOverview());
            mClickHander.onClick(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.movie_grid_items;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder movieAdapterViewHolder, int position) {

        Picasso.with(mContext).load(mMovies.get(position).getImageURL()).into(movieAdapterViewHolder.mMovieImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.size();
    }

    public void setMovies(ArrayList<PopularMovie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }
}
