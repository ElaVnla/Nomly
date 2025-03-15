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
import com.w3itexperts.ombe.adapter.MostOrderedAdapter;
import com.w3itexperts.ombe.databinding.FragmentProfileBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class Profile extends Fragment {
    FragmentProfileBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.editProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);
            transaction.replace(R.id.fragment_view, new EditProfile());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });

        b.mostOrderedView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        b.mostOrderedView.setAdapter(new MostOrderedAdapter(getContext(), DataGenerator.generateMostOrderedList()));




    }
}
