package com.kashyapmedia.moviesdemo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kashyapmedia.moviesdemo.db.entities.FavouritesEntity;
import com.kashyapmedia.moviesdemo.db.entities.MovieEntity;

import java.util.List;


@Dao
public interface FavouritesDao {

    @Query("SELECT * FROM MovieEntity JOIN FavouritesEntity ON FavouritesEntity.id == MovieEntity.id ")
    LiveData<List<MovieEntity>> getFavourites();

    @Query("SELECT * FROM FavouritesEntity ")
    LiveData<List<FavouritesEntity>> getFavouriteIds();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(FavouritesEntity... category);

    @Delete
    void delete(FavouritesEntity category);

    @Query("DELETE FROM FavouritesEntity")
    void deleteAll();

    @Query("DELETE FROM FavouritesEntity WHERE id == :id ")
    void deleteById(int id);
}
