package com.kashyapmedia.moviesdemo;

import android.app.Application;

import com.kashyapmedia.moviesdemo.api.ApiClient;
import com.kashyapmedia.moviesdemo.api.MovieService;
import com.kashyapmedia.moviesdemo.db.AppDatabase;

public class App extends Application {

    // TODO: 01-05-2020 use Dagger instead of saving instance in static fields
    private static AppDatabase appDatabase;
    private static MovieService movieService;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase=AppDatabase.getInstance(getApplicationContext());
        movieService= ApiClient.getClient().create(MovieService.class);

    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }
    public static MovieService getMovieService() {
        return movieService;
    }
}
