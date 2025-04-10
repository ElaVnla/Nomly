package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.Welcome;
import com.w3itexperts.ombe.activity.login_signin_Activity;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.databinding.FragmentProfileBinding;
import com.w3itexperts.ombe.apimodals.users;

public class Profile extends Fragment {
    private FragmentProfileBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve current user details from SessionManager.
        users currentUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (currentUser != null) {
            // Update the profile UI with user data.
            b.displayusername.setText(currentUser.getUsername());  // Adjust method name if necessary
            b.displayemail.setText(currentUser.getEmail());
            // Optionally, update a profile ImageView if you have a URL or resource.
        } else {
            Log.e("Profile", "No current user found.");
            // Optionally, redirect to the login screen if no user is logged in.
            startActivity(new Intent(getContext(), login_signin_Activity.class));
        }

        // Set up edit profile button.
        b.editProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
            transaction.replace(R.id.fragment_view, new EditProfile());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        });

        // Set up logout button.
        b.logout.setOnClickListener(v -> {
            // Clear user session and navigate to a welcome or login screen.
            SessionManager.getInstance(getContext()).setCurrentUser(null);
            startActivity(new Intent(getContext(), Welcome.class));
        });
    }
}
