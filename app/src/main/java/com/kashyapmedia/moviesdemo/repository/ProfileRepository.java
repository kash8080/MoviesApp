package com.kashyapmedia.moviesdemo.repository;

import androidx.lifecycle.LiveData;

import com.kashyapmedia.moviesdemo.App;
import com.kashyapmedia.moviesdemo.AppExecutors;
import com.kashyapmedia.moviesdemo.api.responses.ApiResponse;
import com.kashyapmedia.moviesdemo.db.AppDatabase;
import com.kashyapmedia.moviesdemo.db.dao.ProfileDao;
import com.kashyapmedia.moviesdemo.db.entities.ProfileEntity;

/**
 * Repository that handles Movie objects.
 */
public class ProfileRepository {
    private static final String TAG = "MovieRepository";


    private AppExecutors appExecutors;
    private ProfileDao profileDao;

    private static ProfileRepository _instance;

    public static ProfileRepository get_instance() {
        if(_instance==null){
            _instance=new ProfileRepository(AppExecutors.get_instance(), App.getAppDatabase());
        }
        return _instance;
    }

    public ProfileRepository(AppExecutors appExecutors, AppDatabase appDatabase) {
        this.appExecutors = appExecutors;
        this.profileDao = appDatabase.profileDao();
    }



    public LiveData<Resource<ProfileEntity>> getMyProfile()  {
        return new NetworkBoundResource<ProfileEntity,Void>(appExecutors){

            @Override
            protected LiveData<ProfileEntity> loadFromDb() {
                return profileDao.getMyProfile();
            }

            @Override
            protected boolean shouldFetch(ProfileEntity data) {
                return false;
            }

            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return null;
            }

            @Override
            protected void saveCallResult(Void item) {

            }


        }.asLiveData();

    }

    public void updateProfile(String name,String email){
        appExecutors.getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                ProfileEntity profileEntity=new ProfileEntity();
                profileEntity.id=1;
                profileEntity.name=name;
                profileEntity.email=email;
                profileDao.insertAll(profileEntity);
            }
        });


    }

}
