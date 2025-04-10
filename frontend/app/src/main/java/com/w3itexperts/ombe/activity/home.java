package com.w3itexperts.ombe.activity;
// did you commit?
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.adapter.CoffeeAdapter;
import com.w3itexperts.ombe.component.NavButton;
import com.w3itexperts.ombe.databinding.HomeBinding;
import com.w3itexperts.ombe.fragments.Cart;
import com.w3itexperts.ombe.fragments.Profile;
import com.w3itexperts.ombe.fragments.Wishlist;
import com.w3itexperts.ombe.fragments.allGroups;
import com.w3itexperts.ombe.fragments.allSessions;
import com.w3itexperts.ombe.fragments.home_fragment;
import com.w3itexperts.ombe.modals.CoffeeItem;

import java.util.ArrayList;
import java.util.List;

public class home extends AppCompatActivity implements SessionManager.SessionTimeoutListener{
    HomeBinding b;
    private NavButton selectedButton;

    // it works LOL
//    private static final long INACTIVITY_TIMEOUT = 10 * 60 * 1000; // 10 minutes in milliseconds
//    private Handler sessionHandler = new Handler();
//    private Runnable sessionRunnable = new Runnable() {
//        @Override
//        public void run() {
//            // Inactivity detected: log out the user.
//            logoutUser();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = HomeBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        selectNavItem(b.navHome);

        b.navHome.setOnClickListener(v -> {
            selectNavItem(b.navHome);
            Fragment fragment = new home_fragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });

        /*b.navCart.setOnClickListener(v -> {
            selectNavItem(b.navCart);
            Fragment fragment = new Cart();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });*/

        b.accessSessions.setOnClickListener(v -> {
            selectNavItem(b.accessSessions);
            Fragment fragment = new allSessions();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });

        b.navAllGroups.setOnClickListener(v -> {
            selectNavItem(b.navAllGroups);
            Fragment fragment = new allGroups();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });

        b.navProfile.setOnClickListener(v -> {
            selectNavItem(b.navProfile);
            Fragment fragment = new Profile();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });

        SessionManager.getInstance(this).setSessionTimeoutListener(this);

    }
    private void selectNavItem(NavButton button) {
        if (selectedButton != null) {
            try {
                selectedButton.setSelected(false);
            } catch (Exception e) {
            }
        }
        b.navHome.setSelected(false);
        //b.navCart.setSelected(false);
        b.accessSessions.setSelected(false);
        b.navAllGroups.setSelected(false);
        b.navProfile.setSelected(false);

        button.setSelected(true);
        selectedButton = button;
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        SessionManager.getInstance(this).resetSessionTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SessionManager.getInstance(this).resetSessionTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SessionManager.getInstance(this).cancelSessionTimer();
    }

    @Override
    public void onSessionTimeout() {
        // Handle session timeout (e.g., log out user)
        Intent intent = new Intent(home.this, Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

//    // This method resets the inactivity timer.
//    private void resetInactivityTimer() {
//        sessionHandler.removeCallbacks(sessionRunnable);
//        sessionHandler.postDelayed(sessionRunnable, INACTIVITY_TIMEOUT);
//    }
//
//    // Override dispatchTouchEvent so any touch resets the timer.
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        resetInactivityTimer();
//        return super.dispatchTouchEvent(ev);
//    }
//
//    @Override
//    public void onUserInteraction() {
//        super.onUserInteraction();
//        resetInactivityTimer();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        resetInactivityTimer();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        sessionHandler.removeCallbacks(sessionRunnable);
//    }
//
//    // Define what to do when the session expires.
//    private void logoutUser() {
//        // Clear any session information as needed (e.g., removing login flags from SharedPreferences)
//        Intent intent = new Intent(home.this, login_signin_Activity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        finish();
//    }
}