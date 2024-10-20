package com.example.project;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project.databinding.ActivityAdminBinding;
import com.google.android.material.navigation.NavigationView;

public class Admin extends AppCompatActivity {

    private static final String BUNDLE_KEY_USER_EMAIL = "USER_EMAIL";
    private static final String DATABASE_NAME = "PIZZA_CUSTOMER";
    private static final int DATABASE_VERSION = 1;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityAdminBinding binding;
    private String userEmail;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataBaseHelper = new DataBaseHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
        userEmail = getIntent().getStringExtra(BUNDLE_KEY_USER_EMAIL);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarAdmin.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile, R.id.nav_logout, R.id.nav_add_admin, R.id.nav_add_offers,R.id.nav_view_offers,R.id.nav_reports)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        setupUserProfile(navigationView, drawer);
        setupLogout(navigationView, drawer);

        Bundle Bundle = new Bundle();
        navController.navigate(R.id.nav_add_offers, Bundle);
        // Pass data to fragment using NavController
        Bundle profileBundle = new Bundle();
        profileBundle.putString(BUNDLE_KEY_USER_EMAIL, userEmail);
        navController.navigate(R.id.nav_profile, profileBundle);

        Bundle bundle = new Bundle();
        bundle.putSerializable("dataBase", dataBaseHelper);
        navigationView.getMenu().findItem(R.id.nav_add_admin).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_admin)
                    .navigate(R.id.nav_add_admin, bundle);
            drawer.closeDrawers();
            return true;
        });

        fetchAndDisplayUserImage();

        Bundle bundleOffers = new Bundle();
        bundleOffers.putSerializable("dataBase", dataBaseHelper);
        navigationView.getMenu().findItem(R.id.nav_add_offers).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_admin)
                    .navigate(R.id.nav_add_offers, bundleOffers);
            drawer.closeDrawers();
            return true;
        });

        Bundle bundleViewOffers = new Bundle();
        bundleViewOffers.putSerializable("dataBase", dataBaseHelper);
        navigationView.getMenu().findItem(R.id.nav_view_offers).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_admin)
                    .navigate(R.id.nav_view_offers, bundleViewOffers);
            drawer.closeDrawers();
            return true;
        });
        Bundle bundlereports = new Bundle();
        bundlereports.putSerializable("dataBase", dataBaseHelper);
        navigationView.getMenu().findItem(R.id.nav_reports).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_admin)
                    .navigate(R.id.nav_reports, bundlereports);
            drawer.closeDrawers();
            return true;
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_admin);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setupUserProfile(NavigationView navigationView, DrawerLayout drawer) {
        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.te);
        textView.setText(userEmail);
        navigationView.getMenu().findItem(R.id.nav_profile).setOnMenuItemClickListener(menuItem -> {
            Bundle profileBundle = new Bundle();
            profileBundle.putString(BUNDLE_KEY_USER_EMAIL, userEmail);
            Navigation.findNavController(this, R.id.nav_host_fragment_content_admin).navigate(R.id.nav_profile, profileBundle);
            drawer.closeDrawers();
            return true;
        });
    }

    private void setupLogout(NavigationView navigationView, DrawerLayout drawer) {
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            showLogoutDialog();
            return true;
        });
    }

    private void showLogoutDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_custom, null);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        dialogView.findViewById(R.id.positiveButton).setOnClickListener(v -> {
            startActivity(new Intent(Admin.this, LOGIN.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            alertDialog.dismiss();
        });

        dialogView.findViewById(R.id.negativeButton).setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    private void fetchAndDisplayUserImage() {
        ImageView imageView = findViewById(R.id.ima);

        Cursor cursor = null;
        try {
            cursor = dataBaseHelper.getAllEmail();
            while (cursor.moveToNext()) {
                if (userEmail.equals(cursor.getString(0)) && cursor.getBlob(6) != null) {
                    byte[] imageBytes = cursor.getBlob(6);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imageView.setImageBitmap(bitmap);
                }
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
