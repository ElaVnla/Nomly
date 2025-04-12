package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.apimodals.RegistrationRequest;
import com.w3itexperts.ombe.apimodals.RegistrationResponse;
import com.w3itexperts.ombe.apimodals.OtpVerificationRequest;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.ForgetpasswordOtpBinding;
import com.w3itexperts.ombe.fragments.create_password;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPwOtp extends Fragment {
    private ForgetpasswordOtpBinding b;
    // For forgot password, we now expect email and userId from reset_password.
    private String email;
    private int userId;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull android.view.LayoutInflater inflater,
                                          @Nullable android.view.ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        b = ForgetpasswordOtpBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull android.view.View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve email and userId from arguments.
        if (getArguments() != null) {
            email = getArguments().getString("email", "");
            userId = getArguments().getInt("userId", -1);
        }
        if (TextUtils.isEmpty(email) || userId == -1) {
            String err = "Email or user id not provided";
            Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
            Log.d("RESET_PW_OTP", err);
            return;
        }

        // Re-send OTP email by calling registerEmail.
        ApiService apiService = ApiClient.getApiService();
        RegistrationRequest regRequest = new RegistrationRequest(email);
        apiService.registerEmail(regRequest).enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String msg = response.body().getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("RESET_PW_OTP", "OTP email sent: " + msg);
                } else {
                    String err = "Failed to send OTP email: " + response.code();
                    Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                    Log.d("RESET_PW_OTP", err);
                }
            }
            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                String err = "Error sending OTP email: " + t.getMessage();
                Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                Log.d("RESET_PW_OTP", err);
            }
        });

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
                Log.d("RESET_PW_OTP", msg);
                return;
            }

            // Create the OTP verification request.
            OtpVerificationRequest otpRequest = new OtpVerificationRequest();
            otpRequest.setEmail(email);
            otpRequest.setOtp(code);
            // For forgot password, extra fields are not needed.
            otpRequest.setUsername("");
            otpRequest.setPassword("");
            otpRequest.setAllergies("");

            apiService.verifyOtp(otpRequest).enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                        String msg = "OTP verified successfully.";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("RESET_PW_OTP", msg);
                        // Now, retrieve the user using getUser with the userId.
                        apiService.getUser(userId).enqueue(new Callback<users>() {
                            @Override
                            public void onResponse(Call<users> call, Response<users> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    users user = response.body();
                                    String msg2 = "User found: id=" + user.getUserId();
                                    Toast.makeText(getContext(), msg2, Toast.LENGTH_SHORT).show();
                                    Log.d("RESET_PW_OTP", msg2);
                                    // Bundle the userId and email to pass to the create_password fragment.
                                    Bundle args = new Bundle();
                                    args.putInt("userId", user.getUserId());
                                    args.putString("email", email);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    create_password createPwFragment = new create_password();
                                    createPwFragment.setArguments(args);
                                    transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,
                                            android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                                    transaction.replace(getActivity().findViewById(R.id.main).getId(), createPwFragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                } else {
                                    String msg2 = "User not found with id: " + userId;
                                    Toast.makeText(getContext(), msg2, Toast.LENGTH_SHORT).show();
                                    Log.d("RESET_PW_OTP", msg2);
                                }
                            }

                            @Override
                            public void onFailure(Call<users> call, Throwable t) {
                                String msg2 = "Failed to retrieve user info: " + t.getMessage();
                                Toast.makeText(getContext(), msg2, Toast.LENGTH_SHORT).show();
                                Log.d("RESET_PW_OTP", msg2);
                            }
                        });
                    } else {
                        String msg = "OTP verification failed";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("RESET_PW_OTP", msg);
                    }
                }
                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    String msg = "OTP verification failed: " + t.getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("RESET_PW_OTP", msg);
                }
            });
        });

        // Setup TextWatchers for auto-moving focus between OTP fields.
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
