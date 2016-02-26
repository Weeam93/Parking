package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.Constants;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.adapters.FavoritesAdapter;
import com.example.weeamawad.parking.model.GarageModel;

import java.util.ArrayList;


/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class FavoritesFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private Context mContext;
    private ArrayList<GarageModel> parkingGarageModels;
    private FavoritesAdapter adapter;
    private RecyclerView rvFavorites;
    private TextView clearData;
    private boolean isFavorites;

    public static Fragment newInstance() {
        FavoritesFragment myFragment = new FavoritesFragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        mContext = getActivity();
        initData();
        initViews();
        initListeners();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_clear_data:
                if (isFavorites) {
                    DatabaseUtils.deleteAllFavorites(mContext);
                } else {
                    DatabaseUtils.deleteAllRecents(mContext);
                }
                parkingGarageModels.clear();
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void initViews() {

        rvFavorites = (RecyclerView) mRootView.findViewById(R.id.lv_favorites);
        clearData = (TextView) mRootView.findViewById(R.id.tv_clear_data);
        adapter = new FavoritesAdapter(parkingGarageModels);
        rvFavorites.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorites.setAdapter(adapter);

        if (isFavorites) {
            clearData.setText(getResources().getString(R.string.clear_favorites));
        } else {
            clearData.setText(getResources().getString(R.string.clear_recents));
        }

    }

    private void initListeners() {
        clearData.setOnClickListener(this);
    }

    private void initData() {
        if (!Utils.checkIfNull(getArguments())) {
            isFavorites = getArguments().getBoolean(Constants.FAVORITES_KEY);
        }
        if (isFavorites) {
            parkingGarageModels = DatabaseUtils.getAllFavorites(mContext);
        } else {
            parkingGarageModels = DatabaseUtils.getAllRecent(mContext);
        }
    }
}
