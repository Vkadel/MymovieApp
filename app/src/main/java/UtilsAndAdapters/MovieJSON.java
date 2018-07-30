package UtilsAndAdapters;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.android.mymovieapp.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class MovieJSON {

    public static ArrayList<Movie> getSimpleWeatherStringsFromJson(String jsonMovieServerResponse)
            throws JSONException {

        /* all Movie information comes under this variable*/
        final String RESULTS_LIST = "results";

        /* Is is adult tag */
        final String IS_IT_ADULT = "adult";

        final String HAS_VIDEO = "video";
        /* Back Drop Path */
        final String BACK_DROP_PATH = "backdrop_path";

        /* Original language */
        final String ORGINIAL_LANGUAGE = "original_language";

        /* Original Title */
        final String ORIGINAL_TITTLE = "original_title";

        final String OVERVIEW = "overview";

        final String POPULARITY = "popularity";

        final String POSTER_PATH = "poster_path";

        final String RELEASE_DATE = "release_date";

        final String TITLE = "title";

        final String VOTE_AVERAGE = "vote_average";

        final String VOTE_COUNT = "vote_count";

        final String TOTAL_RESULTS = "total_results";
        final String ID_NUMBER="id";
        ArrayList<Movie> converterMovieList=new ArrayList<Movie>();

        ArrayList<Movie> listOfMovies = new ArrayList<Movie>();

        JSONObject movieJSONObj = new JSONObject(jsonMovieServerResponse);
        JSONArray movieJSONArray = movieJSONObj.getJSONArray("results");
        long resultCount = movieJSONArray.length();
        //TODO:Use status to route response;
        int status = 0;


        //TODO: determine if response is succesful
        if (resultCount > 0) {
            //Get All data from each item

            for (int i = 0; i < movieJSONArray.length(); i++) {
                /* These are the values that will be collected */
                JSONObject movieItem = movieJSONArray.getJSONObject(i);
                int movieID=Integer.valueOf(movieItem.getString(ID_NUMBER));
                String isAdult = movieItem.getString(IS_IT_ADULT);
                String hasVideo = movieItem.getString(HAS_VIDEO);
                double voteAverage = movieItem.getDouble(VOTE_AVERAGE);
                double voteCount = movieItem.getDouble(VOTE_COUNT);
                String tittle = movieItem.getString(TITLE);
                String originalTittle = movieItem.getString(ORIGINAL_TITTLE);
                Long popularity = movieItem.getLong(POPULARITY);
                String posterPath = movieItem.getString(POSTER_PATH);
                String backDropPath = movieItem.getString(BACK_DROP_PATH);
                String language = movieItem.getString(ORGINIAL_LANGUAGE);
                String overview = movieItem.getString(OVERVIEW);
                String releaseDate = movieItem.getString(RELEASE_DATE);


                Movie thisMovie = new Movie(isAdult, hasVideo, backDropPath, language, originalTittle,
                        overview, popularity, tittle, voteAverage, voteCount,posterPath,releaseDate,movieID);
                listOfMovies.add(thisMovie);
            }

            return listOfMovies;
        } else {
            return null;
        }
    }
}
