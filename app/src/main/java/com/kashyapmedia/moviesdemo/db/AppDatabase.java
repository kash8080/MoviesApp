package com.kashyapmedia.moviesdemo.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kashyapmedia.moviesdemo.db.dao.FavouritesDao;
import com.kashyapmedia.moviesdemo.db.dao.MovieDao;
import com.kashyapmedia.moviesdemo.db.dao.ProfileDao;
import com.kashyapmedia.moviesdemo.db.entities.FavouritesEntity;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;
import com.kashyapmedia.moviesdemo.db.entities.ProfileEntity;


@Database(entities = {MovieEntity.class, FavouritesEntity.class, ProfileEntity.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    public abstract FavouritesDao favDao();

    public abstract ProfileDao profileDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context, AppDatabase.class, "room_database")
                    //.allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
