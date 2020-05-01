package com.kashyapmedia.moviesdemo.api;

import androidx.lifecycle.LiveData;

import com.kashyapmedia.moviesdemo.api.models.GenericListResponse;
import com.kashyapmedia.moviesdemo.api.models.Movie;
import com.kashyapmedia.moviesdemo.api.responses.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("movie/{movie_id}")
    LiveData<ApiResponse<Movie>> getMovieDetails(@Path("movie_id")int movie_id, @Query("api_key") String api_key);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("movie/now_playing")
    LiveData<ApiResponse<GenericListResponse<Movie>>> getNowPlayingMovies(@Query("api_key") String api_key,@Query("language") String language);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @GET("movie/upcoming")
    LiveData<ApiResponse<GenericListResponse<Movie>>> getUpcomingMovies(@Query("api_key") String api_key,@Query("language") String language);

}
