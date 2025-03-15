package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentAddaddressBinding;

public class AddAddress extends Fragment {
    FragmentAddaddressBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAddaddressBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.saveAddressBtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.homeIcon.setOnClickListener(v -> toggle(b.homeIcon));
        b.shopIcon.setOnClickListener(v -> toggle(b.shopIcon));
        b.officeIcon.setOnClickListener(v -> toggle(b.officeIcon));


    }
    private void toggle(MaterialButton toggleSelect) {

        LinearLayout viewParent = (LinearLayout) toggleSelect.getParent();

        for (int i = 0; i < viewParent.getChildCount(); i++) {
            View view = viewParent.getChildAt(i);
            MaterialButton button = view.findViewWithTag(toggleSelect.getTag());

            if (button != null) {
                if (button == toggleSelect) {
                    // Selected button
                    button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_primary));
                    button.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                } else {
                    // Other buttons
                    button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_transparent));
                    button.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_black));
                }
            }
        }


    }
}
