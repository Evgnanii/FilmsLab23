package com.example.filmslab23android;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceNet {
    private static ServiceNet instance;
    private static  final String BASE_URL="http://www.omdbapi.com";
    private Retrofit mRetrofit;

    private ServiceNet(){
        mRetrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static ServiceNet getInstance(){
        if (instance==null){
            instance=new ServiceNet();
        }
        return instance;
    }

    public OmdbInt getJSONapi(){
        return mRetrofit.create(OmdbInt.class);
    }
}

