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
import com.w3itexperts.ombe.databinding.FragmentCheckOutBinding;

public class CheckOut extends Fragment {
    FragmentCheckOutBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentCheckOutBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());


        b.submitOrderBtn.setOnClickListener(v -> fragmentChgange(new MyOrders()));
        b.reSelectAddressBtn.setOnClickListener(v -> fragmentChgange(new AddressFragment()));
        b.reSelectPaymentMethod.setOnClickListener(v -> fragmentChgange(new PaymentMethods()));
    }

    private void fragmentChgange(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(
                R.anim.fragment_popup,
                0,
                0,
                R.anim.fragment_popdown);

        transaction.replace(R.id.fragment_view, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }
}
