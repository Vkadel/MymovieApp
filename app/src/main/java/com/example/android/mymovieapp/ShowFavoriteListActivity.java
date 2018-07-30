package com.example.android.mymovieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import DataBase.AppDatabase;
import DataBase.AppExecutors;
import DataBase.MovieFavorites;
import UtilsAndAdapters.MovieAdapter;
import UtilsAndAdapters.MovieAdapterFavorites;

public class ShowFavoriteListActivity extends AppCompatActivity implements MovieAdapterFavorites.ListItemClickListenerfab {
    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context = this;
    private List<MovieFavorites> listOfFavoriteMovies;
    private MovieAdapterFavorites myMovieAdapter = null;
    private MyViewModelforFavorites model;
    private int orientation;
    private GridLayoutManager layoutManager;
    private AppDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favorite_list);
        RecyclerView movieRecycler = findViewById(R.id.list_of_favorite_movies);

        orientation = getResources().getConfiguration().orientation;
        layoutManager = new GridLayoutManager(this, 2);
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.setSpanCount(2);
        } else {
            layoutManager.setSpanCount(4);
        }
        layoutManager.setOrientation(GridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        movieRecycler.setLayoutManager(layoutManager);
        myMovieAdapter = new MovieAdapterFavorites(listOfFavoriteMovies, (MovieAdapterFavorites.ListItemClickListenerfab) context);
        movieRecycler.setAdapter(myMovieAdapter);
        model = ViewModelProviders.of(this).get(MyViewModelforFavorites.class);
        mDb = AppDatabase.getInstance(getApplicationContext());
        SetupViewModel();
    }

    private void SetupViewModel() {
        MyViewModelforFavorites viewModel = ViewModelProviders.of(this).get(MyViewModelforFavorites.class);
        viewModel.getFabMovs().observe(this, new Observer<List<MovieFavorites>>() {
            @Override
            public void onChanged(@Nullable List<MovieFavorites> movieFavorites) {
                Log.d(TAG, "Updating list of favorite movies from LiveData in ViewModel");
                listOfFavoriteMovies = movieFavorites;
                myMovieAdapter.setMovieData(movieFavorites);
            }
        });
    }

    @Override
    protected void onResume() {
        orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.setSpanCount(2);
        } else {
            layoutManager.setSpanCount(4);
        }
        model = ViewModelProviders.of(this).get(MyViewModelforFavorites.class);
        model.getFabMovs().observe(this, liveListMovie -> {
            // update UI
            liveListMovie.size();
            Log.e("TAG", "onCreate: Adding data");
            myMovieAdapter.setMovieData(liveListMovie);
        });
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Options Menu we specified in XML
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //This method creates and initiates in the intent to open the SettingsActivity fragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex, Boolean itWasClicked) {
        //TODO: Code on itemclick listener
        Toast mToast = Toast.makeText(context, "will route " + clickedItemIndex, Toast.LENGTH_LONG);
        mToast.show();
        MovieFavorites thisMovie = listOfFavoriteMovies.get(clickedItemIndex);
        String tittle = thisMovie.getMovieTittle();
        String releaseDate = thisMovie.getmMovieReleaseDate();
        double voteAverage = thisMovie.getMovieVoteAverage();
        String poster = thisMovie.getmMoviePoster();
        String plot = thisMovie.getMovieOverview();
        Intent intent = new Intent(context, DetailActivity.class);
        if (!itWasClicked) {
            intent.putExtra("position", clickedItemIndex);
            intent.putExtra("tittle", tittle);
            intent.putExtra("release_date", releaseDate);
            intent.putExtra("vote_average", voteAverage);
            intent.putExtra("poster", poster);
            intent.putExtra("plot", plot);
            startActivity(intent);
        } else {
            mToast = Toast.makeText(context, "Deleting " + tittle + " to your Fab Movies", Toast.LENGTH_LONG);
            mToast.show();
            mDb = AppDatabase.getInstance(getApplicationContext());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.Movie().deleteMovie(thisMovie);
                }
            });

        }
    }



    @Override
    protected void onDestroy() {
        model.getFabMovs().removeObservers(this);
        super.onDestroy();
    }
}
