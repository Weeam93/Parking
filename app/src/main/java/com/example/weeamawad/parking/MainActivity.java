package com.example.weeamawad.parking;

import com.google.android.gms.maps.GoogleMap;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    //ActivityGroup
    private TabHost tHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        String mapLabel = "Map";
        String listLabel = "List";
        String settingsLabel = "Settings";
        try {
            ActionBar aBar = getSupportActionBar();
            aBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            aBar.setDisplayShowTitleEnabled(true);

            Tab t = aBar.newTab();
            t.setIcon(R.drawable.ic_tab_map_icon_selected);
            MyTabListener<MapActivity> mapTabListener = new MyTabListener<MapActivity>(this, mapLabel, MapActivity.class);
            t.setTabListener(mapTabListener);
            aBar.addTab(t);

            t = aBar.newTab();
            t.setIcon(R.drawable.ic_tab_list_icon_selected);
            MyTabListener<PlaceList> listTabListener = new MyTabListener<PlaceList>(this, listLabel, PlaceList.class);
            t.setTabListener(listTabListener);
            aBar.addTab(t);

            t = aBar.newTab();
            t.setIcon(R.drawable.ic_tab_settings_icon_selected);
            t.setTabListener(null);
            aBar.addTab(t);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void map(View v) {


        Intent mapIntent = new Intent(this, MapActivity.class);
        startActivity(mapIntent);

    }


    private class MyTabListener<T extends Fragment> implements ActionBar.TabListener {

        private Fragment fragment;
        private final Activity activity;
        private final String tag;
        private final Class<T> fClass;
        private Context context;

        public MyTabListener(Activity activity, String tag, Class<T> c) {
            this.activity = activity;
            this.tag = tag;
            this.fClass = c;
        }

        @Override
        public void onTabReselected(Tab t, FragmentTransaction ft) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onTabSelected(Tab t, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            if (fragment == null) {
                fragment = Fragment.instantiate(activity, fClass.getName());
                ft.replace(android.R.id.content, fragment);

            } else
                ft.attach(fragment);
        }

        @Override
        public void onTabUnselected(Tab t, FragmentTransaction ft) {
            // TODO Auto-generated method stub
            if (fragment != null) {
                ft.detach(fragment);
            }
        }

    }

}
