package com.example.project.ui.add_special_offers;

import static java.lang.String.valueOf;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.project.DataBaseHelper;
import com.example.project.R;
import com.example.project.Type;
import com.example.project.databinding.FragmentAddspecialoffersBinding;
import com.example.project.ui.Special_offers.Offer;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class addSpecialOffersFragment extends Fragment implements AddOffersDialog.DialogListener {
    private static final String ARG_DATABASE = "dataBase";
    private static final String TAG_ADD_OFFERS_DIALOG = "AddOffersDialog";
    private FragmentAddspecialoffersBinding binding;
    private DataBaseHelper databaseHelper;
    private EditText name, totalPrice, startDate, endDate;
    private Button saveButton;
    private static final int REQUEST_IMAGE_PICK = 1;
    private ImageView image;
    private byte[] img;
    private Bitmap selectedImageBitmap;
    private Calendar startCalendar;
    private Calendar endCalendar;

    private SimpleDateFormat dateFormat;

    private HashMap<String, List<PizzaDetails> > pizzaDetailsMap = new HashMap<>();
    private HashMap<String, CheckBox> checkeBoxMap = new HashMap<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddspecialoffersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseHelper = (DataBaseHelper) requireArguments().getSerializable(ARG_DATABASE);
        setCheckBoxListeners();

        name = binding.editTextText2;
        totalPrice = binding.editTextText5;
        startDate = binding.editTextText3;
        endDate = binding.editTextText4;
        saveButton = binding.button;
        image = binding.imageView;
        image.setOnClickListener(v -> openGallery());

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        startDate.setOnClickListener(v -> showStartDatePicker());
        endDate.setOnClickListener(v -> showEndDatePicker());

        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();

        saveButton.setOnClickListener(view -> {
            if (databaseHelper != null) {
                String nameText = name.getText().toString();
                String totalPriceText = totalPrice.getText().toString();

                String startDateText = dateFormat.format(startCalendar.getTime());
                String endDateText = dateFormat.format(endCalendar.getTime());

                if (nameText.isEmpty() || totalPriceText.isEmpty() || startDateText.isEmpty() || endDateText.isEmpty() || pizzaDetailsMap.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                int totalPriceValue;
                try {
                    totalPriceValue = Integer.parseInt(totalPriceText);
                } catch (NumberFormatException e) {
                    Toast.makeText(getActivity(), "Please enter a valid total price", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuilder pizzaDetailsBuilder = new StringBuilder();
                for (String pizzaName : pizzaDetailsMap.keySet()) {
                    List <PizzaDetails> details = pizzaDetailsMap.get(pizzaName);
                    pizzaDetailsBuilder.append(pizzaName);
                    for (PizzaDetails pizzaDetails : details) {
                        pizzaDetailsBuilder.append(":")
                                .append(pizzaDetails.getSize())
                                .append(":")
                                .append(pizzaDetails.getQuantity())
                                .append(",");
                    }
                    pizzaDetailsBuilder.append("\n");
                }

                if (pizzaDetailsBuilder.length() > 0) {
                    pizzaDetailsBuilder.deleteCharAt(pizzaDetailsBuilder.length() - 1);
                }

                Offer offer = new Offer();
                offer.setName(nameText);
                offer.setTotalPrice(totalPriceValue);
                offer.setStartDate(startDateText);
                offer.setEndDate(endDateText);
                offer.setPizzaDetails(pizzaDetailsBuilder.toString()); // Set delimited string here
                offer.setImg(img);
                databaseHelper.addSpecialOffer(offer);
                Toast.makeText(getActivity(), "Offer added successfully", Toast.LENGTH_SHORT).show();
                name.getText().clear();
                totalPrice.getText().clear();
                startDate.getText().clear();
                endDate.getText().clear();
                pizzaDetailsMap.clear();
                for (CheckBox checkBox : checkeBoxMap.values()) {
                    checkBox.setChecked(false);
                }
                image.setImageResource(R.drawable.image);
            }
        });
        return root;
    }

    private void setCheckBoxListeners() {
        checkeBoxMap.put("Margarita", binding.checkBox3);
        checkeBoxMap.put("Neapolitan", binding.checkBox4);
        checkeBoxMap.put("Hawaiian", binding.checkBox5);
        checkeBoxMap.put("Pepperoni", binding.checkBox6);
        checkeBoxMap.put("New York Style", binding.checkBox7);
        checkeBoxMap.put("Calzone", binding.checkBox8);
        checkeBoxMap.put("Tandoori Chicken Pizza", binding.checkBox9);
        checkeBoxMap.put("BBQ Chicken Pizza", binding.checkBox31);
        checkeBoxMap.put("Seafood Pizza", binding.checkBox41);
        checkeBoxMap.put("Vegetarian Pizza", binding.checkBox51);
        checkeBoxMap.put("Buffalo Chicken Pizza", binding.checkBox61);
        checkeBoxMap.put("Mushroom Truffle Pizza", binding.checkBox71);
        checkeBoxMap.put("Pesto Chicken Pizza", binding.checkBox81);


        setCheckBoxListener(checkeBoxMap.get("Margarita"), "Margarita");
        setCheckBoxListener(checkeBoxMap.get("Neapolitan"), "Neapolitan");
        setCheckBoxListener(checkeBoxMap.get("Hawaiian"), "Hawaiian");
        setCheckBoxListener(checkeBoxMap.get("Pepperoni"), "Pepperoni");
        setCheckBoxListener(checkeBoxMap.get("New York Style"), "New York Style");
        setCheckBoxListener(checkeBoxMap.get("Calzone"), "Calzone");
        setCheckBoxListener(checkeBoxMap.get("Tandoori Chicken Pizza"), "Tandoori Chicken Pizza");
        setCheckBoxListener(checkeBoxMap.get("BBQ Chicken Pizza"), "BBQ Chicken Pizza");
        setCheckBoxListener(checkeBoxMap.get("Seafood Pizza"), "Seafood Pizza");
        setCheckBoxListener(checkeBoxMap.get("Vegetarian Pizza"), "Vegetarian Pizza");
        setCheckBoxListener(checkeBoxMap.get("Buffalo Chicken Pizza"), "Buffalo Chicken Pizza");
        setCheckBoxListener(checkeBoxMap.get("Mushroom Truffle Pizza"), "Mushroom Truffle Pizza");
        setCheckBoxListener(checkeBoxMap.get("Pesto Chicken Pizza"), "Pesto Chicken Pizza");
    }

    private void setCheckBoxListener(View checkBox, String pizzaName) {
        checkBox.setOnClickListener(view -> {
            if (checkBox instanceof CheckBox) {
                CheckBox cb = (CheckBox) checkBox;
                if (cb.isChecked()) {
                    showDialog(pizzaName, cb);
                } else {
                    pizzaDetailsMap.remove(pizzaName);
                }
            }
        });
    }

    private void showDialog(String pizzaName, CheckBox cb) {
        if (databaseHelper != null) {
            Type typeList = fetchMenuItems(pizzaName);
            FragmentManager fragmentManager = getChildFragmentManager();
            AddOffersDialog dialog = AddOffersDialog.newInstance(typeList.getTypeName(), databaseHelper);
            dialog.setListener(this);
            dialog.show(fragmentManager, TAG_ADD_OFFERS_DIALOG);
        }
    }

    private Type fetchMenuItems(String itemName) {
        Type itemTypes = new Type();
        try (Cursor cursor = databaseHelper != null ? databaseHelper.getPizza(itemName) : null) {
            if (cursor != null && cursor.moveToFirst()) {
                itemTypes.setTypeName(cursor.getString(0));
                itemTypes.setSize(cursor.getString(1));
                itemTypes.setPrice(cursor.getString(2));
                itemTypes.setIngredients(cursor.getString(3));
                itemTypes.setImage(cursor.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemTypes;
    }

    @Override
    public void onDialogResult(String pizzaName, List<PizzaDetails> details) {
        for(PizzaDetails details1 : details) {
        if (details1.getQuantity()==0) {
            checkeBoxMap.get(pizzaName).setChecked(false);
        }else{
            pizzaDetailsMap.put(pizzaName, details);
        }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Uri selectedImageUri = data.getData();
                try {
                    selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), selectedImageUri);
                    image.setImageBitmap(selectedImageBitmap);

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 0, baos);
                    img = baos.toByteArray();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void showStartDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            startCalendar.set(Calendar.YEAR, year);
            startCalendar.set(Calendar.MONTH, monthOfYear);
            startCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateStartDate();
        };
        new DatePickerDialog(requireContext(), dateSetListener,
                startCalendar.get(Calendar.YEAR),
                startCalendar.get(Calendar.MONTH),
                startCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void showEndDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
            endCalendar.set(Calendar.YEAR, year);
            endCalendar.set(Calendar.MONTH, monthOfYear);
            endCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateEndDate();
        };

        new DatePickerDialog(requireContext(), dateSetListener,
                endCalendar.get(Calendar.YEAR),
                endCalendar.get(Calendar.MONTH),
                endCalendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    private void updateStartDate() {
        startDate.setText(dateFormat.format(startCalendar.getTime()));
    }
    private void updateEndDate() {
        endDate.setText(dateFormat.format(endCalendar.getTime()));
    }
}