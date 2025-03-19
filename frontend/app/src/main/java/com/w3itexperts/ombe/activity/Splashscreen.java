package com.w3itexperts.ombe.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.SplashscreenBinding;

import java.util.Timer;
import java.util.TimerTask;

public class Splashscreen extends AppCompatActivity {
    SplashscreenBinding b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = SplashscreenBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(b.getRoot());

        SharedPreferences theme = getSharedPreferences("theme", Context.MODE_PRIVATE);
        boolean th = theme.getBoolean("isNightMode",false);

        if (th) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // This splashscreen will be the first to run when the app runs. load for 3
                // seconds then onboarding class gets called.
                startActivity(new Intent(Splashscreen.this, Onboarding.class));
                finish();

            }
        }, 2000);

    }
}
