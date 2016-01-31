package com.example.weeamawad.parking.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.model.Place;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class FavoritesAdapter extends ArrayAdapter<Place> {
    Context context;
    int layoutResourceID;
    ArrayList<Place> parkingPlaces = null;

    public FavoritesAdapter(Context context, int layoutResourceId, ArrayList<Place> parkingPlaces) {
        super(context, layoutResourceId, parkingPlaces);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutResourceID = layoutResourceId;
        this.parkingPlaces = parkingPlaces;
    }

    static class ViewHolder {
        TextView name;
        TextView address;
        TextView price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceID, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.tv_favName);
            holder.address = (TextView) convertView.findViewById(R.id.tv_favAddress);
            holder.price = (TextView) convertView.findViewById(R.id.tv_favPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String n = parkingPlaces.get(position).getName();
        String a = parkingPlaces.get(position).getCompleteAddress();
        String p = Integer.toString(parkingPlaces.get(position).getPrice());

        holder.name.setText(n);
        holder.address.setText(a);
        holder.price.setText("$" + p);

        return convertView;

    }
}
