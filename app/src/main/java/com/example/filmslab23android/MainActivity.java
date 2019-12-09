package com.example.filmslab23android;

import android.os.Bundle;
import android.util.Log;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainActivInterface {
    @InjectPresenter
    MainActivityPresenter presenter;
    private final String TAG = "MainActivity";

    public void changeFragment(Record record) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, RecordDetailsFragment.newInstance(record))
                .addToBackStack(RecordDetailsFragment.ID)
                .commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            MainFragment fragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(MainFragment.ID)
                    .commit();
        }
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void getActivityInstance() {
        presenter.setMainActivityInstance(this);
    }


}
