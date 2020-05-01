package com.kashyapmedia.moviesdemo.ui.moviedetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.FavouritesRepository;
import com.kashyapmedia.moviesdemo.repository.MovieRepository;
import com.kashyapmedia.moviesdemo.repository.Resource;

public class MovieDetailViewModel extends ViewModel {
    private static final String TAG = "NowPlayingViewModel";

    private LiveData<Resource<MovieEntity>> movieData;


    public LiveData<Resource<MovieEntity>> getMovieData(int movieId) {
        if (movieData == null) {
            movieData=MovieRepository.get_instance().getMovieDetails(movieId);
        }
        return movieData;
    }

    public void removeFavourite(int movieId) {
        FavouritesRepository.get_instance().removeFavourite(movieId);
    }
    public void addTofavourites(int movieId) {
        FavouritesRepository.get_instance().addToFavourites(movieId);
    }

}
