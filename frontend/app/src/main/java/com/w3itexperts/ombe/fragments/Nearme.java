package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.adapter.OurStoresAdapter;
import com.w3itexperts.ombe.adapter.itemViewAdapter;
import com.w3itexperts.ombe.databinding.FragmentNearMeBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class Nearme extends Fragment {
    FragmentNearMeBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentNearMeBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.itemView.setLayoutManager(new LinearLayoutManager(getContext()));

        OurStoresAdapter adapterStores = new OurStoresAdapter(getContext(), DataGenerator.OurStoresItems());
        b.itemView.setLayoutManager(new LinearLayoutManager(getContext()));
        b.itemView.setAdapter(adapterStores);


    }
}
