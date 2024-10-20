package com.example.project.ui.Logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.databinding.FragmentLogoutBinding;
import com.example.project.ui.Logout.LogoutViewModel;


public class  LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LogoutViewModel logoutViewModel =
                new ViewModelProvider(this).get(LogoutViewModel.class);

        binding = FragmentLogoutBinding .inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textLogout;
        logoutViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}