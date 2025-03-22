package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentEditProfileBinding;

import java.util.HashSet;
import java.util.Set;

public class EditProfile extends Fragment {
    FragmentEditProfileBinding b;

    private Set<String> allergySet = new HashSet<>(); // To prevent duplicate allergies

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentEditProfileBinding.inflate(inflater, container, false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.saveProfile.setOnClickListener(v -> getActivity().onBackPressed());

        // Allergy Input - Detect Enter Key to Add Tags
        b.allergyInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String allergyText = b.allergyInput.getText().toString().trim();
                if (!TextUtils.isEmpty(allergyText) && !allergySet.contains(allergyText)) {
                    addTag(allergyText);
                    b.allergyInput.setText(""); // Clear input field
                }
                return true;
            }
            return false;
        });



    }

    // Method to Add Tags Dynamically
    private void addTag(String text) {
        allergySet.add(text); // Prevent duplicate tags

        // Create Layout for Each Tag
        LinearLayout tagLayout = new LinearLayout(getContext());
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setPadding(10, 5, 10, 5);
        tagLayout.setBackground(getResources().getDrawable(R.drawable.tag_background));
        tagLayout.setElevation(8);
        tagLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Create Button for Allergy Tag
        MaterialButton tagButton = new MaterialButton(getContext());
        tagButton.setText(text);
        tagButton.setTextSize(12);
        tagButton.setPadding(20, 10, 20, 10);
        tagButton.setBackgroundColor(getResources().getColor(R.color.color_secondary));
        tagButton.setTextColor(getResources().getColor(R.color.white));
        tagButton.setAllCaps(false);
        tagButton.setCornerRadius(10);
        tagButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Create "X" Button to Remove Allergy
        ImageView closeButton = new ImageView(getContext());
        closeButton.setImageResource(R.drawable.ic_close);
        closeButton.setColorFilter(getResources().getColor(R.color.black));
        closeButton.setPadding(8, 5, 8, 5);
        closeButton.setLayoutParams(new LinearLayout.LayoutParams(50, 50));

        // Remove Tag When "X" Button is Clicked
        closeButton.setOnClickListener(v -> {
            b.tagContainer.removeView(tagLayout);
            allergySet.remove(text);
        });

        tagLayout.addView(tagButton);
        tagLayout.addView(closeButton);
        b.tagContainer.addView(tagLayout);
    }
}
