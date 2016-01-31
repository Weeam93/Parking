package com.example.weeamawad.parking.Utility;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.weeamawad.parking.Listeners.GeocodeListener;
import com.example.weeamawad.parking.Listeners.ParkingListener;
import com.example.weeamawad.parking.Volley.VolleyRequestQueue;
import com.example.weeamawad.parking.model.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class ServiceUtility {

    private static final String GEOCODE_BASE = "https://maps.googleapis.com/maps/api/geocode/json?address=%s";
    private static final String PARKING_Base = "http://api.parkwhiz.com/search/?lat=%s&lng=%s&key=%s";
    private static final String PARKING_API_KEY = "c71066144c39ee80c3d36f995d914d91";
    private static final String LISTING_ID="listing_id";
    private static final String LAT_KEY = "lat";
    private static final String LNG_KEY = "lng";
    private static final String LOCATION_NAME_KEY = "location_name";
    private static final String ADDRESS_KEY = "address";
    private static final String PRICE_KEY = "price";
    private static final String AVAILABLE_SPOTS_KEY = "available_spots";
    private static final String DISTANCE_KEY = "distance";
    private static final String CITY_KEY = "city";
    private static final String STATE_KEY = "state";
    private static final String ZIP_KEY = "zip";
    private static final String PARKING_LISTING_KEY = "parking_listings";
    private static final String RESULTS_KEY = "results";
    private static final String GEOMETRY_KEY = "geometry";
    private static final String LOCATION_KEY = "location";

    public static void geocodeService(Context context, String address, final GeocodeListener listener) {
        String completeUrl = address.replaceAll(" ", "+");
        completeUrl = String.format(GEOCODE_BASE, completeUrl);
        JsonRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray(RESULTS_KEY);
                    JSONObject geometryObject = (JSONObject) resultArray.getJSONObject(0).get(GEOMETRY_KEY);
                    JSONObject locationObject = (JSONObject) geometryObject.get(LOCATION_KEY);
                    double latitude = (Double) locationObject.get(LAT_KEY);
                    double longitude = (Double) locationObject.get(LNG_KEY);
                    LatLng coordinate = new LatLng(latitude, longitude);
                    listener.onSuccess(coordinate);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("GeocodeService", "Failed");
                listener.onFailure();
            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsObjRequest);

    }

    public static void parkingServiceSearch(Context context, LatLng location, final ParkingListener listener) {
        String completeUrl = String.format(PARKING_Base, location.latitude, location.longitude, PARKING_API_KEY);
        JsonRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray(PARKING_LISTING_KEY);
                    ArrayList<Place> parkingList = new ArrayList<Place>();
                    for (int i = 0; i < resultArray.length(); i++) {
                        Place p = parseJSON((JSONObject) resultArray.get(i));
                        parkingList.add(p);
                    }
                    listener.onSuccess(parkingList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("ParkingService", "Failed");
                listener.onFailure();
            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsObjRequest);
    }

    private static Place parseJSON(JSONObject j) {
        Place result = new Place();
        try {
            result.setListingID((String) j.get(LISTING_ID));
            result.setLatitude((Double) j.get(LAT_KEY));
            result.setLongitude((Double) j.get(LNG_KEY));
            result.setName(j.getString(LOCATION_NAME_KEY));
            result.setAddress(j.getString(ADDRESS_KEY));
            result.setPrice(j.getDouble(PRICE_KEY));
            result.setAvailible_spots(j.getInt(AVAILABLE_SPOTS_KEY));
            result.setDistance(j.getInt(DISTANCE_KEY));

            result.setCity(j.getString(CITY_KEY));
            result.setState(j.getString(STATE_KEY));
            result.setZip(j.getString(ZIP_KEY));
            result.formatCompleteAddress();

            result.setOptimized();
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
