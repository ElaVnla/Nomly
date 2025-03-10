package com.w3itexperts.ombe.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.ActivityLoginSigninBinding;
import com.w3itexperts.ombe.fragments.login;

public class login_signin_Activity extends AppCompatActivity {
   ActivityLoginSigninBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityLoginSigninBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

    }
}