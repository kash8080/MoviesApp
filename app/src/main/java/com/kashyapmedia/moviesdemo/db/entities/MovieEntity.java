package com.kashyapmedia.moviesdemo.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieEntity {

    @NonNull
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "budget")
    public int budget;
    @ColumnInfo(name = "revenue")
    public int revenue;

    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "poster_path")
    public String poster_path;
    @ColumnInfo(name = "backdrop_path")
    public String backdrop_path;
    @ColumnInfo(name = "release_date")
    public String release_date;
    @ColumnInfo(name = "status")
    public String status;
    @ColumnInfo(name = "vote_average")
    public double vote_average;
    @ColumnInfo(name = "vote_count")
    public int vote_count;
    @ColumnInfo(name = "adult")
    public boolean adult;

    @ColumnInfo(name = "now_playing")
    public boolean now_playing;
    @ColumnInfo(name = "upcoming")
    public boolean upcoming;

    //local field. not used in room
    public boolean isFavourite;

    @NonNull
    @Override
    public String toString() {
        // TODO: 01-05-2020 add all fields
        return id+"__"+title+"__"+isFavourite+"__"+now_playing+"__"+upcoming;
    }

    public MovieEntity getACopy(){
        MovieEntity movieEntity=new MovieEntity();
        movieEntity.id=id;
        movieEntity.budget=budget;
        movieEntity.revenue=revenue;
        movieEntity.title=title;
        movieEntity.poster_path=poster_path;
        movieEntity.backdrop_path=backdrop_path;
        movieEntity.release_date=release_date;
        movieEntity.status=status;
        movieEntity.vote_average=vote_average;
        movieEntity.vote_count=vote_count;
        movieEntity.adult=adult;
        return movieEntity;
    }
}
