package com.w3itexperts.ombe.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentSnackbarBinding;

public class SnackbarFragment extends Fragment {
    FragmentSnackbarBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentSnackbarBinding.inflate(inflater, container, false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Success Snackbar
        b.successSnackbar.setOnClickListener(v -> showCustomSnackbar("Success Snackbar clicked!",1000,R.drawable.check_circle,R.color.color_sucess));
        b.warningSnackbar.setOnClickListener(v -> showCustomSnackbar("Warning Snackbar clicked!",1000,R.drawable.warning,R.color.color_warning));
        b.infoSnackbar.setOnClickListener(v -> showCustomSnackbar("Info Snackbar clicked!",1000,R.drawable.information,R.color.color_info));
        b.errorSnackbar.setOnClickListener(v -> showCustomSnackbar("Error Snackbar clicked!",1000,R.drawable.danger,R.color.color_danger));

    }

    @SuppressLint("ResourceType")
    private void showCustomSnackbar(String message, int duration, int icon, int backgroundColor) {
        Snackbar snackbar = Snackbar.make(b.getRoot(), "", duration);

        // Inflate custom view
        @SuppressLint("RestrictedApi") Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
        LayoutInflater inflater = LayoutInflater.from(snackbarLayout.getContext());
        View customView = inflater.inflate(R.layout.custom_snackbar, null);

        // Set custom background color
        snackbarLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_transparent));

        // Set message text
        MaterialCardView cardView = customView.findViewById(R.id.cardView);
        cardView.setCardBackgroundColor(ContextCompat.getColor(getContext(),backgroundColor));

        TextView messageTextView = customView.findViewById(R.id.snackbar_text);
        messageTextView.setText(message);

        // Set icon
        ImageView iconImageView = customView.findViewById(R.id.snackbar_icon);
        iconImageView.setImageResource(icon);

        // Set action (optional)
        TextView actionTextView = customView.findViewById(R.id.snackbar_action);
        actionTextView.setOnClickListener(v -> {
            // Perform action on click
            snackbar.dismiss(); // Example: dismiss the Snackbar
        });

        // Remove default Snackbar's text
        snackbarLayout.removeAllViews();

        // Add custom view to Snackbar's layout
        snackbarLayout.addView(customView, 0);

        snackbar.show();
    }


}
