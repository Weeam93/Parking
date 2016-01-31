package com.example.weeamawad.parking.databases;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class FavoriteParking implements DatabaseModel {
    public String listingID;
    public String listingName;
    public String listingCity;
    public String listingState;
    public String listingZip;
    public String latitude;
    public String longitude;

    public static final String TABLE_NAME = "favorites";
    public static final String LISTING_ID = "listing_id";
    public static final String LISTING_NAME = "listing_name";
    public static final String LISTING_CITY = "listing_city";
    public static final String LISTING_STATE = "listing_state";
    public static final String LISTING_ZIP = "listing_zip";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";

    @Override
    public <T extends DatabaseModel> T fromCursor(Cursor cursor) {
        if (cursor != null) {
            FavoriteParking parking = new FavoriteParking();
            parking.listingID = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LISTING_ID));
            parking.listingName = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LISTING_NAME));
            parking.listingCity = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LISTING_CITY));
            parking.listingState = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LISTING_STATE));
            parking.listingZip = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LISTING_ZIP));
            parking.latitude = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LATITUDE));
            parking.longitude = cursor.getString(cursor.getColumnIndex(FavoriteParkingSchema.LONGITUDE));
            return (T) parking;
        }
        return null;
    }

    @Override
    public String[] getColumns() {
        return new String[]{
                FavoriteParkingSchema.LISTING_ID,
                FavoriteParkingSchema.LISTING_NAME,
                FavoriteParkingSchema.LISTING_CITY,
                FavoriteParkingSchema.LISTING_STATE,
                FavoriteParkingSchema.LISTING_ZIP,
                FavoriteParkingSchema.LATITUDE,
                FavoriteParkingSchema.LONGITUDE,
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FavoriteParkingSchema.LISTING_ID, listingID);
        values.put(FavoriteParkingSchema.LISTING_NAME, listingName);
        values.put(FavoriteParkingSchema.LISTING_CITY, listingCity);
        values.put(FavoriteParkingSchema.LISTING_STATE, listingState);
        values.put(FavoriteParkingSchema.LISTING_ZIP, listingZip);
        values.put(FavoriteParkingSchema.LATITUDE, latitude);
        values.put(FavoriteParkingSchema.LONGITUDE, longitude);
        return values;
    }

    @Override
    public String getPrimaryKeyName() {
        return listingID;
    }

    @Override
    public String getTableName() {
        return FavoriteParkingSchema.TABLE_NAME;
    }
}
