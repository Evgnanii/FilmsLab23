package com.example.filmslab23android;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivInterface> {
    MainActivity mainActivityInstance;

    public void setMainActivityInstance(MainActivity instance) {
        this.mainActivityInstance = instance;
    }


}
