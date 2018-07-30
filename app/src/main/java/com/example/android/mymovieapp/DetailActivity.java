package com.example.android.mymovieapp;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import DataBase.AppDatabase;
import DataBase.AppExecutors;
import DataBase.MovieFavorites;
import UtilsAndAdapters.Movie;
import UtilsAndAdapters.MovieDetailJSON;
import UtilsAndAdapters.MovieVideos;
import UtilsAndAdapters.NetworkUtilsMovieDetails;
import UtilsAndAdapters.ReviewAdapter;
import UtilsAndAdapters.Reviews;
import UtilsAndAdapters.VideoAdapter;

public class DetailActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener {
    private List<MovieVideos> thisMovieVideoList;
    private String POSTER_BASE = "https://image.tmdb.org/t/p/w500/";
    private List<MovieVideos> listOfVideos = new ArrayList<MovieVideos>();
    private List<Reviews> listOfReviews = new ArrayList<Reviews>();
    private LinearLayoutManager linearLayoutManagerReview;
    private LinearLayoutManager linearLayoutManagerVideos;
    private ReviewAdapter mReviewAdapter;
    private VideoAdapter mVideoAdapter;
    private TextView tvReleaseDate;
    private MyViewModelForReviews model;
    private MyViewModelForVideos modelVids;
    private Boolean isFav = false;
    private int orientation;
    private String TAG;
    private int movieID = 0;
    private AppDatabase mDb;
    private Context context;
    TextView tvTittle;
    TextView tvPlot;
    TextView userRaiting;
    ImageView poster;
    TextView tvAreThereVids;
    TextView tvArethereRevs;
    Movie thisMovie;
    Button addToFavs;
    AddMovieViewModelFactoryforDetail factoryforDetail;
    AddMovieViewModelFactoryforDetailVideos factoryforDetailVideos;

    @Override
    protected void onDestroy() {
        model.getLiveListOfReviews().removeObservers(this);
        modelVids.getLiveListOfVideos().removeObservers(this);
        addToFavs.setOnClickListener(null);
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        Intent intent = getIntent();
        Bundle intentExtras = intent.getExtras();
        context = this;


        //Set Up Recycler for Reviews
        RecyclerView mReviewRecycler = findViewById(R.id.list_of_reviews);
        RecyclerView mVideoRecycler = findViewById(R.id.list_of_videos);
        TAG = getApplicationContext().getPackageName();
        orientation = getResources().getConfiguration().orientation;
        linearLayoutManagerReview = new LinearLayoutManager(this);
        linearLayoutManagerVideos = new LinearLayoutManager(this);
        linearLayoutManagerReview.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerVideos.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManagerReview.setReverseLayout(false);
        linearLayoutManagerVideos.setReverseLayout(false);
        mReviewRecycler.setLayoutManager(linearLayoutManagerReview);
        mVideoRecycler.setLayoutManager(linearLayoutManagerVideos);
        mReviewAdapter = new ReviewAdapter(listOfReviews);
        mReviewRecycler.setAdapter(mReviewAdapter);
        mVideoAdapter = new VideoAdapter(thisMovieVideoList, this);
        mVideoRecycler.setAdapter(mVideoAdapter);

        //Get data from intent
        movieID = intentExtras.getInt("movieId");

        //Get UI references
        tvTittle = findViewById(R.id.tv_movie_detail_tittle);
        tvReleaseDate = findViewById(R.id.tv_release_date);
        tvPlot = findViewById(R.id.tv_detail_plot_synop);
        userRaiting = findViewById(R.id.tv_detail_user_rating);
        poster = findViewById(R.id.iv_detail_movie_poster);
        addToFavs = findViewById(R.id.detail_but_fav);
        tvAreThereVids=findViewById(R.id.are_there_videos);
        tvArethereRevs=findViewById(R.id.are_there_reviews_tv);
        factoryforDetail = new AddMovieViewModelFactoryforDetail(getApplication(), movieID);
        factoryforDetailVideos = new AddMovieViewModelFactoryforDetailVideos(getApplication(), movieID);
        getMovieDetails();
        SetupViewModelForReviews();
        SetupViewModelForVideos();

        //Create Listener for But
        addToFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting reference to database
                mDb = AppDatabase.getInstance(getApplicationContext());
                final MovieFavorites thisMovieFavorites = new MovieFavorites(thisMovie.getMovieISAdult(),
                        thisMovie.getMovieHasVideo(), thisMovie.getMovieBackDropPath(),
                        thisMovie.getMovieLanguage(), thisMovie.getMovieOriginalTittle(),
                        thisMovie.getMovieOverview(),
                        thisMovie.getMoviePopularity(), thisMovie.getMovieTittle(),
                        thisMovie.getMovieVoteAverage(),
                        thisMovie.getMovieVoteCount(), thisMovie.getmMoviePoster(),
                        thisMovie.getmMovieReleaseDate());
                thisMovieFavorites.setId(thisMovie.getmMovieID());

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            MovieFavorites MovieFavCheck = mDb.Movie().loadMovieByID(thisMovieFavorites.getId());
                            int id = MovieFavCheck.getId();
                            isFav = true;

                        } catch (Exception e) {
                            isFav = false;
                            e.printStackTrace();
                        }

