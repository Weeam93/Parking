package com.example.weeamawad.parking.databases;

import android.content.Context;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.model.Place;

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

    public void addFavorite(Place place) {
        if (!Utils.isStringEmpty(place.getListingID())) {
            FavoriteParking favorite = new FavoriteParking();
            favorite.listingID = place.getListingID();
            favorite.listingName = place.getName();
            favorite.listingAddress = place.getAddress();
            favorite.listingCity = place.getCity();
            favorite.listingState = place.getState();
            favorite.listingZip = place.getZip();
            favorite.latitude = Double.toString(place.getLatitude());
            favorite.longitude = Double.toString(place.getLongitude());
            favorite.price = Integer.toString(place.getPrice());
            mProvider.create(favorite);
        }
    }

    public void deleteFavorite(Place place) {
        mProvider.delete(FavoriteParkingSchema.TABLE_NAME, FavoriteParkingSchema.LISTING_ID + " = ?",
                new String[]{place.getListingID()});
    }

    public ArrayList<Place> getAllFavorites() {
        try {
            List<FavoriteParking> favoriteList = mProvider.getAll(FavoriteParking.class);
            if (!Utils.checkIfNull(favoriteList)) {
                ArrayList<Place> places = new ArrayList<>();
                for (FavoriteParking fp : favoriteList) {
                    places.add(favToPlace(fp));
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

    public void deleteAllFavorites() {
        mProvider.deleteAllData(FavoriteParkingSchema.TABLE_NAME);
    }

    private Place favToPlace(FavoriteParking fav) {
        Place place = new Place();
        place.setListingID(fav.listingID);
        place.setName(fav.listingName);
        place.setAddress(fav.listingAddress);
        place.setCity(fav.listingCity);
        place.setState(fav.listingState);
        place.setZip(fav.listingZip);
        place.setLatitude(Double.parseDouble(fav.latitude));
        place.setLongitude(Double.parseDouble(fav.longitude));
        place.setPrice(Double.parseDouble(fav.price));
        place.formatCompleteAddress();

        return place;
    }

}
