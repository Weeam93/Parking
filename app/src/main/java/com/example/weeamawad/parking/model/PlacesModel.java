package com.example.weeamawad.parking.model;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class PlacesModel {
    private static ArrayList<GarageModel> mParkingGarageModels;

    public static ArrayList<GarageModel> getParkingPlaces() {
        return mParkingGarageModels;
    }

    public static void setParkingPlaces(ArrayList<GarageModel> parkingGarageModels) {
        mParkingGarageModels = parkingGarageModels;
    }
}
