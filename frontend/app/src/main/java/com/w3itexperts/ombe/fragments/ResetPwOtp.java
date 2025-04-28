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

// can use retrofit to communcicate/use API
// https://square.github.io/retrofit/
public class ResetPwOtp extends Fragment {
    private ForgetpasswordOtpBinding b;
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

        // Retrieve email and userId from previous page
        if (getArguments() != null) {
            email = getArguments().getString("email", "");
            userId = getArguments().getInt("userId", -1);
        }
        if (TextUtils.isEmpty(email) || userId == -1) {
            String err = "Email or user id dont have";
            Toast.makeText(getContext(), "404 Error: Please contact Admin SUpport", Toast.LENGTH_SHORT).show();
            Log.d("NOMLYPROCESS", err);
            //since data fail we return them back to login account page
            startActivity(new Intent(getContext(), login.class));
            getActivity().finish();
            return;
        }

        // we reuse the email service in springboot so that we dh to create another function
        // just reuse, reduce, recycle
        ApiService apiService = ApiClient.getApiService();
        SendOtpEmail(apiService);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.verifyBtn.setOnClickListener(v -> {
            // get the 4 otp fields thenc ombine into oone string
            String code = b.otp1.getText().toString().trim()
                    + b.otp2.getText().toString().trim()
                    + b.otp3.getText().toString().trim()
                    + b.otp4.getText().toString().trim();

            // check if all the code is inputted. validation check
            if (code.length() != 4) {
                String msg = "Please enter the complete OTP";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("NOMLYPROCESS", msg);
                return;
            }

            // Create the OTP verification request
            OtpVerificationRequest otpRequest = new OtpVerificationRequest();
            otpRequest.setEmail(email);
            otpRequest.setOtp(code);

            // For forgot password, extra fields are not needed
            otpRequest.setUsername("");
            otpRequest.setPassword("");
            otpRequest.setAllergies("");

            try {
                apiService.verifyOtp(otpRequest).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                            // OTP verification was successful, therefore naavigate here
                            String msg = "OTP verified successfully.";
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            Log.d("NOMLYPROCESS", msg);

                            try {
                                /// get user details to pass to next page
                                apiService.getUser(userId).enqueue(new Callback<users>() {
                                    @Override
                                    public void onResponse(Call<users> call, Response<users> response) {
                                        if (response.isSuccessful() && response.body() != null) {
                                            users user = response.body();
                                            String msg2 = "User found: id=" + user.getUserId();
                                            //Toast.makeText(getContext(), msg2, Toast.LENGTH_SHORT).show();
                                            Log.d("NOMLYPROCESS", msg2);
                                            // group the userId and email to pass to the create_password

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
                                            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
                                            Log.d("NOMLYPROCESS", msg2);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<users> call, Throwable t) {
                                        String msg2 = "Failed to retrieve user info: " + t.getMessage();
                                        Toast.makeText(getContext(), msg2, Toast.LENGTH_SHORT).show();
                                        Log.d("NOMLYPROCESS", msg2);
                                    }
                                });
                            }
                            catch (Exception e) {
                                Log.e("NOMLYPROCESS", "ERROR: Failed to get user - " + e.getMessage());
                                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
                            }
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
            }
            catch (Exception e) {
                Log.e("NOMLYPROCESS", "ERROR: Failed to verify otp - " + e.getMessage());
                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
            }
        });

        // make OTP Input look nice nice
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

    // Debug and amended with stackoverflow and chatgpt assistance
    public void SendOtpEmail(ApiService apiService)
    {
        try {
            RegistrationRequest regRequest = new RegistrationRequest(email);
            apiService.registerEmail(regRequest).enqueue(new Callback<RegistrationResponse>() {
                @Override
                public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String msg = response.body().getMessage();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("NOMLYPROCESS", "OTP email sent: " + msg);
                    } else {
                        String err = "Failed to send OTP email: " + response.code();
                        Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                        Log.d("NOMLYPROCESS", err);
                    }
                }
                @Override
                public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                    String err = "Error sending OTP email: " + t.getMessage();
                    Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
                    Log.d("NOMLYPROCESS", err);
                }
            });
        }
        catch (Exception e) {
            Log.e("NOMLYPROCESS", "ERROR: Failed to send otp - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }
    }
}
