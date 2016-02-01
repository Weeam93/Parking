package com.example.weeamawad.parking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.weeamawad.parking.R;

/**
 * Created by Weeam Awad on 8/31/2015.
 */
public class NavigationAdapter extends BaseAdapter {
    private Context mContext;
    String[] navHeaders = null;
    int[] navImages = {R.drawable.ic_home_white_24dp, R.drawable.ic_setting_dark, R.drawable.ic_bookmark_white_24dp, R.drawable.ic_directions_car_white_24dp, R.drawable.ic_history_white_24dp, R.drawable.ic_info_outline_white_24dp};

    public NavigationAdapter(Context context, String[] settingTitles) {
        mContext = context;
        navHeaders = settingTitles;
    }

    @Override
    public int getCount() {
        return navHeaders.length;
    }

    @Override
    public String getItem(int position) {
        ;
        return navHeaders[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.row_drawer_navigation, parent, false);
        } else {
            row = convertView;
        }
        TextView navigationTxtView = (TextView) row.findViewById(R.id.tv_navigation);
        ImageButton navigationButton = (ImageButton) row.findViewById(R.id.ib_navigation);
        navigationTxtView.setText(navHeaders[position]);
        navigationButton.setImageResource(navImages[position]);

        return row;
    }
}
