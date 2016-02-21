package com.example.weeamawad.parking;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.weeamawad.parking.Utility.Constants;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.Utility.SharedPreference;
import com.example.weeamawad.parking.Volley.VolleyRequestQueue;
import com.example.weeamawad.parking.adapters.NavigationDrawerAdapter;
import com.example.weeamawad.parking.fragments.FavoritesFragment;
import com.example.weeamawad.parking.fragments.MapFragment;
import com.example.weeamawad.parking.model.FilterModel;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private NavigationDrawerAdapter settingsAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mSettingTitles;
    private int currentPosition = -1;
    private LinearLayout mDrawerLinearLayout;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VolleyRequestQueue.getInstance(this);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mTitle = mDrawerTitle = getTitle();
        mSettingTitles = getResources().getStringArray(R.array.navHeaders);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_navigation);
        mDrawerLinearLayout = (LinearLayout) findViewById(R.id.ll_drawer);
        mDrawerList = (ListView) findViewById(R.id.lv_navigation);
        settingsAdapter = new NavigationDrawerAdapter(this, mSettingTitles);
        mDrawerList.setAdapter(settingsAdapter);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setDrawerWidth();
        loadData();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar, R.string.drawer_open, R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
    }

    private void loadData() {
        /*SharedPreference pref = new SharedPreference(this, Constants.SHARED_PREFRENCE_DEFAULT);
        if (!pref.getBooleanPref(Constants.IS_NOT_CLEAN_INSTALL, false)) {
            String[] labels = getResources().getStringArray(R.array.filterLabels);
            String[] descriptions = getResources().getStringArray(R.array.filterDescriptions);
            DatabaseUtils.deleteAllFavorites(this);
            for (int i = 0; i < labels.length; i++) {
                FilterModel fm = new FilterModel(labels[i], descriptions[i], 0);
                DatabaseUtils.saveFilter(this, fm);
            }
            pref.setPref(Constants.IS_NOT_CLEAN_INSTALL, true);
        }*/
    }

    private void setDrawerWidth() {
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        int width = getResources().getDisplayMetrics().widthPixels;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mDrawerLinearLayout.getLayoutParams();
        params.width = width - mActionBarSize;
        mDrawerLinearLayout.setLayoutParams(params);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment;
        if (position != currentPosition) {
            switch (position) {
                case 0:
                    fragment = new MapFragment();
                    replaceFragment(fragment);
                    break;
                case 1:
                    break;
                case 2:
                    fragment = new FavoritesFragment();
                    Bundle favArgs = new Bundle();
                    favArgs.putBoolean(Constants.FAVORITES_KEY, true);
                    fragment.setArguments(favArgs);
                    replaceFragment(fragment);
                    break;
                case 3:
                    break;
                case 4:
                    fragment = new FavoritesFragment();
                    Bundle recentArgs = new Bundle();
                    recentArgs.putBoolean(Constants.FAVORITES_KEY, false);
                    fragment.setArguments(recentArgs);
                    replaceFragment(fragment);
                    break;
                case 5:
                    break;

            }
        }
        // update selected item title, then close the drawer
        setTitle(mSettingTitles[position]);
        currentPosition = position;
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerLinearLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }


/* Called whenever we call invalidateOptionsMenu() */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerLinearLayout);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }
}
