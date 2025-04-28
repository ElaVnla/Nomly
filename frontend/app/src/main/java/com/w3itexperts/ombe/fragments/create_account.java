package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentCreateAccountBinding;
import com.w3itexperts.ombe.methods.EncryptionUtil;

import java.util.HashSet;
import java.util.List;
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

        // functions here ================================================
        SetUpDropDown();

        // BUTTON CLICK LISTENER THINGY HERE =========================================

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

            // check if the user fill in the inputs if not ask them fill up
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
                String msg = "Please fill in all fields";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                android.util.Log.d("NOMLYPROCESS", msg);
                return;
            }

            // password security make sure the password is appropriately hard to solve
            if (!isValidPassword(password)) {
                String msg = "Password must be at least 8 characters long, include 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.";
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                android.util.Log.d("NOMLYPROCESS", msg);
                return;
            }

            // make sure both password is the same
            if (!password.equals(confirmPassword)) {
                String msg = "Passwords do not match";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                android.util.Log.d("NOMLYPROCESS", msg);
                return;
            }

            // instead of storing as list we store all as string separated by comma
            String allergies = TextUtils.join(",", allergySet);

            try {
                // Call getAllUsers to check if email already exist
                apiService.getAllUsers().enqueue(new Callback<List<users>>() {
                    @Override
                    public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Log.d("NOMLYPROCESS", "RETRIEVE USER STARTING TO CREATE");

                            boolean emailExists = false;
                            for (users user : response.body()) {
                                Log.d("NOMLYPROCESS", "EMAIL OF USER: "+user.getEmail());
                                if (user.getEmail().equalsIgnoreCase(email)) {
                                    emailExists = true;
                                    break;
                                }
                            }
                            Log.d("NOMLYPROCESS", "EMAIL exist: "+ emailExists);

                            if (emailExists) {
                                String msg = "Email already taken. Please use another email.";
                                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                                android.util.Log.d("NOMLYPROCESS", msg);
                                return;
                            }
                            Log.d("NOMLYPROCESS", "EMAIL DOES NOT EXIST THEREFORE PROCEED");

                            // email is not taken, proceed to send OTP email
                            RegistrationRequest regRequest = new RegistrationRequest(email);

                            apiService.registerEmail(regRequest).enqueue(new Callback<RegistrationResponse>() {
                                @Override
                                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        String msg = response.body().getMessage();
                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                        android.util.Log.d("NOMLYPROCESS", msg);

                                        // after sending email successful, continue encryption and navigate to OTP
                                        proceedToOtp(username, email, password, allergies);
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

                        } else {
                            String msg = "Failed to retrieve users. Error code: " + response.code();
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            android.util.Log.d("NOMLYPROCESS", msg);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<users>> call, Throwable t) {
                        String msg = "Failed to retrieve users: " + t.getMessage();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        android.util.Log.d("NOMLYPROCESS", msg);
                    }
                });
            } catch (Exception e) {
                Log.e("NOMLYPROCESS", "ERROR: Failed to check existing emails - " + e.getMessage());
                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
            }
        });

        // https://stackoverflow.com/questions/27981167/implement-custom-spinner-on-drop-down-menu-select
        //https://www.digitalocean.com/community/tutorials/android-spinner-drop-down-list
        b.allergySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAllergy = parent.getItemAtPosition(position).toString();

                if (!selectedAllergy.equals("Select your allergy") && !allergySet.contains(selectedAllergy)) {
                    addTag(selectedAllergy);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    // password safety
    // External sources - stackoverflow https://stackoverflow.com/questions/3802192/regexp-java-for-password-validation
    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }

    private void SetUpDropDown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Select your allergy", "No Beef", "No Seafood", "Vegetarian", "Vegan"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b.allergySpinner.setAdapter(adapter);
    }

    // lost my braincells doing this
    // https://stackoverflow.com/questions/1851633/how-to-add-a-button-dynamically-in-android
    // https://www.geeksforgeeks.org/how-to-generate-dynamic-multiple-buttons-in-android/
    private void addTag(String text) {
        allergySet.add(text);
        LinearLayout TagLL = new LinearLayout(getContext());
        TagLL.setOrientation(LinearLayout.HORIZONTAL);
        TagLL.setPadding(10, 5, 10, 5);
        TagLL.setBackground(getResources().getDrawable(R.drawable.tag_background));
        TagLL.setElevation(8);
        TagLL.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        MaterialButton TagBtn = new MaterialButton(getContext());
        TagBtn.setText(text);
        TagBtn.setTextSize(12);
        TagBtn.setPadding(20, 10, 20, 10);
        TagBtn.setBackgroundColor(getResources().getColor(R.color.color_secondary));
        TagBtn.setTextColor(getResources().getColor(R.color.white));
        TagBtn.setAllCaps(false);
        TagBtn.setCornerRadius(10);
        TagBtn.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView CloseBtn = new ImageView(getContext());
        CloseBtn.setImageResource(R.drawable.ic_close);
        CloseBtn.setColorFilter(getResources().getColor(R.color.black));
        CloseBtn.setPadding(8, 5, 8, 5);
        CloseBtn.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        CloseBtn.setOnClickListener(v -> {
            b.tagContainer.removeView(TagLL);
            allergySet.remove(text);
            android.util.Log.d("NOMLYPROCESS", "Removed allergy tag: " + text);
        });

        TagLL.addView(TagBtn);
        TagLL.addView(CloseBtn);
        b.tagContainer.addView(TagLL);
    }

    // encrypt password and navigate
    private void proceedToOtp(String username, String email, String password, String allergies) {
        Bundle args = new Bundle();
        args.putString("username", username);
        args.putString("email", email);

        String encryptedPassword = password;
        boolean encryptstat = false;
        try {
            encryptedPassword = EncryptionUtil.encrypt(password);
            encryptstat = true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Error encrypting password", Toast.LENGTH_SHORT).show();
        }

        if (encryptstat) {
            args.putString("password", encryptedPassword);
            args.putString("allergies", allergies);

            android.util.Log.d("NOMLYPROCESS", "navigating to otp page now");

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            otp otpFragment = new otp();
            otpFragment.setArguments(args);
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.replace(getActivity().findViewById(R.id.main).getId(), otpFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else {
            String erroroutput = "ERROR ENCRYPTING PASSWORD";
            Toast.makeText(getContext(), "404 Error: Please contact admin", Toast.LENGTH_SHORT).show();
            android.util.Log.d("NOMLYPROCESS", erroroutput);
        }
    }
}
