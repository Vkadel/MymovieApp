package com.example.android.mymovieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;


import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;

import DataBase.AppDatabase;
import DataBase.AppExecutors;
import UtilsAndAdapters.Movie;
import UtilsAndAdapters.MovieJSON;
import UtilsAndAdapters.NetworkUtils;


class MyViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<ArrayList<Movie>> liveListMovie;
    // Constant for logging
    private static final String TAG = MyViewModel.class.getSimpleName();

    public MyViewModel(@NonNull Application application) {
        super(application);
        context=application;
        if (liveListMovie == null) {
            liveListMovie = new MutableLiveData<ArrayList<Movie>>();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    url = NetworkUtils.buildUrl(context);
                    try {
                        String jsonMovieResponse = NetworkUtils
                                .getResponseFromHttpUrl(url);
                        ArrayList<Movie>  movieArrayList= MovieJSON
                                .getSimpleWeatherStringsFromJson(jsonMovieResponse);
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                liveListMovie.setValue(movieArrayList);
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
           Log.d(TAG, "Actively retrieving the favorite movies from the DataBase");
        }

    }

    public MutableLiveData<ArrayList<Movie>> getLiveListMovie() {
        return liveListMovie;
    }

}
