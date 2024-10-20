package com.example.project.ui.CallOrFindUs;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.project.databinding.FragmentCallorfindusBinding;

public class CallOrFindUsFragment extends Fragment {

    private FragmentCallorfindusBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CallOrFindUsViewModel callOrFindUsViewModel =
                new ViewModelProvider(this).get(CallOrFindUsViewModel.class);

        binding = FragmentCallorfindusBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:0599000000"));
                startActivity(callIntent);
            }
        });

        binding.buttonFindUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent();
                mapIntent.setAction(Intent.ACTION_VIEW);
                mapIntent.setData(Uri.parse("geo:31.961013, 35.190483"));
                startActivity(mapIntent);
            }
        });

        binding.buttonEmailUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gmailIntent = new Intent();
                gmailIntent.setAction(Intent.ACTION_SENDTO);
                gmailIntent.setType("message/rfc822");
                gmailIntent.setData(Uri.parse("mailto:"));
                gmailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"AdvancePizza@Pizza.com"});
                gmailIntent.putExtra(Intent.EXTRA_SUBJECT, "My Subject");
                gmailIntent.putExtra(Intent.EXTRA_TEXT, "Content of the message");
                startActivity(gmailIntent);
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
