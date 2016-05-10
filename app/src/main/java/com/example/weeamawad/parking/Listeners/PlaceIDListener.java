package com.example.weeamawad.parking.Listeners;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Weeam Awad on 3/16/2016.
 */
public interface PlaceIDListener {
    void onSuccess(String placeID);

    void onFailure();
}
