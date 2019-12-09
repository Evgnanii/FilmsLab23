package com.example.filmslab23android;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {
    @SerializedName("Search")
    List<Record> records;

    public List<Record> getRecords() {
        return records;
    }

}
