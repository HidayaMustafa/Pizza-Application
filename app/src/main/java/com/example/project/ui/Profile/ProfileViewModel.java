package com.example.project.ui.Profile;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.project.R;

public class ProfileViewModel extends ViewModel {
    boolean isValid;
    private final MutableLiveData<String> firstName;
    private final MutableLiveData<String> lastName;
    private final MutableLiveData<String> password;
    private final MutableLiveData<String> confpassword;

    private final MutableLiveData<String> phoneNumber;

    public ProfileViewModel() {

        firstName = new MutableLiveData<>();
        lastName = new MutableLiveData<>();
        password = new MutableLiveData<>();
        phoneNumber = new MutableLiveData<>();
        confpassword = new MutableLiveData<>();
    }



}
