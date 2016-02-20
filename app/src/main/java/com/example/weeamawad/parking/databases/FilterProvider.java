package com.example.weeamawad.parking.databases;

import android.content.Context;

import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.model.FilterModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterProvider {

    private DatabaseProvider<Filter> mProvider;
    private Context mContext;

    public FilterProvider(Context context) {
        mContext = context;
        mProvider = DatabaseProvider.getInstance(mContext);
    }

    public void saveFilters(ArrayList<FilterModel> filterModels) {
        for (FilterModel fm : filterModels) {
            saveFilter(fm);
        }
    }

    public void saveFilter(FilterModel filterModel) {
        if (!Utils.isStringEmpty(filterModel.getType())) {
            Filter filter = new Filter();
            filter.filterType = filterModel.getType();
            filter.filterDescription = filterModel.getDescription();
            filter.filterValue = filterModel.getValue();
            mProvider.create(filter);
        }
    }

    public ArrayList<FilterModel> getAllFilters() {
        try {
            List<Filter> filters = mProvider.getAll(Filter.class);
            if (!Utils.checkIfNull(filters)) {
                ArrayList<FilterModel> filterModels = new ArrayList<>();
                for (Filter filter : filters) {
                    filterModels.add(convertToAppObject(filter));
                }
                return filterModels;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FilterModel convertToAppObject(Filter filter) {
        FilterModel filterModel = new FilterModel();
        filterModel.setType(filter.filterType);
        filterModel.setDescription(filter.filterDescription);
        filterModel.setValue(filter.filterValue);
        return filterModel;
    }

    public void deleteAllFilters() {
        mProvider.deleteAllData(FilterSchema.TABLE_NAME);
    }

}
