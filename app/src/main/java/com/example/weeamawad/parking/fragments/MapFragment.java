package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.view.BodyTextView;
import com.arlib.floatingsearchview.util.view.IconImageView;
import com.example.weeamawad.parking.Listeners.AutoCompleteListener;
import com.example.weeamawad.parking.Listeners.GeocodeListener;
import com.example.weeamawad.parking.Listeners.ParkingListener;
import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.Constants;
import com.example.weeamawad.parking.Utility.DatabaseUtils;
import com.example.weeamawad.parking.Utility.ServiceUtility;
import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.entities.AutoCompleteSuggestion;
import com.example.weeamawad.parking.entities.Place;
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

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMarkerClickListener, View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName().toString();

    private GoogleMap map;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private LocationRequest mLocationRequest;
    private LatLng myCoordinates;
    private Circle vision;
    private ArrayList<Place> parkingPlaces;
    private Context mContext;
    private LinearLayout outerBottomPanel;
    private boolean mRequestingLocationUpdates;
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
    private ImageButton myLocationBtn;
    private Place selectedPlace;
    private boolean isGpsClicked;

    private View mRootView;
    private FloatingSearchView mSearchView;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.activity_map, container, false);
        mContext = getActivity();
        initMap();
        initViews();
        initListeners();
        initialize();
        return mRootView;

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
            case R.id.autoComplete:
                break;
            case R.id.ib_favoriteOff:
                Log.i(TAG, "Favorite Off Clicked");
                favoriteBtnOff.setVisibility(View.GONE);
                favoriteBtnOn.setVisibility(View.VISIBLE);
                DatabaseUtils.saveFavorite(mContext, selectedPlace);
                break;
            case R.id.ib_favoriteOn:
                Log.i(TAG, "Favorite On Clicked");
                favoriteBtnOn.setVisibility(View.GONE);
                favoriteBtnOff.setVisibility(View.VISIBLE);
                DatabaseUtils.deleteFavorite(mContext, selectedPlace);
                break;
            case R.id.launchNavigationBtn1:
                String uri = Constants.OPEN_GOOGLE_MAPS + placeCompleteAddress;
                Toast.makeText(mContext, uri, Toast.LENGTH_LONG).show();
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(navigationIntent);
                break;
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
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
                if (outerBottomPanel.getVisibility() != View.GONE) {
                    Utils.animateOutViewFromFromSide(outerBottomPanel, true);
                }
                Toast.makeText(mContext, Double.toString(position.latitude) + "," + Double.toString(position.longitude), Toast.LENGTH_SHORT).show();
                findNearbyParking(position);
            }
        });
    }

    private void initViews() {

        mSearchView = (FloatingSearchView) mRootView.findViewById(R.id.floating_search_view);
        outerBottomPanel = (LinearLayout) mRootView.findViewById(R.id.OuterBottomPanel);
        bottomPanel = (LinearLayout) mRootView.findViewById(R.id.bottomPanel);
        placeName = (TextView) mRootView.findViewById(R.id.mapPlaceName);
        placeAddress = (TextView) mRootView.findViewById(R.id.mapPlaceAddress);
        placeDistance = (TextView) mRootView.findViewById(R.id.mapDistanceInfo);
        placePrice = (TextView) mRootView.findViewById(R.id.mapPriceInfo);
        placeOpenings = (TextView) mRootView.findViewById(R.id.mapOpeningsInfo);
        favoriteBtnOff = (ImageButton) mRootView.findViewById(R.id.ib_favoriteOff);
        favoriteBtnOn = (ImageButton) mRootView.findViewById(R.id.ib_favoriteOn);
        navigationBtn = (ImageButton) mRootView.findViewById(R.id.launchNavigationBtn1);

    }

    private void initListeners() {
        // myLocationBtn.setOnClickListener(this);
        favoriteBtnOff.setOnClickListener(this);
        favoriteBtnOn.setOnClickListener(this);
        navigationBtn.setOnClickListener(this);
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                } else {

                    //this shows the top left circular progress
                    //you can call it where ever you want, but
                    //it makes sense to do it when loading something in
                    //the background.
                    mSearchView.showProgress();

                    //simulates a query call to a data source
                    //with a new query.
                    ServiceUtility.autoComplete(mContext, newQuery, new AutoCompleteListener() {
                        @Override
                        public void onSuccess(ArrayList<AutoCompleteSuggestion> strings) {

                            mSearchView.swapSuggestions(strings);
                            mSearchView.hideProgress();
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }

                Log.d("SearchBar", "onSearchTextChanged()");
            }
        });
        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Log.d(TAG, "onSuggestionClicked()");
                ServiceUtility.geocodeService(mContext, searchSuggestion.getBody(), new GeocodeListener() {
                    @Override
                    public void onSuccess(LatLng location) {
                        findNearbyParking(location);
                    }

                    @Override
                    public void onFailure() {
                    }
                });

            }

            @Override
            public void onSearchAction() {

                Log.d(TAG, "onSearchAction()");
            }
        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_myLocation:
                        if (!isGpsClicked) {
                            isGpsClicked = true;
                            mSearchView.setMenuItemIconColor(getResources().getColor(R.color.Teal));
                            updateCameraLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                        } else {
                            isGpsClicked = false;
                            mSearchView.setMenuItemIconColor(getResources().getColor(R.color.actionMenu_color));
                        }
                        break;
                }
            }
        });
        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(IconImageView leftIcon, BodyTextView bodyText, SearchSuggestion item, int itemPosition) {
                leftIcon.setImageResource(R.drawable.ic_place_black_24dp);
                leftIcon.setAlpha(.36f);
            }
        });
    }

    private void initialize() {
        buildGoogleAPI();
        mGoogleApiClient.connect();
        createLocationRequest();
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

            if (outerBottomPanel.getVisibility() != View.VISIBLE) {
                Utils.animateInViewFromFromSide(outerBottomPanel, true);
            }
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

