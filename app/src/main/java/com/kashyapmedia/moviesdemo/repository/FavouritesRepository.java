package com.kashyapmedia.moviesdemo.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.kashyapmedia.moviesdemo.App;
import com.kashyapmedia.moviesdemo.AppExecutors;
import com.kashyapmedia.moviesdemo.api.models.GenericListResponse;
import com.kashyapmedia.moviesdemo.api.models.Movie;
import com.kashyapmedia.moviesdemo.api.responses.ApiResponse;
import com.kashyapmedia.moviesdemo.db.AppDatabase;
import com.kashyapmedia.moviesdemo.db.dao.FavouritesDao;
import com.kashyapmedia.moviesdemo.db.dao.MovieDao;
import com.kashyapmedia.moviesdemo.db.entities.FavouritesEntity;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

import java.util.List;

/**
 * Repository that handles Movie objects.
 */
public class FavouritesRepository {
    private static final String TAG = "MovieRepository";


    private AppExecutors appExecutors;
    private FavouritesDao favouritesDao;
    private MovieDao movieDao;

    private static FavouritesRepository _instance;

    public static FavouritesRepository get_instance() {
        if(_instance==null){
            _instance=new FavouritesRepository(AppExecutors.get_instance(), App.getAppDatabase());
        }
        return _instance;
    }

    public FavouritesRepository(AppExecutors appExecutors, AppDatabase appDatabase) {
        this.appExecutors = appExecutors;
        this.favouritesDao = appDatabase.favDao();
        this.movieDao=appDatabase.movieDao();
    }



    public LiveData<Resource<List<MovieEntity>>> getMyFavourites()  {
        return new NetworkBoundResource<List<MovieEntity>,GenericListResponse<Movie>>(appExecutors){

            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                MediatorLiveData<List<MovieEntity>> result=new MediatorLiveData<>();
                LiveData<List<MovieEntity>> favLiveData= favouritesDao.getFavourites();
                result.addSource(favLiveData, new Observer<List<MovieEntity>>() {
                    @Override
                    public void onChanged(List<MovieEntity> movieEntities) {
                        for(MovieEntity movieEntity:movieEntities){
                            movieEntity.isFavourite=true;
                        }
                        result.setValue(movieEntities);
                    }
                });
                return result;
            }

            @Override
            protected boolean shouldFetch(List<MovieEntity> data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<GenericListResponse<Movie>>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(GenericListResponse<Movie> item) {

            }

        }.asLiveData();

    }

    public void removeFavourite(int movieId){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                favouritesDao.deleteById(movieId);

            }
        });
    }
    public void addToFavourites(int movieId){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                FavouritesEntity entity=new FavouritesEntity();
                entity.id=movieId;
                favouritesDao.insertAll(entity);
            }
        });

    }

}
