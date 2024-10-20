package com.example.project.ui.view_orders;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBaseHelper;
import com.example.project.Order;
import com.example.project.databinding.FragmentYourordersBinding;

import java.util.ArrayList;

public class ViewAllOrderFragment extends Fragment {
    private FragmentYourordersBinding binding;
    private DataBaseHelper databaseHelper;
    private ArrayList<Order> orderList=new ArrayList<>() ;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentYourordersBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            databaseHelper = (DataBaseHelper) getArguments().getSerializable("dataBase");
        }

        View root = binding.getRoot();

        recyclerView = binding.recyclerPizzaOrders;
        setAdapter();
        return root;
    }

    private void setAdapter() {
        fetchOrderData();
        FragmentManager fragmentManager = getChildFragmentManager();
        RecyclerAdapterViewOrders adapter = new RecyclerAdapterViewOrders(fragmentManager,databaseHelper, orderList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void fetchOrderData() {
        Cursor cursor = null;
        try {
            cursor = databaseHelper.getOrder();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Order order = new Order();
                    order.setPizzaName(cursor.getString(1));
                    order.setCustomerEmail(cursor.getString(2));
                    order.setOrderTime(cursor.getLong(3));
                    order.setSizeQuantity(cursor.getString(4));
                    order.setTotalPrice(cursor.getInt(5));
                    cursor.getString(6);
                    order.setData(cursor.getString(6));
                    order.setImage(cursor.getBlob(7));
                    order.setIsOffer(cursor.getInt(8));
                    orderList.add(order);
                } while (cursor.moveToNext());
            } else {
                System.out.println("Cursor is empty or null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}

