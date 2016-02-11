package com.example.weeamawad.parking.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.databinding.RowFavoritesBinding;
import com.example.weeamawad.parking.entities.GarageViewModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 1/30/2016.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<GarageViewHolder> {

    ArrayList<GarageViewModel> items = null;

    public FavoritesAdapter(ArrayList<GarageViewModel> items) {
        super();
        // TODO Auto-generated constructor stub
        this.items = items;
    }

    @Override
    public GarageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowFavoritesBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_favorites, parent, false);
        return new GarageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(GarageViewHolder holder, int position) {
        holder.onBindConnection(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
