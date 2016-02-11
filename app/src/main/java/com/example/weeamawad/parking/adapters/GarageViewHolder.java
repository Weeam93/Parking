package com.example.weeamawad.parking.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weeamawad.parking.databinding.RowFavoritesBinding;
import com.example.weeamawad.parking.entities.GarageViewModel;

/**
 * Created by Weeam Awad on 2/10/2016.
 */
public class GarageViewHolder extends RecyclerView.ViewHolder {
    private RowFavoritesBinding mRowBinding;

    public GarageViewHolder(RowFavoritesBinding rowBinding) {
        super(rowBinding.getRoot());
        mRowBinding = rowBinding;
    }

    public void onBindConnection(GarageViewModel garageViewModel) {
        mRowBinding.setGarage(garageViewModel);
    }
}
