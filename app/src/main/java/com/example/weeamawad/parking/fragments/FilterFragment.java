package com.example.weeamawad.parking.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.weeamawad.parking.R;
import com.example.weeamawad.parking.Utility.Constants;
import com.example.weeamawad.parking.Utility.SharedPreference;
import com.example.weeamawad.parking.Utility.Utils;
import com.example.weeamawad.parking.adapters.FilterAdapter;
import com.example.weeamawad.parking.databinding.FragmentFilterBinding;
import com.example.weeamawad.parking.databinding.LinearLayoutFilterSectionBinding;
import com.example.weeamawad.parking.model.AppSettingsModel;
import com.example.weeamawad.parking.model.NewFilterModel;

import org.w3c.dom.Text;


/**
 * Created by Weeam Awad on 2/20/2016.
 */
public class FilterFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private View mRootView;
    private NewFilterModel filter;
    private Button btnApply;
    private FragmentFilterBinding mBinding;
    private SharedPreference pref;
    private CheckBox isMonthly, isShuttle, isEticket, isPerk, isValet, isIndoor, isHandicap, isRestRoom, isSecurity, isTailGate, isRv, isUnObstructed, isAttended, isReentryAllowed;
    private SeekBar maxPrice;
    private TextView tvMaxPrice;
    private OnApplyFilterListenter mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter, container, false);
        mRootView = mBinding.getRoot();
        mContext = getActivity();
        initData();
        initViews();
        initListeners();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        pref = new SharedPreference(mContext, Constants.SHARED_PREFRENCE_DEFAULT);
        filter = (NewFilterModel) pref.getObjectPref(Constants.FILTER_DATA, new NewFilterModel());
        if (Utils.checkIfNull(filter)) {
            filter = new NewFilterModel();
        }
        mBinding.setFilter(filter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (OnApplyFilterListenter) getActivity();
    }

    private void initViews() {
        btnApply = (Button) mRootView.findViewById(R.id.btn_apply_filter);
        maxPrice = (SeekBar) mRootView.findViewById(R.id.sb_max_price);
        tvMaxPrice = (TextView) mRootView.findViewById(R.id.tv_max_price);
        isMonthly = (CheckBox) mRootView.findViewById(R.id.cb_isMonthly);
        isShuttle = (CheckBox) mRootView.findViewById(R.id.cb_isShuttle);
        isEticket = (CheckBox) mRootView.findViewById(R.id.cb_isETicket);
        isPerk = (CheckBox) mRootView.findViewById(R.id.cb_isPerk);
        isValet = (CheckBox) mRootView.findViewById(R.id.cb_isValet);
        isIndoor = (CheckBox) mRootView.findViewById(R.id.cb_isIndoor);
        isHandicap = (CheckBox) mRootView.findViewById(R.id.cb_isHandicap);
        isRestRoom = (CheckBox) mRootView.findViewById(R.id.cb_isRestRoom);
        isSecurity = (CheckBox) mRootView.findViewById(R.id.cb_isSecurity);
        isTailGate = (CheckBox) mRootView.findViewById(R.id.cb_isTailGate);
        isRv = (CheckBox) mRootView.findViewById(R.id.cb_isRv);
        isUnObstructed = (CheckBox) mRootView.findViewById(R.id.cb_isUnObstructed);
        isAttended = (CheckBox) mRootView.findViewById(R.id.cb_isAttended);
        isReentryAllowed = (CheckBox) mRootView.findViewById(R.id.cb_isReentryAllowed);

    }

    private void initListeners() {
        btnApply.setOnClickListener(this);
        isMonthly.setOnClickListener(this);
        isShuttle.setOnClickListener(this);
        isEticket.setOnClickListener(this);
        isPerk.setOnClickListener(this);
        isValet.setOnClickListener(this);
        isIndoor.setOnClickListener(this);
        isHandicap.setOnClickListener(this);
        isRestRoom.setOnClickListener(this);
        isSecurity.setOnClickListener(this);
        isTailGate.setOnClickListener(this);
        isRv.setOnClickListener(this);
        isUnObstructed.setOnClickListener(this);
        isAttended.setOnClickListener(this);
        isReentryAllowed.setOnClickListener(this);
        maxPrice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvMaxPrice.setText("MAX PRICE: $" + progress);
                filter.setMaxPrice(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply_filter:
                pref.setObjectPref(Constants.FILTER_DATA, filter);
                AppSettingsModel.filters = filter;
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().remove(this).commit();
                mCallback.applyFilter();
                break;
            case R.id.cb_isMonthly:
                filter.setIsMonthly(isMonthly.isChecked());
                break;
            case R.id.cb_isShuttle:
                filter.setIsShuttle(isShuttle.isChecked());
                break;
            case R.id.cb_isETicket:
                filter.setIsEticket(isEticket.isChecked());
                break;
            case R.id.cb_isPerk:
                filter.setIsPerk(isPerk.isChecked());
                break;
            case R.id.cb_isValet:
                filter.setIsValet(isValet.isChecked());
                break;
            case R.id.cb_isIndoor:
                filter.setIsIndoor(isIndoor.isChecked());
                break;
            case R.id.cb_isHandicap:
                filter.setIsHandicap(isHandicap.isChecked());
                break;
            case R.id.cb_isRestRoom:
                filter.setIsRestRoom(isRestRoom.isChecked());
                break;
            case R.id.cb_isSecurity:
                filter.setIsSecurity(isSecurity.isChecked());
                break;
            case R.id.cb_isTailGate:
                filter.setIsTailGate(isTailGate.isChecked());
                break;
            case R.id.cb_isRv:
                filter.setIsRv(isRv.isChecked());
                break;
            case R.id.cb_isUnObstructed:
                filter.setIsUnObstructed(isUnObstructed.isChecked());
                break;
            case R.id.cb_isAttended:
                filter.setIsAttended(isAttended.isChecked());
                break;
            case R.id.cb_isReentryAllowed:
                filter.setIsReentryAllowed(isReentryAllowed.isChecked());
                break;
        }
    }

    public interface OnApplyFilterListenter {
        void applyFilter();
    }
}
