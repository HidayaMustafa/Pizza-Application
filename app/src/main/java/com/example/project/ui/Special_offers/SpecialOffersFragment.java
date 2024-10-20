package com.example.project.ui.Special_offers;

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
import com.example.project.databinding.FragmentSpecialoffersBinding;

public class SpecialOffersFragment extends Fragment {

    private FragmentSpecialoffersBinding binding;
    private DataBaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private String Email;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSpecialoffersBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            databaseHelper = (DataBaseHelper) getArguments().getSerializable("dataBase");
            Email = getArguments().getString("USER_EMAIL");
        }
        View root = binding.getRoot();
        recyclerView = binding.recyclerPizzaOffers;
        setAdapter();
        return root;
    }

    private void setAdapter() {
        FragmentManager fragmentManager = getChildFragmentManager();
        RecyclerAdapterSpecialOffers adapter = new RecyclerAdapterSpecialOffers(fragmentManager, databaseHelper,Email);
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