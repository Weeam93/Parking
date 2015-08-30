package com.example.weeamawad.parking.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.adapters.PlaceAdapter;
import com.example.weeamawad.parking.model.Place;
import com.example.weeamawad.parking.model.PlacesModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlaceListFragment extends Fragment {

    private ListView list;
    private ArrayList<Place> parkingPlaces;
    private Button distanceBtn;
    private Button priceBtn;
    private Button optimizedBtn;
    private PlaceAdapter adapter;

    int btnSelectedTxtColor;
    int btnDefualtTxtColor;

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.place_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        btnSelectedTxtColor = getResources().getColor(R.color.aqua);
        btnDefualtTxtColor = getResources().getColor(R.color.white);

        list = (ListView) v.findViewById(R.id.placeList);
        distanceBtn = (Button) v.findViewById(R.id.distanceBtn);
        priceBtn = (Button) v.findViewById(R.id.priceBtn);

        parkingPlaces = PlacesModel.getParkingPlaces();
        adapter = new PlaceAdapter(this.getActivity(), R.layout.custom_place_view, parkingPlaces);
        list.setAdapter(adapter);

        distanceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                distanceBtn.setTextColor(btnSelectedTxtColor);
                priceBtn.setTextColor(btnDefualtTxtColor);


                Collections.sort(parkingPlaces, new Comparator<Place>() {

                    @Override
                    public int compare(Place p1, Place p2) {
                        // TODO Auto-generated method stub
                        if (p1.getDistance() < p2.getDistance()) {
                            return -1;
                        } else if (p1.getDistance() > p2.getDistance()) {
                            return 1;
                        }
                        return 0;
                    }

                });

                adapter.notifyDataSetChanged();
            }
        });

        priceBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                distanceBtn.setTextColor(btnDefualtTxtColor);
                priceBtn.setTextColor(btnSelectedTxtColor);


                Collections.sort(parkingPlaces, new Comparator<Place>() {

                    @Override
                    public int compare(Place p1, Place p2) {
                        // TODO Auto-generated method stub
                        return (p1.getPrice() - p2.getPrice());
                    }

                });

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println(parkingPlaces.get(0).getName());

    }

	/*public void sortDistanceBtn(View v)
    {
		distanceBtn.setTextColor(btnSelectedTxtColor);
		priceBtn.setTextColor(btnDefualtTxtColor);
		optimizedBtn.setTextColor(btnDefualtTxtColor);

		Collections.sort(parkingPlaces, new Comparator<Place>(){

			@Override
			public int compare(Place p1, Place p2) {
				// TODO Auto-generated method stub
				if(p1.getDistance() < p2.getDistance())
				{
					return -1;
				}
				else if(p1.getDistance() > p2.getDistance())
				{
					return 1;
				}
				return 0;
			}

		});

		adapter.notifyDataSetChanged();
	}
	public void sortPriceBtn(View v)
	{
		distanceBtn.setTextColor(btnDefualtTxtColor);
		priceBtn.setTextColor(btnSelectedTxtColor);
		optimizedBtn.setTextColor(btnDefualtTxtColor);

		Collections.sort(parkingPlaces, new Comparator<Place>(){

			@Override
			public int compare(Place p1, Place p2) {
				// TODO Auto-generated method stub
				return (p1.getPrice() - p2.getPrice());
			}

		});

		adapter.notifyDataSetChanged();
	}*/


}
