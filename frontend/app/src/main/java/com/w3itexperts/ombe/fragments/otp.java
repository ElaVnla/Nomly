package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.OtpLayoutBinding;

public class otp extends Fragment {
    OtpLayoutBinding b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = OtpLayoutBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.verifyBtn.setOnClickListener(v -> {

            Fragment fragment = new create_password();
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


        b.otp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp1.getText().length()==1){
                    b.otp2.requestFocus();

                }else {


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.otp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp2.getText().length()==1){
                    b.otp3.requestFocus();

                }else {
                    b.otp1.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.otp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp3.getText().length()==1){
                    b.otp4.requestFocus();

                }else {
                    b.otp2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        b.otp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp4.getText().length()==1){


                }else {
                    b.otp3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



    }
}
