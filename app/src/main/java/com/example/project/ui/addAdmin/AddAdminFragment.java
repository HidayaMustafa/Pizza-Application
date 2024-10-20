package com.example.project.ui.addAdmin;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.project.DataBaseHelper;
import com.example.project.User;
import com.example.project.databinding.FragmentAddAdminBinding;

public class AddAdminFragment extends Fragment {
    EditText editTextEmail,editTextPhone,editTextFirstName,editTextLastName,editTextPassword,editTextConfPassword;
    String email,phone,firstName,lastName,password,confPassword;
    Spinner genderSpinner;
    String[] options = {"Male", "Female"};
    boolean isValid = false, EmailExist = false, isValidE =false,isValidPH=false, isValidF=false, isValidL=false, isValidP=false, isValidCP=false;
    int count = 0;
    private FragmentAddAdminBinding binding;
    DataBaseHelper dataBaseHelper ;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAdminBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            dataBaseHelper = (DataBaseHelper) getArguments().getSerializable("dataBase");
        }

        genderSpinner =  binding.spinner;
        ArrayAdapter<String> objGenderArr = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, options);
        genderSpinner.setAdapter(objGenderArr);
        Start ();
        Button buttonSign = binding.buttonSign;
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
                Cursor allCustomersCursor = dataBaseHelper.getAllEmail();
                EmailExist =false;
                while (allCustomersCursor.moveToNext()){
                    if (email.equals(allCustomersCursor.getString(0))) {
                        EmailExist =true;
                        break;
                    }
                }
                if (!isValid ||!isValidE ||!isValidPH ||!isValidF ||!isValidL || !isValidP || !isValidCP) {
                    Toast.makeText(getContext(), "Invalid input, check them", Toast.LENGTH_SHORT).show();
                }
                else if(EmailExist){
                    Toast.makeText(getContext(), "This email already exists, please go to the login or try another email.", Toast.LENGTH_SHORT).show();
                }
                else {
                    newUser.setEmail(email);
                    newUser.setmPhone(phone);
                    newUser.setmFName(firstName);
                    newUser.setmLName(lastName);
                    newUser.setPassword(password);
                    newUser.setmGender(genderSpinner.getSelectedItem().toString());
                    newUser.setConfPassword(confPassword);
                    newUser.setIsCustomer(0);
                    newUser.setProfilePicture(null);
                    try {
                        dataBaseHelper.insertUser(newUser);
                        Toast.makeText(getContext(), "Admin added successfully", Toast.LENGTH_SHORT).show();
                        clearInputs();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                allCustomersCursor.close();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void clearInputs() {
        editTextEmail.getText().clear();
        editTextPhone.getText().clear();
        editTextFirstName.getText().clear();
        editTextLastName.getText().clear();
        editTextPassword.getText().clear();
        editTextConfPassword.getText().clear();
        genderSpinner.setSelection(0);
    }

    void Start (){
    isValid =true;
    editTextEmail = binding.editTextemailSign;
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

    editTextPhone = binding.editTextPhone;
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

    editTextFirstName = binding.editTextFirstName;
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

    editTextLastName = binding.editTextLastName;
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

    editTextPassword = binding.editTextPassword1;
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

    editTextConfPassword = binding.editTextConfPassword;
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
