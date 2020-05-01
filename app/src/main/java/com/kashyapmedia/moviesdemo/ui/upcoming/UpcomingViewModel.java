package com.kashyapmedia.moviesdemo.ui.upcoming;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.FavouritesRepository;
import com.kashyapmedia.moviesdemo.repository.MovieRepository;
import com.kashyapmedia.moviesdemo.repository.Resource;

import java.util.List;

public class UpcomingViewModel extends ViewModel {
    private static final String TAG = "NowPlayingViewModel";

    private LiveData<Resource<List<MovieEntity>>> movies;


    public LiveData<Resource<List<MovieEntity>>> getMovieData() {
        if (movies == null) {
            movies=MovieRepository.get_instance().getUpcomingMovies();
        }
        return movies;
    }

    public void removeFavourite(int movieId) {
        FavouritesRepository.get_instance().removeFavourite(movieId);
    }
    public void addTofavourites(int movieId) {
        FavouritesRepository.get_instance().addToFavourites(movieId);
    }

}
