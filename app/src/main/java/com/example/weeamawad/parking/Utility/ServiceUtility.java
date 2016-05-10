package com.example.weeamawad.parking.Utility;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.weeamawad.parking.Listeners.AutoCompleteListener;
import com.example.weeamawad.parking.Listeners.GeocodeListener;
import com.example.weeamawad.parking.Listeners.ParkingListener;
import com.example.weeamawad.parking.Listeners.PlaceIDListener;
import com.example.weeamawad.parking.Listeners.ReverseGeocodeListener;
import com.example.weeamawad.parking.Volley.VolleyRequestQueue;
import com.example.weeamawad.parking.model.AppSettingsModel;
import com.example.weeamawad.parking.model.GarageModel;
import com.example.weeamawad.parking.model.NewFilterModel;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class ServiceUtility {

    private static final String AUTO_COMPLETE_BASE = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&components=country:us&key=%s";
    private static final String PLACE_DETAIL_BASE = "https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=%s";
    private static final String PLACE_PHOTO_BASE = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=%s&photoreference=%s&key=%s";
    private static final String GEOCODE_BASE = "https://maps.googleapis.com/maps/api/geocode/json?address=%s";
    private static final String REVERSE_GEOCODE_BASE = "https://maps.googleapis.com/maps/api/geocode/json?latlng=%s&key=%s";
    private static final String PARKING_BASE = "http://api.parkwhiz.com/search/?lat=%s&lng=%s" +
            //  "&price=%s" +
            "&monthly=%s&shuttle=%s&eticket=%s&perk=%s&valet=%s&indoor=%s&handicap=%s&restroom=%s&security=%s&tailgate=%s&rv=%s&unobstructed=%s&attended=%s&reentry_allowed=%s&key=%s";
    private static final String PARKING_API_KEY = "c71066144c39ee80c3d36f995d914d91";
    private static final String GOOGLE_API_KEY = "AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU";

    private static final String LISTING_ID_KEY = "listing_id";
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

    private static final String RESULT_KEY = "result";
    private static final String RESULTS_KEY = "results";
    private static final String GEOMETRY_KEY = "geometry";
    private static final String LOCATION_KEY = "location";
    private static final String PLACE_ID_KEY = "place_id";
    private static final String PHOTOS_KEY = "photos";
    private static final String ADDRESS_COMPONENTS_KEY = "address_components";
    private static final String FORMATTED_ADDRESS_KEY = "formatted_address";
    private static final String HTML_ATTRIBUTIONS_KEY = "html_attributions";
    private static final String REFERENCE_KEY = "reference";
    private static final String PHOTO_REFERENCE_KEY = "photo_reference";


    public static void parkingServiceSearch(Context context, LatLng location, final ParkingListener listener) {
        String completeUrl = parkingUrlApplyFilters(location);
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

        final ArrayList<String> resultList = new ArrayList<String>();
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray("predictions");

                    for (int i = 0; i < resultArray.length(); i++) {
                        resultList.add(resultArray.getJSONObject(i).getString("description"));
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

    public static void reverseGeocodeService(Context context, LatLng origin, final ReverseGeocodeListener listener) {
        String completeUrl = Double.toString(origin.latitude) + "," + Double.toString(origin.longitude);
        completeUrl = String.format(GEOCODE_BASE, completeUrl, GOOGLE_API_KEY);
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("ReverseGeoCodeService", "Success");
                Log.i("ReverseGeoCodeService", response.toString());
                try {
                    JSONArray resultArray = response.getJSONArray(RESULTS_KEY);
                    JSONArray addressComponents = (JSONArray)resultArray.getJSONObject(0).get(ADDRESS_COMPONENTS_KEY);
                    String formattedAddress = (String)resultArray.getJSONObject(0).get(FORMATTED_ADDRESS_KEY);
                    listener.onSuccess(formattedAddress);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("ReverseGeoCodeService", "Failed");
                listener.onFailure();
            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsonRequest);

    }

    public static void getPlaceID(Context context, String address, final PlaceIDListener listener) {
        String completeUrl = address.replaceAll(" ", "+");
        completeUrl = String.format(GEOCODE_BASE, completeUrl);
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray resultArray = response.getJSONArray(RESULTS_KEY);
                    String placeId = (String) resultArray.getJSONObject(0).get(PLACE_ID_KEY);
                    listener.onSuccess(placeId);
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

    public static void placeDetail(Context context, String placeID, final PlaceIDListener listener) {
        String completeUrl = String.format(PLACE_DETAIL_BASE, placeID, GOOGLE_API_KEY);
        JsonRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, completeUrl, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.i("PlaceDetail", response.toString());
                    JSONObject result = response.getJSONObject(RESULT_KEY);
                    JSONArray photosArray = result.getJSONArray(PHOTOS_KEY);
                    String photoReference = (String) photosArray.getJSONObject(0).get(PHOTO_REFERENCE_KEY);
                    listener.onSuccess(photoReference);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("PlaceDetail", "Failed");
                listener.onFailure();
            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsonRequest);

    }

    public static void placePhoto(Context context, String photoReference, PlaceIDListener listener) {
        //CpQBhwAAAIxU7HLGNUPmONJBxFdfeWQ_ZaTGjzV66nCCLftFbx5smceIgkj-l1ZbwhSmK91jykKh662Ye6iifs8GZULQYE0jU8KB8e7nMxkYlHsMESiELgelAYqM_wPSHeaAB3Uf65jsmBqGRziREJA_VjDLxHxY0H-wUSm7UkCyExQa9TeIu_Htg_RcTeHOLsjBOINN7hIQln07Xc2s3cE8w2rTb7jfDRoUzbXi-2OfUSEIIi_J1ttPVm47oJ4
        //photoReference = "CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU";
        String completeUrl = String.format(PLACE_PHOTO_BASE, 400, photoReference, GOOGLE_API_KEY);
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, completeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("PlacePhoto", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PlacePhoto", "Failed - " + error.getMessage());

            }
        });
        VolleyRequestQueue.getInstance(context.getApplicationContext()).addToRequestQueue(jsonRequest);
    }


    private static GarageModel parseJSON(JSONObject j) {
        GarageModel result = new GarageModel();
        try {
            result.setListingID(Integer.toString((Integer) j.get(LISTING_ID_KEY)));
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

    private static String parkingUrlApplyFilters(LatLng location) {
        NewFilterModel filters = AppSettingsModel.filters;
        String completeUrl = String.format(PARKING_BASE,
                location.latitude,
                location.longitude,
                //(float)filters.getMaxPrice(),
                filters.isMonthly() ? 1 : 0,
                filters.isShuttle() ? 1 : 0,
                filters.isEticket() ? 1 : 0,
                filters.isPerk() ? 1 : 0,
                filters.isValet() ? 1 : 0,
                filters.isIndoor() ? 1 : 0,
                filters.isHandicap() ? 1 : 0,
                filters.isRestRoom() ? 1 : 0,
                filters.isSecurity() ? 1 : 0,
                filters.isTailGate() ? 1 : 0,
                filters.isRv() ? 1 : 0,
                filters.isUnObstructed() ? 1 : 0,
                filters.isAttended() ? 1 : 0,
                filters.isReentryAllowed() ? 1 : 0,
                PARKING_API_KEY);


        return completeUrl;
    }
}
