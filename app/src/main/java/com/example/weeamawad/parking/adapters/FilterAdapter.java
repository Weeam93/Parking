package com.example.weeamawad.parking.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.databinding.RowFilterBinding;
import com.example.weeamawad.parking.model.FilterModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {
    ArrayList<FilterModel> items = null;

    public FilterAdapter(ArrayList<FilterModel> items) {
        super();
        this.items = items;
    }


    @Override
    public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RowFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_filter, parent, false);
        return new FilterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(FilterViewHolder holder, int position) {
        holder.onBindConnection(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
