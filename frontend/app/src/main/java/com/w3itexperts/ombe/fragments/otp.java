package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.apimodals.OtpVerificationRequest;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.OtpLayoutBinding;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otp extends Fragment {
    private OtpLayoutBinding b;
    // Registration details passed from create_account.
    private String username, email, password, allergies;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull android.view.LayoutInflater inflater,
                                          @Nullable android.view.ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        b = OtpLayoutBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve registration details from arguments passed
        if (getArguments() != null) {
            username = getArguments().getString("username", "");
            email = getArguments().getString("email", "");
            password = getArguments().getString("password", "");
            allergies = getArguments().getString("allergies", "");
        }

        // go back to create account page
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.verifyBtn.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getApiService();
            // get the 4 separate numbers and then combine them together
            String OTPCode = b.otp1.getText().toString().trim()
                    + b.otp2.getText().toString().trim()
                    + b.otp3.getText().toString().trim()
                    + b.otp4.getText().toString().trim();

            // validation below
            // if less than 4 numbers inputted, output an error
            if (OTPCode.length() != 4) {
                String msg = "Please enter the 4 digit OTP Number";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("NOMLYPROCESS", msg);
                return;
            }

            // initiale the class to be send to the backend to varify wehtehr the otp is correct or not
            OtpVerificationRequest otpRequest = new OtpVerificationRequest();
            otpRequest.setEmail(email);
            otpRequest.setOtp(OTPCode);
            otpRequest.setUsername(username);
            otpRequest.setPassword(password);
            otpRequest.setAllergies(allergies);

            // call api
            apiService.verifyOtp(otpRequest).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                        // OTP verified successfully
                        String msg = "OTP verified successfully.";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("NOMLYPROCESS", msg);
                        // Create a Map to to put user details to register
                        Map<String, String> userMap = new HashMap<>();
                        userMap.put("username", username);
                        userMap.put("email", email);
                        userMap.put("password", password);
                        userMap.put("preferences", allergies);

                        // since otp verified, add user call api
                        apiService.addUser(userMap).enqueue(new Callback<users>() {
                            @Override
                            public void onResponse(Call<users> call, Response<users> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    users newUser = response.body();
                                    String msg = "Account created successfully!";
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    Log.d("NOMLYPROCESS", msg);
                                    SessionManager.getInstance(getContext()).setCurrentUser(newUser);
                                    startActivity(new Intent(getContext(), home.class));
                                    getActivity().finish();
                                } else {
                                    String errorMsg = "Internal: Account creation fail: " + response.code();
                                    Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                                    Log.d("NOMLYPROCESS", String.valueOf(response.errorBody()));
                                }
                            }
                            @Override
                            public void onFailure(Call<users> call, Throwable t) {
                                String msg = "Account creation failed: " + t.getMessage();
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                Log.d("NOMLYPROCESS", msg);
                            }
                        });

                    } else {
                        String msg = "OTP verification failed";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("NOMLYPROCESS", msg);
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    String msg = "OTP verification failed: " + t.getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("NOMLYPROCESS", msg);
                }
            });
        });

        // auto-move focus between OTP fields.
        b.otp1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp1.getText().length() == 1) b.otp2.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        b.otp2.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp2.getText().length() == 1) b.otp3.requestFocus();
                else b.otp1.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        b.otp3.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp3.getText().length() == 1) b.otp4.requestFocus();
                else b.otp2.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) { }
        });
        b.otp4.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (b.otp4.getText().length() != 1) b.otp3.requestFocus();
            }
            @Override public void afterTextChanged(Editable s) { }
        });
    }
}
