package com.example.weeamawad.parking.databases;

import android.content.Context;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.model.GarageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class FavoriteParkingProvider {

    private DatabaseProvider<FavoriteParking> mProvider;
    private Context mContext;

    public FavoriteParkingProvider(Context context) {
        mContext = context;
        mProvider = DatabaseProvider.getInstance(mContext);
    }

    public void addFavorite(GarageModel garageModel) {
        if (!Utils.isStringEmpty(garageModel.getListingID())) {
            FavoriteParking favorite = new FavoriteParking();
            favorite.listingID = garageModel.getListingID();
            favorite.listingName = garageModel.getName();
            favorite.listingAddress = garageModel.getAddress();
            favorite.listingCity = garageModel.getCity();
            favorite.listingState = garageModel.getState();
            favorite.listingZip = garageModel.getZip();
            favorite.latitude = Double.toString(garageModel.getLatitude());
            favorite.longitude = Double.toString(garageModel.getLongitude());
            favorite.price = Integer.toString(garageModel.getPrice());
            mProvider.create(favorite);
        }
    }

    public void deleteFavorite(GarageModel garageModel) {
        mProvider.delete(FavoriteParkingSchema.TABLE_NAME, FavoriteParkingSchema.LISTING_ID + " = ?",
                new String[]{garageModel.getListingID()});
    }

    public ArrayList<GarageModel> getAllFavorites() {
        try {
            List<FavoriteParking> favoriteList = mProvider.getAll(FavoriteParking.class);
            if (!Utils.checkIfNull(favoriteList)) {
                ArrayList<GarageModel> garageModels = new ArrayList<>();
                for (FavoriteParking fp : favoriteList) {
                    garageModels.add(favToPlace(fp));
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

    public boolean checkIfFavorite(String listingID) {
        String queryf = "select * from " + FavoriteParkingSchema.TABLE_NAME + " where " + FavoriteParkingSchema.LISTING_ID + "='" + listingID + "'";
        return mProvider.itemExists(queryf);
    }

    public void deleteAllFavorites() {
        mProvider.deleteAllData(FavoriteParkingSchema.TABLE_NAME);
    }

    private GarageModel favToPlace(FavoriteParking fav) {
        GarageModel garageModel = new GarageModel();
        garageModel.setListingID(fav.listingID);
        garageModel.setName(fav.listingName);
        garageModel.setAddress(fav.listingAddress);
        garageModel.setCity(fav.listingCity);
        garageModel.setState(fav.listingState);
        garageModel.setZip(fav.listingZip);
        garageModel.setLatitude(Double.parseDouble(fav.latitude));
        garageModel.setLongitude(Double.parseDouble(fav.longitude));
        garageModel.setPrice(Integer.parseInt(fav.price));
        garageModel.formatCompleteAddress();

        return garageModel;
    }

}
