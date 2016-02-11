package com.example.weeamawad.parking.Listeners;

import com.example.weeamawad.parking.entities.GarageViewModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public interface ParkingListener {
    void onSuccess(ArrayList<GarageViewModel> parkingGarageViewModels);

    void onFailure();
}
