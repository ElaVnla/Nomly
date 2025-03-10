package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.WelcomeBinding;

public class Welcome extends AppCompatActivity {
    WelcomeBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        b = WelcomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.signInWithEmail.setOnClickListener(v -> startActivity(new Intent(this, login_signin_Activity.class)));
        b.signInWithFacebook.setOnClickListener(v -> {});
        b.signInWithGoogle.setOnClickListener(v -> {});


    }
}