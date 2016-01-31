package com.example.weeamawad.parking.databases;

/**
 * Created by Weeam Awad on 1/31/2016.
 */
public class RecentParkingSchema {
    public static final String TABLE_NAME = "recents";
    public static final String LISTING_ID = "listing_id";
    public static final String LISTING_NAME = "listing_name";
    public static final String LISTING_ADDRESS = "listing_address";
    public static final String LISTING_CITY = "listing_city";
    public static final String LISTING_STATE = "listing_state";
    public static final String LISTING_ZIP = "listing_zip";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String PRICE = "price";

    public static String getCreateTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + "(");
        sb.append(LISTING_ID + " TEXT NOT NULL, ");
        sb.append(LISTING_NAME + " TEXT, ");
        sb.append(LISTING_ADDRESS + " TEXT, ");
        sb.append(LISTING_CITY + " TEXT, ");
        sb.append(LISTING_STATE + " TEXT, ");
        sb.append(LISTING_ZIP + " TEXT, ");
        sb.append(LATITUDE + " TEXT, ");
        sb.append(LONGITUDE + " TEXT, ");
        sb.append(PRICE + " TEXT");
        sb.append(");");
        return sb.toString();
    }
}
