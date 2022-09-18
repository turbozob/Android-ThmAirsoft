package com.example.android_thm_airsoft;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ItemViewModel extends ViewModel {

    // values which will be used for fragment share
    private final MutableLiveData<String> FragGameTime = new MutableLiveData<String>();

    public void setFragGameTime(String value) {
        FragGameTime.setValue(value);
    }

    public LiveData<String> getFragGameTime() {
        //selectedString.getValue();
        return FragGameTime;
    }

    private final MutableLiveData<String> FragBombTime = new MutableLiveData<String>();
    public void setFragBombTime(String value) {
        FragBombTime.setValue(value);
    }

    public LiveData<String> getFragBombTime() {
        //selectedString.getValue();
        return FragBombTime;
    }
    private final MutableLiveData<String> FragArmPinCode = new MutableLiveData<String>();
    public void setFragArmPinCode(String value) {
        FragArmPinCode.setValue(value);
    }

    public LiveData<String> getFragArmPinCode() {
        //selectedString.getValue();
        return FragArmPinCode;
    }
    private final MutableLiveData<String> FragDisarmPinCode = new MutableLiveData<String>();

    public void setFragDisarmPinCode(String value) {
        FragDisarmPinCode.setValue(value);
    }

    public LiveData<String> getFragDisarmPinCode() {
        //selectedString.getValue();
        return FragDisarmPinCode;
    }




}

