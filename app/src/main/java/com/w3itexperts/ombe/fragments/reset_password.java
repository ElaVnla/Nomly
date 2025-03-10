package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentResetPasswordBinding;

public class reset_password extends Fragment {
    FragmentResetPasswordBinding b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentResetPasswordBinding.inflate(getLayoutInflater(), container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.senddBtn.setOnClickListener(v -> {

            Fragment fragment = new otp();
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
        });



    }
}
