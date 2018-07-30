package UtilsAndAdapters;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.android.mymovieapp.MainActivity;
import com.example.android.mymovieapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the MOVIE.DB servers.
 */
public class NetworkUtils extends Application {

    private static final String TAG = NetworkUtils.class.getSimpleName();




    //BUILDS URL TO GET MOVIES

    public static URL buildUrl(Context context) {
        final String THIS_USER_KEY =context.
                getString(R.string.your_key_movie_DB);
        final String URL_FOR_MOST_POPULAR =context.
                getString(R.string.url_without_key_most_popular);
        final String URL_FOR_RATED =context.
                getString(R.string.url_without_key_highest_rated);
        THIS_USER_KEY.trim();
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        Boolean israted=sharedPreferences.getBoolean(context.getString(R.string.preference_sort_by_rating_key),
                context.getString(R.string.preference_sort_by_rating_default).equals("true"));
        Uri builtUri;

        if(israted){
            builtUri = Uri.parse(URL_FOR_RATED)
                .buildUpon()
                .appendQueryParameter("api_key",THIS_USER_KEY)
                .appendQueryParameter("language","en-US")
                .appendQueryParameter("page","1")
                .build();}
                else{
            builtUri = Uri.parse(URL_FOR_MOST_POPULAR)
                .buildUpon()
                .appendQueryParameter("api_key",THIS_USER_KEY)
                    .appendQueryParameter("page","1")
                .build();
                    }
          URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection=null;
        urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.reset();
            String delimiter= String.valueOf(scanner.delimiter());
            String json=scanner.next();
            scanner.useDelimiter("\\A");
            String jsont=scanner.next();
            String jsonFull=json+jsont;
            boolean hasInput = scanner.hasNext();
            if (jsonFull!=null) {
                return jsonFull;
            } else {
                return null;
            }
        } finally {
            //Log.e(TAG, "getResponseFromHttpUrl: "+json);
            urlConnection.disconnect();
        }
    }
}