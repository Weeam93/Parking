/*
package com.example.weeamawad.parking.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Parking {

    private String baseUrl;
    private String completeUrl;

    private double latitude;
    ;
    private double longitude;
    private int radius;
    private String type;
    private String sensor;
    private String API_KEY;

    private String distance;

    public Parking(double latitude, double longitude) {
        this.API_KEY = "c71066144c39ee80c3d36f995d914d91"; //browser api key
        this.baseUrl = "http://api.parkwhiz.com/search/?";
        this.latitude = latitude;
        this.longitude = longitude;

        setCompleteUrl();
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getCompleteUrl() {
        return completeUrl;
    }

    public void setCompleteUrl() {

        StringBuilder a = new StringBuilder(this.baseUrl);
        a.append("lat=");
        a.append(this.latitude);
        a.append("&lng=");
        a.append(this.longitude);
        a.append("&key=");
        a.append(this.API_KEY);
        this.completeUrl = a.toString();
    }

    public ArrayList<Place> parsePlaces() {
        String json = getData();
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
			*/
/*for(int i=0;i<placeList.size();i++)
            {
			System.out.println(placeList.get(i).printInfo());
			}*//*

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Place parseJSON(JSONObject j) {
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

    public String getData() {
        StringBuilder contents = new StringBuilder();
        try {
            System.out.println(this.getCompleteUrl());
            URL u = new URL(this.getCompleteUrl());
            URLConnection urlcon = u.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
            String con;
            while ((con = br.readLine()) != null) {
                contents.append(con);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(contents.toString());
        System.out.println("DONE");
        return contents.toString();
    }


}



*/
