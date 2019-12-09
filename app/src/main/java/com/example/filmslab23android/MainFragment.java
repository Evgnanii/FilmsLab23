package com.example.filmslab23android;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmslab23android.DB.AppDb;
import com.example.filmslab23android.DB.DB;
import com.example.filmslab23android.DB.DbHelpInt;

import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;

public class MainFragment extends MvpAppCompatFragment
        implements RecycleViewListener, MainFrgInterface{
    @InjectPresenter
    MFP presenter;

    public final static String ID = "VeryUniqueId";
    private String API_KEY = "d862f73d";
    private RecyclerView recyclerView;
    private ViewAdapter viewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ServiceNet networkService;
    private CompositeDisposable disposibles = new CompositeDisposable();
    private AppDb appDb;
    private ConnectivityManager connectivityManager;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        networkService = ServiceNet.getInstance();

        appDb = DB.getInstance(getContext()).getAppDb();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.findButton).setOnClickListener(this::clickedLoadInternetRecord);
        recyclerView = view.findViewById(R.id.recordsRecycleView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        viewAdapter = new ViewAdapter(view.getContext(),this);
        recyclerView.setAdapter(viewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        ConnectivityManager tcm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        presenter.initConnectivityManager(tcm);
        presenter.setMoviesDb(appDb);
        presenter.setNetworkService(networkService);
    }

    @Override
    public void setParent(MainActivity parent){
    }

    public void clickedLoadInternetRecord(View v){
        String text = ((EditText) getView().findViewById(R.id.searchText)).getText().toString();
        presenter.loadInternetRecord(text);
    }



    @Override
    public void setConnectivityManager(ConnectivityManager cm) {
        this.connectivityManager=cm;
    }

    @Override
    public void showInternetRecord(Record record) {
        viewAdapter.setRecords(Arrays.asList(record));
        viewAdapter.notifyDataSetChanged();
        disposibles.dispose();
        hideKeyboardFrom(getContext(), getView());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadInternetData() {
        String text = ((EditText) getView().findViewById(R.id.searchText)).getText().toString();
        if (text.length() == 0) return;
        if (disposibles.isDisposed()) disposibles = new CompositeDisposable();
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            disposibles.add(networkService.getJSONapi()
                    .searchByTitle(text, API_KEY)
                    .subscribeOn(Schedulers.io())
                    .map(searchResponse -> {
                        DbHelpInt dao = appDb.dbHelpInt();
                        for (Record record : searchResponse.getRecords()) {
                            dao.insert(record);
                        }
                        return searchResponse;
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(records -> {
                        viewAdapter.setRecords(records.getRecords());
                        viewAdapter.notifyDataSetChanged();
                        disposibles.dispose();
                        hideKeyboardFrom(getContext(), getView());
                    }, error -> {
                        error.printStackTrace();
                    }));
        } else {//try to load from db
            disposibles.add(appDb.dbHelpInt()
                    .getByTitle("%" + text + "%")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(records -> {
                        if (records != null && records.size() != 0) {
                            viewAdapter.setRecords(records);
                            viewAdapter.notifyDataSetChanged();
                            hideKeyboardFrom(getContext(), getView());
                        } else {
                            Toast toast = Toast.makeText(getContext(),
                                    "Запрашиваемые данные недоступны!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        disposibles.dispose();
                    }, error -> {
                    }));
        }
    }

    @Override
    public void onClick(Record clickedRecord) {
        ((MainActivity) getActivity()).changeFragment(clickedRecord);
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}