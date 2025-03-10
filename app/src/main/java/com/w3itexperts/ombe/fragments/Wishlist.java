package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.wishlistAdapter;
import com.w3itexperts.ombe.databinding.FragmentWishlistBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class Wishlist extends Fragment {
    FragmentWishlistBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentWishlistBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.wishlistRecyclerView.setAdapter(new wishlistAdapter(DataGenerator.createWishlistItems(), getContext()));
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.searchBtn.setOnClickListener(v -> {
            Fragment fragment = new SearchView();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

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
}
