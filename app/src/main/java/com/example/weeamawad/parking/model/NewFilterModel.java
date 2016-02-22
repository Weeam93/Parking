package com.example.weeamawad.parking.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.weeamawad.parking.BR;
import com.example.weeamawad.parking.Utility.Constants;

/**
 * Created by Weeam Awad on 2/21/2016.
 */
public class NewFilterModel extends BaseObservable {
    private int maxPrice = Constants.DEFAULT_MAX_PRICE;
    private boolean isEnabled;
    private boolean isMonthly;
    private boolean isShuttle;
    private boolean isEticket;
    private boolean isPerk;
    private boolean isValet;
    private boolean isIndoor;
    private boolean isHandicap;
    private boolean isRestRoom;
    private boolean isSecurity;
    private boolean isTailGate;
    private boolean isRv;
    private boolean isUnObstructed;
    private boolean isAttended;
    private boolean isReentryAllowed;

    public boolean isEnabled() {
        return isEnabled;
    }

    @Bindable
    public int getMaxPrice() {
        return maxPrice;
    }

    @Bindable
    public boolean isMonthly() {
        return isMonthly;
    }

    @Bindable
    public boolean isShuttle() {
        return isShuttle;
    }

    @Bindable
    public boolean isEticket() {
        return isEticket;
    }

    @Bindable
    public boolean isPerk() {
        return isPerk;
    }

    @Bindable
    public boolean isValet() {
        return isValet;
    }

    @Bindable
    public boolean isIndoor() {
        return isIndoor;
    }

    @Bindable
    public boolean isHandicap() {
        return isHandicap;
    }

    @Bindable
    public boolean isRestRoom() {
        return isRestRoom;
    }

    @Bindable
    public boolean isSecurity() {
        return isSecurity;
    }

    @Bindable
    public boolean isTailGate() {
        return isTailGate;
    }

    @Bindable
    public boolean isRv() {
        return isRv;
    }

    @Bindable
    public boolean isUnObstructed() {
        return isUnObstructed;
    }

    @Bindable
    public boolean isAttended() {
        return isAttended;
    }

    @Bindable
    public boolean isReentryAllowed() {
        return isReentryAllowed;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
        this.isEnabled = true;
        notifyPropertyChanged(BR.maxPrice);
    }

    public void setIsMonthly(boolean isMonthly) {
        this.isMonthly = isMonthly;
        this.isEnabled = true;
        notifyPropertyChanged(BR.monthly);
    }

    public void setIsShuttle(boolean isShuttle) {
        this.isShuttle = isShuttle;
        this.isEnabled = true;
        notifyPropertyChanged(BR.shuttle);
    }

    public void setIsEticket(boolean isEticket) {
        this.isEticket = isEticket;
        this.isEnabled = true;
        notifyPropertyChanged(BR.eticket);
    }

    public void setIsPerk(boolean isPerk) {
        this.isPerk = isPerk;
        this.isEnabled = true;
        notifyPropertyChanged(BR.perk);

    }

    public void setIsValet(boolean isValet) {
        this.isValet = isValet;
        this.isEnabled = true;
        notifyPropertyChanged(BR.valet);
    }

    public void setIsIndoor(boolean isIndoor) {
        this.isIndoor = isIndoor;
        this.isEnabled = true;
        notifyPropertyChanged(BR.indoor);

    }

    public void setIsHandicap(boolean isHandicap) {
        this.isHandicap = isHandicap;
        notifyPropertyChanged(BR.handicap);

    }

    public void setIsRestRoom(boolean isRestRoom) {
        this.isRestRoom = isRestRoom;
        this.isEnabled = true;
        notifyPropertyChanged(BR.restRoom);

    }

    public void setIsSecurity(boolean isSecurity) {
        this.isSecurity = isSecurity;
        this.isEnabled = true;
        notifyPropertyChanged(BR.security);

    }

    public void setIsTailGate(boolean isTailGate) {
        this.isTailGate = isTailGate;
        this.isEnabled = true;
        notifyPropertyChanged(BR.tailGate);

    }

    public void setIsRv(boolean isRv) {
        this.isRv = isRv;
        this.isEnabled = true;
        notifyPropertyChanged(BR.rv);

    }

    public void setIsUnObstructed(boolean isUnObstructed) {
        this.isUnObstructed = isUnObstructed;
        notifyPropertyChanged(BR.unObstructed);

    }

    public void setIsAttended(boolean isAttended) {
        this.isAttended = isAttended;
        this.isEnabled = true;
        notifyPropertyChanged(BR.attended);

    }

    public void setIsReentryAllowed(boolean isReentryAllowed) {
        this.isReentryAllowed = isReentryAllowed;
        this.isEnabled = true;
        notifyPropertyChanged(BR.reentryAllowed);

    }
}
