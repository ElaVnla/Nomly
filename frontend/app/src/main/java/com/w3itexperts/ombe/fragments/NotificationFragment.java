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
import com.w3itexperts.ombe.databinding.FragmentNotificationBinding;
import com.w3itexperts.ombe.databinding.ItemNotificationBinding;

public class NotificationFragment extends Fragment {
    FragmentNotificationBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentNotificationBinding.inflate(getLayoutInflater());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Arrays for notifications
        String[] arrivals = {
                "Iced Caramel Latte",
                "Dark Roast Espresso",
                "Vanilla Cold Brew",
                "Matcha Latte",
                "Mocha Frappuccino",
                "Pumpkin Spice Latte",
                "Almond Milk Cappuccino",
                "Classic Americano",
                "Honey Lavender Latte",
                "Flat White"
        };

        String[] Dates = {
                "15 July 2024",
                "20 July 2024",
                "25 July 2024",
                "30 July 2024",
                "1 August 2024",
                "5 August 2024",
                "10 August 2024",
                "15 August 2024",
                "20 August 2024",
                "25 August 2024"
        };

        int[] drawable = {
                R.drawable.person1,
                R.drawable.person2,
                R.drawable.person3,
                R.drawable.person4,
                R.drawable.person5,
                R.drawable.person6,
                R.drawable.person7,
                R.drawable.person8,
                R.drawable.person9,
                R.drawable.person10
        };

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

        for (int i = 0; i < arrivals.length; i++) {
            ItemNotificationBinding binding = ItemNotificationBinding.inflate(getLayoutInflater());
            binding.title.setText(arrivals[i]);
            binding.dateLable.setText(Dates[i]);
            binding.image.setImageResource(drawable[i]);
            b.containerNoti.addView(binding.getRoot());

        }
    }
}
