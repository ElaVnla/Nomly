package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.AddressAdapter;
import com.w3itexperts.ombe.databinding.FragmentAddressBinding;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.modals.AddressModal;

import java.util.ArrayList;

public class AddressFragment extends Fragment {
    FragmentAddressBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAddressBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.addressView.setLayoutManager(new LinearLayoutManager(getContext()));
        b.addressView.setAdapter(new AddressAdapter(getContext(), DataGenerator.addressList()));

        b.addAddressBtn.setOnClickListener(v -> {
            Fragment fragment = new AddAddress();
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


        b.continueBtn.setOnClickListener(v -> {

            Fragment fragment = new PaymentMethods();
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
