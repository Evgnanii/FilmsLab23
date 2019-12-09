package com.example.filmslab23android.DB;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.filmslab23android.Record;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface DbHelpInt {
    @Query("select * from Record where title like :title")
    Single<List<Record>> getByTitle(String title);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Record record);
}
