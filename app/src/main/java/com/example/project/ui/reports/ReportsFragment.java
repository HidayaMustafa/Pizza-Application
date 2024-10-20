package com.example.project.ui.reports;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.DataBaseHelper;
import com.example.project.databinding.FragmentReportsBinding;

public class ReportsFragment extends Fragment {
    private static final String ARG_DATABASE = "dataBase";
    private FragmentReportsBinding binding;
    private DataBaseHelper databaseHelper;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            databaseHelper = (DataBaseHelper) getArguments().getSerializable(ARG_DATABASE);
        }
        recyclerView = binding.recyclerReports;
        TextView totalIncomes = binding.totalIncome;
        totalIncomes.setText("Total Income =  " +String.valueOf(getTotalIncomes())+" $");
        setAdapter();
        return root;
    }
    private int getTotalIncomes(){
        int total = 0;
        if (databaseHelper != null) {
            total = databaseHelper.getTotalIncome();
        }
        return total;
    }

    private void setAdapter() {
        FragmentManager fragmentManager = getChildFragmentManager();
        RecyclerAdapterReport adapter = new RecyclerAdapterReport(fragmentManager,databaseHelper);
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
}