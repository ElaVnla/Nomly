package com.w3itexperts.ombe.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.DialogAreYouConfirmBinding;
import com.w3itexperts.ombe.databinding.DialogError400Binding;
import com.w3itexperts.ombe.databinding.FragmentLoginBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends Fragment {
    FragmentLoginBinding b;
    Dialog dialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new Dialog(getContext() , R.style.TransparentDialog);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.createAccountBtn.setOnClickListener(v -> {

            Fragment fragment = new create_account();
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
//
//            getActivity().findViewById(R.id.main);
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment).commit();
        });

        b.resetPasswordBnt.setOnClickListener(v -> {

            Fragment fragment = new reset_password(); // call this specific fragment
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
//
//            getActivity().findViewById(R.id.main);
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main,fragment).commit();
        });

        //b.loginBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), home.class)));

        // Login button click handler
        b.loginBtn.setOnClickListener(v -> {
            String enteredUsername = b.usernameEditText.getText().toString().trim();
            String enteredPassword = b.passwordEditText.getText().toString().trim();

            ApiService apiService = ApiClient.getApiService();
            apiService.getAllUsers().enqueue(new Callback<List<users>>() {
                @Override
                public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        boolean validUser = false;
                        for (users user : response.body()) {
                            if (user.getUsername().equalsIgnoreCase(enteredUsername)
                                    && user.getPassword().equals(enteredPassword)) {
                                validUser = true;
                                // Save the session info using the centralized SessionManager.
                                SessionManager.getInstance(getContext()).setCurrentUser(user);
                                // Navigate to the home activity.
                                startActivity(new Intent(getContext(), home.class));
                                break;
                            }
                        }
                        if (!validUser) {
                            DialogAreYouConfirmBinding bb = DialogAreYouConfirmBinding.inflate(getLayoutInflater());
                            //bb.cancelBtn.setOnClickListener(v1 -> dialog.dismiss());
                            bb.confirmBtn.setOnClickListener(v1 -> dialog.dismiss());
                            dialog.setContentView(bb.getRoot());
                            dialog.show();
                            //Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                            return;
                            //Log.e("LOGIN", "Invalid credentials!");
                            // Optionally, show a Toast or error message to the user.
                        }
                    } else {
                        Log.e("LOGIN", "API response error: " + response.code());
                        DialogError400Binding bb = DialogError400Binding.inflate(getLayoutInflater());
                        //bb.cancelBtn.setOnClickListener(v1 -> dialog.dismiss());
                        bb.confirmBtn.setOnClickListener(v1 -> dialog.dismiss());
                        dialog.setContentView(bb.getRoot());
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<List<users>> call, Throwable t) {
                    Log.e("LOGIN", "API call failed: " + t.getMessage());
                }
            });
        });


        b.signInWithGoogle.setOnClickListener(v -> startActivity(new Intent(getContext(), home.class)));

    }
}
