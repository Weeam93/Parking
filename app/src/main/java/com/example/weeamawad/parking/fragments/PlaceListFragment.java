package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.adapters.NearyByPlacesAdapter;
import com.example.weeamawad.parking.model.AppSettingsModel;
import com.example.weeamawad.parking.model.GarageModel;
import com.example.weeamawad.parking.model.PlacesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlaceListFragment extends Fragment implements View.OnClickListener {

    private ListView list;
    private ArrayList<GarageModel> parkingGarageModels;
    private Button distanceBtn;
    private Button priceBtn;
    private NearyByPlacesAdapter adapter;
    private Context mContext;
    private int btnSelectedTxtColor;
    private int btnDefualtTxtColor;
    private ImageButton mapBtn;
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.place_list, container, false);
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
            case R.id.distanceBtn:
                distanceBtn.setTextColor(btnSelectedTxtColor);
                priceBtn.setTextColor(btnDefualtTxtColor);
                Collections.sort(parkingGarageModels, new Comparator<GarageModel>() {

                    @Override
                    public int compare(GarageModel p1, GarageModel p2) {
                        if (p1.getDistance() < p2.getDistance()) {
                            return -1;
                        } else if (p1.getDistance() > p2.getDistance()) {
                            return 1;
                        }
                        return 0;
                    }

                });

                adapter.notifyDataSetChanged();
                break;
            case R.id.priceBtn:
                distanceBtn.setTextColor(btnDefualtTxtColor);
                priceBtn.setTextColor(btnSelectedTxtColor);
                Collections.sort(parkingGarageModels, new Comparator<GarageModel>() {

                    @Override
                    public int compare(GarageModel p1, GarageModel p2) {
                        return (p1.getPrice() - p2.getPrice());
                    }

                });
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void initViews() {

        list = (ListView) mRootView.findViewById(R.id.placeList);
        distanceBtn = (Button) mRootView.findViewById(R.id.distanceBtn);
        priceBtn = (Button) mRootView.findViewById(R.id.priceBtn);
        mapBtn = (ImageButton) mRootView.findViewById(R.id.ib_mapButton);
        adapter = new NearyByPlacesAdapter(mContext, R.layout.custom_place_view, parkingGarageModels);
        list.setAdapter(adapter);

    }

    private void initListeners() {
        distanceBtn.setOnClickListener(this);
        priceBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
    }

    private void initData() {
        mContext = getActivity();
        if (AppSettingsModel.isFavPage) {
            parkingGarageModels = DatabaseUtils.getAllFavorites(mContext);

        } else {
            parkingGarageModels = PlacesModel.getParkingPlaces();

        }
        btnSelectedTxtColor = getResources().getColor(R.color.aqua);
        btnDefualtTxtColor = getResources().getColor(R.color.white);
    }

}
