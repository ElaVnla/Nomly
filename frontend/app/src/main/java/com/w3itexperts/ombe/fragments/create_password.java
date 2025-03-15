package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.activity.login_signin_Activity;
import com.w3itexperts.ombe.databinding.CreatePasswordBinding;

public class create_password extends Fragment {
    CreatePasswordBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = CreatePasswordBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.continueBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), home.class)));
        b.signinBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), login_signin_Activity.class)));



    }
}
