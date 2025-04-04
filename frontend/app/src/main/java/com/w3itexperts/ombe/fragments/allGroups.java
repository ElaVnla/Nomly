package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.allGroupsAdapter;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.databinding.FragmentAddCardBinding;
import com.w3itexperts.ombe.databinding.FragmentAllgroupsBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class allGroups extends Fragment {

    FragmentAllgroupsBinding b;

    private allGroupsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAllgroupsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //b.saveCreditCard.setOnClickListener(v -> getActivity().onBackPressed());
        //b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());


        adapter = new allGroupsAdapter(DataGenerator.AllGroupsList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        b.allgroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        b.allgroupsView.setAdapter(new allGroupsAdapter(DataGenerator.AllGroupsList()));


    }

    private void animateViewRotation(View view, float startRotation, float endRotation) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);  // Duration in milliseconds
        rotationAnimator.start();
    }

    private void animateViewTranslation(View view, int startLeftMargin, int endLeftMargin, int startTopMargin, int endTopMargin,
                                        int startRightMargin, int endRightMargin, int startBottomMargin, int endBottomMargin) {
        ValueAnimator marginAnimator = ValueAnimator.ofFloat(0f, 1f);
        marginAnimator.setDuration(300);  // Duration in milliseconds
        marginAnimator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = (int) (startLeftMargin + fraction * (endLeftMargin - startLeftMargin));
            params.topMargin = (int) (startTopMargin + fraction * (endTopMargin - startTopMargin));
            params.rightMargin = (int) (startRightMargin + fraction * (endRightMargin - startRightMargin));
            params.bottomMargin = (int) (startBottomMargin + fraction * (endBottomMargin - startBottomMargin));
            view.setLayoutParams(params);
        });
        marginAnimator.start();
    }
}
