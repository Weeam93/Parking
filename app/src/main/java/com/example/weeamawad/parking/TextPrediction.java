package com.example.weeamawad.parking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TextPrediction {



	private static final String BASEURL="https://maps.googleapis.com/maps/api/place/autocomplete/json?";
	private static final String COMPONENTS="&components=country:us";
	private static final String TYPES="&types=(cities)";
	private static final String API_KEY="&key=AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU";	

	private String input;
	private String completeUrl;
	public TextPrediction(String input)
	{
		this.input="&input="+input;

		StringBuilder sb= new StringBuilder(BASEURL+this.input+COMPONENTS+TYPES+API_KEY);
		this.completeUrl=sb.toString();
	}
	public ArrayList<String> getPredictions()
	{
		ArrayList<String> resultList = new ArrayList<String>();
		String json=getData();

		try
		{
			JSONObject object=new JSONObject(json);
			JSONArray resultArray=object.getJSONArray("predictions");

			for(int i=0; i< resultArray.length();i++)
			{
				resultList.add(resultArray.getJSONObject(i).getString("description"));
			}
		}catch (JSONException e) 
		{
			//Log.e(LOG_TAG, "Cannot process JSON results", e);
		}

		return resultList;

	}
	public String getData()
	{

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
			return contents.toString();
		} catch (IOException e) {
			//Log.e(LOG_TAG, "Error connecting to Places API", e);
			return contents.toString();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return contents.toString();

	}
	//https://maps.googleapis.com/maps/api/place/autocomplete/json?input=Vict&components=country:us&types=(cities)&key=AIzaSyBOqw91vKAUAaRhgSDoDBy4lmg-4pEOBwU
}
