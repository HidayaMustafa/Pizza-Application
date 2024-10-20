package com.example.project.ui.Your_favorites;

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
import com.example.project.databinding.FragmentYourfavoritesBinding;


public class YourFavoritesFragment extends Fragment {

    private FragmentYourfavoritesBinding binding;
    private DataBaseHelper databaseHelper;
    private RecyclerView recyclerView;
    String email;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentYourfavoritesBinding.inflate(inflater, container, false);
        if (getArguments() != null) {
            email = getArguments().getString("USER_EMAIL");
            databaseHelper = (DataBaseHelper) getArguments().getSerializable("dataBase");

        }



        View root = binding.getRoot();

        recyclerView = binding.recyclerPizzaFavorites;
        setAdapter();
        return root;
    }

    private void setAdapter() {
        FragmentManager fragmentManager = getChildFragmentManager();
        RecyclerAdapterFav adapter = new RecyclerAdapterFav( fragmentManager, email, databaseHelper);
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
