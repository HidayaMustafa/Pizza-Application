package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class SIGNUP extends AppCompatActivity {
    EditText editTextEmail,editTextPhone,editTextFirstName,editTextLastName,editTextPassword,editTextConfPassword;
    String email,phone,firstName,lastName,password,confPassword;
    Spinner genderSpinner;
    String[] options = {"Male", "Female"};
    boolean isValid = false, EmailExist = false, isValidE =false,isValidPH=false, isValidF=false, isValidL=false, isValidP=false, isValidCP=false;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        genderSpinner =  findViewById(R.id.spinner);
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);
        Start ();
        Button buttonSign = (Button) findViewById(R.id.buttonSign);
        buttonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newUser = new User();
                if (count > 0){
                    Start();
                }
                count++;
                if (email.isEmpty() || phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || confPassword.isEmpty()) {
                    isValid = false;
                }
                DataBaseHelper dataBaseHelper = new DataBaseHelper(SIGNUP.this, "PIZZA_CUSTOMER", null, 1);
                Cursor allCustomersCursor = dataBaseHelper.getAllEmail();
                EmailExist =false;
                while (allCustomersCursor.moveToNext()){
                    if (email.equals(allCustomersCursor.getString(0))) {
                        EmailExist =true;
                        break;
                    }
                }
                if (!isValid ||!isValidE ||!isValidPH ||!isValidF ||!isValidL || !isValidP || !isValidCP) {
                    Toast.makeText(SIGNUP.this, "Invalid input, check them", Toast.LENGTH_SHORT).show();
                }
                else if(EmailExist){
                    Toast.makeText(SIGNUP.this, "This email already exists, please go to the login or try another email.", Toast.LENGTH_SHORT).show();
                }
                else {

                    newUser.setEmail(email);
                    newUser.setmPhone(phone);
                    newUser.setmFName(firstName);
                    newUser.setmLName(lastName);
                    newUser.setPassword(password);
                    newUser.setmGender(genderSpinner.getSelectedItem().toString());
                    newUser.setConfPassword(confPassword);
                    newUser.setIsCustomer(1);
                    newUser.setProfilePicture(null);
                    try {
                        dataBaseHelper.insertUser(newUser);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    Intent intent = new Intent(SIGNUP.this, LOGIN.class);
                    SIGNUP.this.startActivity(intent);
                    finish();
                }
                allCustomersCursor.close();
            }
        });

    }

    void Start (){
        isValid =true;
        editTextEmail = (EditText) findViewById(R.id.editTextemailSign);
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextEmail.setError("Invalid email address");
                    isValidE = false;
                } else {
                    editTextEmail.setError(null);
                    isValidE = true;
                }
            }
        });

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString();
                if (!phone.matches("05\\d{8}")) {
                    editTextPhone.setError("Phone number must start with '05' and be exactly 10 digits");
                    isValidPH = false;
                } else {
                    editTextPhone.setError(null);
                    isValidPH = true;
                }
            }
        });

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                firstName = s.toString();
                if (firstName.length() < 3) {
                    editTextFirstName.setError("First name must be at least 3 characters long");
                    isValidF = false;
                } else {
                    editTextFirstName.setError(null);
                    isValidF = true;
                }
            }
        });

        editTextLastName = findViewById(R.id.editTextLastName);
        editTextLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                lastName = s.toString();
                if (lastName.length() < 3) {
                    editTextLastName.setError("Last name must be at least 3 characters long");
                    isValidL = false;
                } else {
                    editTextLastName.setError(null);
                    isValidL = true;
                }
            }
        });

        editTextPassword = findViewById(R.id.editTextPassword1);
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
                if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
                    editTextPassword.setError("Password must be at least 8 characters long and contain at least one letter and one number");
                    isValidP = false;
                } else {
                    editTextPassword.setError(null);
                    isValidP = true;
                }
            }
        });

        editTextConfPassword = findViewById(R.id.editTextConfPassword);
        editTextConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                confPassword = s.toString();
                if (!password.equals(confPassword)) {
                    editTextConfPassword.setError("Passwords do not match");
                    isValidCP = false;
                } else {
                    editTextConfPassword.setError(null);
                    isValidCP = true;
                }
            }
        });
    }
}
