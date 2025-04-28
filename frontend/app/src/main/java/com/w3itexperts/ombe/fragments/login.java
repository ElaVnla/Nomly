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
import com.w3itexperts.ombe.methods.EncryptionUtil;

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

        // button listener thingy her e=======================
        // redirect them back to welcome page
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        // if they wan to navigate to creat account
        b.createAccountBtn.setOnClickListener(v -> {

            Fragment fragment = new create_account();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            // Animation for transition
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

        // if user when reset password, navigate them to reset password page
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

            // Obtain the inputted details
            String enteredUsername = b.usernameEditText.getText().toString().trim();
            String enteredPassword = b.passwordEditText.getText().toString().trim();

            try {
                ApiService apiService = ApiClient.getApiService();

                // check if user exist. call api
                apiService.getAllUsers().enqueue(new Callback<List<users>>() {
                    @Override
                    public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            boolean validUser = false;
                            for (users user : response.body()) {
                                // check if user exist
                                if (user.getUsername().equalsIgnoreCase(enteredUsername)) {
                                    try {
                                        // password is encrypted so need to decrypt
                                        // generated by chatgpt for decryption - edit by erika
                                        String decryptedPassword = EncryptionUtil.decrypt(user.getPassword());

                                        // if password is correct, login is successful
                                        if (decryptedPassword.equals(enteredPassword)) {
                                            validUser = true;
                                            // create a session then login
                                            SessionManager.getInstance(getContext()).setCurrentUser(user);
                                            startActivity(new Intent(getContext(), home.class));
                                            break;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Toast.makeText(getContext(), "Error decrypting password", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }

                            // output error model
                            if (!validUser) {
                                DialogAreYouConfirmBinding bb = DialogAreYouConfirmBinding.inflate(getLayoutInflater());
                                //bb.cancelBtn.setOnClickListener(v1 -> dialog.dismiss());
                                bb.confirmBtn.setOnClickListener(v1 -> dialog.dismiss());
                                dialog.setContentView(bb.getRoot());
                                dialog.show();
                                //Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                return;
                                //Log.e("LOGIN", "Invalid credentials!");
                            }
                        } else {
                            Log.e("NOMLYPROCESS", "API response error: " + response.code());
                            DialogError400Binding bb = DialogError400Binding.inflate(getLayoutInflater());
                            //bb.cancelBtn.setOnClickListener(v1 -> dialog.dismiss());
                            bb.confirmBtn.setOnClickListener(v1 -> dialog.dismiss());
                            dialog.setContentView(bb.getRoot());
                            dialog.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<users>> call, Throwable t) {
                        Log.e("NOMLYPROCESS", "API call failed: " + t.getMessage());
                    }
                });

            } catch (Exception e) {
                Log.e("NOMLYPROCESS", "ERROR: Failed to login  - " + e.getMessage());
                Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
            }

        });


        //b.signInWithGoogle.setOnClickListener(v -> startActivity(new Intent(getContext(), home.class)));

    }
}
