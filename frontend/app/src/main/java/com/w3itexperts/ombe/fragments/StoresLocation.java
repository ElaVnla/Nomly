package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.w3itexperts.ombe.databinding.FragmentStoresLocationBinding;

public class StoresLocation extends Fragment {
    FragmentStoresLocationBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentStoresLocationBinding.inflate(inflater, container, false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());
        b.viewPager.setAdapter(adapter);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        // Connect TabLayout with ViewPager
        new TabLayoutMediator(b.tabLayout, b.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Near me");
                        break;
                    case 1:
                        tab.setText("Popular");
                        break;
                    case 2:
                        tab.setText("Top Rated");
                        break;
                }
            }
        }).attach();
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (int i = 0; i < b.tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = b.tabLayout.getTabAt(i);
            if (tab != null) {
                View tabView = ((ViewGroup) b.tabLayout.getChildAt(0)).getChildAt(i);

                if (tabView != null) {
                    // Set margins for tab
                    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) tabView.getLayoutParams();
                    params.setMargins(20, 0, 10, 0); // Example margins (left, top, right, bottom)
                    tabView.setLayoutParams(params);
                }
            }
        }



    }
    private static class ViewPagerAdapter extends FragmentStateAdapter {

        public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new Nearme(); // Fragment for Tab 1
                case 1:
                    return new Nearme(); // Fragment for Tab 2
                case 2:
                    return new Nearme(); // Fragment for Tab 3

            }
            return new Nearme(); // Default fragment
        }

        @Override
        public int getItemCount() {
            return 3; // Number of tabs
        }
    }

}
