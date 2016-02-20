package com.example.weeamawad.parking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.adapters.FilterAdapter;
import com.example.weeamawad.parking.model.FilterModel;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private View mRootView;
    private RecyclerView rvFilters;
    private FilterAdapter adapter;
    private ArrayList<FilterModel> filters;
    private Button btnApply;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_filter, container, false);
        mContext = getActivity();
        initData();
        initViews();
        initListeners();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        filters = DatabaseUtils.getAllFilters(mContext);
    }

    private void initViews() {
        btnApply = (Button) mRootView.findViewById(R.id.btn_apply_filter);
        rvFilters = (RecyclerView) mRootView.findViewById(R.id.rv_filters);
        adapter = new FilterAdapter(filters);
        rvFilters.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFilters.setAdapter(adapter);
    }

    private void initListeners() {
        btnApply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply_filter:
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(this).commit();
                break;
        }
    }
}
