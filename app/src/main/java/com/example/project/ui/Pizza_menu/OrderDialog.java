package com.example.project.ui.Pizza_menu;

import static java.lang.String.valueOf;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.project.DataBaseHelper;
import com.example.project.Order;
import com.example.project.R;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderDialog extends DialogFragment {
    int quantity = 0;
    String size;
    ArrayList<String> order = new ArrayList<>();
    int price = 0;
    HashMap<String, Integer> sizeQuantities = new HashMap<>();
    DataBaseHelper dbHelper;
    String email, pizzaName;
    byte[] image;

    public static OrderDialog newInstance(byte [] image, String pizzaName, String priceS, String priceM, String priceL, DataBaseHelper dbHelper, String email) {
        OrderDialog dialog = new OrderDialog();
        Bundle args = new Bundle();
        args.putString("pizza_name", pizzaName);
        args.putString("priceS", priceS);
        args.putString("priceM", priceM);
        args.putString("priceL", priceL);
        args.putString("email", email);
        args.putSerializable("dbHelper", dbHelper);
        args.putSerializable("PIC",image);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_order, container, false);

        TextView name = view.findViewById(R.id.pizza_name);
        TextView priceS = view.findViewById(R.id.priceS);
        TextView priceM = view.findViewById(R.id.priceM);
        TextView priceL = view.findViewById(R.id.priceL);
        TextView quantityText = view.findViewById(R.id.text_quantity);
        RadioButton small = view.findViewById(R.id.radio1);
        RadioButton medium = view.findViewById(R.id.radio2);
        RadioButton large = view.findViewById(R.id.radio3);
        TextView total = view.findViewById(R.id.total);
        Button increment = view.findViewById(R.id.button_increase);
        Button decrement = view.findViewById(R.id.button_decrease);
        Button add = view.findViewById(R.id.submit);

        small.setOnClickListener(view1 -> updateSize("small", quantityText));
        medium.setOnClickListener(view1 -> updateSize("medium", quantityText));
        large.setOnClickListener(view1 -> updateSize("large", quantityText));

        increment.setOnClickListener(view1 -> {
            if (size == null) {
                Toast.makeText(getContext(), "Please select a size", Toast.LENGTH_SHORT).show();
                return;
            }
            order.add(size);
            quantity++;
            sizeQuantities.put(size, sizeQuantities.getOrDefault(size, 0) + 1);
            quantityText.setText(valueOf(quantity));
            updatePrice(priceS, priceM, priceL, total);
        });

        decrement.setOnClickListener(view1 -> {
            if (quantity > 0 && sizeQuantities.getOrDefault(size, 0) > 0) {
                quantity--;
                sizeQuantities.put(size, sizeQuantities.get(size) - 1);
                quantityText.setText(valueOf(quantity));
                order.remove(size);
                updatePrice(priceS, priceM, priceL, total);
            }
        });

        add.setOnClickListener(view1 -> {
            if (!order.isEmpty()) {
                LocalTime currentTime = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    currentTime = LocalTime.now();
                }
                long timeInMillis = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    timeInMillis = (currentTime.getHour() * 3600 + currentTime.getMinute() * 60) * 1000;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDate = sdf.format(new Date());

                Order newOrder = new Order(email, pizzaName, timeInMillis ,valueOf(sizeQuantities) ,price,currentDate,image,0);

                dbHelper.insertOrder(newOrder);
                Toast.makeText(getContext(), "Order added", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "No items in order", Toast.LENGTH_SHORT).show();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            pizzaName = args.getString("pizza_name");
            String priceSmall = args.getString("priceS");
            String priceMedium = args.getString("priceM");
            String priceLarge = args.getString("priceL");
            email = args.getString("email");
            dbHelper = (DataBaseHelper) args.getSerializable("dbHelper");
            name.setText(pizzaName);
            priceS.setText(priceSmall);
            priceM.setText(priceMedium);
            priceL.setText(priceLarge);
            image = (byte[]) args.getSerializable("PIC");
        }

        return view;
    }

    private void updateSize(String newSize, TextView quantityText) {
        if (size != null) {
            sizeQuantities.put(size, quantity);
        }
        size = newSize;
        quantity = sizeQuantities.getOrDefault(size, 0);
        quantityText.setText(valueOf(quantity));
    }

    private void updatePrice(TextView priceS, TextView priceM, TextView priceL, TextView total) {
        price = 0;
        for (String size : sizeQuantities.keySet()) {
            int quantity = sizeQuantities.get(size);
            if (size.equals("small")) {
                price += quantity * Integer.parseInt(priceS.getText().toString());
            } else if (size.equals("medium")) {
                price += quantity * Integer.parseInt(priceM.getText().toString());
            } else if (size.equals("large")) {
                price += quantity * Integer.parseInt(priceL.getText().toString());
            }
        }
        total.setText(valueOf(price));
    }
}
