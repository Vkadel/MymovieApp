package com.example.android.mymovieapp;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import DataBase.AppDatabase;
import DataBase.AppExecutors;
import DataBase.MovieFavorites;
import UtilsAndAdapters.Movie;
import UtilsAndAdapters.MovieAdapter;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {
    private AppDatabase mDb;
    private Context context = this;
    private ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
    private MovieAdapter myMovieAdapter = null;
    private MyViewModel model;
    private int orientation;
    private GridLayoutManager layoutManager;

    @Override
    protected void onDestroy() {
        model.getLiveListMovie().removeObservers(this);
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView movieRecycler = findViewById(R.id.list_of_movies);
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
        myMovieAdapter = new MovieAdapter(listOfMovies, (MovieAdapter.ListItemClickListener) context);
        movieRecycler.setAdapter(myMovieAdapter);
        model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getLiveListMovie().observe(this, liveListMovie -> {
            // update UI
            liveListMovie.size();
            Log.e("TAG", "onCreate: Adding data");
            myMovieAdapter.setMovieData(liveListMovie);
            listOfMovies = liveListMovie;
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
        model = ViewModelProviders.of(this).get(MyViewModel.class);
        model.getLiveListMovie().observe(this, liveListMovie -> {
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
        if (id == R.id.list_of_favorite_movies) {
            Intent settingsIntent = new Intent(this, ShowFavoriteListActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex, boolean isTryingtoFav, boolean isFavMovie, Button thisItemButton) {

        Toast mToast;
        Movie thisMovie = listOfMovies.get(clickedItemIndex);
        int movieID = thisMovie.getmMovieID();
        if (!isTryingtoFav) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("movieId", movieID);
            startActivity(intent);
            mToast = Toast.makeText(context, "Navigating to Item: "
                    + clickedItemIndex, Toast.LENGTH_LONG);
        } else {
            String tittle = thisMovie.getMovieTittle();
            String releaseDate = thisMovie.getmMovieReleaseDate();
            double voteAverage = thisMovie.getMovieVoteAverage();
            String poster = thisMovie.getmMoviePoster();
            String plot = thisMovie.getMovieOverview();
            String MovieISAdult = thisMovie.getMovieISAdult();
            String MovieHasVideo = thisMovie.getMovieHasVideo();
            String MovieBackDropPath = thisMovie.getMovieBackDropPath();
            String MovieLanguage = thisMovie.getMovieLanguage();
            String MovieOriginalTittle = thisMovie.getMovieOriginalTittle();
            String MovieOverview = thisMovie.getMovieOverview();
            Long MoviePopularity = thisMovie.getMoviePopularity();
            String MovieTittle = thisMovie.getMovieTittle();
            double MovieVoteAverage = thisMovie.getMovieVoteAverage();
            double MovieVoteCount = thisMovie.getMovieVoteCount();
            String moviePoster = thisMovie.getmMoviePoster();
            String MovieReleaseDate = thisMovie.getmMovieReleaseDate();
            int MovieID = thisMovie.getmMovieID();
            mDb = AppDatabase.getInstance(getApplicationContext());
            final MovieFavorites movieFavorite = new MovieFavorites(MovieISAdult,
                    MovieHasVideo, MovieBackDropPath,
                    MovieLanguage, MovieOriginalTittle, MovieOverview,
                    MoviePopularity, MovieTittle, MovieVoteAverage,
                    MovieVoteCount, moviePoster, MovieReleaseDate);
            movieFavorite.setId(MovieID);
            int thisFabMovieID = movieFavorite.id;

            //Adding or Taking out the Item
            if (isFavMovie) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        MovieFavorites movieLivedata = mDb.Movie().loadMovieByID(movieFavorite.getId());
                        mDb.Movie().deleteMovie(movieFavorite);
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                thisItemButton.setText(context.getString(R.string.Favorite));
                                Toast thisToast;
                                thisToast = Toast.makeText(context, "Deleting: " +
                                        movieFavorite.getMovieTittle() + " from your Fav List", Toast.LENGTH_LONG);
                                thisToast.show();

                            }
                        });

                    }
                });
            } else {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        MovieFavorites movieLivedata = mDb.Movie().loadMovieByID(movieFavorite.getId());
                        mDb.Movie().insertTask(movieFavorite);
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                thisItemButton.setText(context.getString(R.string.isNotFavorite));
                                Toast thisToast;
                                thisToast = Toast.makeText(context, context.getString(R.string.Adding) +
                                        movieFavorite.getMovieTittle() + context.getString(R.string.to_fav_list), Toast.LENGTH_LONG);
                                thisToast.show();

                            }
                        });


                    }
                });
            }
        }
    }
}
