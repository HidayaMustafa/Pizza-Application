package com.example.project.ui.Logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class LogoutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LogoutViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}