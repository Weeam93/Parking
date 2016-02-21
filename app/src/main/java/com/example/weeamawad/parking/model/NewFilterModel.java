package com.example.weeamawad.parking.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.weeamawad.parking.BR;

/**
 * Created by Weeam Awad on 2/21/2016.
 */
public class NewFilterModel extends BaseObservable {
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

    public void setIsMonthly(boolean isMonthly) {
        this.isMonthly = isMonthly;
        notifyPropertyChanged(BR.monthly);
    }

    public void setIsShuttle(boolean isShuttle) {
        this.isShuttle = isShuttle;
        notifyPropertyChanged(BR.shuttle);
    }

    public void setIsEticket(boolean isEticket) {
        this.isEticket = isEticket;
        notifyPropertyChanged(BR.eticket);
    }

    public void setIsPerk(boolean isPerk) {
        this.isPerk = isPerk;
        notifyPropertyChanged(BR.perk);

    }

    public void setIsValet(boolean isValet) {
        this.isValet = isValet;
        notifyPropertyChanged(BR.valet);
    }

    public void setIsIndoor(boolean isIndoor) {
        this.isIndoor = isIndoor;
        notifyPropertyChanged(BR.indoor);

    }

    public void setIsHandicap(boolean isHandicap) {
        this.isHandicap = isHandicap;
        notifyPropertyChanged(BR.handicap);

    }

    public void setIsRestRoom(boolean isRestRoom) {
        this.isRestRoom = isRestRoom;
        notifyPropertyChanged(BR.restRoom);

    }

    public void setIsSecurity(boolean isSecurity) {
        this.isSecurity = isSecurity;
        notifyPropertyChanged(BR.security);

    }

    public void setIsTailGate(boolean isTailGate) {
        this.isTailGate = isTailGate;
        notifyPropertyChanged(BR.tailGate);

    }

    public void setIsRv(boolean isRv) {
        this.isRv = isRv;
        notifyPropertyChanged(BR.rv);

    }

    public void setIsUnObstructed(boolean isUnObstructed) {
        this.isUnObstructed = isUnObstructed;
        notifyPropertyChanged(BR.unObstructed);

    }

    public void setIsAttended(boolean isAttended) {
        this.isAttended = isAttended;
        notifyPropertyChanged(BR.attended);

    }

    public void setIsReentryAllowed(boolean isReentryAllowed) {
        this.isReentryAllowed = isReentryAllowed;
        notifyPropertyChanged(BR.reentryAllowed);

    }
}
