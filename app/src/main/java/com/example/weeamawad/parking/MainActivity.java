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
import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.Volley.VolleyRequestQueue;
import com.example.weeamawad.parking.adapters.NavigationDrawerAdapter;
import com.example.weeamawad.parking.fragments.FavoritesFragment;
import com.example.weeamawad.parking.fragments.FilterFragment;
import com.example.weeamawad.parking.fragments.MapFragment;

public class MainActivity extends AppCompatActivity implements FilterFragment.OnApplyFilterListenter {
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
        setDrawerWidth();

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
    public void applyFilter() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.executePendingTransactions();
        MapFragment fragment = (MapFragment) fragmentManager.findFragmentByTag(MapFragment.class.getSimpleName());
        if (!Utils.checkIfNull(fragment)) {
            fragment.findNearbyParking(null);
        }
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
        Fragment fragment = null;
        Class fragmentClass = MapFragment.class;
        Bundle args = new Bundle();

        if (position != currentPosition) {
            switch (position) {
                case 0:
                    fragmentClass = MapFragment.class;
                    break;
                case 1:
                    break;
                case 2:
                    fragmentClass = FavoritesFragment.class;
                    args.putBoolean(Constants.FAVORITES_KEY, true);
                    break;
                case 3:
                    break;
                case 4:
                    fragmentClass = FavoritesFragment.class;
                    args.putBoolean(Constants.FAVORITES_KEY, false);
                    break;
                case 5:
                    break;
                default:
                    fragmentClass = MapFragment.class;
                    break;
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }

            fragment.setArguments(args);

            if (Utils.checkIfNull(getSupportFragmentManager().getFragments())) {
                replaceFragment(fragment, fragmentClass.getSimpleName());
            } else {
                addFragment(fragment, fragmentClass.getSimpleName());
            }
        }

        // update selected item title, then close the drawer
        updateSelection(position);

    }

    private void updateSelection(int position) {
        setTitle(mSettingTitles[position]);
        currentPosition = position;
        mDrawerList.setItemChecked(position, true);
        mDrawerList.setSelection(position);
        mDrawerLayout.closeDrawer(mDrawerLinearLayout);
    }

    private void replaceFragment(Fragment fragment, String fragmentTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment, fragmentTag);
        ft.commit();
    }

    private void addFragment(Fragment fragment, String fragmentTag) {
        removeFragment(); //removes current fragment before adding new one
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.content_frame, fragment, fragmentTag);
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }

    private void removeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            Fragment fragment = fragmentManager.getFragments().get(fragmentManager.getFragments().size() - 1);
            fragmentManager.beginTransaction().remove(fragment).commit();
            fragmentManager.popBackStack();
            fragmentManager.executePendingTransactions();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            removeFragment();
            updateSelection(0);
            return;
        }
        super.onBackPressed();
    }
}
