package com.example.weeamawad.parking.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.weeamawad.parking.databinding.RowFilterBinding;
import com.example.weeamawad.parking.model.FilterModel;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterViewHolder extends RecyclerView.ViewHolder {
    private RowFilterBinding rowFilterBinding;

    public FilterViewHolder(RowFilterBinding rowbinding) {
        super(rowbinding.getRoot());
        this.rowFilterBinding = rowbinding;
    }

    public void onBindConnection(FilterModel filterModel) {
        rowFilterBinding.setFilter(filterModel);
    }
}
