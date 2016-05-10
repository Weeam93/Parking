package com.example.weeamawad.parking.Listeners;

import java.util.ArrayList;

/**
 * Created by Weeam Awad on 3/16/2016.
 */
public interface AutoCompleteListener {

    void onSuccess(ArrayList<String> suggestions);

    void onFailure();
}
