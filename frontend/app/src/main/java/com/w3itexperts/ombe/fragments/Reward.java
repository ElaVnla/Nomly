package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.adapter.rewardAdapter;
import com.w3itexperts.ombe.databinding.FragmentRewardBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class Reward extends Fragment {
    FragmentRewardBinding b;
    rewardAdapter rewardAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentRewardBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rewardAdapter = new rewardAdapter(DataGenerator.rewardItems(), getContext());
        b.rewardView.setAdapter(rewardAdapter);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());



    }
}
