package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.activity.login_signin_Activity;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.CreatePasswordBinding;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create_password extends Fragment {
    private CreatePasswordBinding b;
    private int userId;
    private String email;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = CreatePasswordBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve userId and email from arguments.
        if (getArguments() != null) {
            userId = getArguments().getInt("userId", -1);
            email = getArguments().getString("email", "");
        }
        if (userId == -1 || TextUtils.isEmpty(email)) {
            String err = "Error retrieving user information";
            Toast.makeText(getContext(), err, Toast.LENGTH_SHORT).show();
            Log.d("CREATE_PW", err);
            return;
        }

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.continueBtn.setOnClickListener(v -> {
            String newPassword = b.enterpassword.getText().toString().trim();
            String confirmPassword = b.confirmpassword.getText().toString().trim();

            if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                String msg = "Please fill in all fields";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("CREATE_PW", msg);
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                String msg = "Passwords do not match";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("CREATE_PW", msg);
                return;
            }

            ApiService apiService = ApiClient.getApiService();
            Log.d("CREATE_PW", "Retrieving current user for userId: " + userId);
            // First, retrieve the current user details.
            apiService.getUser(userId).enqueue(new Callback<users>() {
                @Override
                public void onResponse(Call<users> call, Response<users> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        users currentUser = response.body();
                        Log.d("CREATE_PW", "User retrieved: " + currentUser.getUserId());
                        // Build a map containing the fields required by your backend.
                        // If you want to preserve the current username and preferences, get them from currentUser.
                        Map<String, String> updateBody = new HashMap<>();
                        updateBody.put("username", currentUser.getUsername() != null ? currentUser.getUsername() : "");
                        updateBody.put("email", email);
                        updateBody.put("password", newPassword);
                        updateBody.put("preferences", currentUser.getPreferences() != null ? currentUser.getPreferences() : "");

                        Log.d("CREATE_PW", "Calling updateUser with updateBody: " + updateBody.toString());
                        apiService.updateUser(userId, updateBody).enqueue(new Callback<users>() {
                            @Override
                            public void onResponse(Call<users> call, Response<users> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    String msg = "Password updated successfully";
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    Log.d("CREATE_PW", msg);
                                    startActivity(new Intent(getContext(), login_signin_Activity.class));
                                    getActivity().finish();
                                } else {
                                    String msg = "Password update failed: " + response.code();
                                    try {
                                        String errorBody = response.errorBody().string();
                                        Log.d("CREATE_PW", "Error body: " + errorBody);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                    Log.d("CREATE_PW", msg);
                                }
                            }
                            @Override
                            public void onFailure(Call<users> call, Throwable t) {
                                String msg = "Password update failed: " + t.getMessage();
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                Log.d("CREATE_PW", msg);
                            }
                        });
                    } else {
                        String msg = "Failed to retrieve user details: " + response.code();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("CREATE_PW", msg);
                    }
                }
                @Override
                public void onFailure(Call<users> call, Throwable t) {
                    String msg = "Failed to retrieve user: " + t.getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("CREATE_PW", msg);
                }
            });
        });

        b.signinBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), login_signin_Activity.class)));
    }
}
