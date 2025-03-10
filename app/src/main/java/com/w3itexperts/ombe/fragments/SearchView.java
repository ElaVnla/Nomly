package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.databinding.FragmentSearchViewBinding;
import com.w3itexperts.ombe.databinding.SearchItemBinding;

public class SearchView extends Fragment {
    FragmentSearchViewBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentSearchViewBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        String[] items = {
                "Hot Cappuccino Latte White Mocha",
                "Iced Vanilla Latte",
                "Espresso Macchiato",
                "Caramel Frappuccino",
                "Americano",
                "Flat White",
                "Mocha Frappuccino",
                "Iced Caramel Macchiato",
                "Café au Lait",
                "Double Espresso",
                "Pumpkin Spice Latte",
                "Cold Brew Coffee",
                "Matcha Green Tea Latte",
                "Iced Mocha",
                "Chai Latte"
        };
        for (int i = 0; i < items.length ; i++) {
            SearchItemBinding binding = SearchItemBinding.inflate(getLayoutInflater());
            binding.textView.setText(items[i]);
            b.historyContainer.addView(binding.getRoot());
        }
    }
}
