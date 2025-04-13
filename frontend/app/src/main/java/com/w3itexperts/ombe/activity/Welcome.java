package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

        Log.e("NOMLYPROCESS", "Welcome page is being displayed");

        b.logInAccount.setOnClickListener(v -> startActivity(new Intent(this, login_signin_Activity.class)));
        b.createNewAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, login_signin_Activity.class);
            intent.putExtra("showCreateAccount", true);
            startActivity(intent);
        });

//        b.signInWithFacebook.setOnClickListener(v -> {});
//        b.signInWithGoogle.setOnClickListener(v -> {});


    }
}