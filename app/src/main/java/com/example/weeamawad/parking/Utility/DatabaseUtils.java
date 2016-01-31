package com.example.weeamawad.parking.Utility;

import android.content.Context;
import android.os.AsyncTask;

import com.example.weeamawad.parking.databases.FavoriteParkingProvider;
import com.example.weeamawad.parking.model.Place;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class DatabaseUtils {
    private static DatabaseUtils instance;
    private Context mContext;

    public static void saveFavorite(Context context, Place p) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.addFavorite(p);
    }
    public static void deleteFavorite(Context context, Place p) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.deleteFavorite(p);
    }
}


