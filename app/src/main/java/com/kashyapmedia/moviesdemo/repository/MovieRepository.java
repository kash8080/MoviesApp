package com.kashyapmedia.moviesdemo.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.kashyapmedia.moviesdemo.App;
import com.kashyapmedia.moviesdemo.AppExecutors;
import com.kashyapmedia.moviesdemo.api.ApiKeys;
import com.kashyapmedia.moviesdemo.api.MovieService;
import com.kashyapmedia.moviesdemo.api.models.GenericListResponse;
import com.kashyapmedia.moviesdemo.api.models.Movie;
import com.kashyapmedia.moviesdemo.api.responses.ApiResponse;
import com.kashyapmedia.moviesdemo.db.AppDatabase;
import com.kashyapmedia.moviesdemo.db.dao.FavouritesDao;
import com.kashyapmedia.moviesdemo.db.dao.MovieDao;
import com.kashyapmedia.moviesdemo.db.entities.FavouritesEntity;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Repository that handles Movie objects.
 */
public class MovieRepository {
    private static final String TAG = "MovieRepository";


    private AppExecutors appExecutors;
    private MovieDao movieDao;
    private FavouritesDao favouritesDao;
    private MovieService movieService;

    private static MovieRepository _instance;

    public static MovieRepository get_instance() {
        if(_instance==null){
            _instance=new MovieRepository(AppExecutors.get_instance(), App.getAppDatabase(),App.getMovieService());
        }
        return _instance;
    }

    public MovieRepository(AppExecutors appExecutors, AppDatabase appDatabase, MovieService movieService) {
        this.appExecutors = appExecutors;
        this.movieDao = appDatabase.movieDao();
        this.favouritesDao=appDatabase.favDao();
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

    public LiveData<Resource<List<MovieEntity>>> getNowPlayingMovies(String lang)  {
        return new NetworkBoundResource<List<MovieEntity>,GenericListResponse<Movie>>(appExecutors){

            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return getMoviesWithFavs(movieDao.getNowPlaying());

            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return data==null || data.size()==0;
            }

            @Override
            protected LiveData<ApiResponse<GenericListResponse<Movie>>> createCall() {
                return movieService.getNowPlayingMovies(ApiKeys.MOVIEAPIKEY,lang);
            }

            @Override
            protected void saveCallResult(GenericListResponse<Movie> item) {
                List<MovieEntity> l=new ArrayList<>();
                MovieEntity entity;
                for(Movie movie:item.getResults()){
                    entity=ModelsToEntities.changeMovieEntity(movie);
                    entity.now_playing=true;
                    l.add(entity);
                }
                movieDao.insertAll(l);
            }

        }.asLiveData();

    }

    public LiveData<Resource<List<MovieEntity>>> getUpcomingMovies(String lang)  {
        return new NetworkBoundResource<List<MovieEntity>,GenericListResponse<Movie>>(appExecutors){

            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return getMoviesWithFavs(movieDao.getUpcoming());
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return data==null || data.size()==0;
            }

            @Override
            protected LiveData<ApiResponse<GenericListResponse<Movie>>> createCall() {
                return movieService.getUpcomingMovies(ApiKeys.MOVIEAPIKEY,lang);
            }

            @Override
            protected void saveCallResult(GenericListResponse<Movie> item) {
                List<MovieEntity> l=new ArrayList<>();
                MovieEntity entity;
                for(Movie movie:item.getResults()){
                    entity=ModelsToEntities.changeMovieEntity(movie);
                    entity.upcoming=true;
                    l.add(entity);
                }
                movieDao.insertAll(l);
            }

        }.asLiveData();

    }

    private LiveData<List<MovieEntity>> getMoviesWithFavs(LiveData<List<MovieEntity>> liveData){
        //return liveData;

        MediatorLiveData<List<MovieEntity>> mediatorLiveData=new MediatorLiveData<>();
        LiveData<List<FavouritesEntity>> favs=favouritesDao.getFavouriteIds();
        mediatorLiveData.addSource(liveData, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(List<MovieEntity> movieEntities) {
                Log.d(TAG, "onChanged: list changed");
                if(favs.getValue()==null){
                    mediatorLiveData.setValue(movieEntities);
                    return;
                }
                HashSet<Integer> sets=new HashSet<>();
                for(FavouritesEntity favouritesEntity:favs.getValue()){
                    sets.add(favouritesEntity.id);
                }
                MovieEntity entity;
                for(int i=0;i<movieEntities.size();i++){
                    entity=movieEntities.get(i);
                    if(sets.contains(entity.id)){
                        entity.isFavourite=true;
                    }else{
                        entity.isFavourite=false;
                    }
                }
                mediatorLiveData.setValue(movieEntities);
            }
        });
        mediatorLiveData.addSource(favs, new Observer<List<FavouritesEntity>>() {
            @Override
            public void onChanged(List<FavouritesEntity> favouritesEntities) {
                Log.d(TAG, "onChanged: fav list  changed");
                List<MovieEntity> cur=mediatorLiveData.getValue();
                if(cur==null){
                    return;
                }
                HashSet<Integer> sets=new HashSet<>();
                for(FavouritesEntity favouritesEntity:favouritesEntities){
                    sets.add(favouritesEntity.id);
                }
                MovieEntity entity;
                for(int i=0;i<cur.size();i++){
                    entity=cur.get(i);
                    if(sets.contains(entity.id)){
                        entity.isFavourite=true;
                    }else{
                        entity.isFavourite=false;
                    }
                }
                mediatorLiveData.setValue(cur);
            }
        });
        return mediatorLiveData;
    }

    public void resetLocalData(){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                movieDao.deleteAll();
            }
        });
    }

}
