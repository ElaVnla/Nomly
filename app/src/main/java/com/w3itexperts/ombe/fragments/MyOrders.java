package com.w3itexperts.ombe.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.MyOrderAdapter;
import com.w3itexperts.ombe.databinding.FragmentMyordersBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class MyOrders extends Fragment {
    FragmentMyordersBinding b;
    MyOrderAdapter myOrderAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentMyordersBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.ongoingItems.setLayoutManager(new LinearLayoutManager(getContext()));
        myOrderAdapter = new MyOrderAdapter(getContext(), DataGenerator.myOrderList(),getActivity());
        b.ongoingItems.setAdapter(myOrderAdapter);

        b.allFilter.setOnClickListener(v -> filterQ("All",b.allFilter));
        b.onGoingFilter.setOnClickListener(v -> filterQ("Ongoing",b.onGoingFilter));
        b.complitedFilter.setOnClickListener(v -> filterQ("Completed",b.complitedFilter));
        filterQ("All",b.allFilter);

    }

    private void filterQ(String status, MaterialButton allFilter) {
        b.allFilter.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        b.onGoingFilter.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        b.complitedFilter.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        myOrderAdapter.filter(status);

        allFilter.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),R.color.color_primary)));
    }
}
