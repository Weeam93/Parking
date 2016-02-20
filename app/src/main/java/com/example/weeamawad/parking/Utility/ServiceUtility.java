package com.example.weeamawad.parking.Utility;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.example.weeamawad.parking.Listeners.AutoCompleteListener;
import com.example.weeamawad.parking.Listeners.GeocodeListener;
import com.example.weeamawad.parking.Listeners.ParkingListener;
import com.example.weeamawad.parking.Volley.VolleyRequestQueue;
import com.example.weeamawad.parking.entities.AutoCompleteSuggestion;
import com.example.weeamawad.parking.model.GarageModel;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class ServiceUtility {

    private static final String GEOCODE_BASE = "https://maps.googleapis.com/maps/api/geocode/json?address=%s";
    private static final String PARKING_BASE = "http://api.parkwhiz.com/search/?lat=%s&lng=%s&key=%s";
    private static final String AUTO_COMPLETE_BASE = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&components=country:us&key=%s";
    private static final String PARKING_API_KEY = "c71066144c39ee80c3d36f995d914d91";
    private static final String GOOGLE_API_KEY = "AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU";
    private static final String LISTING_ID = "listing_id";
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

    private static final String BASEURL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
    private static final String COMPONENTS = "&components=country:us";
    private static final String TYPES = "&types=(cities)";
    private static final String API_KEY = "&key=AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU";

    public static void parkingServiceSearch(Context context, LatLng location, final ParkingListener listener) {
        String completeUrl = String.format(PARKING_BASE, location.latitude, location.longitude, PARKING_API_KEY);
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray(PARKING_LISTING_KEY);
                    ArrayList<GarageModel> parkingList = new ArrayList<GarageModel>();
                    for (int i = 0; i < resultArray.length(); i++) {
                        GarageModel p = parseJSON((JSONObject) resultArray.get(i));
                        parkingList.add(p);
                    }
                    listener.onSuccess(parkingList);
                } catch (JSONException e) {
                    e.printStackTrace();
                    listener.onFailure();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("ParkingService", "Failed");
                listener.onFailure();
            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsonRequest);
    }

    public static void autoComplete(Context context, String input, final AutoCompleteListener listener) {
        String types = "(cities)";
        // input = "&input=" + input;
        String completeUrl = null;
        try {
            completeUrl = String.format(AUTO_COMPLETE_BASE, URLEncoder.encode(input, "utf8"), GOOGLE_API_KEY);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final ArrayList<AutoCompleteSuggestion> resultList = new ArrayList<AutoCompleteSuggestion>();
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray("predictions");

                    for (int i = 0; i < resultArray.length(); i++) {
                        resultList.add(convertTOSuggestion(resultArray.getJSONObject(i).getString("description")));
                    }
                    listener.onSuccess(resultList);
                } catch (JSONException e) {
                    Log.e("AutoComplete", "Cannot process JSON results", e);
                    listener.onFailure();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailure();
            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsonRequest);
    }

    public static void geocodeService(Context context, String address, final GeocodeListener listener) {
        String completeUrl = address.replaceAll(" ", "+");
        completeUrl = String.format(GEOCODE_BASE, completeUrl);
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
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
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsonRequest);

    }

    private static AutoCompleteSuggestion convertTOSuggestion(String address) {
        return new AutoCompleteSuggestion(address);
    }

    private static GarageModel parseJSON(JSONObject j) {
        GarageModel result = new GarageModel();
        try {
            result.setListingID(Integer.toString((Integer) j.get(LISTING_ID)));
            result.setLatitude((Double) j.get(LAT_KEY));
            result.setLongitude((Double) j.get(LNG_KEY));
            result.setName(j.getString(LOCATION_NAME_KEY));
            result.setAddress(j.getString(ADDRESS_KEY));
            result.setPrice(j.getInt(PRICE_KEY));
            result.setAvailibleSpots(j.getInt(AVAILABLE_SPOTS_KEY));
            result.setDistance(j.getInt(DISTANCE_KEY));

            result.setCity(j.getString(CITY_KEY));
            result.setState(j.getString(STATE_KEY));
            result.setZip(j.getString(ZIP_KEY));
            result.formatCompleteAddress();
            result.setMarkerStyle();
            return result;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
