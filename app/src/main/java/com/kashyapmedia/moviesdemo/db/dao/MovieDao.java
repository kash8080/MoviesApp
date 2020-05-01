package com.kashyapmedia.moviesdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM MovieEntity WHERE now_playing == 1 ORDER BY title ASC")
    LiveData<List<MovieEntity>> getNowPlaying();

    @Query("SELECT * FROM MovieEntity WHERE upcoming == 1 ORDER BY title ASC")
    LiveData<List<MovieEntity>> getUpcoming();

    @Query("SELECT * FROM MovieEntity WHERE id == :id LIMIT 1")
    LiveData<MovieEntity> findMovieById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieEntity... category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MovieEntity> category);

    @Delete
    void delete(MovieEntity category);

    @Query("DELETE FROM MovieEntity")
    void deleteAll();


    @Update
    void update(MovieEntity data);

}
