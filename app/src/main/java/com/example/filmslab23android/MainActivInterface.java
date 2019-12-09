package com.example.filmslab23android;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainActivInterface extends MvpView {
    @StateStrategyType(AddToEndSingleStrategy.class)
    void getActivityInstance();
}
