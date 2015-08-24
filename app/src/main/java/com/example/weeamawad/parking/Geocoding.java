package com.example.weeamawad.parking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Geocoding {

	private static final String BASEURL="https://maps.googleapis.com/maps/api/geocode/json?";
	private static final String API_KEY="&key=AIzaSyCyoNNdAu2MD9qgdfNlgkZUVObun2nF7cM";	
	private String address;
	private String completeUrl;
	public Geocoding(String address)
	{
		
		String temp=address.replaceAll(" ", "+");
		this.address="address="+temp;
		StringBuilder sb= new StringBuilder(BASEURL+this.address);
		this.completeUrl=sb.toString();
		
	}
	public LatLng getCoordinates()
	{
		
		String json=getData();
		double lattitude=0;
		double longitude=0;
		System.out.println("this is it"+json);
		try
		{
			JSONObject object=new JSONObject(json);
			System.out.println("Working");
			JSONArray resultArray=object.getJSONArray("results");

			
			JSONObject geometryObject=(JSONObject) resultArray.getJSONObject(0).get("geometry");
			JSONObject locationObject=(JSONObject) geometryObject.get("location");
			lattitude=(Double) locationObject.get("lat");
			longitude=(Double) locationObject.get("lng");

			System.out.println(lattitude);
		}catch (JSONException e) 
		{
			//Log.e(LOG_TAG, "Cannot process JSON results", e);
			System.out.println("Cannot process JSON results");
		}
		LatLng coordinate=new LatLng(lattitude,longitude); 
		return coordinate;
	}
	private String getData()
	{

		System.out.println("FULL: "+this.completeUrl);
		HttpURLConnection conn = null;
		StringBuilder contents = new StringBuilder();
		try
		{
			URL url = new URL(this.completeUrl);
			conn=(HttpURLConnection)url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line;
			while((line = br.readLine()) != null) 
			{
				contents.append(line);
			}
		}
		catch (MalformedURLException e) {
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
}
