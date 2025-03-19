package com.w3itexperts.ombe.activity;
// did you commit?
import android.graphics.Rect;
import android.os.Bundle;
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
import com.w3itexperts.ombe.adapter.CoffeeAdapter;
import com.w3itexperts.ombe.component.NavButton;
import com.w3itexperts.ombe.databinding.HomeBinding;
import com.w3itexperts.ombe.fragments.Cart;
import com.w3itexperts.ombe.fragments.Profile;
import com.w3itexperts.ombe.fragments.Wishlist;
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

        b.navCart.setOnClickListener(v -> {
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

        });

        b.navWishlist.setOnClickListener(v -> {
            selectNavItem(b.navWishlist);
            Fragment fragment = new Wishlist();
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

    }
    private void selectNavItem(NavButton button) {
        if (selectedButton != null) {
            try {
                selectedButton.setSelected(false);
            } catch (Exception e) {
            }
        }
        b.navHome.setSelected(false);
        b.navCart.setSelected(false);
        b.navWishlist.setSelected(false);
        b.navProfile.setSelected(false);

        button.setSelected(true);
        selectedButton = button;
    }
}