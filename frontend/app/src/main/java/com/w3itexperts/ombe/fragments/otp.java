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

// can use retrofit to communcicate/use API
// https://square.github.io/retrofit/
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

        // Function stuff ================================================================
        // Retrieve registration details from the arguments passed from the other page
        if (getArguments() != null) {
            android.util.Log.d("NOMLYPROCESS", "user info retrieve");

            username = getArguments().getString("username", "");
            email = getArguments().getString("email", "");
            password = getArguments().getString("password", "");
            allergies = getArguments().getString("allergies", "");
            android.util.Log.d("NOMLYPROCESS", "Email retrieve: "+ username);

        }
        else {
            Toast.makeText(getContext(),"Unable to retrieve your details. Please try again or contact Admin support",Toast.LENGTH_SHORT).show();
            //since data fail we return them back to create account page
            startActivity(new Intent(getContext(), create_account.class));
            getActivity().finish();
        }

        // button listener stuff ===================================================================

        // go back to create account page
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.verifyBtn.setOnClickListener(v -> {
            ApiService apiService = ApiClient.getApiService();
            // get the 4 separate numbers and then combine them together
            // trim incase got extra space or smt
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

            try {
                Log.d("NOMLYPROCESS", "Starting to send email========================");

                // initiale the class to be send to the backend to varify wehtehr the otp is correct or not
                OtpVerificationRequest otpRequest = new OtpVerificationRequest();
                otpRequest.setEmail(email);
                otpRequest.setOtp(OTPCode);
                otpRequest.setUsername(username);
                otpRequest.setPassword(password);
                otpRequest.setAllergies(allergies);

                Log.d("NOMLYPROCESS", "Calling email api");
                Log.d("NOMLYPROCESS", "check here 1");


                // call api
                // https://stackoverflow.com/questions/64256637/how-can-i-handle-the-response-from-the-retrofit-here-my-response-not-showing-th
                // chatgpt assisted with debugging
                apiService.verifyOtp(otpRequest).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        Log.d("NOMLYPROCESS", "check here 2");
                        if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                            Log.d("NOMLYPROCESS", "check here 3");

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
                                    Log.d("NOMLYPROCESS", "check here 4");
                                    if (response.isSuccessful() && response.body() != null) {
                                        Log.d("NOMLYPROCESS", "check here 5");

                                        users newUser = response.body();
                                        String msg = "Account created successfully! Navigating to home page...";

                                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                        Log.d("NOMLYPROCESS", msg);

                                        // create active session then navigate to the home page
                                        SessionManager.getInstance(getContext()).setCurrentUser(newUser);
                                        startActivity(new Intent(getContext(), home.class));
                                        getActivity().finish();
                                    } else {
                                        Log.d("NOMLYPROCESS", "check here 6");

                                        String errorMsg = "Internal: Account creation fail: " + response.code();
                                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();

                                        Log.d("NOMLYPROCESS", String.valueOf(response.errorBody()));
                                    }
                                }
                                @Override
                                public void onFailure(Call<users> call, Throwable t) {
                                    Log.d("NOMLYPROCESS", "check here 7");
                                    String msg = "Account creation failed: " + t.getMessage();
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    Log.d("NOMLYPROCESS", msg);
                                }
                            });

                        } else {
                            Log.d("NOMLYPROCESS", "check here 8");
                            String msg = "OTP inputted is wrong. Please try again.";
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                            Log.d("NOMLYPROCESS", msg);
                        }
                    }
                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.d("NOMLYPROCESS", "check here 9");
                        String msg = "OTP verification failed: " + t.getMessage();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("NOMLYPROCESS", msg);
                    }
                });
            } catch (Exception e) {
                Log.e("NOMLYPROCESS", "ERROR: Failed to send email  - " + e.getMessage());
                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
            }
        });

        // Create the animation for otp inputs to move from the left to the right
        //https://stackoverflow.com/questions/26361953/java-syntax-of-addtextchangedlistenernew-textwatcher
        // https://www.geeksforgeeks.org/how-to-implement-textwatcher-in-android/
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
