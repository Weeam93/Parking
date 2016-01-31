package com.example.weeamawad.parking.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Weeam Awad on 1/15/2016.
 */
public class DatabaseProvider<T extends DatabaseModel> extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "parking.db";
    public static final int DATABASE_VERSION = 1;
    private static DatabaseProvider mInstance;
    private SQLiteDatabase mDatabase;

    /**
     * Method is used to get the instance
     *
     * @param context
     * @param <T>
     * @return
     */
    public synchronized static <T extends DatabaseModel> DatabaseProvider<T> getInstance(Context context) {
        if (mInstance != null) {
            mInstance = new DatabaseProvider<T>(context);
        }
        return mInstance;
    }

    private DatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (mDatabase != null || !mDatabase.isOpen()) {
            mDatabase = getWritableDatabase();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
