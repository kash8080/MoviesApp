package com.kashyapmedia.moviesdemo.repository;

import androidx.lifecycle.LiveData;

import com.kashyapmedia.moviesdemo.App;
import com.kashyapmedia.moviesdemo.AppExecutors;
import com.kashyapmedia.moviesdemo.api.ApiKeys;
import com.kashyapmedia.moviesdemo.api.MovieService;
import com.kashyapmedia.moviesdemo.api.models.GenericListResponse;
import com.kashyapmedia.moviesdemo.api.models.Movie;
import com.kashyapmedia.moviesdemo.api.responses.ApiResponse;
import com.kashyapmedia.moviesdemo.db.dao.MovieDao;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository that handles Movie objects.
 */
public class MovieRepository {
    private static final String TAG = "MovieRepository";


    private AppExecutors appExecutors;
    private MovieDao movieDao;
    private MovieService movieService;

    private static MovieRepository _instance;

    public static MovieRepository get_instance() {
        if(_instance==null){
            _instance=new MovieRepository(AppExecutors.get_instance(), App.getAppDatabase().movieDao(),App.getMovieService());
        }
        return _instance;
    }

    public MovieRepository(AppExecutors appExecutors, MovieDao movieDao, MovieService movieService) {
        this.appExecutors = appExecutors;
        this.movieDao = movieDao;
        this.movieService = movieService;
    }


    public LiveData<Resource<MovieEntity>> getMovieDetails(int movieId )  {
        return new NetworkBoundResource<MovieEntity,Movie>(appExecutors){

            @Override
            protected LiveData<MovieEntity> loadFromDb() {
                return movieDao.findMovieById(movieId);
            }

            @Override
            protected boolean shouldFetch(MovieEntity data) {
                return data==null;
            }

            @Override
            protected LiveData<ApiResponse<Movie>> createCall() {
                return movieService.getMovieDetails(movieId, ApiKeys.MOVIEAPIKEY);
            }

            @Override
            protected void saveCallResult(Movie item) {
                movieDao.insertAll(ModelsToEntities.changeMovieEntity(item));
            }

        }.asLiveData();

    }

    public LiveData<Resource<List<MovieEntity>>> getNowPlayingMovies()  {
        return new NetworkBoundResource<List<MovieEntity>,GenericListResponse<Movie>>(appExecutors){

            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return movieDao.getAll();
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return data==null || data.size()==0;
            }

            @Override
            protected LiveData<ApiResponse<GenericListResponse<Movie>>> createCall() {
                return movieService.getNowPlayingMovies(ApiKeys.MOVIEAPIKEY);
            }

            @Override
            protected void saveCallResult(GenericListResponse<Movie> item) {
                List<MovieEntity> l=new ArrayList<>();
                for(Movie movie:item.getResults()){
                    l.add(ModelsToEntities.changeMovieEntity(movie));
                }
                movieDao.insertAll(l);
            }

        }.asLiveData();

    }

}
