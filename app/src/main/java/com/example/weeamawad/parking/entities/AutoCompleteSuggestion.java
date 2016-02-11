package com.example.weeamawad.parking.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Weeam Awad on 2/1/2016.
 */
public class AutoCompleteSuggestion implements SearchSuggestion {

    private String mAddress;

    public AutoCompleteSuggestion(String address) {
        mAddress = address;
    }

    @Override
    public String getBody() {
        return mAddress;
    }

    @Override
    public Creator getCreator() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAddress);
    }
}