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

package eu.pellerito.movies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class UIModel implements Parcelable {

    private final String mTitle;
    private final String mOverview;
    private final String mReleaseDate;
    private final int mVote;
    private final URL mPosterUrl;
    private final URL mBackDropUrl;


    /**
     *  add costants LOG TAG
     */
    private static final String LOG_TAG = UIModel.class.getSimpleName();


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int flags) {

        if (mTitle != null) out.writeString(mTitle);
        if (mOverview != null) out.writeString(mOverview);
        if (mReleaseDate != null) out.writeString(mReleaseDate);
        out.writeInt(mVote);
        if (mPosterUrl != null) out.writeString(mPosterUrl.toString());
        if (mBackDropUrl != null) out.writeString(mBackDropUrl.toString());

    }


    @Nullable
    public static final Parcelable.Creator<UIModel> CREATOR
            = new Parcelable.Creator<UIModel>() {
        public UIModel createFromParcel(@NonNull Parcel in) {

            try {
                String name = in.readString();
                String overview = in.readString();
                String release = in.readString();
                int vote = in.readInt();
                URL url = new URL(in.readString());
                URL backdrop = new URL(in.readString());

                return new UIModel(
                        url,
                        backdrop,
                        overview,
                        release,
                        name,
                        vote

                );
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Failed to parse url.");
                return null;
            }
        }

        @NonNull
        public UIModel[] newArray(int size) {
            return new UIModel[size];
        }
    };


    public UIModel(URL posterUrl, URL backdropUrl, String overview, String releaseDate, String title, int vote) {

        mPosterUrl = posterUrl;
        mBackDropUrl = backdropUrl;
        mOverview = overview;
        mReleaseDate = releaseDate;
        mTitle = title;
        mVote = vote;

    }


    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public int getVote() {
        return mVote;
    }

    public URL getPosterUrl() {
        return mPosterUrl;
    }

    public URL getBackDropUrl() {
        return mBackDropUrl;
    }
}
