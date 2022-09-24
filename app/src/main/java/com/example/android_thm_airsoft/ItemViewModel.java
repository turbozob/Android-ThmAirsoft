package com.example.android_thm_airsoft;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {

    // values which will be used for fragment share

    // FragGameTime
    private final MutableLiveData<String> FragGameTime = new MutableLiveData<String>();
    public void setFragGameTime(String value) {FragGameTime.setValue(value);}
    public LiveData<String> getFragGameTime() {return FragGameTime;}

    // FragBombTime
    private final MutableLiveData<String> FragBombTime = new MutableLiveData<String>();
    public void setFragBombTime(String value) {
        FragBombTime.setValue(value);
    }
    public LiveData<String> getFragBombTime() {return FragBombTime;}

    // FragArmPinCode
    private final MutableLiveData<String> FragArmPinCode = new MutableLiveData<String>();
    public void setFragArmPinCode(String value) {
        FragArmPinCode.setValue(value);
    }
    public LiveData<String> getFragArmPinCode() {return FragArmPinCode;}

    // FragDisarmPinCode
    private final MutableLiveData<String> FragDisarmPinCode = new MutableLiveData<String>();
    public void setFragDisarmPinCode(String value) {
        FragDisarmPinCode.setValue(value);
    }
    public LiveData<String> getFragDisarmPinCode() {return FragDisarmPinCode;}

    // FragWinnerName
    private final MutableLiveData<String> FragWinnerName = new MutableLiveData<String>();
    public void setFragWinnerName(String value) {FragWinnerName.setValue(value);}
    public LiveData<String> getFragWinnerName() {return FragWinnerName;}

    // currentGameTime
    private final MutableLiveData<String> currentGameTime = new MutableLiveData<String>();
    public void setCurrentGameTime(String value) {currentGameTime.setValue(value);}
    public LiveData<String> getCurrentGameTime() {return currentGameTime;}

    // currentBombTime
    private final MutableLiveData<String> currentBombTime = new MutableLiveData<String>();
    public void setCurrentBombTime(String value) {currentBombTime.setValue(value);}
    public LiveData<String> getCurrentBombTime() {return currentBombTime;}



}

