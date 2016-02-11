package com.example.weeamawad.parking.model;

import com.example.weeamawad.parking.entities.GarageViewModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class PlacesModel {
    private static ArrayList<GarageViewModel> mParkingGarageViewModels;

    public static ArrayList<GarageViewModel> getParkingPlaces() {
        return mParkingGarageViewModels;
    }

    public static void setParkingPlaces(ArrayList<GarageViewModel> parkingGarageViewModels) {
        mParkingGarageViewModels = parkingGarageViewModels;
    }
}
