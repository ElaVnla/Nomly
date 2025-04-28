package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentResetPasswordBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// can use retrofit to communcicate/use API
// https://square.github.io/retrofit/
public class reset_password extends Fragment {
    FragmentResetPasswordBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentResetPasswordBinding.inflate(getLayoutInflater(), container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.senddBtn.setOnClickListener(v -> {
            // Retrieve the entered email from the text field.
            String enteredEmail = b.emailinput.getText().toString().trim();

            // validation
            if (enteredEmail.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an email address", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiService apiService = ApiClient.getApiService();
            // Get all users and find the one matching the entered email.
            try {
                apiService.getAllUsers().enqueue(new Callback<List<users>>() {
                    @Override
                    public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // need validate if user exist and check if email exist or not cannot change forget password
                            Log.d("NOMLYPROCESS", "PLEASE WORK");

                            int userId = -1;
                            for (users user : response.body()) {
                                if (user.getEmail().equalsIgnoreCase(enteredEmail)) {
                                    userId = user.getUserId();
                                    break;
                                }
                            }

                            // if user not found
                            if (userId == -1) {
                                Log.d("NOMLYPROCESS", "WHY U NOT GETTIN GTHE EMAIL");
                                Toast.makeText(getContext(), "User does not exist. Please create an account.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Pass both email and userId to ResetPwOtp.
                            Bundle args = new Bundle();
                            args.putString("email", enteredEmail);
                            args.putInt("userId", userId);

                            Fragment fragment = new ResetPwOtp();
                            fragment.setArguments(args);
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(
                                    android.R.anim.slide_in_left,  // Enter animation
                                    android.R.anim.slide_out_right,  // Exit animation
                                    android.R.anim.slide_in_left,  // Pop enter animation
                                    android.R.anim.slide_out_right  // Pop exit animation
                            );

                            transaction.replace(getActivity().findViewById(R.id.main).getId(), fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        } else {
                            String errormsg = "";
                            try {
                                if (response.errorBody() != null) {
                                    errormsg = response.errorBody().string();
                                }
                            } catch (Exception e) {
                                errormsg = "Unable to read error: " + e.getMessage();
                            }
                            Toast.makeText(getContext(), "Error retrieving users: " + response.code(), Toast.LENGTH_SHORT).show();
                            android.util.Log.e("NOMLYPROCESS", "Retrieval failed. Code: " + response.code() + ", Error body: " + errormsg);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<users>> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed to retrieve users: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                        android.util.Log.e("RESET_PASSWORD", "Error retrieving users", t);
                    }
                });
            }
            catch (Exception e)
            {
                Log.e("NOMLYPROCESS", "ERROR: Failed to send email - " + e.getMessage());
                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
