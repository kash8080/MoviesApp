package com.kashyapmedia.moviesdemo.repository;

import android.util.Log;

import com.kashyapmedia.moviesdemo.api.models.Movie;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

public class ModelsToEntities {
    private static final String TAG = "ModelsToEntities";

    public static MovieEntity changeMovieEntity(Movie movie){
        Log.d(TAG, "changeMovieEntity: "+movie.getOverview());
        MovieEntity movieEntity=new MovieEntity();
        movieEntity.id=movie.getId();
        movieEntity.budget=movie.getBudget();
        movieEntity.revenue=movie.getRevenue();
        movieEntity.title=movie.getTitle();
        movieEntity.overview=movie.getOverview();
        movieEntity.poster_path=movie.getPoster_path();
        movieEntity.backdrop_path=movie.getBackdrop_path();
        movieEntity.release_date=movie.getRelease_date();
        movieEntity.status=movie.getStatus();
        movieEntity.vote_average=movie.getVote_average();
        movieEntity.vote_count=movie.getVote_count();
        movieEntity.adult=movie.isAdult();
        return movieEntity;
    }
}
