package com.kashyapmedia.moviesdemo.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ProfileEntity {

    @NonNull
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "name")
    public String name;
    @ColumnInfo(name = "email")
    public String email;

}
