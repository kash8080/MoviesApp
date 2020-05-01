package com.kashyapmedia.moviesdemo.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kashyapmedia.moviesdemo.db.entities.ProfileEntity;
import com.kashyapmedia.moviesdemo.repository.MovieRepository;
import com.kashyapmedia.moviesdemo.repository.ProfileRepository;
import com.kashyapmedia.moviesdemo.repository.Resource;

public class MainActViewModel extends ViewModel {
    private static final String TAG = "NowPlayingViewModel";

    private LiveData<Resource<ProfileEntity>> profile;


    public LiveData<Resource<ProfileEntity>> getProfileData() {
        if (profile == null) {
            profile = ProfileRepository.get_instance().getMyProfile();
        }
        return profile;
    }


    public void saveProfileData(String name,String email) {
        ProfileRepository.get_instance().updateProfile(name,email);
    }

    public void resetLocalData(){
        MovieRepository.get_instance().resetLocalData();
    }

}
