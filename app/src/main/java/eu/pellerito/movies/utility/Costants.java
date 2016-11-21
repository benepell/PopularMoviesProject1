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

/**
 * Defines several constants used in application
 */

package eu.pellerito.movies.utility;

public class Costants {

    private Costants() {
    } // use private Constructor to prevents instantiation of class


    /**
     * SHARED PREFERENCES -
     */

    public static final String MOVIE_PREF_KEY_DEFAULT = "KEY";


    /**
     * CATEGORY - MENU -
     */
    public static final String POPULARITY_DESC = "popularity.desc";
    public static final String VOTE_AVERAGE_DESC = "vote_average.desc";
    public static final String SORTBY_DEFAULT_PARAM = "popularity.desc";

    /**
     * COSTANTS FETCH TASK - NETWORK
     */

    public static final int CONN_READ_TIMEOUT = 10000;
    public static final int CONN_TIMEOUT = 15000;
    public static final String CONN_METHOD = "GET";

    /**
     * Costants URI
     * Possible parameters are avaiable at
     * https://www.themoviedb.org/documentation/api/discover
     */

    public static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org";
    public static final String MOVIE_DB_VERSION = "3";
    public static final String MOVIE_DB_MODE = "discover";
    public static final String MOVIE_DB_TYPE = "movie";
    public static final String SORTBY_PARAM = "sort_by";
    public static final String APPID_PARAM = "api_key";

    /**
     * FETCH TASK - JSON - DATE FORMAT
     */

    public static final String JSON_MOVIES_RESULT = "results";
    public static final String JSON_MOVIES_ORIGINAL_TITLE = "original_title";
    public static final String JSON_MOVIES_POSTER_PATH = "poster_path";
    public static final String JSON_MOVIES_BACKDROP_PATH = "backdrop_path";
    public static final String JSON_MOVIES_OVERVIEW = "overview";
    public static final String JSON_MOVIES_VOTE_AVERAGE = "vote_average";
    public static final String JSON_MOVIES_RELEASE_DATE = "release_date";

    public static final String JSON_MOVIES_DATE_FORMAT = "yyyy-MM-dd";
    public static final String MOVIE_DETAIL_DATE_FORMAT = "MMM dd, yyyy";

    /**
     * IMAGE - GRID VIEW DIMENSION
     */
    public static final String MOVIE_DB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String MOVIE_DB_IMAGE_ERROR = "http://image.tmdb.org/error/error.jpg";
    public static final String MOVIE_DB_IMAGE_WIDTH = "w185";
    public static final String MOVIE_DB_IMAGE_BACKDROP_WIDTH = "w500";

    public static final int MOVIE_DB_IMAGE_GRID_VIEW_WIDTH = 500;
    public static final int MOVIE_DB_IMAGE_GRID_VIEW_HEIGHT = 750;
    public static final int MOVIE_DB_IMAGE_GRID_VIEW_WIDTH_LAND = 600;
    public static final int MOVIE_DB_IMAGE_GRID_VIEW_HEIGHT_LAND = 900;

    public static final int GLIDE_IMAGE_BACKDROP_WIDTH = 800;
    public static final int GLIDE_IMAGE_BACKDROP_HEIGHT = 600;


}
