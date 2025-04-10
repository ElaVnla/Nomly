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

        // Retrieve registration details from arguments.
        if (getArguments() != null) {
            username = getArguments().getString("username", "");
            email = getArguments().getString("email", "");
            password = getArguments().getString("password", "");
            allergies = getArguments().getString("allergies", "");
        }

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.verifyBtn.setOnClickListener(v -> {
            // Combine OTP fields.
            String code = b.otp1.getText().toString().trim()
                    + b.otp2.getText().toString().trim()
                    + b.otp3.getText().toString().trim()
                    + b.otp4.getText().toString().trim();
            if (code.length() != 4) {
                String msg = "Please enter the complete OTP";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("OTP", msg);
                return;
            }

            // Create the OTP verification request.
            OtpVerificationRequest otpRequest = new OtpVerificationRequest();
            otpRequest.setEmail(email);
            otpRequest.setOtp(code);
            otpRequest.setUsername(username);
            otpRequest.setPassword(password);
            otpRequest.setAllergies(allergies);

            ApiService apiService = ApiClient.getApiService();
            // First, verify the OTP.
            apiService.verifyOtp(otpRequest).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                        // OTP verified successfully.
                        String msg = "OTP verified successfully.";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("OTP", msg);
                        // Now create the new user by calling addUser.
                        users newUser = new users(username, email, password, allergies);
                        apiService.addUser(newUser).enqueue(new Callback<users>() {
                            @Override
                            public void onResponse(Call<users> call, Response<users> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    users createdUser = response.body();
                                    String msg = "Account created successfully!";
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    Log.d("OTP", msg);
                                    SessionManager.getInstance(getContext()).setCurrentUser(createdUser);
                                    startActivity(new Intent(getContext(), home.class));
                                    getActivity().finish();
                                } else {
                                    String msg = "Account creation failed: " + response.code();
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    Log.d("OTP", msg);
                                }
                            }
                            @Override
                            public void onFailure(Call<users> call, Throwable t) {
                                String msg = "Account creation failed: " + t.getMessage();
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                Log.d("OTP", msg);
                            }
                        });
                    } else {
                        String msg = "OTP verification failed";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("OTP", msg);
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    String msg = "OTP verification failed: " + t.getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("OTP", msg);
                }
            });
        });

        // Optional: Setup TextWatchers to auto-move focus between OTP fields.
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
