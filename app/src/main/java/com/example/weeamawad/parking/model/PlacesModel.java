package com.example.weeamawad.parking.model;

import com.example.weeamawad.parking.entities.Place;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class PlacesModel {
    private static ArrayList<Place> mParkingPlaces;

    public static ArrayList<Place> getParkingPlaces() {
        return mParkingPlaces;
    }

    public static void setParkingPlaces(ArrayList<Place> parkingPlaces) {
        mParkingPlaces = parkingPlaces;
    }
}
