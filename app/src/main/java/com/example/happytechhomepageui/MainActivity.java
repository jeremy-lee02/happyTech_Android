package com.example.happytechhomepageui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    InfoFragment infoFragment = new InfoFragment();
    CartFragment cartFragment = new CartFragment();
    ProfileFragment profileFragment = new ProfileFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        ImageButton cartBtn = findViewById(R.id.cart_button);
        TextView fragmentName = findViewById(R.id.fragmentText);
        ImageView logo = (ImageView) findViewById(R.id.imageView);

        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, homeFragment, "home").commit();
        //Check if current fragment is is not cartFragment
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
                if (currentFragment instanceof HomeFragment) {
                    fragmentName.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.GONE);
                    fragmentName.setText("Home");
                    bottomNavigationView.getMenu().getItem(0).setChecked(true);
                }
                else if (currentFragment instanceof InfoFragment) {
                    fragmentName.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.GONE);
                    fragmentName.setText("Order Information");
                    bottomNavigationView.getMenu().getItem(1).setChecked(true);
                }
                else if (currentFragment instanceof CartFragment) {
                    fragmentName.setVisibility(View.VISIBLE);
                    logo.setVisibility(View.GONE);
                    fragmentName.setText("Cart");
                    bottomNavigationView.getMenu().getItem(2).setChecked(true);
                }
                else if (currentFragment instanceof ProfileFragment) {
                    fragmentName.setVisibility(View.GONE);
                    logo.setVisibility(View.VISIBLE);
                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                }
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Fragment home = homeFragment;
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.frameLayout, home, "home");
                        transaction1.addToBackStack(null);
                        transaction1.commit();
                        fragmentName.setVisibility(View.VISIBLE);
                        fragmentName.setText("Home");
                        logo.setVisibility(View.GONE);
                        return true;
                    case R.id.info:
                        Fragment order = infoFragment;
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.frameLayout, order, "order");
                        transaction2.addToBackStack(null);
                        transaction2.commit();
                        fragmentName.setVisibility(View.VISIBLE);
                        fragmentName.setText("Order Information");
                        logo.setVisibility(View.GONE);
                        return true;
                    case R.id.cart:
                        Fragment cart = cartFragment;
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.frameLayout, cart, "cart");
                        transaction3.addToBackStack(null);
                        transaction3.commit();
                        fragmentName.setVisibility(View.VISIBLE);
                        fragmentName.setText("Cart");
                        logo.setVisibility(View.GONE);
                        return true;
                    case R.id.profile:
                        Fragment profile = profileFragment;
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.frameLayout, profile, "profile");
                        transaction4.addToBackStack(null);
                        transaction4.commit();
                        fragmentName.setVisibility(View.GONE);
                        fragmentName.setText("");
                        logo.setVisibility(View.VISIBLE);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frameLayout);
        BottomNavigationView nav =  (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        if (currentFragment instanceof HomeFragment) {
            finish();
        }else {
            super.onBackPressed();
        }

    }
}