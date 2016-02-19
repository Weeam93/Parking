package com.example.weeamawad.parking.entities;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.weeamawad.parking.BR;
import com.google.maps.android.ui.IconGenerator;

import java.text.DecimalFormat;

public class GarageViewModel extends BaseObservable {

    private String mListingID;
    private String mName;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZip;
    private String mCompleteAddress;

    private int mAvailibleSpots;
    private int mPrice;
    private double mDistance;
    private double mLatitude;
    private double mLongitude;
    private boolean isFavorite;

    public int getMarkerStyle() {
        return markerStyle;
    }

    public void setMarkerStyle() {
        if (mPrice <= 15) {
            this.markerStyle = IconGenerator.STYLE_GREEN;
        } else if ((mPrice > 15) && (mPrice <= 35)) {
            this.markerStyle = IconGenerator.STYLE_ORANGE;
        } else if ((mPrice > 35)) {
            this.markerStyle = IconGenerator.STYLE_RED;
        }

    }

    private int markerStyle;

    @Bindable
    public String getListingID() {
        return mListingID;
    }

    @Bindable
    public String getName() {
        return mName;
    }

    @Bindable
    public String getAddress() {
        return mAddress;
    }

    @Bindable
    public String getCity() {
        return mCity;
    }

    @Bindable
    public String getState() {
        return mState;
    }

    @Bindable
    public String getZip() {
        return mZip;
    }

    @Bindable
    public String getCompleteAddress() {
        return mCompleteAddress;
    }

    @Bindable
    public int getAvailibleSpots() {
        return mAvailibleSpots;
    }

    @Bindable
    public int getPrice() {
        return mPrice;
    }

    @Bindable
    public double getDistance() {
        return mDistance;
    }

    @Bindable
    public double getLatitude() {
        return mLatitude;
    }

    @Bindable
    public double getLongitude() {
        return mLongitude;
    }

    @Bindable
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setListingID(String mListingID) {
        this.mListingID = mListingID;
        notifyPropertyChanged(BR.listingID);
    }

    public void setName(String mName) {
        this.mName = mName;
        notifyPropertyChanged(BR.name);
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
        notifyPropertyChanged(BR.address);
    }

    public void setCity(String mCity) {
        this.mCity = mCity;
        notifyPropertyChanged(BR.city);
    }

    public void setState(String mState) {
        this.mState = mState;
        notifyPropertyChanged(BR.state);
    }

    public void setZip(String mZip) {
        this.mZip = mZip;
        notifyPropertyChanged(BR.zip);
    }

    public void setAvailibleSpots(int mAvailibleSpots) {
        this.mAvailibleSpots = mAvailibleSpots;
        notifyPropertyChanged(BR.availibleSpots);
    }

    public void setPrice(int mPrice) {
        Double d = Math.ceil(mPrice);
        this.mPrice = mPrice;
        notifyPropertyChanged(BR.price);
    }

    public void setDistance(double mDistance) {
        double miles = mDistance * 0.000189394;
        DecimalFormat f = new DecimalFormat("###.#");

        this.mDistance = Double.parseDouble(f.format(miles));
        notifyPropertyChanged(BR.distance);
    }

    public void setLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
        notifyPropertyChanged(BR.latitude);
    }

    public void setLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
        notifyPropertyChanged(BR.longitude);
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        notifyPropertyChanged(BR.favorite);

    }

    public void formatCompleteAddress() {
        StringBuilder a = new StringBuilder();
        a.append(this.mAddress + " ");
        a.append(this.mCity + " ");
        a.append(this.mState + " ");
        a.append(this.mZip);

        this.mCompleteAddress = a.toString();
    }

    public String printInfo() {
        return "Name: " + mName + ", Coordinates:" + mLatitude + ", " + mLongitude;

    }
}
