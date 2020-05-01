package com.kashyapmedia.moviesdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kashyapmedia.moviesdemo.db.entities.ProfileEntity;


@Dao
public interface ProfileDao {

    @Query("SELECT * FROM ProfileEntity WHERE id == 1 LIMIT 1")
    LiveData<ProfileEntity> getMyProfile();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ProfileEntity... category);

}
