package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.w3itexperts.ombe.databinding.BottomSheetLoginBinding;
import com.w3itexperts.ombe.databinding.BottomSheetRegisterBinding;
import com.w3itexperts.ombe.databinding.BottomSheetSuccessBinding;
import com.w3itexperts.ombe.databinding.FragmentBottomSheetsBinding;

public class BottomSheet extends Fragment {
    FragmentBottomSheetsBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentBottomSheetsBinding.inflate(inflater, container, false);
        return b.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.loginSheet.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(getContext());
            BottomSheetLoginBinding bb = BottomSheetLoginBinding.inflate(getLayoutInflater());
            bb.closebtn.setOnClickListener(v1 -> dialog.dismiss());
            bb.loginBtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();
        });

        b.registerSheet.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(getContext());
            BottomSheetRegisterBinding bb = BottomSheetRegisterBinding.inflate(getLayoutInflater());
            bb.closebtn.setOnClickListener(v1 -> dialog.dismiss());
            bb.register.setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();
        });

        b.successSheet.setOnClickListener(v -> {
            BottomSheetDialog dialog = new BottomSheetDialog(getContext());
            BottomSheetSuccessBinding bb = BottomSheetSuccessBinding.inflate(getLayoutInflater());
            bb.getRoot().setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();
        });




    }
}
