package com.example.filmslab23android.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.filmslab23android.Record;


@Database(entities = {Record.class}, version = 2, exportSchema = false)
public abstract class AppDb extends RoomDatabase {
    public abstract DbHelpInt dbHelpInt();
}
