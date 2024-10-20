package com.example.project;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.project.databinding.ActivityHomeCustomerBinding;
import com.google.android.material.navigation.NavigationView;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HomeCustomer extends AppCompatActivity {

    private static final String BUNDLE_KEY_DB_HELPER = "DB_HELPER";
    private static final String BUNDLE_KEY_USER_EMAIL = "USER_EMAIL";
    private static final String DATABASE_NAME = "PIZZA_CUSTOMER";
    private static final int DATABASE_VERSION = 1;

    private ArrayList<Type> types;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeCustomerBinding binding;
    private String userEmail;
    private DataBaseHelper dataBaseHelper;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseHelper = new DataBaseHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
        userEmail = getIntent().getStringExtra(BUNDLE_KEY_USER_EMAIL);

        binding = ActivityHomeCustomerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHomeCustomer.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        setupNavigation(navigationView, drawer);

        setupUserProfile(navigationView);
        setupMenuNavigation(navigationView, drawer);
        setupLogout(navigationView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_customer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void setupNavigation(NavigationView navigationView, DrawerLayout drawer) {
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_menu, R.id.nav_yourorders, R.id.nav_yourfavorites,
                R.id.nav_specialoffers, R.id.nav_profile, R.id.nav_callorfindus, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setupUserProfile(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        TextView textView = headerView.findViewById(R.id.te);
        textView.setText(userEmail);
        imageView = headerView.findViewById(R.id.ima);
        fetchAndDisplayUserImage();
    }

    private void setupMenuNavigation(NavigationView navigationView, DrawerLayout drawer) {
        ArrayList<Type> menuData = fetchMenuData();
        if (menuData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BUNDLE_KEY_DB_HELPER, menuData);
            bundle.putSerializable("dataBase", dataBaseHelper);
            bundle.putString(BUNDLE_KEY_USER_EMAIL, userEmail);

            navigationView.getMenu().findItem(R.id.nav_menu).setOnMenuItemClickListener(menuItem -> {
                Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer)
                        .navigate(R.id.nav_menu, bundle);
                drawer.closeDrawers();
                return true;
            });}


        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("dataBase", dataBaseHelper);
        bundle1.putString(BUNDLE_KEY_USER_EMAIL, userEmail);

        navigationView.getMenu().findItem(R.id.nav_yourfavorites).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer).navigate(R.id.nav_yourfavorites, bundle1);
            drawer.closeDrawers();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_profile).setOnMenuItemClickListener(menuItem -> {
            Bundle profileBundle = new Bundle();
            profileBundle.putString(BUNDLE_KEY_USER_EMAIL, userEmail);
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer).navigate(R.id.nav_profile, profileBundle);
            drawer.closeDrawers();
            return true;
        });

        Bundle bundle2 = new Bundle();
        bundle2.putSerializable("dataBase", dataBaseHelper);
        bundle2.putString(BUNDLE_KEY_USER_EMAIL, userEmail);

        navigationView.getMenu().findItem(R.id.nav_yourorders).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer).navigate(R.id.nav_yourorders, bundle2);
            drawer.closeDrawers();
            return true;
        });

        Bundle bundle3 = new Bundle();
        bundle3.putSerializable("dataBase", dataBaseHelper);
        bundle3.putString(BUNDLE_KEY_USER_EMAIL, userEmail);
        navigationView.getMenu().findItem(R.id.nav_specialoffers).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer).navigate(R.id.nav_specialoffers, bundle3);
            drawer.closeDrawers();
            return true;
        });

        navigationView.getMenu().findItem(R.id.nav_home).setOnMenuItemClickListener(menuItem -> {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_home_customer).navigate(R.id.nav_home);
            drawer.closeDrawers();
            return true;
        });
    }


    private void setupLogout(NavigationView navigationView) {
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
            startActivity(new Intent(HomeCustomer.this, LOGIN.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
            alertDialog.dismiss();
        });

        dialogView.findViewById(R.id.negativeButton).setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    private void fetchAndDisplayUserImage() {
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

    private ArrayList<Type> fetchMenuData() {
        ArrayList<Type> types = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = dataBaseHelper.getMenu();
            while (cursor.moveToNext()) {
                Type type = new Type();
                type.setTypeName(cursor.getString(0));
                type.setSize(cursor.getString(1));
                type.setPrice(cursor.getString(2));
                type.setIngredients(cursor.getString(3));
                type.setImage(cursor.getInt(4));
                types.add(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return types.isEmpty() ? null : types;
    }
}