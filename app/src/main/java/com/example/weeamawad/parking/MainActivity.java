package com.example.weeamawad.parking;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;

import com.example.weeamawad.parking.Volley.VolleyRequestQueue;
import com.example.weeamawad.parking.fragments.MapFragment;

public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VolleyRequestQueue.getInstance(this);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        showMainMap();

    }

    public void showMainMap() {
        Intent mapIntent = new Intent(this, MapFragment.class);
        startActivity(mapIntent);
    }
}
