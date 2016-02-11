package com.example.weeamawad.parking.databases;

import android.content.Context;
import android.database.Cursor;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.entities.GarageViewModel;

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

    public void addFavorite(GarageViewModel garageViewModel) {
        if (!Utils.isStringEmpty(garageViewModel.getListingID())) {
            FavoriteParking favorite = new FavoriteParking();
            favorite.listingID = garageViewModel.getListingID();
            favorite.listingName = garageViewModel.getName();
            favorite.listingAddress = garageViewModel.getAddress();
            favorite.listingCity = garageViewModel.getCity();
            favorite.listingState = garageViewModel.getState();
            favorite.listingZip = garageViewModel.getZip();
            favorite.latitude = Double.toString(garageViewModel.getLatitude());
            favorite.longitude = Double.toString(garageViewModel.getLongitude());
            favorite.price = Integer.toString(garageViewModel.getPrice());
            mProvider.create(favorite);
        }
    }

    public void deleteFavorite(GarageViewModel garageViewModel) {
        mProvider.delete(FavoriteParkingSchema.TABLE_NAME, FavoriteParkingSchema.LISTING_ID + " = ?",
                new String[]{garageViewModel.getListingID()});
    }

    public ArrayList<GarageViewModel> getAllFavorites() {
        try {
            List<FavoriteParking> favoriteList = mProvider.getAll(FavoriteParking.class);
            if (!Utils.checkIfNull(favoriteList)) {
                ArrayList<GarageViewModel> garageViewModels = new ArrayList<>();
                for (FavoriteParking fp : favoriteList) {
                    garageViewModels.add(favToPlace(fp));
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

    public boolean checkIfFavorite(String listingID) {
        String queryf = "select * from " + FavoriteParkingSchema.TABLE_NAME + " where " + FavoriteParkingSchema.LISTING_ID + "='" + listingID + "'";
        return mProvider.itemExists(queryf);
    }

    public void deleteAllFavorites() {
        mProvider.deleteAllData(FavoriteParkingSchema.TABLE_NAME);
    }

    private GarageViewModel favToPlace(FavoriteParking fav) {
        GarageViewModel garageViewModel = new GarageViewModel();
        garageViewModel.setListingID(fav.listingID);
        garageViewModel.setName(fav.listingName);
        garageViewModel.setAddress(fav.listingAddress);
        garageViewModel.setCity(fav.listingCity);
        garageViewModel.setState(fav.listingState);
        garageViewModel.setZip(fav.listingZip);
        garageViewModel.setLatitude(Double.parseDouble(fav.latitude));
        garageViewModel.setLongitude(Double.parseDouble(fav.longitude));
        garageViewModel.setPrice(Integer.parseInt(fav.price));
        garageViewModel.formatCompleteAddress();

        return garageViewModel;
    }

}
