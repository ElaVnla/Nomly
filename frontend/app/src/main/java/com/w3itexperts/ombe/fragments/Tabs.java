package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentTabsBinding;

public class Tabs extends Fragment {
    FragmentTabsBinding b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentTabsBinding.inflate(inflater,container,false);
        return b.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.home.setOnClickListener(v -> tabSelect(b.home,R.drawable.tab_top,R.color.text_color_black));
        b.profile.setOnClickListener(v -> tabSelect(b.profile,R.drawable.tab_top,R.color.text_color_black));
        b.about.setOnClickListener(v -> tabSelect(b.about,R.drawable.tab_top,R.color.text_color_black));

        b.home0.setOnClickListener(v -> tabSelect(b.home0,R.drawable.tab_right,R.color.text_color_black));
        b.profile0.setOnClickListener(v -> tabSelect(b.profile0,R.drawable.tab_right,R.color.text_color_black));
        b.about0.setOnClickListener(v -> tabSelect(b.about0,R.drawable.tab_right,R.color.text_color_black));

        b.home1.setOnClickListener(v -> tabSelect(b.home1,R.drawable.tab_background_pri,R.color.white));
        b.profile1.setOnClickListener(v -> tabSelect(b.profile1,R.drawable.tab_background_pri,R.color.white));
        b.about1.setOnClickListener(v -> tabSelect(b.about1,R.drawable.tab_background_pri,R.color.white));


       b.home2.setOnClickListener(v -> tabSelect(b.home2,R.drawable.tab_bottom,R.color.white));
        b.profile2.setOnClickListener(v -> tabSelect(b.profile2,R.drawable.tab_bottom,R.color.white));
        b.about2.setOnClickListener(v -> tabSelect(b.about2,R.drawable.tab_bottom,R.color.white));





    }


    private void tabSelect(TextView home, int bgColor, int textColor) {
       LinearLayout v = (LinearLayout) home.getParent();
        for (int i = 0; i < v.getChildCount(); i++) {
            View view = v.getChildAt(i);
            TextView tab =  view.findViewWithTag(home.getTag());
            tab.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_black));
            tab.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_transparent));
        }

        home.setTextColor(ContextCompat.getColor(getContext(), textColor /*R.color.text_color_black*/));
        home.setBackground(getActivity().getDrawable(bgColor) /*R.color.background_color_primary*/);

    }

}
