package com.example.filmslab23android.DB;

import android.content.Context;

import androidx.room.Room;

public class DB {
    private AppDb appDb;
    private static DB instance;

    private DB(Context context){
        appDb = Room.databaseBuilder(context, AppDb.class, "MoviesDb")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static DB getInstance(Context context){
        if (instance==null){
            instance = new DB(context);
        }
        return instance;
    }

    public AppDb getAppDb(){
        return this.appDb;
    }
}
