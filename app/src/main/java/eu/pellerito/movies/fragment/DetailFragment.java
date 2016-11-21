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

package eu.pellerito.movies.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import eu.pellerito.movies.R;
import eu.pellerito.movies.model.UIModel;
import eu.pellerito.movies.utility.Costants;

public class DetailFragment extends Fragment {


    /**
     * add costants LOG TAG
     */
    private final String LOG_TAG = DetailFragment.class.getSimpleName();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        UIModel UIModel = getActivity().getIntent().getParcelableExtra(getString(R.string.intent_extra_movie_model));


        // set title
        String title = UIModel.getTitle();

        if (!title.isEmpty()) {

            title = getString(R.string.details_detail_text) +
                    " - " +
                    "\"" +
                    title +
                    "\"";

            getActivity().setTitle(title);
        }


        // BackDrop ImageView Reference
        ImageView backDrop = (ImageView) rootView.findViewById(R.id.detail_back_drop);


        // change image with poster if BackDrop not present

        String imageBackDrop = UIModel.getBackDropUrl().toString();

        // String default error backDrop


        // set imageBackDrop to value Poster if values is null
        if ((imageBackDrop != null) &&
                (imageBackDrop.substring(4).equalsIgnoreCase("null"))) {
            imageBackDrop = UIModel.getPosterUrl().toString();
        }


        // get Orientation 1 Portrait, 2 LandScape
        int orientationScreen = getResources().getConfiguration().orientation;

        if (orientationScreen == 1) {
            // orientation Portrait
            Glide.with(getActivity())
                    .load(imageBackDrop)
                    .override(Costants.GLIDE_IMAGE_BACKDROP_WIDTH, Costants.GLIDE_IMAGE_BACKDROP_HEIGHT)
                    .fitCenter()
                    .placeholder(R.drawable.download_in_progress)
                    .error(R.drawable.no_image)
                    .into(backDrop);

        } else {
            // orientation Landscape
            Glide.with(getActivity())
                    .load(imageBackDrop)
                    .override(Costants.GLIDE_IMAGE_BACKDROP_WIDTH * 2, Costants.GLIDE_IMAGE_BACKDROP_HEIGHT)
                    .centerCrop()
                    .placeholder(R.drawable.download_in_progress)
                    .error(R.drawable.no_image)
                    .into(backDrop);

        }

        // title
        ((TextView) rootView.findViewById(R.id.detail_title)).setText(UIModel.getTitle());

        // OverView
        ((TextView) rootView.findViewById(R.id.detail_overview)).setText(UIModel.getOverview());


        // ReleaseDate
        ((TextView) rootView.findViewById(R.id.detail_release_date))
                .setText(getDateStr(UIModel.getReleaseDate()));

        // rating
        ((RatingBar) rootView.findViewById(R.id.detail_rating)).setRating((float) UIModel.getVote());


        return rootView;
    }

    private String getDateStr(@NonNull String strReleaseDate) {

        String formattedDate;

        if (strReleaseDate.isEmpty()) {

            formattedDate = getString(R.string.movies_details_date_error);

        } else {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Costants.JSON_MOVIES_DATE_FORMAT, Locale.US);

            try {

                Date date = simpleDateFormat.parse(strReleaseDate);
                formattedDate = new SimpleDateFormat(Costants.MOVIE_DETAIL_DATE_FORMAT, Locale.US).format(date);

            } catch (ParseException e) {
                Log.e(LOG_TAG, "parsing date error " + strReleaseDate);
                return null;
            }

        }

        return formattedDate;
    }

}