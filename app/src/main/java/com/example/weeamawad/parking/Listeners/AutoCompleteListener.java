package com.example.weeamawad.parking.Listeners;

import com.example.weeamawad.parking.entities.AutoCompleteSuggestion;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 2/6/2016.
 */
public interface AutoCompleteListener {
    void onSuccess(ArrayList<AutoCompleteSuggestion> strings);

    void onFailure();
}
