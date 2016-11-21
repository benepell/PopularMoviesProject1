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

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

import eu.pellerito.movies.R;
import eu.pellerito.movies.activity.DetailActivity;
import eu.pellerito.movies.adapter.UIAdapter;
import eu.pellerito.movies.data.FetchTask;
import eu.pellerito.movies.interfaces.UIListener;
import eu.pellerito.movies.model.UIModel;
import eu.pellerito.movies.utility.Costants;
import eu.pellerito.movies.utility.NetworkState;
import eu.pellerito.movies.utility.PrefUtil;

public class BaseFragment extends Fragment implements UIListener {

    private UIAdapter mUIAdapter;
    @Nullable
    private String mOrder;

    /**
     *  add costants LOG TAG
     */
    //private final String LOG_TAG = BaseFragment.class.getSimpleName();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add method to fragment menu events
        setHasOptionsMenu(true);

        // Read SharedPreference and initialize value
        mOrder = new PrefUtil(getActivity(), getString(R.string.pref_key_sort))
                .getString(getString(R.string.pref_key_sort));


        // initialize default
        if ((mOrder != null) && mOrder.isEmpty()) {
            mOrder = Costants.SORTBY_DEFAULT_PARAM;
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        switch (item.getItemId()) {

            case R.id.menu_sort_popularity:

                mOrder = String.valueOf(Costants.POPULARITY_DESC);

                // update date
                updateUI();

                /*
                * click checked controls
                * */
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                return true;

            case R.id.menu_sort_rating:

                mOrder = String.valueOf(Costants.VOTE_AVERAGE_DESC);

                // update date
                updateUI();
                /*
                * click checked controls
                * */
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // compare SharedPreference ...
        if ((mOrder != null) && mOrder.equalsIgnoreCase(String.valueOf(Costants.VOTE_AVERAGE_DESC))) {
            menu.findItem(R.id.menu_sort_rating).setChecked(true);
        } else {
            menu.findItem(R.id.menu_sort_popularity).setChecked(true);
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_base, container, false);

        mUIAdapter = new UIAdapter(getActivity());

        GridView gridView = (GridView) rootView.findViewById(R.id.my_movie_grid);
        gridView.setAdapter(mUIAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Parcelable movie = mUIAdapter.getItem(i);


                Intent detailActivity = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("UIModel", movie);
                startActivity(detailActivity);

            }
        });

        return rootView;
    }

    @Override
    public void onReceiveMovies(@NonNull UIModel[] movies) {

        ArrayList<UIModel> moviesList = new ArrayList<>(movies.length);

        Collections.addAll(moviesList, movies);

        mUIAdapter.addMovies(moviesList);
        mUIAdapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();

    }

    @Override
    public void onStop() {
        super.onStop();

        // save SharedPreferences

        new PrefUtil(getActivity(), getString(R.string.pref_key_sort))
                .putString(getString(R.string.pref_key_sort), mOrder);

    }

    private void updateUI() {

        ImageView noInternetImage = (ImageView) getActivity().findViewById(R.id.no_internet_imageView);

        // adapter work clear and send
        mUIAdapter.notifyDataSetChanged();
        mUIAdapter.clear();


        // network state validation
        if (new NetworkState(getActivity()).isConnected()) {
            noInternetImage.setVisibility(View.INVISIBLE);
            new FetchTask(getActivity(), this).execute(mOrder);

        } else {
            noInternetImage.setVisibility(View.VISIBLE);

            Glide.with(getActivity())
                    .load(R.drawable.no_internet)
                    .into(noInternetImage);


            Toast.makeText(getActivity(), R.string.network_state_not_connected, Toast.LENGTH_LONG).show();
        }
    }
}
