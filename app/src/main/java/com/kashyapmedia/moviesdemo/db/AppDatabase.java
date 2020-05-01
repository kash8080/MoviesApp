package com.kashyapmedia.moviesdemo.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kashyapmedia.moviesdemo.db.dao.MovieDao;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;


@Database(entities = {MovieEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

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
