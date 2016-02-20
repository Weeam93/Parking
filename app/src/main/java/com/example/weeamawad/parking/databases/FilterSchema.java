package com.example.weeamawad.parking.databases;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterSchema {
    public static final String TABLE_NAME = "filters";
    public static final String FILTER_TYPE = "filter_type";
    public static final String FILTER_DESCRIPTION = "filter_description";
    public static final String FILTER_VALUE = "filter_value";

    public static String getCreateTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + TABLE_NAME + "(");
        sb.append(FILTER_TYPE + " TEXT NOT NULL, ");
        sb.append(FILTER_DESCRIPTION + " TEXT, ");
        sb.append(FILTER_VALUE + " INTEGER ");
        sb.append(");");
        return sb.toString();
    }
}
