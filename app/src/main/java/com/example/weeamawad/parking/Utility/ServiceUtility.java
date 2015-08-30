package com.example.weeamawad.parking.Utility;

import com.example.weeamawad.parking.model.Place;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Weeam Awad on 8/29/2015.
 */
public class ServiceUtility {

    private static final String GEOCODE_BASE = "https://maps.googleapis.com/maps/api/geocode/json?";
    private static final String PARKING_Base = "http://api.parkwhiz.com/search/?";
    private static final String PARKING_API_KEY = "c71066144c39ee80c3d36f995d914d91";

    public static LatLng geocodeService(String address) {
        String completeUrl = address.replaceAll(" ", "+");
        completeUrl = "address=" + completeUrl;
        completeUrl = GEOCODE_BASE + completeUrl;
        String json = getJSON(completeUrl);

        double lattitude = 0;
        double longitude = 0;
        System.out.println("this is it" + json);
        try {
            JSONObject object = new JSONObject(json);
            System.out.println("Working");
            JSONArray resultArray = object.getJSONArray("results");


            JSONObject geometryObject = (JSONObject) resultArray.getJSONObject(0).get("geometry");
            JSONObject locationObject = (JSONObject) geometryObject.get("location");
            lattitude = (Double) locationObject.get("lat");
            longitude = (Double) locationObject.get("lng");

            System.out.println(lattitude);
        } catch (JSONException e) {
            //Log.e(LOG_TAG, "Cannot process JSON results", e);
            System.out.println("Cannot process JSON results");
        }
        LatLng coordinate = new LatLng(lattitude, longitude);
        return coordinate;
    }

    public static ArrayList<Place> parkingService(LatLng location) {
        double lat = location.latitude;
        double lng = location.longitude;
        String completeUrl = PARKING_Base;
        completeUrl = completeUrl + "lat=" + lat;
        completeUrl = completeUrl + "&lng=" + lng;
        completeUrl = completeUrl + "&key=" + PARKING_API_KEY;
        String json = getJSON(completeUrl);

        try {

            System.out.println("Starting");
            JSONObject object = new JSONObject(json);
            JSONArray resultArray = object.getJSONArray("parking_listings");
            //JSONArray resultArray=object.getJSONArray("results");

            ArrayList<Place> parkingList = new ArrayList<Place>();
            for (int i = 0; i < resultArray.length(); i++) {
                Place p = parseJSON((JSONObject) resultArray.get(i));
                parkingList.add(p);
            }
            return parkingList;
            /*for(int i=0;i<placeList.size();i++)
			{
			System.out.println(placeList.get(i).printInfo());
			}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getJSON(String completeUrl) {
        System.out.println("FULL: " + completeUrl);
        HttpURLConnection conn = null;
        StringBuilder contents = new StringBuilder();
        try {
            URL url = new URL(completeUrl);
            conn = (HttpURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                contents.append(line);
            }
        } catch (MalformedURLException e) {
            //Log.e(LOG_TAG, "Error processing Places API URL", e);
            System.out.println("Error processing Geocoding API URL");
            return contents.toString();
        } catch (IOException e) {
            //Log.e(LOG_TAG, "Error connecting to Places API", e);
            System.out.println("Error connecting to Geocoding API");
            return contents.toString();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return contents.toString();
    }

    private static Place parseJSON(JSONObject j) {
        Place result = new Place();
        try {
            result.setLatitude((Double) j.get("lat"));
            result.setLongitude((Double) j.get("lng"));
            result.setName(j.getString("location_name"));
            result.setAddress(j.getString("address"));
            result.setPrice(j.getDouble("price"));
            result.setAvailible_spots(j.getInt("available_spots"));
            result.setDistance(j.getInt("distance"));

            result.setCity(j.getString("city"));
            result.setState(j.getString("state"));
            result.setZip(j.getString("zip"));
            result.setCompleteAddress();

            result.setOptimized();
            //result.setMarkerImage();
            return result;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