                        if (isFav) {
                            mDb.Movie().deleteMovie(thisMovieFavorites);
                            AppExecutors.getInstance().mainThread.execute(new Runnable() {
                                @Override
                                public void run() {
                                    AppExecutors.getInstance().mainThread.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast mToast = Toast.makeText(context, "DELETING " + thisMovie.getMovieTittle() + " to your Fab Movies", Toast.LENGTH_LONG);
                                            mToast.show();
                                            addToFavs.setText(R.string.Favorite);
                                        }
                                    });

                                }
                            });
                        } else {
                            mDb.Movie().insertTask(thisMovieFavorites);
                            AppExecutors.getInstance().mainThread.execute(new Runnable() {
                                @Override
                                public void run() {
                                    Toast mToast = Toast.makeText(context, "ADDING " + thisMovie.getMovieTittle() + " to your Fab Movies", Toast.LENGTH_LONG);
                                    mToast.show();
                                    addToFavs.setText(R.string.isNotFavorite);

                                }
                            });
                            AppExecutors.getInstance().mainThread.execute(new Runnable() {
                                @Override
                                public void run() {

                                }
                            });
                        }
                    }
                });
            }
        });

    }

    void getMovieDetails() {
        if (movieID != 0) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    URL url = null;
                    url = NetworkUtilsMovieDetails.buildUrl(context, movieID);
                    try {
                        String jsonMovieResponse = NetworkUtilsMovieDetails
                                .getResponseFromHttpUrl(url);
                        thisMovie = MovieDetailJSON.getSimpleWeatherStringsFromJson(jsonMovieResponse);
                        AppExecutors.getInstance().mainThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                //Done:UPDATE UI

                                tvTittle.setText(thisMovie.getMovieTittle());
                                tvReleaseDate.setText(thisMovie.getmMovieReleaseDate());
                                tvPlot.setText(thisMovie.getMovieOverview());
                                userRaiting.setText(Double.toString(thisMovie.getMovieVoteAverage()));
                                String posterAddress = thisMovie.getMovieBackDropPath();
                                //Getting dimensions for movie poster
                                @SuppressLint("ResourceType") String pictureHString = getResources().getString(R.dimen.picture_h_detail);
                                pictureHString = pictureHString.replace("dip", "");
                                pictureHString = pictureHString.replace(".0", "");
                                int pictureH = Integer.parseInt(pictureHString.trim());

                                @SuppressLint("ResourceType") String pictureWString = getResources().getString(R.dimen.picture_w_detail);
                                pictureWString = pictureWString.replace("dip", "");
                                pictureWString = pictureWString.replace(".0", "");
                                int pictureW = Integer.parseInt(pictureHString.trim());

                                posterAddress = POSTER_BASE + posterAddress;
                                Picasso.get().load(posterAddress)
                                        .resize(pictureW, pictureH)
                                        .centerCrop()
                                        .into(poster);
                                CheckIfFav();

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

    private void SetupViewModelForVideos() {
        modelVids = ViewModelProviders.of(this, factoryforDetailVideos).get(MyViewModelForVideos.class);
        modelVids.getLiveListOfVideos().observe(this, new Observer<List<MovieVideos>>() {
            @Override
            public void onChanged(@Nullable List<MovieVideos> MovieVideos) {
                Log.d(TAG, "Updating list of Reviews From");
                listOfVideos = MovieVideos;
                mVideoAdapter.setMovieData(MovieVideos);
                if (listOfVideos.size()==0){ tvAreThereVids.setText(context.getString(R.string.there_are_no_videos));}
                else{tvAreThereVids.setText("");}
            }
        });
    }

    void SetupViewModelForReviews() {

        model = ViewModelProviders.of(this, factoryforDetail).get(MyViewModelForReviews.class);
        model.getLiveListOfReviews().observe(this, new Observer<List<Reviews>>() {
            @Override
            public void onChanged(@Nullable List<Reviews> reviews) {
                Log.d(TAG, "Updating list of Reviews From");
                listOfReviews = reviews;
                mReviewAdapter.setReviewsData(reviews);
                if (listOfReviews.size()==0){ tvArethereRevs.setText(context.getString(R.string.are_there_no_reviews));}
                else{tvArethereRevs.setText("");}
            }
        });
    }

    void CheckIfFav() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb = AppDatabase.getInstance(getApplicationContext());
                MovieFavorites MovieFavCheck = mDb.Movie().loadMovieByID(thisMovie.getmMovieID());
                try {
                    int movieIDCheck = MovieFavCheck.getId();
                    isFav = true;
                } catch (Exception e) {
                    isFav = false;
                    e.printStackTrace();
                }
                //If is a favorite change button text
                if (isFav) {
                    AppExecutors.getInstance().mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            addToFavs.setText(R.string.isNotFavorite);
                            ;
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onListItemClick(int clickedItemIndex, String movieKey) {
        //TODO: Create intent and launch it
        //String address = Resources.getSystem().getString(R.string.get_movie_from_youtobe);
        //address.trim();
        String address="https://www.youtube.com/watch?v=";
        address = address + movieKey;
        address.trim();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(address));
        startActivity(browserIntent);
    }
}
