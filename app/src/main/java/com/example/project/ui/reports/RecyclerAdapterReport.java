package com.example.project.ui.reports;

import static java.lang.String.valueOf;

import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBaseHelper;
import com.example.project.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Adapter for displaying pizza report.
 */
public class RecyclerAdapterReport extends RecyclerView.Adapter<RecyclerAdapterReport.MyViewHolder> {
    private ArrayList<String> pizzaList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private DataBaseHelper dbHelper;
    private static final Set<String> TYPES = new HashSet<>(Arrays.asList(
            "Margarita", "Neapolitan", "Hawaiian", "Pepperoni", "New York Style",
            "Calzone", "Tandoori Chicken Pizza", "BBQ Chicken Pizza", "Seafood Pizza",
            "Vegetarian Pizza", "Buffalo Chicken Pizza", "Mushroom Truffle Pizza", "Pesto Chicken Pizza"));

    /**
     * Constructor for RecyclerAdapterReport.
     *
     * @param fragmentManager FragmentManager instance for managing fragments.
     * @param dbHelper        Database helper instance for database operations.
     */
    public RecyclerAdapterReport(FragmentManager fragmentManager, DataBaseHelper dbHelper) {
        this.fragmentManager = fragmentManager;
        this.dbHelper = dbHelper;
        loadData();
    }

    private void loadData() {
        Cursor cursor = null;
        try {
            cursor = dbHelper.getOrder();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String pizzaName = cursor.getString(1);
                    Log.d("RecyclerAdapterReport", "Loaded pizza: " + pizzaName);
                    if (TYPES.contains(pizzaName)) {
                        pizzaList.add(pizzaName);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pizzaType;
        TextView count;
        TextView income;

        public MyViewHolder(final View view) {
            super(view);
            pizzaType = view.findViewById(R.id.pizza_name);
            count = view.findViewById(R.id.pizza_count);
            income = view.findViewById(R.id.pizza_income);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.type, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String type = pizzaList.get(position);
        if (TYPES.contains(type)) {
            holder.pizzaType.setText(type);
            int[] countPrice = dbHelper.getTypeCountPrice(type);
            holder.count.setText("|   " + valueOf(countPrice[0]));
            holder.income.setText("|    " + valueOf(countPrice[1]));
        }
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }
}
