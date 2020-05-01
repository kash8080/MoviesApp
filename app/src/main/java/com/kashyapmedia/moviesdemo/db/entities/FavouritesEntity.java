package com.kashyapmedia.moviesdemo.db.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavouritesEntity {

    @NonNull
    @PrimaryKey
    public int id;

}
