package com.example.project;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.HomeCustomer;

public class LOGIN extends AppCompatActivity {
    String email,Password;
    EditText editTextUserEmail;
    EditText editTextPassword ;
    boolean IsExist = false;
    Cursor allCustomersCursor, cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextUserEmail = (EditText) findViewById(R.id.editTextemailLog);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        Button buttonLog = (Button) findViewById(R.id.buttonLogin);
        Button Sign = (Button) findViewById(R.id.SignUp);
        CheckBox RememberMe = (CheckBox) findViewById(R.id.checkBox);
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(this);
        DataBaseHelper dataBaseHelper = new DataBaseHelper(LOGIN.this, "PIZZA_CUSTOMER", null, 1);
        allCustomersCursor = dataBaseHelper.getAllEmail();

        String rememberedEmail = sharedPrefManager.readString("userEmail", "noValue");
        String rememberedPassword = sharedPrefManager.readString("password", "noValue");
        if (!"noValue".equals(rememberedEmail) && !"noValue".equals(rememberedPassword)) {
            allCustomersCursor = dataBaseHelper.getAllEmail();
            boolean IsExistE = check(rememberedEmail, rememberedPassword);
            if (IsExistE) {
                editTextUserEmail.setText(rememberedEmail);
                editTextPassword.setText(rememberedPassword);
            }
        }
        Start();

        RememberMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCustomersCursor = dataBaseHelper.getAllEmail();
                checkEmail();
                if(IsExist) {
                    sharedPrefManager.writeString("userEmail", email);
                    sharedPrefManager.writeString("password", Password);
                    Toast.makeText(LOGIN.this, "Remembered your login information.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCustomersCursor = dataBaseHelper.getAllEmail();
                checkEmail();
                cursor = dataBaseHelper.getCusOrAdmin(email);
                if(IsExist) {
                    if (cursor.moveToFirst()) {
                        int c = cursor.getInt(7);
                        if (cursor.getInt(7) == 1) {
                                Intent intent = new Intent(LOGIN.this, HomeCustomer.class);
                                intent.putExtra("USER_EMAIL", email);
                                LOGIN.this.startActivity(intent);
                        }else{
                            Intent intent = new Intent(LOGIN.this, Admin.class);
                            intent.putExtra("USER_EMAIL", email);
                            LOGIN.this.startActivity(intent);
                        }
                    }
                }else{
                    Toast.makeText(LOGIN.this, "User not found. Please sign up or verify your information.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LOGIN.this, SIGNUP.class);
                LOGIN.this.startActivity(intent);
                LOGIN.this.finish();
            }
        });
    }
    void Start (){
        email = editTextUserEmail.getText().toString();
        Password = editTextPassword.getText().toString();
        editTextUserEmail.addTextChangedListener(new TextWatcher() {
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
            }
        });

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
                Password = s.toString();
            }
        });
    }

    void checkEmail(){
        while (allCustomersCursor.moveToNext()) {
            String cursorEmail = allCustomersCursor.getString(0).toString();
            String cursorPassword = allCustomersCursor.getString(5).toString();
            if (email.equals(cursorEmail) && Password.equals(cursorPassword)) {
                IsExist = true;
                break;
            }
        }
    }
    boolean check(String email, String Password){
        while (allCustomersCursor.moveToNext()) {
            String cursorEmail = allCustomersCursor.getString(0).toString();
            String cursorPassword = allCustomersCursor.getString(5).toString();
            if (email.equals(cursorEmail) && Password.equals(cursorPassword)) {
                return true;
            }
        }
        return false;
    }
}