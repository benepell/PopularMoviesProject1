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

import android.net.Uri;
import android.support.annotation.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

import eu.pellerito.movies.utility.Costants;

class FetchTaskControl {


    public URL getPosterURL(String path) throws MalformedURLException {

        Uri builtUri;
        // POSTER_PATH  obtain url error image

        if (Costants.MOVIE_DB_IMAGE_BASE_URL.isEmpty()) {
            // PosterDrop_path VALUES NOT 'null'
            builtUri = Uri.parse(Costants.MOVIE_DB_IMAGE_ERROR).buildUpon()
                    .build();

        } else {

            builtUri = Uri.parse(Costants.MOVIE_DB_IMAGE_BASE_URL).buildUpon()
                    .appendEncodedPath(Costants.MOVIE_DB_IMAGE_WIDTH)
                    .appendEncodedPath(path)
                    .build();

        }


        return new URL(builtUri.toString());

    }

    public URL getBackDropUrl(String path) throws MalformedURLException {

        Uri builtUri;


        // POSTER_PATH  obtain url error image

        if (Costants.MOVIE_DB_IMAGE_BASE_URL.isEmpty()) {
            // BackDrop_path VALUES NOT 'null'
            builtUri = Uri.parse(Costants.MOVIE_DB_IMAGE_ERROR).buildUpon()
                    .build();

        } else {

            // BackDrop_path VALUES NOT 'null'
            builtUri = Uri.parse(Costants.MOVIE_DB_IMAGE_BASE_URL).buildUpon()
                    .appendEncodedPath(Costants.MOVIE_DB_IMAGE_BACKDROP_WIDTH)
                    .appendEncodedPath(path)
                    .build();

        }

        return new URL(builtUri.toString());

    }


    @Nullable
    public String getOverView(@Nullable String overView) {

        String overViewStr = "";

        if (overView != null) {
            overViewStr = overView;
        }

        return overViewStr;

    }


    @Nullable
    public String getReleaseDateString(@Nullable String releaseDateString) {

        String relDateStr;

        if ((releaseDateString == null) ||
                releaseDateString.isEmpty() ||
                (releaseDateString.equalsIgnoreCase("null"))) {

            // Default Value Error
            relDateStr = "";

        } else {
            relDateStr = releaseDateString;
        }
        return relDateStr;
    }

    @Nullable
    public String getTitle(@Nullable String titleStr) {

        String title;

        if ((titleStr == null) ||
                titleStr.isEmpty() ||
                (titleStr.equalsIgnoreCase("null"))) {
            // Default Value Error
            title = "";

        } else {

            title = titleStr;
        }

        return title;
    }

    public int getRating(double rating) {

        int ratingValue;

        if (Double.isNaN(rating)) {

            // Default Value Error
            ratingValue = 0;

        } else {

            ratingValue = (int) (Math.round(rating) * 0.5);
        }

        return ratingValue;
    }


}
