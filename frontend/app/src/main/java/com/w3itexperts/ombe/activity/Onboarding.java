package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.OnBordingAdapter;
import com.w3itexperts.ombe.databinding.OnboardingBinding;
import com.w3itexperts.ombe.modals.OnBordingModal;

import java.util.ArrayList;
import java.util.List;

public class Onboarding extends AppCompatActivity {
    OnboardingBinding b;
    private OnBordingAdapter adapter;
    private Handler sliderHandler = new Handler();
    private ImageView[] dots;
    private List<OnBordingModal> onboardingData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = OnboardingBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        Log.e("NOMLYPROCESS", "Onboarding screen to be displayed");
        // When user click on "get started" button, this will be triggered and activate welcome
        // java class
        b.next.setOnClickListener(v -> startActivity(new Intent(this,Welcome.class)));

        onboardingData = new ArrayList<>();
        onboardingData.add(new OnBordingModal(R.drawable.nomlylogo, "Welcome to Nomly", "Simply swipe through restaurants, and let the highest votes decide where to eat!"));
        onboardingData.add(new OnBordingModal(R.drawable.junkfoodintro, "Create Groups & Sessions", "Start a group, invite your friends and create as many sessions as you like!"));
        //onboardingData.add(new OnBordingModal(R.drawable.image1, "Let’s meet our summer coffee drinks", "orem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"));

        // Set adapter
        adapter = new OnBordingAdapter(this, onboardingData);
        b.viewPager.setAdapter(adapter);
        addDotsIndicator(0);

// Auto slide
        sliderHandler.postDelayed(sliderRunnable, 2500);



        // ViewPager2 page change callback
        b.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                addDotsIndicator(position);
            }
        });

    }

    public void addDotsIndicator(int position) {
        b.dotsLayout.removeAllViews();
        dots = new ImageView[onboardingData.size()];

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.indicator_unselected));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            b.dotsLayout.addView(dots[i], params);
        }

        if (dots.length > 0) {
            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.indicator_selected));
        }
    }

    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            int currentItem = b.viewPager.getCurrentItem();
            int totalItems = b.viewPager.getAdapter().getItemCount();
            if (currentItem < totalItems - 1) {
                b.viewPager.setCurrentItem(currentItem + 1); // Slide to next item
            } else {
                b.viewPager.setCurrentItem(0); // Reset to first item
            }
            sliderHandler.postDelayed(this, 2500); // Repeat every 2.5 seconds
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sliderHandler.removeCallbacks(sliderRunnable); // Stop auto sliding when activity is destroyed
    }

}