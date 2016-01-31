package com.example.weeamawad.parking.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.weeamawad.parking.Utility.Utils;

import java.util.ArrayList;
import java.util.List;

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
        if (mInstance == null) {
            mInstance = new DatabaseProvider<T>(context);
        }
        return mInstance;
    }

    private DatabaseProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = getWritableDatabase();
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(FavoriteParkingSchema.getCreateTable());
        db.execSQL(RecentParkingSchema.getCreateTable());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteParkingSchema.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RecentParkingSchema.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Method is used to create table
     *
     * @param model
     * @return variable of type T
     */
    public T create(T model) {
        long rowId = mDatabase.insert(model.getTableName(), null, model.getContentValues());
        String selection = model.getPrimaryKeyName() + " = " + rowId;
        T temp = null;
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                Cursor cursor = mDatabase.query(model.getTableName(),
                        model.getColumns(), selection, null, null, null, null);
                if (!Utils.checkIfNull(cursor) && cursor.moveToFirst()) {
                    temp = model.fromCursor(cursor);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                e.printStackTrace();
            }
        }
        return temp;
    }

    /**
     * Method is used to delete a data item
     *
     * @param tableName
     * @param whereClause
     * @param whereArgs
     * @return zero if database is not open
     */
    public int delete(String tableName, String whereClause, String[] whereArgs) {
        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            return mDatabase.delete(tableName, whereClause, whereArgs);
        }
        return 0;
    }

    /**
     * Method is used to retrieve a row of data
     *
     * @param cls
     * @return rows
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<T> getAll(Class<T> cls) throws InstantiationException, IllegalAccessException {
        List<T> rows = new ArrayList<T>();
        T model = cls.newInstance();

        mDatabase = getWritableDatabase();
        if (mDatabase.isOpen()) {
            try {
                Cursor cursor = mDatabase.query(model.getTableName(),
                        model.getColumns(), null, null, null, null, null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    rows.add((T) model.fromCursor(cursor));
                    cursor.moveToNext();
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rows;
    }
}
