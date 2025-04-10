package com.w3itexperts.ombe.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.BottomSheetLoginBinding;
import com.w3itexperts.ombe.databinding.BottomSheetRegisterBinding;
import com.w3itexperts.ombe.databinding.BottomSheetSuccessBinding;
import com.w3itexperts.ombe.databinding.DialogAreYouConfirmBinding;
import com.w3itexperts.ombe.databinding.FragmentModalBoxBinding;

public class ModalBox extends Fragment {
    FragmentModalBoxBinding b;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentModalBoxBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        dialog = new Dialog(getContext() , R.style.TransparentDialog);
/*
        if (dialog.getWindow() != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        }
*/
        // Set the dialog width to match parent and add margins
     /*   if (dialog.getWindow() != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(dialog.getWindow().getAttributes());
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            layoutParams.horizontalMargin = 0.05f; // Set margin as a fraction of screen width (e.g., 0.05f for 5%)

            // Alternatively, set margins in pixels
            int marginInPixels = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 20, getContext().getResources().getDisplayMetrics());
            layoutParams.width = dialog.getWindow().getWindowManager().getDefaultDisplay().getWidth() - (2 * marginInPixels);

            dialog.getWindow().setAttributes(layoutParams);
        }*/

        b.confirmDialog.setOnClickListener(v -> {

            DialogAreYouConfirmBinding bb = DialogAreYouConfirmBinding.inflate(getLayoutInflater());
            //bb.cancelBtn.setOnClickListener(v1 -> dialog.dismiss());
            bb.confirmBtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();

        });

        b.loginModal.setOnClickListener(v -> {
//            dialog = new Dialog(getContext() , R.style.TransparentDialog);
            BottomSheetLoginBinding bb = BottomSheetLoginBinding.inflate(getLayoutInflater());
            bb.closebtn.setOnClickListener(v1 -> dialog.dismiss());
            bb.loginBtn.setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();
        });
        b.registerModal.setOnClickListener(v -> {
//            dialog = new Dialog(getContext(), R.style.TransparentDialog);
            BottomSheetRegisterBinding bb = BottomSheetRegisterBinding.inflate(getLayoutInflater());
            bb.closebtn.setOnClickListener(v1 -> dialog.dismiss());
            bb.register.setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();
        });

        b.successModal.setOnClickListener(v -> {
//            dialog = new Dialog(getContext(), R.style.TransparentDialog);
            BottomSheetSuccessBinding bb = BottomSheetSuccessBinding.inflate(getLayoutInflater());
            bb.getRoot().setOnClickListener(v1 -> dialog.dismiss());
            dialog.setContentView(bb.getRoot());
            dialog.show();
        });





    }
}
