package com.example.weeamawad.parking;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlaceAdapter extends ArrayAdapter<Place> {
	Context context;
	int layoutResourceID;
	ArrayList<Place> parkingPlaces=null;

	public PlaceAdapter(Context context, int layoutResourceId, ArrayList<Place> parkingPlaces ) {
		super(context, layoutResourceId, parkingPlaces );
		// TODO Auto-generated constructor stub
		this.context=context;
		this.layoutResourceID=layoutResourceId;
		this.parkingPlaces=parkingPlaces;
	}
	static class ViewHolder
	{
		TextView name;
		TextView address;
		TextView distance;
		TextView price;
		TextView availble_spots;
	}

	@Override
	public View getView(int position, View convertView,ViewGroup parent)
	{
		ViewHolder holder=null;
		if(convertView==null)
		{
			LayoutInflater inflater= ((Activity) context).getLayoutInflater();
			convertView=inflater.inflate(layoutResourceID, parent, false);

			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.placeName);
			holder.address=(TextView)convertView.findViewById(R.id.placeAddress);
			holder.distance=(TextView)convertView.findViewById(R.id.placeDistance);
			holder.price=(TextView)convertView.findViewById(R.id.placePrice);
			holder.availble_spots=(TextView)convertView.findViewById(R.id.placeSpots);
			convertView.setTag(holder);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
		DecimalFormat df= new DecimalFormat("###.#");
		
		String n=parkingPlaces.get(position).getName();
		String a=parkingPlaces.get(position).getCompleteAddress();
		String d=(df.format(parkingPlaces.get(position).getDistance()));
		String p=Integer.toString(parkingPlaces.get(position).getPrice());
		String s=Integer.toString(parkingPlaces.get(position).getFreeSpots());
		
		
		
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/Rounds Black.otf");
		holder.name.setTypeface(tf);
		
		
		holder.name.setText(n);
		holder.address.setText(a);
		holder.distance.setText("Distance: "+d+ " miles");
		holder.price.setText("Price: $"+p);
		holder.availble_spots.setText("Available Spots: "+s);

		return convertView;

	}


}
