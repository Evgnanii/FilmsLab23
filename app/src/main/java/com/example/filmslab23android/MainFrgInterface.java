package com.example.filmslab23android;


import android.net.ConnectivityManager;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.SingleStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface MainFrgInterface extends MvpView {
    @StateStrategyType(SingleStateStrategy.class)
    void loadInternetData();

    @StateStrategyType(SingleStateStrategy.class)
    void showInternetRecord(Record record);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setConnectivityManager(ConnectivityManager cm);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setParent(MainActivity parent);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showError(String message);
}
