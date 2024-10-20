package com.example.project.ui.Pizza_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.project.R;

public class pizzaDialog extends DialogFragment {

    public static pizzaDialog newInstance(String pizzaName, String size, String price, String ingredients) {
        pizzaDialog dialog = new pizzaDialog();
        Bundle args = new Bundle();
        args.putString("pizza_name", pizzaName);
        args.putString("pizza_size", size);
        args.putString("pizza_price", price);
        args.putString("pizza_ingredients", ingredients);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pizza_info, container, false);

        TextView name = view.findViewById(R.id.pizza_name);
        TextView size = view.findViewById(R.id.pizza_size);
        TextView price = view.findViewById(R.id.pizza_price);
        TextView ingredients = view.findViewById(R.id.pizza_ingredients);

        Bundle args = getArguments();
        if (args != null) {
            String Name = args.getString("pizza_name");
            String Size = args.getString("pizza_size");
            String Price= args.getString("pizza_price");
            String Ingredients = args.getString("pizza_ingredients");
            name.setText(Name);
            size.setText(Size);
            price.setText(Price);
            ingredients.setText(Ingredients);
        }

        return view;
    }
}
