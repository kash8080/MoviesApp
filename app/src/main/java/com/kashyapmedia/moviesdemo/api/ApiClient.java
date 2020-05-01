package com.kashyapmedia.moviesdemo.api;

import com.kashyapmedia.moviesdemo.api.util.LiveDataCallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class ApiClient {

    public static final String BASEURL = "https://api.themoviedb.org/3/";
    public static final String IMAGEBASEURL = "https://image.tmdb.org/t/p";
    public static final String IMAGESIZENORMAL = "/w185";//"w92", "w154", "w185", "w342", "w500", "w780", or "original";
    public static final String IMAGESIZEMEDIUM = "/w342";//"w92", "w154", "w185", "w342", "w500", "w780", or "original";
    public static final String IMAGESIZE500 = "/w500";//"w92", "w154", "w185", "w342", "w500", "w780", or "original";
    public static final boolean okHttpLogging=true;

    private static Retrofit retrofit = null;
    private static OkHttpClient myOkHttpClient=null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .client(getHttpClient())
                    .build();
        }
        return retrofit;
    }


    private static OkHttpClient getHttpClient(){
        if(myOkHttpClient!=null){
            return myOkHttpClient;
        }

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (okHttpLogging) {
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        myOkHttpClient = httpClientBuilder.build();

        return myOkHttpClient;
    }


}
