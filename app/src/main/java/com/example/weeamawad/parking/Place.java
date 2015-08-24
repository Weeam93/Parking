package com.example.weeamawad.parking;

import java.io.Serializable;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Place implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String vicinity;
	private double latitude;
	private double longitude;
	private String icon;

	private String address;
	private String city;
	private String state;
	private String zip;
	private String completeAddress;

	private double distance;
	private int availible_spots;
	private int price;
	private double optimized;

	private Bitmap markerImage;

	public void setName(String name)
	{
		this.name=name;
	}
	public void setVicinity(String vicinity)
	{
		this.vicinity=vicinity;
	}
	public void setLatitude(double latitude)
	{
		this.latitude=latitude;
	}
	public void setLongitude(double longitude)
	{
		this.longitude=longitude;
	}
	public void setIcon(String icon)
	{
		this.icon=icon;
	}
	public String getName()
	{
		return this.name;
	}
	public String getVicinity()
	{
		return this.vicinity;
	}
	public double getLatitude()
	{
		return this.latitude;
	}
	public double getLongitude()
	{
		return this.longitude;
	}
	public String getIcon()
	{
		return this.icon;
	}



	public void setAddress(String address)
	{
		this.address=address;
	}
	public void setCity(String city)
	{
		this.city=city;
	}
	public void setState(String state)
	{
		this.state=state;
	}
	public void setZip(String zip)
	{
		this.zip=zip;
	}
	public void setCompleteAddress()
	{
		StringBuilder a = new StringBuilder();
		a.append(this.address+" ");
		a.append(this.city+" ");
		a.append(this.state+" ");
		a.append(this.zip);

		this.completeAddress=a.toString();
	}	
	public String getAddress()
	{
		return this.address;
	}
	public String getCity()
	{
		return this.city;
	}
	public String getState()
	{
		return this.state;
	}
	public String getZip()
	{
		return this.zip;
	}
	public String getCompleteAddress()
	{
		return this.completeAddress;
	}
	public void setDistance(double distance)
	{
		double miles=distance*0.000189394;
		this.distance=miles;
	}
	public double getDistance()
	{
		return this.distance;
	}
	public void setAvailible_spots(int spots)
	{
		this.availible_spots=spots;
	}
	public int getFreeSpots()
	{
		return this.availible_spots;
	}
	public void setPrice(double price)
	{
		Double d=Math.ceil(price);
		this.price=d.intValue();
	}
	public int getPrice()
	{
		return this.price;
	}
	public void setOptimized()
	{
		this.optimized=getPrice()/getDistance();
	}
	public double getOptimized()
	{
		return this.optimized;
	}
	public String printInfo()
	{
		return "Name: "+ name + ", Coordinates:" + latitude + ", " + longitude;

	}
	public Bitmap getbitMap()
	{
		return this.markerImage;
	}

}
