package com.example.weeamawad.parking.databases;

import android.content.Context;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.entities.Place;

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

    public void addRecent(Place place) {
        if (!Utils.isStringEmpty(place.getListingID())) {
            RecentParking recentParking = new RecentParking();
            recentParking.listingID = place.getListingID();
            recentParking.listingName = place.getName();
            recentParking.listingAddress = place.getAddress();
            recentParking.listingCity = place.getCity();
            recentParking.listingState = place.getState();
            recentParking.listingZip = place.getZip();
            recentParking.latitude = Double.toString(place.getLatitude());
            recentParking.longitude = Double.toString(place.getLongitude());
            recentParking.price = Integer.toString(place.getPrice());
            mProvider.create(recentParking);
        }
    }

    public void deleteRecent(Place place) {
        mProvider.delete(RecentParkingSchema.TABLE_NAME, RecentParkingSchema.LISTING_ID + " = ?",
                new String[]{place.getListingID()});
    }

    public ArrayList<Place> getAllRecents() {
        try {
            List<RecentParking> recentParkingList = mProvider.getAll(RecentParking.class);
            if (!Utils.checkIfNull(recentParkingList)) {
                ArrayList<Place> places = new ArrayList<>();
                for (RecentParking recent : recentParkingList) {
                    places.add(recentToPlace(recent));
                }
                return places;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Place recentToPlace(RecentParking recent) {
        Place place = new Place();
        place.setListingID(recent.listingID);
        place.setName(recent.listingName);
        place.setAddress(recent.listingAddress);
        place.setCity(recent.listingCity);
        place.setState(recent.listingState);
        place.setZip(recent.listingZip);
        place.setLatitude(Double.parseDouble(recent.latitude));
        place.setLongitude(Double.parseDouble(recent.longitude));
        place.setPrice(Double.parseDouble(recent.price));
        place.formatCompleteAddress();

        return place;
    }

    public void deleteAllRecents() {
        mProvider.deleteAllData(RecentParkingSchema.TABLE_NAME);
    }
}
