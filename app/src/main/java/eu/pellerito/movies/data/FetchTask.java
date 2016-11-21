/*
 * | \  / |
 * |  __ \                | |            |  \/  |          (_)
 * |  ___/ _ \| '_ \| | | | |/ _` | '__| | |\/| |/ _ \ \ / / |/ _ \/ __|
 * | |  | (_) | |_) | |_| | | (_| | |    | |  | | (_) \ V /| |  __/\__ \
 * |_|   \___/| .__/ \__,_|_|\__,_|_|    |_|  |_|\___/ \_/ |_|\___||___/
 * | |
 * |_|
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.pellerito.movies.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import eu.pellerito.movies.BuildConfig;
import eu.pellerito.movies.R;
import eu.pellerito.movies.interfaces.UIListener;
import eu.pellerito.movies.model.UIModel;
import eu.pellerito.movies.utility.Costants;

/**
 * Open Connection to server ....
 * Parsing Json data .......
 * set data in model ...
 */

public class FetchTask extends AsyncTask<String, Void, UIModel[]> {


    private final UIListener mListener;
    private final Context mContext;
    // fix problem rotation portrait-landscape use static ....
    private static ProgressDialog sDialog;

    protected void onPreExecute() {
        /**
         Display dialog preload
         */
        sDialog = new ProgressDialog(mContext);
        sDialog.setMessage(mContext.getString(R.string.download_message_task));
        sDialog.show();

    }


    // constructor
    public FetchTask(Context context, UIListener listener) {
        mContext = context;
        mListener = listener;
    }

    /**
     * add costants LOG TAG
     */
    private final String LOG_TAG = FetchTask.class.getSimpleName();


    @Nullable
    @Override
    protected UIModel[] doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;

        // contain Json Response as a String
        String dataJsonStr = null;


        try {

            /**
             * Construct the URL for "the Movie Db" query.
             * Possible parameters are avaiable at
             * https://www.themoviedb.org/documentation/api/discover
             */

            Uri builtUri = Uri.parse(Costants.MOVIE_DB_BASE_URL).buildUpon()
                    .appendEncodedPath(Costants.MOVIE_DB_VERSION)
                    .appendEncodedPath(Costants.MOVIE_DB_MODE)
                    .appendEncodedPath(Costants.MOVIE_DB_TYPE)
                    .appendQueryParameter(Costants.SORTBY_PARAM, params[0]) // vote_average , popularity
                    .appendQueryParameter(Costants.APPID_PARAM,
                            BuildConfig.MOVIE_DB_API_KEY) // ADD API KEY in build.gradle Module app
                    .build();

            // LOG: String URI format
            Log.v(LOG_TAG, "Uri: " + builtUri.toString());

            URL url = new URL(builtUri.toString());

            /**
             * Create the request to MOVIEDB, and open the connection
             */
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(Costants.CONN_TIMEOUT);
            urlConnection.setReadTimeout(Costants.CONN_READ_TIMEOUT);
            urlConnection.setRequestMethod(Costants.CONN_METHOD);
            urlConnection.connect();

            /**
             * Read the input stream into a String
             */
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = bufferedReader.readLine()) != null) {

                stringBuilder.append(line).append("\n");
            }

            if (stringBuilder.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            dataJsonStr = stringBuilder.toString();

            // Log.v(LOG_TAG, "Json string: " + dataJsonStr);

        } catch (IOException e) {

            Log.e(LOG_TAG, "Error I/O", e);

            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (@NonNull final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }

        }
        try {

            return getMoviesDataFromJson(dataJsonStr);

        } catch (JSONException e) {
            Log.e(LOG_TAG, "error parsing Json" + e);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Url not valid: " + e);

        }

        return null;
    }

    private UIModel[] getMoviesDataFromJson(String moviesJsonString) throws JSONException, MalformedURLException {

        JSONObject moviesJson = new JSONObject(moviesJsonString);
        JSONArray moviesArray = moviesJson.getJSONArray(Costants.JSON_MOVIES_RESULT);

        UIModel[] myMovies = new UIModel[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            JSONObject myMovieJSON = moviesArray.getJSONObject(i);


            /**
             * controls object UI model
             */
            FetchTaskControl control = new FetchTaskControl();

            myMovies[i] = new UIModel(
                    control.getPosterURL(
                            myMovieJSON.getString(Costants.JSON_MOVIES_POSTER_PATH)),
                    control.getBackDropUrl(
                            myMovieJSON.getString(Costants.JSON_MOVIES_BACKDROP_PATH)),
                    control.getOverView(
                            myMovieJSON.getString(Costants.JSON_MOVIES_OVERVIEW)),
                    control.getReleaseDateString(
                            myMovieJSON.getString(Costants.JSON_MOVIES_RELEASE_DATE)),
                    control.getTitle(
                            myMovieJSON.getString(Costants.JSON_MOVIES_ORIGINAL_TITLE)),
                    control.getRating(
                            myMovieJSON.getDouble(Costants.JSON_MOVIES_VOTE_AVERAGE))
            );

        }

        return myMovies;
    }


    @Override
    protected void onPostExecute(@Nullable UIModel[] movies) {
        super.onPostExecute(movies);

        sDialog.dismiss();
        if (movies != null) {
            mListener.onReceiveMovies(movies);
        }

    }
}