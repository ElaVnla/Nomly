package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.w3itexperts.ombe.databinding.FragmentProductsBinding;

public class ProductsFragment extends Fragment {
    FragmentProductsBinding b;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentProductsBinding.inflate(inflater, container, false);

        // Set up the ViewPager with the adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
        b.viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager
        new TabLayoutMediator(b.tabLayout, b.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Breverages");
                        break;
                    case 1:
                        tab.setText("Brewed Coffee");
                        break;
                    case 2:
                        tab.setText("Blended Coffee");
                        break;
                }
            }
        }).attach();

        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.moreOptionBtn.setOnClickListener(v -> {

        });

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
                    return new Breverages_List(); // Fragment for Tab 1
                case 1:
                    return new Breverages_List(); // Fragment for Tab 2
                case 2:
                    return new Breverages_List(); // Fragment for Tab 3

            }
            return new Breverages_List(); // Default fragment
        }

        @Override
        public int getItemCount() {
            return 3; // Number of tabs
        }
    }

}
