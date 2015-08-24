package com.example.weeamawad.parking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.net.Uri;
import android.os.AsyncTask;

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
        //https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&components=country:us&types=(cities)&key=AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU
        /*this.API_KEY="AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU"; //browser api key
		this.baseUrl="https://maps.googleapis.com/maps/api/place/search/json?";
		this.type="parking";
		this.latitude=latitude;
		this.longitude=longitude;
		this.sensor="false";
		this.radius=5000;*/

        this.API_KEY = "c71066144c39ee80c3d36f995d914d91"; //browser api key
        this.baseUrl = "http://api.parkwhiz.com/search/?";
        this.latitude = latitude;
        this.longitude = longitude;

        setCompleteUrl();
        //this.completeUrl="http://api.parkwhiz.com/search/?destination=312+N+wacker+Dr,+Chicago&start=1402441321&end=1402452121&key=62d882d8cfe5680004fa849286b6ce20";
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
		/*a.append("location=");
		a.append(this.latitude);
		a.append(',');
		a.append(this.longitude);
		a.append("&radius=");
		a.append(this.radius);
		a.append("&types=");
		a.append(this.type);
		a.append("&sensor=");
		a.append(this.sensor);
		a.append("&key=");
		a.append(this.API_KEY);*/

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
			/*for(int i=0;i<placeList.size();i++)
			{
			System.out.println(placeList.get(i).printInfo());
			}*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Place parseJSON(JSONObject j) {
        Place result = new Place();
        try {
			/*JSONObject geometryObject=(JSONObject) j.get("geometry");
			JSONObject locationObject=(JSONObject) geometryObject.get("location");
			result.setLatitude((Double) locationObject.get("lat"));
			result.setLongitude((Double) locationObject.get("lng"));
			result.setName(j.getString("name"));
			result.setVicinity(j.getString("vicinity"));
			result.setIcon(j.getString("icon"));*/

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



