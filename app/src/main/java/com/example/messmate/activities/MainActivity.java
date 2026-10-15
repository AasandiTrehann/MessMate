package com.example.messmate.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.example.messmate.fragments.HomeFragment;
import com.example.messmate.fragments.MenuFragment;
import com.example.messmate.fragments.ProfileFragment;
import com.example.messmate.fragments.RecommendationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        com.example.messmate.utils.DataSeeder.seedInitialDataIfEmpty();

        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment fragment;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                fragment = new HomeFragment();
            } else if (id == R.id.nav_menu) {
                fragment = new MenuFragment();
            } else if (id == R.id.nav_recommendation) {
                fragment = new RecommendationFragment();
            } else if (id == R.id.nav_profile) {
                fragment = new ProfileFragment();
            } else {
                return false;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            android.R.anim.fade_in,
                            android.R.anim.fade_out)
                    .replace(R.id.mainFragmentContainer, fragment)
                    .commit();

            return true;
        });

        if (savedInstanceState == null) {
            bottomNavigation.setSelectedItemId(R.id.nav_home);
        }
    }

    public void selectTab(int itemId) {
        if (bottomNavigation != null) {
            bottomNavigation.setSelectedItemId(itemId);
        }
    }
}