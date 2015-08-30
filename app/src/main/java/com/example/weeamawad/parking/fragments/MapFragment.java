package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weeamawad.parking.Listeners.GeocodeListener;
import com.example.weeamawad.parking.Listeners.ParkingListener;
import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.ServiceUtility;
import com.example.weeamawad.parking.adapters.AutoCompleteAdapter;
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

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, OnMarkerClickListener {
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
    private double newLat;
    private double newLng;
    private boolean mRequestingLocationUpdates;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);

        autoCompView = (AutoCompleteTextView) v.findViewById(R.id.autoComplete);
        autoCompView.setThreshold(1);
        AutoCompleteAdapter adapter = new AutoCompleteAdapter(getActivity(), android.R.layout.simple_list_item_1);
        autoCompView.setAdapter(adapter);
        autoCompView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(autoCompView.getWindowToken(), 0);

                Toast.makeText(mContext, autoCompView.getText().toString(), Toast.LENGTH_LONG).show();
                ServiceUtility.geocodeService(getActivity(), autoCompView.getText().toString(), new GeocodeListener() {
                    @Override
                    public void onSuccess(LatLng location) {
                        newLat = location.latitude;
                        newLng = location.longitude;
                        System.out.println(newLat);
                        System.out.println(newLng);
                        findNearbyParking(location);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });

        findParkingBtn = (Button) v.findViewById(R.id.findparkingbtn);
        findParkingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                findParkingBtn.setText("Pushed");
                //40.7903 73.9597 manhattan
                //34.0508590,-118.2489990 LA
                findNearbyParking(new LatLng(40.7903, -73.9597));
            }
        });

        ImageButton listBtn = (ImageButton) v.findViewById(R.id.listButton);
        listBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String t = "Los+Angeles,+CA,+United+States";
                Toast.makeText(mContext, t, Toast.LENGTH_LONG).show();
                ServiceUtility.geocodeService(getActivity(), t, new GeocodeListener() {
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

        outerBottomPanel = (LinearLayout) v.findViewById(R.id.OuterBottomPanel);
        map = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
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
                outerBottomPanel.setVisibility(View.INVISIBLE);
                Toast.makeText(mContext, Double.toString(position.latitude) + "," + Double.toString(position.longitude), Toast.LENGTH_SHORT).show();
                findNearbyParking(position);

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = getActivity();
        buildGoogleAPI();
        mGoogleApiClient.connect();
        createLocationRequest();
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
        {


        }
    }

    @Override
    public void onConnected(Bundle arg0) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Toast.makeText(mContext, "Connected", Toast.LENGTH_SHORT).show();
        Toast.makeText(mContext, mLastLocation.toString(), Toast.LENGTH_LONG).show();
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

    private void buildGoogleAPI() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    private void findNearbyParking(LatLng position) {
        ServiceUtility.parkingService(getActivity(), position, new ParkingListener() {
            @Override
            public void onSuccess(final ArrayList<Place> parkingLocations) {
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

        int index = Integer.parseInt(m.getSnippet());
        Place chosenPlace = parkingPlaces.get(index);
        DecimalFormat f = new DecimalFormat("###.#");


        outerBottomPanel.setVisibility(View.VISIBLE);
        LinearLayout bottomPanel = (LinearLayout) this.getView().findViewById(R.id.bottomPanel);
        TextView placeName = (TextView) this.getView().findViewById(R.id.mapPlaceName);
        final TextView placeAddress = (TextView) this.getView().findViewById(R.id.mapPlaceAddress);
        TextView placeDistance = (TextView) this.getView().findViewById(R.id.mapDistanceInfo);
        TextView placePrice = (TextView) this.getView().findViewById(R.id.mapPriceInfo);
        TextView placeOpenings = (TextView) this.getView().findViewById(R.id.mapOpeningsInfo);


        placeName.setText(chosenPlace.getName());
        placeAddress.setText(chosenPlace.getAddress());
        placeDistance.setText(f.format(chosenPlace.getDistance()) + " miles");
        placePrice.setText("$" + chosenPlace.getPrice());
        placeOpenings.setText(chosenPlace.getFreeSpots() + " Openings");

        final String address = chosenPlace.getCompleteAddress().replace(" ", "+");
        ImageButton navigationBtn = (ImageButton) this.getView().findViewById(R.id.launchNavigationBtn1);
        navigationBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String uri = "google.navigation:q=" + address;
                Toast.makeText(getActivity(), uri, Toast.LENGTH_LONG).show();
                Intent navigationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                startActivity(navigationIntent);

            }
        });


        return true;
    }

}

