package com.example.filmslab23android;


import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class Details extends MvpPresenter<DetailsInter> {
    public void showData(Record record){
        if (record!=null) {
            getViewState().loadData(record);
        }

    }
}
