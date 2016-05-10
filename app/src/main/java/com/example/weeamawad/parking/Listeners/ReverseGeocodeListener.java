package com.example.weeamawad.parking.Listeners;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Weeam Awad on 5/7/2016.
 */
public interface ReverseGeocodeListener {
    void onSuccess(String address);

    void onFailure();
}
