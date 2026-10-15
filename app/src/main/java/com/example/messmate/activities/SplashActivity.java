package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        com.example.messmate.utils.DataSeeder.seedInitialDataIfEmpty();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Class<?> next = FirebaseAuth.getInstance().getCurrentUser() == null
                    ? LoginActivity.class : MainActivity.class;

            startActivity(new Intent(this, next));
            overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }, 1800);
    }
}