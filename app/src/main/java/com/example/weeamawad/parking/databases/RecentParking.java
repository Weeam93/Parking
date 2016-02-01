package com.example.weeamawad.parking.databases;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Weeam Awad on 1/31/2016.
 */
public class RecentParking implements DatabaseModel {
    public String listingID;
    public String listingName;
    public String listingAddress;
    public String listingCity;
    public String listingState;
    public String listingZip;
    public String latitude;
    public String longitude;
    public String price;

    @Override
    public <T extends DatabaseModel> T fromCursor(Cursor cursor) {
        if (cursor != null) {
            RecentParking parking = new RecentParking();
            parking.listingID = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LISTING_ID));
            parking.listingName = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LISTING_NAME));
            parking.listingAddress = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LISTING_ADDRESS));
            parking.listingCity = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LISTING_CITY));
            parking.listingState = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LISTING_STATE));
            parking.listingZip = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LISTING_ZIP));
            parking.latitude = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LATITUDE));
            parking.longitude = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.LONGITUDE));
            parking.price = cursor.getString(cursor.getColumnIndex(RecentParkingSchema.PRICE));
            return (T) parking;
        }
        return null;
    }

    @Override
    public String[] getColumns() {
        return new String[]{
                RecentParkingSchema.LISTING_ID,
                RecentParkingSchema.LISTING_NAME,
                RecentParkingSchema.LISTING_ADDRESS,
                RecentParkingSchema.LISTING_CITY,
                RecentParkingSchema.LISTING_STATE,
                RecentParkingSchema.LISTING_ZIP,
                RecentParkingSchema.LATITUDE,
                RecentParkingSchema.LONGITUDE,
                RecentParkingSchema.PRICE,
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(RecentParkingSchema.LISTING_ID, listingID);
        values.put(RecentParkingSchema.LISTING_NAME, listingName);
        values.put(RecentParkingSchema.LISTING_ADDRESS, listingAddress);
        values.put(RecentParkingSchema.LISTING_CITY, listingCity);
        values.put(RecentParkingSchema.LISTING_STATE, listingState);
        values.put(RecentParkingSchema.LISTING_ZIP, listingZip);
        values.put(RecentParkingSchema.LATITUDE, latitude);
        values.put(RecentParkingSchema.LONGITUDE, longitude);
        values.put(RecentParkingSchema.PRICE, price);
        return values;
    }

    @Override
    public String getPrimaryKeyName() {
        return listingID;
    }

    @Override
    public String getTableName() {
        return RecentParkingSchema.TABLE_NAME;
    }
}
