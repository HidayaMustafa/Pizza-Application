package com.example.project.ui.Pizza_menu;

import android.database.Cursor;
import com.example.project.R;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBaseHelper;
import com.example.project.Type;
import com.example.project.databinding.FragmentMenuBinding;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private ArrayList<Type> types;
    private DataBaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private Button buttonAll;
    private Button buttonChicken;
    private Button buttonMushrooms;
    private Button buttonHam;
    private Button buttonveggies;
    private EditText editTextSearch;
    private String email;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMenuBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            types = (ArrayList<Type>) getArguments().getSerializable("DB_HELPER");
            email = getArguments().getString("USER_EMAIL");
            databaseHelper = (DataBaseHelper) getArguments().getSerializable("dataBase");
        }

        View root = binding.getRoot();

        recyclerView = binding.recyclerPizzaMenu;
        buttonAll = binding.buttonAll;
        buttonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAdapter(types);
                updateButtonColors(buttonAll);
            }
        });
        buttonChicken = binding.button1;
        buttonChicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Type> chickenTypes = fetchMenuItemsByCategory("Chicken");
                setAdapter(chickenTypes);
                updateButtonColors(buttonChicken);
            }
        });
        buttonMushrooms = binding.button2;
        buttonMushrooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Type> mushroomTypes = fetchMenuItemsByCategory("Mushrooms");
                setAdapter(mushroomTypes);
                updateButtonColors(buttonMushrooms);
            }
        });

        buttonHam = binding.button3;
        buttonHam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Type> HamType = fetchMenuItemsByCategory("Ham");
                setAdapter(HamType);
                updateButtonColors(buttonHam);
            }
        });
        buttonveggies = binding.button4;
        buttonveggies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Type> veggiesType = fetchMenuItemsByCategory("Vegetarian Pizza");
                setAdapter(veggiesType);
                updateButtonColors(buttonveggies);
            }
        });
        editTextSearch = binding.editTextText;
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                String queryLower = query.toLowerCase();
                if (queryLower.equals("small") || queryLower.equals("medium") || queryLower.equals("large")) {
                    setAdapter(types);
                }
                else if (isNumeric(query)){
                    ArrayList<Type> searchResults = fetchMenuItemsBySearch(query);
                    setAdapter(searchResults);}
                else {
                    // Don't care if it's a capital letter or a small letter, what matters is the presence of the word
                    ArrayList<Type> INGREDIENTS = fetchMenuItemsByCategory(query);
                    setAdapter(INGREDIENTS);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed
            }
        });

        setAdapter(types);
        return root;
    }

    private void setAdapter(ArrayList<Type> types) {
        recyclerView.setAdapter(null);
        FragmentManager fragmentManager = getChildFragmentManager();
        RecyclerAdapter adapter = new RecyclerAdapter(types, fragmentManager, email, databaseHelper);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void updateButtonColors(Button selectedButton) {
        Button[] buttons = {buttonAll, buttonChicken, buttonMushrooms, buttonHam, buttonveggies};
        for (Button button : buttons) {
            if (button == selectedButton) {
                button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.selected_button_color));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            } else {
                button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.default_button_color));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ArrayList<Type> fetchMenuItemsByCategory(String category) {
        ArrayList<Type> categoryTypes = new ArrayList<>();
        Cursor cursor = null;
        try {
            if (category.equals("Vegetarian Pizza")) {
                cursor = databaseHelper.getPizza(category);
            }
            else {
                cursor = databaseHelper.getPizzaByCategory(category);}
            while (cursor.moveToNext()) {
                Type type = new Type();
                type.setTypeName(cursor.getString(0));
                type.setSize(cursor.getString(1));
                type.setPrice(cursor.getString(2));
                type.setIngredients(cursor.getString(3));
                type.setImage(cursor.getInt(4));
                categoryTypes.add(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return categoryTypes;
    }
    private ArrayList<Type> fetchMenuItemsBySearch(String query) {
        ArrayList<Type> searchResults = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = databaseHelper.SearchByPrice(query);
            while (cursor.moveToNext()) {
                Type type = new Type();
                type.setTypeName(cursor.getString(0));
                type.setSize(cursor.getString(1));
                type.setPrice(cursor.getString(2));
                type.setIngredients(cursor.getString(3));
                type.setImage(cursor.getInt(4));
                searchResults.add(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return searchResults;
    }
    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}

