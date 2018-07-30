package com.example.android.mymovieapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import DataBase.AppDatabase;
import DataBase.AppExecutors;
import DataBase.MovieFavorites;


class MyViewModelforFavorites extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MyViewModelforFavorites.class.getSimpleName();
    private AppDatabase database;
    private LiveData<List<MovieFavorites>> tasks;

    public MyViewModelforFavorites(Application application) {
        super(application);
        database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
    }

    public LiveData<List<MovieFavorites>> getFabMovs() {
        tasks = database.Movie().loadMovies();
        return tasks;
    }
}