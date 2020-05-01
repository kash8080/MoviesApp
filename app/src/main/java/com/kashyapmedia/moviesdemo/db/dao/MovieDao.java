package com.kashyapmedia.moviesdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM MovieEntity ORDER BY title ASC")
    LiveData<List<MovieEntity>> getAll();

    @Query("SELECT * FROM MovieEntity WHERE id == :id LIMIT 1")
    LiveData<MovieEntity> findMovieById(int id);

    @Insert
    void insertAll(MovieEntity... category);

    @Insert
    void insertAll(List<MovieEntity> category);

    @Delete
    void delete(MovieEntity category);

    @Query("DELETE FROM MovieEntity")
    void deleteAll();


    @Update
    void update(MovieEntity data);

}
