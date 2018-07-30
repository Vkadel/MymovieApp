package com.example.android.mymovieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.net.URL;
import java.util.List;

import DataBase.AppExecutors;
import UtilsAndAdapters.NetworkUtilsReview;
import UtilsAndAdapters.MovieVideos;
import UtilsAndAdapters.NetworkUtilsVideos;
import UtilsAndAdapters.VideoJSON;


public class MyViewModelForVideos extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<MovieVideos>> liveListOfVideos;
    // Constant for logging
    private static final String TAG = MyViewModelForVideos.class.getSimpleName();

    public MyViewModelForVideos(@NonNull Application application, int myMovieID) {
        super(application);
        context=application;
        if (liveListOfVideos == null) {
            liveListOfVideos = new MutableLiveData<List<MovieVideos>>();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                     URL url = null;
                    url = NetworkUtilsVideos.buildUrl(context, myMovieID);
                    try {
                        String jsonMovieResponse = NetworkUtilsVideos.
                                getResponseFromHttpUrl(url);
                        List<MovieVideos>  listOfVideos= VideoJSON.
                                getVideosFromJSON(jsonMovieResponse);
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                liveListOfVideos.setValue(listOfVideos);
                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });
           Log.d(TAG, "Retrieving reviews for Movie");
        }

    }

    public MutableLiveData<List<MovieVideos>> getLiveListOfVideos() {
        return liveListOfVideos;
    }

}
