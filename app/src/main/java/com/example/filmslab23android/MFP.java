package com.example.filmslab23android;


import android.net.ConnectivityManager;

import com.example.filmslab23android.DB.AppDb;
import com.example.filmslab23android.DB.DbHelpInt;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;


@InjectViewState
public class MFP extends MvpPresenter<MainFrgInterface> {

    ConnectivityManager cm;
    private ServiceNet serviceNet;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AppDb appDb;

    private String API_KEY = "d862f73d";

    public void initConnectivityManager(ConnectivityManager cm){
        if (this.cm==null) this.cm=cm;
        getViewState().setConnectivityManager(this.cm);
    }

    public void setNetworkService(ServiceNet service) {
        serviceNet = service;
    }

    public void setMoviesDb(AppDb db) {
        appDb = db;
    }

    public void loadInternetRecord(String strForSearch){
        if (compositeDisposable.isDisposed()) compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(serviceNet.getJSONapi()
                .getByTitle(strForSearch, API_KEY)
                .subscribeOn(Schedulers.io())
                .map(record -> {
                    DbHelpInt dao = appDb.dbHelpInt();
                    dao.insert(record);
                    return record;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(record -> {
                    getViewState().showInternetRecord(record);

                }, error -> {
                    getViewState().showError("ERROR OCCURED" + error.getMessage());
                }));
    }

}
