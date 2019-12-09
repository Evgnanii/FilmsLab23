package com.example.filmslab23android;


import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface DetailsInter extends MvpView {
    @StateStrategyType(AddToEndStrategy.class)
    void loadData(Record record);
}

