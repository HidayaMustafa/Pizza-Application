package com.example.project.ui.Profile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.DataBaseHelper;
import com.example.project.User;
import com.example.project.databinding.FragmentProfileBinding;
import java.io.ByteArrayOutputStream;


public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private DataBaseHelper dataBaseHelper;
    private boolean isValidPH=true, isValidF= true , isValidL=true, isValidP= true , isValidCP = true;
    private EditText editTextFirstName, editTextLastName, editTextPhone, editTextPassword, editTextConfPassword;
    private TextView textEmail , labelConfirmPassword;
    private Button saveButton, editButton;
    private String email, phone, firstName, lastName, password, confPassword;
    private String firstName1, lastName1, password1, phoneNumber1;
    private ImageView imgProfile ;
    private byte[] imageData,imageData1;
    int iscustomer;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            email = getArguments().getString("USER_EMAIL");
        }

        initializeViews();
        initializeDatabase();
        if (email != null) {
            loadCustomerData();
        }
        setFieldsNonEditable();

        editButton.setOnClickListener(view -> {
            enableEditing();
        });

        saveButton.setOnClickListener(view -> {
            handleSaveButtonClick();
        });

        return root;
    }

    private void initializeViews() {
        textEmail = binding.editTextEmail;
        editTextFirstName = binding.editTextFirstName;
        editTextLastName = binding.editTextLastName;
        editTextPhone = binding.editTextPhoneNumber;
        editTextPassword = binding.editTextPassword;
        editTextConfPassword = binding.editTextconPassword;
        saveButton = binding.buttonSave;
        editButton = binding.edit;
        labelConfirmPassword=binding.labelConfirmPassword;
        imgProfile=binding.imageView2;
    }

    private void initializeDatabase() {
        dataBaseHelper = new DataBaseHelper(this.getContext(), "PIZZA_CUSTOMER", null, 1);
    }

    private void loadCustomerData() {
        Cursor allCustomersCursor = dataBaseHelper.getAllEmail();
        while (allCustomersCursor.moveToNext()) {
            String cursorEmail = allCustomersCursor.getString(0);
            if (email != null && email.equals(cursorEmail)) {
                firstName1 = allCustomersCursor.getString(2);
                lastName1 = allCustomersCursor.getString(3);
                phoneNumber1 = allCustomersCursor.getString(1);
                password1 = allCustomersCursor.getString(5);
                imageData1 = allCustomersCursor.getBlob(6);
                iscustomer = allCustomersCursor.getInt(7);

                textEmail.setText(email);
                editTextFirstName.setText(firstName1);
                editTextLastName.setText(lastName1);
                editTextPhone.setText(phoneNumber1);
                editTextPassword.setText(password1);
                if (imageData1 != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData1, 0, imageData1.length);
                    imgProfile.setImageBitmap(bitmap);
                }
                break;
            }
        }
    }


    private void enableEditing() {
        editButton.setVisibility(View.GONE);
        saveButton.setVisibility(View.VISIBLE);
        setFieldsEditable();
        startValidation();
    }

    private void startValidation() {
        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override public void afterTextChanged(Editable s) {
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

        editTextFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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

        editTextLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                password = s.toString();
                editTextConfPassword.setVisibility(View.VISIBLE);
                labelConfirmPassword.setVisibility(View.VISIBLE);
                setFieldEditable(editTextConfPassword);
                if (password.length() < 8 || !password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
                    editTextPassword.setError("Password must be at least 8 characters long and contain at least one letter and one number");
                    isValidP = false;
                } else {
                    editTextPassword.setError(null);
                    isValidP = true;
                }
            }
        });

        editTextConfPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

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

        imgProfile.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1);
        });
    }

    private void handleSaveButtonClick() {
        if (password != null && confPassword == null) {
            Toast.makeText(getContext(), "Invalid input, check them.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isValidPH || !isValidF || !isValidL || !isValidP || !isValidCP) {
            Toast.makeText(getContext(), "Invalid input, check them.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone == null && firstName==null && lastName==null && password==null && imageData==null ) {
            Toast.makeText(getContext(), "No changes made.", Toast.LENGTH_SHORT).show();
            disableEditing();
        }else {

            if (phone == null) phone = phoneNumber1;
            if (firstName == null) firstName = firstName1;
            if (lastName == null) lastName = lastName1;
            if (password == null) password = password1;
            if (confPassword == null) confPassword = password;
            if (imageData == null) imageData = imageData1;

            User newUser = new User();
            newUser.setEmail(email);
            newUser.setmPhone(phone);
            newUser.setmFName(firstName);
            newUser.setmLName(lastName);
            newUser.setPassword(password);
            newUser.setConfPassword(confPassword);
            newUser.setProfilePicture(imageData);
            newUser.setIsCustomer(iscustomer);

            try {
                dataBaseHelper.editCustomer(newUser);
                loadCustomerData();
                Toast.makeText(getContext(), "Changes saved successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            disableEditing();
        }
    }

    private void setFieldsEditable() {
        //THIS IS FOR THE EDIT BUTTON
        setFieldEditable(editTextFirstName);
        setFieldEditable(editTextLastName);
        setFieldEditable(editTextPhone);
        setFieldEditable(editTextPassword);
        imgProfile.setClickable(true);
        imgProfile.setEnabled(true);
    }

    private void setFieldEditable(EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setClickable(true);
    }

    private void setFieldsNonEditable() {
        editTextConfPassword.setVisibility(View.GONE);
        labelConfirmPassword.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        imgProfile.setClickable(false);
        imgProfile.setEnabled(false);
        setFieldNonEditable(editTextFirstName);
        setFieldNonEditable(editTextLastName);
        setFieldNonEditable(editTextPhone);
        setFieldNonEditable(editTextPassword);
    }

    private void setFieldNonEditable(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
        editText.setClickable(false);
    }

    private void disableEditing() {
        setFieldsNonEditable();
        editButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imgProfile.setImageURI(selectedImage);

            // Convert selected image to byte array
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, baos);
                imageData = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}