package com.w3itexperts.ombe.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentReviewBinding;

public class Review extends Fragment {
    FragmentReviewBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentReviewBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRatingSystem();
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.sendBtn.setOnClickListener(v -> getActivity().onBackPressed());

    }

    private void setupRatingSystem() {
        ImageView[] stars = {
                b.rating1,
                b.rating2,
                b.rating3,
                b.rating4,
                b.rating5
        };

        for (int i = 0; i < stars.length; i++) {
            int finalI = i;
            stars[i].setOnClickListener(v -> setRating(finalI + 1, stars));
            stars[i].setOnTouchListener(new View.OnTouchListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_MOVE) {
                        for (@SuppressLint("ClickableViewAccessibility") int j = 0; j < stars.length; j++) {
                            if (isInViewBounds(stars[j], event.getRawX(), event.getRawY())) {
                                setRating(j + 1, stars);
                                break;
                            }
                        }
                    }
                    return true;
                }
            });
        }
    }

    private void setRating(int rating, ImageView[] stars) {
        for (int i = 0; i < stars.length; i++) {
            if (i < rating) {
                stars[i].setImageResource(R.drawable.activestar);
            } else {
                stars[i].setImageResource(R.drawable.inactivestar);
            }
        }
    }

    private boolean isInViewBounds(View view, float x, float y) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];

        return (x > viewX && x < (viewX + view.getWidth()) &&
                y > viewY && y < (viewY + view.getHeight()));
    }


}
