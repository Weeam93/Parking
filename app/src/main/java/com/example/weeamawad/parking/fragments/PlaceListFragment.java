package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.adapters.PlaceAdapter;
import com.example.weeamawad.parking.model.Place;
import com.example.weeamawad.parking.model.PlacesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlaceListFragment extends FragmentActivity implements View.OnClickListener {

    private ListView list;
    private ArrayList<Place> parkingPlaces;
    private Button distanceBtn;
    private Button priceBtn;
    private PlaceAdapter adapter;
    private Context mContext;
    private int btnSelectedTxtColor;
    private int btnDefualtTxtColor;
    private ImageButton mapBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_list);
        initData();
        initViews();
        initListeners();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ib_mapButton:
                Intent mapIntent = new Intent(this, MapFragment.class);
                startActivity(mapIntent);
                break;
            case R.id.distanceBtn:
                distanceBtn.setTextColor(btnSelectedTxtColor);
                priceBtn.setTextColor(btnDefualtTxtColor);
                Collections.sort(parkingPlaces, new Comparator<Place>() {

                    @Override
                    public int compare(Place p1, Place p2) {
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
                Collections.sort(parkingPlaces, new Comparator<Place>() {

                    @Override
                    public int compare(Place p1, Place p2) {
                        return (p1.getPrice() - p2.getPrice());
                    }

                });
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private void initViews() {

        list = (ListView) findViewById(R.id.placeList);
        distanceBtn = (Button) findViewById(R.id.distanceBtn);
        priceBtn = (Button) findViewById(R.id.priceBtn);
        mapBtn = (ImageButton) findViewById(R.id.ib_mapButton);
        adapter = new PlaceAdapter(this, R.layout.custom_place_view, parkingPlaces);
        list.setAdapter(adapter);

    }

    private void initListeners() {
        distanceBtn.setOnClickListener(this);
        priceBtn.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
    }

    private void initData() {
        mContext = this;
        parkingPlaces = PlacesModel.getParkingPlaces();
        btnSelectedTxtColor = getResources().getColor(R.color.aqua);
        btnDefualtTxtColor = getResources().getColor(R.color.white);
    }

}
