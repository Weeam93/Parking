package com.example.weeamawad.parking.Utility;

import android.content.Context;

import com.example.weeamawad.parking.databases.FavoriteParkingProvider;
import com.example.weeamawad.parking.databases.RecentParkingProvider;
import com.example.weeamawad.parking.entities.GarageViewModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class DatabaseUtils {
    private static DatabaseUtils instance;
    private Context mContext;

    public static void saveFavorite(Context context, GarageViewModel p) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.addFavorite(p);
    }

    public static void deleteFavorite(Context context, GarageViewModel p) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.deleteFavorite(p);
    }

    public static ArrayList<GarageViewModel> getAllFavorites(Context context) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        return provider.getAllFavorites();
    }

    public static void deleteAllFavorites(Context context) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.deleteAllFavorites();
    }

    public static boolean isFavorite(Context context, String listingID) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        return provider.checkIfFavorite(listingID);
    }

    public static void saveRecent(Context context, GarageViewModel p) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        provider.addRecent(p);
    }

    public static void deleteRecent(Context context, GarageViewModel p) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        provider.deleteRecent(p);
    }

    public static ArrayList<GarageViewModel> getAllRecent(Context context) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        return provider.getAllRecents();
    }

    public static void deleteAllRecents(Context context) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        provider.deleteAllRecents();
    }

}


