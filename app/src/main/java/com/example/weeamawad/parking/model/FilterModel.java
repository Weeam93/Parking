package com.example.weeamawad.parking.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.weeamawad.parking.BR;

/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterModel extends BaseObservable {
    private String type;
    private String description;
    private int value;

    public FilterModel() {
    }

    public FilterModel(String t, String d, int v) {
        setType(t);
        setDescription(d);
        setValue(v);
    }

    @Bindable
    public String getType() {
        return type;
    }

    @Bindable

    public String getDescription() {
        return description;
    }

    @Bindable
    public int getValue() {
        return value;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);

    }

    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }


    public void setValue(int value) {
        this.value = value;
        notifyPropertyChanged(BR.value);
    }
}
