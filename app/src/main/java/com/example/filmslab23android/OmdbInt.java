package com.example.filmslab23android;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbInt {

    @GET("/")
    Single<Record> getByTitle(@retrofit2.http.Query("t") String title, @retrofit2.http.Query("apikey") String apiKey);

    @GET("/")
    Single<Search> searchByTitle(@retrofit2.http.Query("s") String title, @Query("apikey") String apiKey);
}
