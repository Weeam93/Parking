package com.example.weeamawad.parking.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weeamawad.parking.Listeners.GeocodeListener;
import com.example.weeamawad.parking.Listeners.ParkingListener;
import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.Constants;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.Utility.ServiceUtility;
import com.example.weeamawad.parking.adapters.AutoCompleteAdapter;
import com.example.weeamawad.parking.adapters.NavigationAdapter;
import com.example.weeamawad.parking.model.AppSettingsModel;
import com.example.weeamawad.parking.model.Place;
import com.example.weeamawad.parking.model.PlacesModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MapFragment extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMarkerClickListener, View.OnClickListener {
    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LatLng myCoordinates;
    private Circle vision;
    private ArrayList<Place> parkingPlaces;
    private AutoCompleteTextView autoCompView;
    private Context mContext;
    private Button findParkingBtn;
    private LinearLayout outerBottomPanel;
    private boolean mRequestingLocationUpdates;
    private ImageButton listBtn;
    private ImageButton favoriteBtnOff;
    private ImageButton favoriteBtnOn;
    private ImageButton navigationBtn;
    private LinearLayout bottomPanel;
    private TextView placeName;
    private TextView placeOpenings;
    private TextView placeDistance;
    private TextView placeAddress;
    private TextView placePrice;
    private String placeCompleteAddress;
    private ListView mLv_DrawerList;
    private DrawerLayout mDl_Navigation;
    private String[] mList;
    private ImageButton myLocationBtn;
    private Place selectedPlace;

    private NavigationAdapter settingsAdapter;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);     // 5 seconds
        mLocationRequest.setFastestInterval(16);  // 16ms = 60fps
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        mRequestingLocationUpdates = true;
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    protected void stopLocationUpdates() {
        mRequestingLocationUpdates = false;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected() && !mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mContext = this;
        initMap();
        initViews();
        initListeners();
        initialize();



        /*if(!m.isProviderEnabled(LocationManager.GPS_PROVIDER) && !m.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {

			AlertDialog.Builder d=new AlertDialog.Builder(context);
			d.setTitle("GPS Disabled");
			d.setMessage("Location Settings must be Enabled");
			d.setPositiveButton("Settings", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent gpsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(gpsIntent);
				}});
			d.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub

				}});
			d.create();
			d.show();
		}*/
    }

    @Override
    public void onConnected(Bundle arg0) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        myCoordinates = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        CircleOptions c = new CircleOptions()
                .center(myCoordinates)
                .radius(500)
                .strokeColor(Color.argb(62, 0, 255, 0))
                .fillColor(Color.argb(62, 0, 0, 255));
        vision = map.addCircle(c);

        updateCameraLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
        // TODO Auto-generated method stub
        //Toast.makeText(context, "Connection Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_listButton:
                mDl_Navigation.openDrawer(Gravity.LEFT);
                break;
            case R.id.ib_myLocatoinBtn:
                updateCameraLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());
                break;
            case R.id.autoComplete:
                break;
            case R.id.ib_favoriteOff:
                Log.i("Favorite", "Clicked");
                favoriteBtnOff.setVisibility(View.GONE);
                favoriteBtnOn.setVisibility(View.VISIBLE);
                DatabaseUtils.saveFavorite(mContext, selectedPlace);
                break;
            case R.id.ib_favoriteOn:
                Log.i("Favorite", "Clicked");
                favoriteBtnOn.setVisibility(View.GONE);
                favoriteBtnOff.setVisibility(View.VISIBLE);
                DatabaseUtils.deleteFavorite(mContext, selectedPlace);
                break;
            case R.id.launchNavigationBtn1:
                String uri = "google.navigation:q=" + placeCompleteAddress;
                Toast.makeText(mContext, uri, Toast.LENGTH_LONG).show();
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(navigationIntent);
                break;
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        map.getUiSettings().setMyLocationButtonEnabled(false);
        map.getUiSettings().setCompassEnabled(false);
        map.getUiSettings().setZoomControlsEnabled(false);
        map.setOnMarkerClickListener(this);
        map.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng position) {
                // TODO Auto-generated method stub
                map.clear();
                outerBottomPanel.setVisibility(View.GONE);
                Toast.makeText(mContext, Double.toString(position.latitude) + "," + Double.toString(position.longitude), Toast.LENGTH_SHORT).show();
                findNearbyParking(position);
            }
        });
    }

    private void initViews() {
        listBtn = (ImageButton) findViewById(R.id.ib_listButton);
        myLocationBtn = (ImageButton) findViewById(R.id.ib_myLocatoinBtn);
        outerBottomPanel = (LinearLayout) findViewById(R.id.OuterBottomPanel);
        bottomPanel = (LinearLayout) findViewById(R.id.bottomPanel);
        placeName = (TextView) findViewById(R.id.mapPlaceName);
        placeAddress = (TextView) findViewById(R.id.mapPlaceAddress);
        placeDistance = (TextView) findViewById(R.id.mapDistanceInfo);
        placePrice = (TextView) findViewById(R.id.mapPriceInfo);
        placeOpenings = (TextView) findViewById(R.id.mapOpeningsInfo);
        favoriteBtnOff = (ImageButton) findViewById(R.id.ib_favoriteOff);
        favoriteBtnOn = (ImageButton) findViewById(R.id.ib_favoriteOn);
        navigationBtn = (ImageButton) findViewById(R.id.launchNavigationBtn1);
        autoCompView = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        autoCompView.setThreshold(1);

    }

    private void initListeners() {
        myLocationBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
        favoriteBtnOff.setOnClickListener(this);
        favoriteBtnOn.setOnClickListener(this);
        navigationBtn.setOnClickListener(this);
    }

    private void initialize() {
        mContext = this;
        buildGoogleAPI();
        mGoogleApiClient.connect();
        createLocationRequest();
        autoCompView.setAdapter(new AutoCompleteAdapter(mContext, android.R.layout.simple_list_item_1));
        autoCompView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(autoCompView.getWindowToken(), 0);

                ServiceUtility.geocodeService(mContext, autoCompView.getText().toString(), new GeocodeListener() {
                    @Override
                    public void onSuccess(LatLng location) {
                        findNearbyParking(location);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    private void buildGoogleAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    private void findNearbyParking(final LatLng position) {
        ServiceUtility.parkingServiceSearch(mContext, position, new ParkingListener() {
            @Override
            public void onSuccess(final ArrayList<Place> parkingLocations) {
                parkingPlaces = parkingLocations;
                map.addMarker(new MarkerOptions()
                        .position(position)
                        .snippet(Constants.CENTER_LOCATION));
                android.os.Handler h = new android.os.Handler();
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Plotting");
                        IconGenerator iconGenerator = new IconGenerator(mContext);
                        iconGenerator.setStyle(iconGenerator.STYLE_BLUE);
                        iconGenerator.setTextAppearance(R.style.Bubble_TextAppearance_Light);
                        PlacesModel.setParkingPlaces(parkingLocations);
                        try {
                            for (int i = 0; i < parkingLocations.size(); i++) {
                                Place temp = parkingLocations.get(i);
                                Bitmap bmp = iconGenerator.makeIcon("$" + Integer.toString(temp.getPrice()));
                                map.addMarker(new MarkerOptions()
                                        .title(temp.getName()) //name
                                        .position(new LatLng(temp.getLatitude(), temp.getLongitude())) //location
                                        .snippet(Integer.toString(i))
                                        .icon(BitmapDescriptorFactory.fromBitmap(bmp)));
                            }
                            updateCameraLocation(parkingLocations.get(0).getLatitude(), parkingLocations.get(0).getLongitude());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "No Nearby Parking Found", Toast.LENGTH_SHORT).show();
                        }
                        System.out.println("Finished");
                    }
                });
            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void updateCameraLocation(double latitude, double longitude) {
        CameraPosition cam = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(14)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cam));
    }

    @Override
    public boolean onMarkerClick(Marker m) {

        // TODO Auto-generated method stub
        System.out.println("Marker CLicked");
        if (m.getSnippet().equalsIgnoreCase(Constants.CENTER_LOCATION)) {
            return false;
        } else {
            int index = Integer.parseInt(m.getSnippet());
            selectedPlace = parkingPlaces.get(index);
            DecimalFormat f = new DecimalFormat("###.#");

            outerBottomPanel.setVisibility(View.VISIBLE);
            placeName.setText(selectedPlace.getName());
            placeAddress.setText(selectedPlace.getAddress());
            placeDistance.setText(f.format(selectedPlace.getDistance()) + " miles");
            placePrice.setText("$" + selectedPlace.getPrice());
            placeOpenings.setText(selectedPlace.getFreeSpots() + " Opening");
            placeCompleteAddress = selectedPlace.getCompleteAddress().replace(" ", "+");
            DatabaseUtils.saveRecent(mContext, selectedPlace);

            return true;
        }
    }
}

