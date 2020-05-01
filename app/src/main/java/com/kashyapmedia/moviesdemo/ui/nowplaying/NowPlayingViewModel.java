package com.kashyapmedia.moviesdemo.ui.nowplaying;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.repository.FavouritesRepository;
import com.kashyapmedia.moviesdemo.repository.MovieRepository;
import com.kashyapmedia.moviesdemo.repository.Resource;

import java.util.List;

public class NowPlayingViewModel extends ViewModel {
    private static final String TAG = "NowPlayingViewModel";

    private LiveData<Resource<List<MovieEntity>>> movies;


    public LiveData<Resource<List<MovieEntity>>> getMovieData(String lang) {
        if (movies == null) {
            movies=MovieRepository.get_instance().getNowPlayingMovies(lang);
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
