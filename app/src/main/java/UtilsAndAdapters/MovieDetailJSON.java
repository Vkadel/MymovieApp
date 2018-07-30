package UtilsAndAdapters;

import org.json.JSONException;
import org.json.JSONObject;

public final class MovieDetailJSON {

    public static Movie getSimpleWeatherStringsFromJson(String jsonMovieServerResponse)
            throws JSONException {


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

        final String ID_NUMBER = "id";
        Movie thisMovie;

        JSONObject movieItem = new JSONObject(jsonMovieServerResponse);

        //Done:Use status to route response;
        int status = 0;


        //TODO: determine if response is succesful
        if (movieItem != null) {

            /* These are the values that will be collected */
            int movieID = Integer.valueOf(movieItem.getString(ID_NUMBER));
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


            thisMovie = new Movie(isAdult, hasVideo, backDropPath, language, originalTittle,
                    overview, popularity, tittle, voteAverage, voteCount, posterPath, releaseDate, movieID);


            return thisMovie;
        } else {
            return null;
        }
    }
}

