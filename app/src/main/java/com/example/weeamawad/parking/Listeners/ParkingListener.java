package com.example.weeamawad.parking.Listeners;

import com.example.weeamawad.parking.model.GarageModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public interface ParkingListener {
    void onSuccess(ArrayList<GarageModel> parkingGarageModels);

    void onFailure();
}
