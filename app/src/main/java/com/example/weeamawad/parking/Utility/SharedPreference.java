package com.example.weeamawad.parking.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class SharedPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    /**
     * @param context
     * @param prefName name of preference
     */
    public SharedPreference(Context context, String prefName) {
        this.sharedPreferences = context.getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    /**
     *  Method for clearing all data of preference.
     */
    public void clearAllPreferences(){
        prefsEditor.clear();
        prefsEditor.commit();
    }

    /**
     *  Method for remove data of preference.
     */
    public void removePreference(String key){
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

    /**
     * @param key
     * @param value String value
     */
    public void setPref(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    /**
     * @param key
     * @param defValue
     * @return String Type
     */
    public String getStringPref(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    /**
     *
     * @param key
     * @param value : boolean value
     */
    public void setPref(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    /**
     *
     * @param key
     * @return boolean type
     */
    public boolean getBooleanPref(String key, boolean defValue){
        return sharedPreferences.getBoolean(key, defValue);
    }

    /**
     *
     * @param key
     * @param value : float value
     */
    public void setPref(String key, float value) {
        prefsEditor.putFloat(key, value);
        prefsEditor.commit();
    }

    /**
     *
     * @param key
     * @return float type
     */
    public float getFloatPref(String key, float defValue){
        return sharedPreferences.getFloat(key, defValue);
    }

}