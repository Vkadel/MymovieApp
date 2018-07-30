package com.example.android.mymovieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import DataBase.AppExecutors;
import UtilsAndAdapters.NetworkUtilsReview;
import UtilsAndAdapters.MovieVideos;
import UtilsAndAdapters.ReviewJSON;
import UtilsAndAdapters.Reviews;


class MyViewModelForReviews extends AndroidViewModel {
    private Context context;
    private MutableLiveData<List<Reviews>> liveListOfReviews;
    // Constant for logging
    private static final String TAG = MyViewModelForReviews.class.getSimpleName();

    public MyViewModelForReviews(@NonNull Application application,int myMovieID) {
        super(application);
        context=application;
        if (liveListOfReviews == null) {
            liveListOfReviews = new MutableLiveData<List<Reviews>>();
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                     URL url = null;
                    url = NetworkUtilsReview.buildUrl(context, myMovieID);
                    try {
                        String jsonMovieResponse = NetworkUtilsReview.
                                getResponseFromHttpUrl(url);
                        List<Reviews>  reviewClassArrayList= ReviewJSON.
                                getReviewsFromJSON(jsonMovieResponse);
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                liveListOfReviews.setValue(reviewClassArrayList);
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

    public MutableLiveData<List<Reviews>> getLiveListOfReviews() {
        return liveListOfReviews;
    }

}
