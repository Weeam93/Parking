package com.example.weeamawad.parking.databases;

import android.content.Context;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.model.GarageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weeam Awad on 1/31/2016.
 */
public class RecentParkingProvider {

    private DatabaseProvider<RecentParking> mProvider;
    private Context mContext;

    public RecentParkingProvider(Context context) {
        mContext = context;
        mProvider = DatabaseProvider.getInstance(mContext);
    }

    public void saveRecent(GarageModel garageModel) {
        if (!Utils.isStringEmpty(garageModel.getListingID())) {
            RecentParking recentParking = new RecentParking();
            recentParking.listingID = garageModel.getListingID();
            recentParking.listingName = garageModel.getName();
            recentParking.listingAddress = garageModel.getAddress();
            recentParking.listingCity = garageModel.getCity();
            recentParking.listingState = garageModel.getState();
            recentParking.listingZip = garageModel.getZip();
            recentParking.latitude = Double.toString(garageModel.getLatitude());
            recentParking.longitude = Double.toString(garageModel.getLongitude());
            recentParking.price = Integer.toString(garageModel.getPrice());
            mProvider.create(recentParking);
        }
    }

    public void deleteRecent(GarageModel garageModel) {
        mProvider.delete(RecentParkingSchema.TABLE_NAME, RecentParkingSchema.LISTING_ID + " = ?",
                new String[]{garageModel.getListingID()});
    }

    public ArrayList<GarageModel> getAllRecents() {
        try {
            List<RecentParking> recentParkingList = mProvider.getAll(RecentParking.class);
            if (!Utils.checkIfNull(recentParkingList)) {
                ArrayList<GarageModel> garageModels = new ArrayList<>();
                for (RecentParking recent : recentParkingList) {
                    garageModels.add(recentToPlace(recent));
                }
                return garageModels;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GarageModel recentToPlace(RecentParking recent) {
        GarageModel garageModel = new GarageModel();
        garageModel.setListingID(recent.listingID);
        garageModel.setName(recent.listingName);
        garageModel.setAddress(recent.listingAddress);
        garageModel.setCity(recent.listingCity);
        garageModel.setState(recent.listingState);
        garageModel.setZip(recent.listingZip);
        garageModel.setLatitude(Double.parseDouble(recent.latitude));
        garageModel.setLongitude(Double.parseDouble(recent.longitude));
        garageModel.setPrice(Integer.parseInt(recent.price));
        garageModel.formatCompleteAddress();

        return garageModel;
    }

    public void deleteAllRecents() {
        mProvider.deleteAllData(RecentParkingSchema.TABLE_NAME);
    }
}
