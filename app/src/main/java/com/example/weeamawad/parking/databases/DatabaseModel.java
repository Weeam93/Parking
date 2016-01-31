package com.example.weeamawad.parking.databases;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by Weeam Awad on 1/15/2016.
 */
public interface DatabaseModel {
    <T extends DatabaseModel> T fromCursor(Cursor cursor);

    String[] getColumns();

    ContentValues getContentValues();

    String getPrimaryKeyName();

    String getTableName();

    String toString();
}
