package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.adapters.FavoritesAdapter;
import com.example.weeamawad.parking.model.Place;

import java.util.ArrayList;


/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class FavoritesFragment extends Fragment implements View.OnClickListener {

    private View mRootView;
    private Context mContext;
    private ArrayList<Place> parkingPlaces;
    private FavoritesAdapter adapter;
    private ListView list;

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
            case R.id.ib_mapButton:
                Intent mapIntent = new Intent(getActivity(), MapFragment.class);
                startActivity(mapIntent);
                break;
        }
    }

    private void initViews() {

        list = (ListView) mRootView.findViewById(R.id.lv_favorites);
        adapter = new FavoritesAdapter(mContext, R.layout.row_favorites, parkingPlaces);
        list.setAdapter(adapter);

    }

    private void initListeners() {
    }

    private void initData() {
        mContext = getActivity();
        parkingPlaces = DatabaseUtils.getAllFavorites(mContext);
    }
}
