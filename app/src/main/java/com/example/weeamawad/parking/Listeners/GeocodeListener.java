package com.example.weeamawad.parking.Listeners;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public interface GeocodeListener {
    void onSuccess(LatLng location);

    void onFailure();
}
