package com.example.weeamawad.parking.databases;

import android.content.Context;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.entities.GarageViewModel;

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

    public void addRecent(GarageViewModel garageViewModel) {
        if (!Utils.isStringEmpty(garageViewModel.getListingID())) {
            RecentParking recentParking = new RecentParking();
            recentParking.listingID = garageViewModel.getListingID();
            recentParking.listingName = garageViewModel.getName();
            recentParking.listingAddress = garageViewModel.getAddress();
            recentParking.listingCity = garageViewModel.getCity();
            recentParking.listingState = garageViewModel.getState();
            recentParking.listingZip = garageViewModel.getZip();
            recentParking.latitude = Double.toString(garageViewModel.getLatitude());
            recentParking.longitude = Double.toString(garageViewModel.getLongitude());
            recentParking.price = Integer.toString(garageViewModel.getPrice());
            mProvider.create(recentParking);
        }
    }

    public void deleteRecent(GarageViewModel garageViewModel) {
        mProvider.delete(RecentParkingSchema.TABLE_NAME, RecentParkingSchema.LISTING_ID + " = ?",
                new String[]{garageViewModel.getListingID()});
    }

    public ArrayList<GarageViewModel> getAllRecents() {
        try {
            List<RecentParking> recentParkingList = mProvider.getAll(RecentParking.class);
            if (!Utils.checkIfNull(recentParkingList)) {
                ArrayList<GarageViewModel> garageViewModels = new ArrayList<>();
                for (RecentParking recent : recentParkingList) {
                    garageViewModels.add(recentToPlace(recent));
                }
                return garageViewModels;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private GarageViewModel recentToPlace(RecentParking recent) {
        GarageViewModel garageViewModel = new GarageViewModel();
        garageViewModel.setListingID(recent.listingID);
        garageViewModel.setName(recent.listingName);
        garageViewModel.setAddress(recent.listingAddress);
        garageViewModel.setCity(recent.listingCity);
        garageViewModel.setState(recent.listingState);
        garageViewModel.setZip(recent.listingZip);
        garageViewModel.setLatitude(Double.parseDouble(recent.latitude));
        garageViewModel.setLongitude(Double.parseDouble(recent.longitude));
        garageViewModel.setPrice(Integer.parseInt(recent.price));
        garageViewModel.formatCompleteAddress();

        return garageViewModel;
    }

    public void deleteAllRecents() {
        mProvider.deleteAllData(RecentParkingSchema.TABLE_NAME);
    }
}
