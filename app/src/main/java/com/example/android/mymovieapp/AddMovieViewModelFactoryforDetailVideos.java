package com.example.android.mymovieapp;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
class AddMovieViewModelFactoryforDetailVideos extends ViewModelProvider.NewInstanceFactory {

    // COMPLETED (2) Add two member variables. One for the
    private final int mMovieID;
    private final Application mApp;

    // COMPLETED (3) Initialize the member variables in the constructor with the parameters received
    public AddMovieViewModelFactoryforDetailVideos(Application app,int movieID) {
        mApp=app;
        mMovieID = movieID;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MyViewModelForVideos(mApp,mMovieID);
    }
}

