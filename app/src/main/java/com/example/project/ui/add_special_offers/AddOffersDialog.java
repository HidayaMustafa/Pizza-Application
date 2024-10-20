package com.example.project.ui.add_special_offers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.project.DataBaseHelper;
import com.example.project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOffersDialog extends DialogFragment {
    private DialogListener listener;
    private int quantity = 0;
    private String size;
    private HashMap<String, Integer> sizeQuantities = new HashMap<>();
    private String pizzaName;
    List<PizzaDetails>d=new ArrayList<>();


    public interface DialogListener {
        void onDialogResult(String pizzaName, List<PizzaDetails> details);
    }

    public void setListener(DialogListener listener) {
        this.listener = listener;
    }

    public static AddOffersDialog newInstance(String pizzaName, DataBaseHelper dbHelper) {
        AddOffersDialog dialog = new AddOffersDialog();
        Bundle args = new Bundle();
        args.putString("pizza_name", pizzaName);
        args.putSerializable("dbHelper", dbHelper);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_s_offer, container, false);

        TextView name = view.findViewById(R.id.pizza_name);
        TextView quantityText = view.findViewById(R.id.text_quantity);
        RadioButton small = view.findViewById(R.id.radio1);
        RadioButton medium = view.findViewById(R.id.radio2);
        RadioButton large = view.findViewById(R.id.radio3);
        Button increment = view.findViewById(R.id.button_increase);
        Button decrement = view.findViewById(R.id.button_decrease);
        Button save = view.findViewById(R.id.submit);

        small.setOnClickListener(view1 -> updateSize("small", quantityText));
        medium.setOnClickListener(view1 -> updateSize("medium", quantityText));
        large.setOnClickListener(view1 -> updateSize("large", quantityText));

        increment.setOnClickListener(view1 -> {
            if (size == null) {
                Toast.makeText(getContext(), "Please select a size", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity++;
            sizeQuantities.put(size, sizeQuantities.getOrDefault(size, 0) + 1);
            quantityText.setText(String.valueOf(quantity));
        });

        decrement.setOnClickListener(view1 -> {
            if (quantity > 0 && sizeQuantities.getOrDefault(size, 0) > 0) {
                quantity--;
                sizeQuantities.put(size, sizeQuantities.get(size) - 1);
                quantityText.setText(String.valueOf(quantity));
            }
        });
        Bundle args = getArguments();
        if (args != null) {
            pizzaName = args.getString("pizza_name");
            name.setText(pizzaName);
        }
        save.setOnClickListener(view1 -> {
            for(String k:sizeQuantities.keySet()) {
                PizzaDetails p = new PizzaDetails(k,sizeQuantities.get(k));
                d.add(p);
            }
            listener.onDialogResult(pizzaName, d);
            dismiss();
        });

        return view;
    }

    private void updateSize(String newSize, TextView quantityText) {
        if (size != null) {
            sizeQuantities.put(size, quantity);
        }
        size = newSize;
        quantity = sizeQuantities.getOrDefault(size, 0);
        quantityText.setText(String.valueOf(quantity));
    }
}
