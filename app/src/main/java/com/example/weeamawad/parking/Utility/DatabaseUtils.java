package com.example.weeamawad.parking.Utility;

import android.content.Context;

import com.example.weeamawad.parking.databases.FavoriteParkingProvider;
import com.example.weeamawad.parking.databases.FilterProvider;
import com.example.weeamawad.parking.databases.RecentParkingProvider;
import com.example.weeamawad.parking.model.FilterModel;
import com.example.weeamawad.parking.model.GarageModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class DatabaseUtils {
    private static DatabaseUtils instance;
    private Context mContext;

    public static void saveFavorite(Context context, GarageModel p) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.addFavorite(p);
    }

    public static void deleteFavorite(Context context, GarageModel p) {
        FavoriteParkingProvider provider = new FavoriteParkingProvider(context);
        provider.deleteFavorite(p);
    }

    public static ArrayList<GarageModel> getAllFavorites(Context context) {
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

    public static void saveRecent(Context context, GarageModel p) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        provider.saveRecent(p);
    }

    public static void deleteRecent(Context context, GarageModel p) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        provider.deleteRecent(p);
    }

    public static ArrayList<GarageModel> getAllRecent(Context context) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        return provider.getAllRecents();
    }

    public static void deleteAllRecents(Context context) {
        RecentParkingProvider provider = new RecentParkingProvider(context);
        provider.deleteAllRecents();
    }

    public static ArrayList<FilterModel> getAllFilters(Context context) {
        FilterProvider provider = new FilterProvider(context);
        return provider.getAllFilters();
    }

    public static void saveAllFilters(Context context, ArrayList<FilterModel> filterModels) {
        FilterProvider provider = new FilterProvider(context);
        provider.deleteAllFilters();
        provider.saveFilters(filterModels);
    }

    public static void deleteFilters(Context context, ArrayList<FilterModel> filterModels) {
        FilterProvider provider = new FilterProvider(context);
        provider.deleteAllFilters();
    }

    public static void saveFilter(Context context, FilterModel filterModel) {
        FilterProvider provider = new FilterProvider(context);
        provider.saveFilter(filterModel);
    }

}


