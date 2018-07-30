package UtilsAndAdapters;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

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
public class NetworkUtilsMovieDetails extends Application {

    private static final String TAG = NetworkUtilsMovieDetails.class.getSimpleName();
    //BUILDS URL TO GET MOVIE Details

    public static URL buildUrl(Context context,long movieID) {
        final String THIS_USER_KEY =context.
                getString(R.string.your_key_movie_DB);
        final String URL_FOR_REVIEWS =context.
                getString(R.string.get_movie_details_url);
        final String MOVIE_QUERY="/movie";
        MOVIE_QUERY.trim();
        THIS_USER_KEY.trim();

        String movieIDString= String.valueOf(movieID).trim();
        SharedPreferences sharedPreferences=
                PreferenceManager.getDefaultSharedPreferences(context);
        Uri builtUri;
            builtUri = Uri.parse(URL_FOR_REVIEWS+movieIDString+"?").buildUpon()
                    .appendQueryParameter("api_key",THIS_USER_KEY)
                    .appendQueryParameter("language","en-US").build();

            //builtUri=Uri.parse("https://api.themoviedb.org/3/movie/12477/reviews?page=1&language=en-US&api_key=b0faf55f23b074ae60f3fc0dbe9ddd07");
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