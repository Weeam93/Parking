package com.example.weeamawad.parking.databases;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class Filter implements DatabaseModel {

    public String filterType;
    public String filterDescription;
    public int filterValue;

    @Override
    public <T extends DatabaseModel> T fromCursor(Cursor cursor) {
        if (cursor != null) {
            Filter filter = new Filter();
            filter.filterType = cursor.getString(cursor.getColumnIndex(FilterSchema.FILTER_TYPE));
            filter.filterDescription = cursor.getString(cursor.getColumnIndex(FilterSchema.FILTER_DESCRIPTION));
            filter.filterValue = cursor.getInt(cursor.getColumnIndex(FilterSchema.FILTER_VALUE));
            return (T) filter;
        }
        return null;
    }

    @Override
    public String[] getColumns() {
        return new String[]{
                FilterSchema.FILTER_TYPE,
                FilterSchema.FILTER_DESCRIPTION,
                FilterSchema.FILTER_VALUE,
        };
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(FilterSchema.FILTER_TYPE, filterType);
        values.put(FilterSchema.FILTER_DESCRIPTION, filterDescription);
        values.put(FilterSchema.FILTER_VALUE, filterValue);
        return values;
    }

    @Override
    public String getPrimaryKeyName() {
        return null;
    }

    @Override
    public String getTableName() {
        return FilterSchema.TABLE_NAME;
    }
}
