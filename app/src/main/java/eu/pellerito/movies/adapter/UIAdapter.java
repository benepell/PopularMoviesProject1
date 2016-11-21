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

package eu.pellerito.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import eu.pellerito.movies.R;
import eu.pellerito.movies.model.UIModel;
import eu.pellerito.movies.utility.Costants;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public class UIAdapter extends BaseAdapter {

    private final Context mContext;
    @NonNull
    private final List<UIModel> mImageUrls;


    public UIAdapter(Context context) {
        mContext = context;
        mImageUrls = new ArrayList<>();
    }

    /**
     * add costants LOG TAG
     */
    // private final String LOG_TAG = UIAdapter.class.getSimpleName();
    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public UIModel getItem(int i) {
        return mImageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = (ImageView) view;


        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(
                    Costants.MOVIE_DB_IMAGE_GRID_VIEW_WIDTH,
                    Costants.MOVIE_DB_IMAGE_GRID_VIEW_HEIGHT));
            imageView.setScaleType(CENTER_CROP);
        }

        // get Orientation 1 Portrait, 2 LandScape
        int mOrientation = mContext.getResources().getConfiguration().orientation;


        if (getItem(i).getPosterUrl() != null) {

            // PORTRAIT
            if (mOrientation == 1) {

                Glide.with(mContext)
                        .load(getItem(i).getPosterUrl().toString())
                        .override(Costants.MOVIE_DB_IMAGE_GRID_VIEW_WIDTH, Costants.MOVIE_DB_IMAGE_GRID_VIEW_HEIGHT)
                        .fitCenter()
                        .placeholder(R.drawable.download_in_progress)
                        .error(R.drawable.no_image)
                        .into(imageView);

            } else {
                //Landscape

                Glide.with(mContext)
                        .load(getItem(i).getPosterUrl().toString())
                        .override(Costants.MOVIE_DB_IMAGE_GRID_VIEW_WIDTH_LAND, Costants.MOVIE_DB_IMAGE_GRID_VIEW_HEIGHT_LAND)
                        .fitCenter()
                        .placeholder(R.drawable.download_in_progress)
                        .error(R.drawable.no_image)
                        .into(imageView);


            }

        }

        return imageView;
    }

    public void addMovies(@NonNull List<UIModel> urls) {

        mImageUrls.addAll(urls);
    }

    public void clear() {

        mImageUrls.clear();
    }

}
