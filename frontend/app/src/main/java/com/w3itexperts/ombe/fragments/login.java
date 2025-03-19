package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.databinding.FragmentLoginBinding;

public class login extends Fragment {
    FragmentLoginBinding b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.createAccountBtn.setOnClickListener(v -> {

            Fragment fragment = new create_account();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    android.R.anim.slide_in_left,  // Enter animation
                    android.R.anim.slide_out_right,  // Exit animation
                    android.R.anim.slide_in_left,  // Pop enter animation
                    android.R.anim.slide_out_right  // Pop exit animation
            );

            transaction.replace(getActivity().findViewById(R.id.main).getId(), fragment);
            transaction.addToBackStack(null);
            transaction.commit();
//
//            getActivity().findViewById(R.id.main);
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment).commit();
        });

        b.resetPasswordBnt.setOnClickListener(v -> {

            Fragment fragment = new reset_password(); // call this specific fragment
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    android.R.anim.slide_in_left,  // Enter animation
                    android.R.anim.slide_out_right,  // Exit animation
                    android.R.anim.slide_in_left,  // Pop enter animation
                    android.R.anim.slide_out_right  // Pop exit animation
            );

            transaction.replace(getActivity().findViewById(R.id.main).getId(), fragment);
            transaction.addToBackStack(null);
            transaction.commit();
//
//            getActivity().findViewById(R.id.main);
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment).commit();
        });

        b.loginBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), home.class)));
        b.signInWithGoogle.setOnClickListener(v -> startActivity(new Intent(getContext(), home.class)));

    }
}
