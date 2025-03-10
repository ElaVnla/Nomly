package com.w3itexperts.ombe.fragments;

import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.CartAdapter;
import com.w3itexperts.ombe.databinding.FragmentCartBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class Cart extends Fragment {
    FragmentCartBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentCartBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.cardView.setAdapter(new CartAdapter(DataGenerator.generateCartItems(), getContext(), b.totalPrice, b.allItemPrice, b.allItemDiscountPrice));
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.proceedtoBuyBtn.setOnClickListener(v -> {

            Fragment fragment = new AddressFragment();
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
