package UtilsAndAdapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class ReviewJSON {

    public static List<Reviews> getReviewsFromJSON(String jsonMovieServerResponse)
            throws JSONException {

        /* all Movie information comes under this variable*/
        final String RESULTS_LIST = "results";

        final String AUTHOR = "author";
        final String REVIEW="content";
        final String REVIEW_URL="url";

        final String ReviewID = "id";
        /* Back Drop Path */
        final String UrlReview = "url";

        /* Original language */
        final String TOTAL_PAGES = "total_pages";

        /* Original Title */
        final String TOTAL_RESULTS = "total_results";


        List<Reviews> reviewList=new ArrayList<>();
        //TODO: Send the appropiate JSON here
        JSONObject ReviewsJSON = new JSONObject(jsonMovieServerResponse);
        JSONArray reviewsJSONArray = ReviewsJSON.getJSONArray("results");
        long resultCount = reviewsJSONArray.length();

        int status = 0;

        if (resultCount > 0) {
            //Get All data from each item

            for (int i = 0; i < reviewsJSONArray.length(); i++) {
                /* These are the values that will be collected */
                JSONObject ReviewItem = reviewsJSONArray.getJSONObject(i);
                String author = ReviewItem.getString(AUTHOR);
                String review = ReviewItem.getString(REVIEW);
                String reviewURL = ReviewItem.getString(REVIEW_URL);

                Reviews thisReview = new Reviews(review,author,reviewURL);
                reviewList.add(thisReview);
            }

            return reviewList;
        } else {
            return null;
        }
    }
}
