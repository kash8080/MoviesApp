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



}
