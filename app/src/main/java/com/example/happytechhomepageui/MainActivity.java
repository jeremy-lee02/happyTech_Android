package com.example.happytechhomepageui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    InfoFragment qrScannerFragment = new InfoFragment();
    NotiFragment notiFragment = new NotiFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    CartFragment cartFragment = new CartFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ImageButton cartBtn = findViewById(R.id.cart_button);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
        //Check if current fragment is is not cartFragment
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (currentFragment instanceof CartFragment) {
                    cartBtn.setVisibility(View.GONE);
                } else {
                    cartBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
                        cartBtn.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.info:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, qrScannerFragment).commit();
                        cartBtn.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.notification:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, notiFragment).commit();
                        cartBtn.setVisibility(View.VISIBLE);
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, profileFragment).commit();
                        cartBtn.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, cartFragment, "CART_FRAGMENT").commit();
                cartBtn.setVisibility(View.GONE);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageButton cartBtn = findViewById(R.id.cart_button);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        if (currentFragment instanceof CartFragment) {
            cartBtn.setVisibility(View.GONE);
        } else {
            cartBtn.setVisibility(View.VISIBLE);
        }
    }
}