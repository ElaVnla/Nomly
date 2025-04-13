package com.w3itexperts.ombe.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
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

public class home extends AppCompatActivity {
    HomeBinding b;
    private NavButton selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            b = HomeBinding.inflate(getLayoutInflater());
            setContentView(b.getRoot());


            // HOME NAVIGATION BAR IS HERE NOT IN HOME_FRAGMENT
            // we want the bar to be shown in the pages
            // ig this the main activity - erika

            // Selected page to show first
            selectNavItem(b.navHome);

            // Home page aka main page
            b.navHome.setOnClickListener(v -> {
                Log.e("NOMLYPROCESS", "User is being navigated to the home page");
                selectNavItem(b.navHome);
                Fragment fragment = new home_fragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Add animation
                transaction.setCustomAnimations(
                        R.anim.fragment_popup,
                        0,
                        0,
                        R.anim.fragment_popdown);
                transaction.replace(R.id.fragment_view, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            });

            // Page to access allsessions
            b.accessSessions.setOnClickListener(v -> {
                Log.e("NOMLYPROCESS", "User is being navigated to the all sessions page");
                selectNavItem(b.accessSessions);
                Fragment fragment = new allSessions();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Add animation
                transaction.setCustomAnimations(
                        R.anim.fragment_popup,
                        0,
                        0,
                        R.anim.fragment_popdown);
                transaction.replace(R.id.fragment_view, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            });

            // to access allgroups
            b.navAllGroups.setOnClickListener(v -> {
                Log.e("NOMLYPROCESS", "User is being navigated to the all groups page");
                selectNavItem(b.navAllGroups);
                Fragment fragment = new allGroups();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Add animation
                transaction.setCustomAnimations(
                        R.anim.fragment_popup,
                        0,
                        0,
                        R.anim.fragment_popdown);
                transaction.replace(R.id.fragment_view, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            });

            // profile page - where use log out also
            // note to self: should i add logout button at main page? - erika
            // eh maybe, idk we see later - toni
            b.navProfile.setOnClickListener(v -> {
                Log.e("NOMLYPROCESS", "User is being navigated to the profile page");
                selectNavItem(b.navProfile);
                Fragment fragment = new Profile();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                // Add animation
                transaction.setCustomAnimations(
                        R.anim.fragment_popup,
                        0,
                        0,
                        R.anim.fragment_popdown);
                transaction.replace(R.id.fragment_view, fragment);
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            });

            // Show home fragment if flag is passed from intent
            if (getIntent().getBooleanExtra("loadHomeFragment", false)) {

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.fragment_popup,
                        0,
                        0,
                        R.anim.fragment_popdown);
                transaction.replace(R.id.fragment_view, new home_fragment());
                transaction.commitAllowingStateLoss();

                // Visually highlight the home nav button
                selectNavItem(b.navHome);
            }
        }
        catch (Exception e)
        {
            Log.e("NOMLYPROCESS", "ERROR: Home activity " + e.getMessage());
            Toast.makeText(this, "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectNavItem(NavButton button) {
        if (selectedButton != null) {
            try {
                selectedButton.setSelected(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        b.navHome.setSelected(false);
        b.accessSessions.setSelected(false);
        b.navAllGroups.setSelected(false);
        b.navProfile.setSelected(false);

        button.setSelected(true);
        selectedButton = button;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_view);
        if (currentFragment instanceof home_fragment) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(currentFragment).attach(currentFragment).commit(); // force refresh
        }
    }
}
