package com.example.weeamawad.parking.Listeners;

import com.example.weeamawad.parking.entities.Place;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public interface ParkingListener {
    void onSuccess(ArrayList<Place> parkingPlaces);

    void onFailure();
}
