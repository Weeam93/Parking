package com.example.weeamawad.parking.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Weeam Awad on 2/1/2016.
 */
public class AutocompleteSuggestion implements SearchSuggestion {
    @Override
    public String getBody() {
        return null;
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

    }
}
