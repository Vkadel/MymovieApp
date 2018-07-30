package UtilsAndAdapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class VideoJSON {

    public static List<MovieVideos> getVideosFromJSON(String jsonMovieServerResponse)
            throws JSONException {

        /* all Movie information comes under this variable*/
        final String RESULTS_LIST = "results";

        final String KEY = "key";
        final String NAME="name";
        final String SITE="site";

        final String MOVIE_VIDEO_ID = "id";
        /* Back Drop Path */
        final String VIDEO_TYPE = "type";

       
        final String VIDEO_SIZE = "size";


        List<MovieVideos> reviewList=new ArrayList<MovieVideos>();
        //TODO: Send the appropiate JSON here
        JSONObject ReviewsJSON = new JSONObject(jsonMovieServerResponse);
        JSONArray MovieVideosArray = ReviewsJSON.getJSONArray("results");
        long resultCount = MovieVideosArray.length();

        int status = 0;

        if (resultCount > 0) {
            //Get All data from each item

            for (int i = 0; i < MovieVideosArray.length(); i++) {
                /* These are the values that will be collected */
                JSONObject MovieVideoItem = MovieVideosArray.getJSONObject(i);
                String id = MovieVideoItem.getString(MOVIE_VIDEO_ID);
                String key = MovieVideoItem.getString(KEY);
                String name = MovieVideoItem.getString(NAME);
                String site=MovieVideoItem.getString(SITE);
                String size=MovieVideoItem.getString(VIDEO_SIZE);
                String videoType=MovieVideoItem.getString(VIDEO_TYPE);

                MovieVideos thisReview = new MovieVideos(key,size,name,id,site,videoType);
                reviewList.add(thisReview);
            }

            return reviewList;
        } else {
            return null;
        }
    }
}
