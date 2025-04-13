package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.apimodals.RegistrationRequest;
import com.w3itexperts.ombe.apimodals.RegistrationResponse;
import com.w3itexperts.ombe.databinding.FragmentCreateAccountBinding;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_account extends Fragment {
    private FragmentCreateAccountBinding b;
    private Set<String> allergySet = new HashSet<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentCreateAccountBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // click here when they wan go back welcome page
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        // When the user taps Sign Up, validate and call the registerEmail API.
        b.signupBtn.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getApiService();

            // get the inputs from the form
            String username = b.AddUsername.getText().toString().trim();
            String email = b.AddEmail.getText().toString().trim();
            String password = b.enterpassword.getText().toString().trim();
            String confirmPassword = b.confirmpassword.getText().toString().trim();

            // VALIDATION OF FORM HERE =====================================================
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                String msg = "Please fill in all fields";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                android.util.Log.d("NOMLYPROCESS", msg);
                return;
            }

            if (!password.equals(confirmPassword)) {
                String msg = "Passwords do not match";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                android.util.Log.d("NOMLYPROCESS", msg);
                return;
            }

            // instead of storing as list we store all as string separated by comma
            String allergies = TextUtils.join(",", allergySet);

            // prep the email to sned otp
            RegistrationRequest regRequest = new RegistrationRequest(email);


            try {
                apiService.registerEmail(regRequest).enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            String msg = response.body().getMessage();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            android.util.Log.d("NOMLYPROCESS", msg);

                            // group the registration details to pass to the OTP fragment.
                            Bundle args = new Bundle();
                            args.putString("username", username);
                            args.putString("email", email);
                            args.putString("password", password);
                            args.putString("allergies", allergies);

                            // Navigate to OTP fragment.
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            otp otpFragment = new otp();
                            otpFragment.setArguments(args); // we pass in to different fragment here
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                    android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(getActivity().findViewById(R.id.main).getId(), otpFragment);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        } else {
                            String msg = "Registration failed: " + response.code();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            android.util.Log.d("NOMLYPROCESS", msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                        String msg = "Registration failed: " + t.getMessage();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        android.util.Log.d("NOMLYPROCESS", msg);
                    }
                });
            }
            catch (Exception e)
            {
                Log.e("NOMLYPROCESS", "ERROR: Failed to registeremail  - " + e.getMessage());
                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
            }
        });


        b.allergyInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {

                String allergyText = b.allergyInput.getText().toString().trim();
                if (!TextUtils.isEmpty(allergyText) && !allergySet.contains(allergyText)) {
                    addTag(allergyText);
                    b.allergyInput.setText(""); // Clear input field
                    android.util.Log.d("NOMLYPROCESS", "Added allergy tag: " + allergyText);
                }
                return true;
            }
            return false;
        });
    }

    // allergy tag thingy
    // lost my braincells doing this
    private void addTag(String text) {
        allergySet.add(text);
        // Tag Layout segment
        LinearLayout tagLayout = new LinearLayout(getContext());
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setPadding(10, 5, 10, 5);
        tagLayout.setBackground(getResources().getDrawable(R.drawable.tag_background));
        tagLayout.setElevation(8);
        tagLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Tag button segment
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

        // Close button segment
        ImageView closeButton = new ImageView(getContext());
        closeButton.setImageResource(R.drawable.ic_close);
        closeButton.setColorFilter(getResources().getColor(R.color.black));
        closeButton.setPadding(8, 5, 8, 5);
        closeButton.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        closeButton.setOnClickListener(v -> {
            b.tagContainer.removeView(tagLayout);
            allergySet.remove(text);
            android.util.Log.d("NOMLYPROCESS", "Removed allergy tag: " + text);
        });

        // tag layout segment again
        tagLayout.addView(tagButton);
        tagLayout.addView(closeButton);
        b.tagContainer.addView(tagLayout);
    }
}
