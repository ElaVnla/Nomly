package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.CategoriesAdapter;
import com.w3itexperts.ombe.adapter.CoffeeAdapter;
import com.w3itexperts.ombe.adapter.FeaturedAdapter;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.adapter.yourSessionAdapter;
import com.w3itexperts.ombe.databinding.FragmentHomeBinding;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.methods.OffsetItemDecoration;
import com.w3itexperts.ombe.modals.FeaturedModal;


// imports
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.List;

public class home_fragment extends Fragment {
    FragmentHomeBinding b;
    private yourGroupsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentHomeBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences theme = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        boolean th = theme.getBoolean("isNightMode",false);

        if (th) {
            b.menu.moon.setBackground(getResources().getDrawable(R.drawable.roundbg));
            b.menu.sun.setBackgroundColor(Color.TRANSPARENT);
            b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.BLACK));
            b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.WHITE));
        } else {
            b.menu.sun.setBackground(getResources().getDrawable(R.drawable.roundbg));
            b.menu.moon.setBackgroundColor(Color.TRANSPARENT);
            b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.WHITE));
            b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.BLACK));
        }

        b.menu.switchBtn.setOnClickListener(v -> {
            if (!th) {
                // Switch to dark theme
                b.menu.moon.setBackground(getResources().getDrawable(R.drawable.roundbg));
                b.menu.sun.setBackgroundColor(Color.TRANSPARENT);
                b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.BLACK));
                b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                theme.edit().putBoolean("isNightMode", true).apply();
            } else {
                // Switch to light theme

                b.menu.sun.setBackground(getResources().getDrawable(R.drawable.roundbg));
                b.menu.moon.setBackgroundColor(Color.TRANSPARENT);
                b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.WHITE));
                b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.BLACK));
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                theme.edit().putBoolean("isNightMode", false).apply();

            }
        });


        adapter = new yourGroupsAdapter(DataGenerator.AllGroupsList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        b.coffeeView.setLayoutManager(layoutManager);
//        b.coffeeView.setAdapter(adapter);
//        new LinearSnapHelper().attachToRecyclerView(b.coffeeView);
//        b.coffeeView.scrollToPosition(30);
//        b.coffeeView.callOnClick();


// Define your page margin and offset from resources
        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.page_margin);
        int offsetPx = getResources().getDimensionPixelOffset(R.dimen.page_offset);

// Add an ItemDecoration for page margin
        //b.coffeeView.addItemDecoration(new OffsetItemDecoration(pageMarginPx, offsetPx));

// Add a scroll listener to apply the transformation effect

//        b.coffeeView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                int center = (recyclerView.getWidth() / 2); // Center of the RecyclerView
//                int itemCount = layoutManager.getChildCount();
//
//                for (int i = 0; i < itemCount; i++) {
//                    View view = layoutManager.getChildAt(i);
//                    if (view != null) {
//                        int childCenter = (view.getLeft() + view.getRight()) / 2;
//                        int distanceFromCenter = Math.abs(center - childCenter);
//
//                        // Scale and adjust alpha based on distance from the center
//                        float scaleFactor = Math.max(0.7f, 1 - (float) distanceFromCenter / center);
//                        view.setScaleY(scaleFactor);
////                        view.setAlpha(scaleFactor);
//                    }
//                }
//            }
//        });


        b.yourGroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        b.yourGroupsView.setAdapter(new yourGroupsAdapter(DataGenerator.AllGroupsList()));

        b.yourSessionView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        b.yourSessionView.setAdapter(new yourSessionAdapter(DataGenerator.AllSessionsList()));


//        List<FeaturedModal> featuredList = DataGenerator.generateFeaturedList();
//        FeaturedAdapter featuredAdapter = new FeaturedAdapter(featuredList, getContext());
//
//        b.featuredView.setAdapter(featuredAdapter);
//        b.featuredView.setLayoutManager(new LinearLayoutManager(getContext()));


        b.menuBtn.setOnClickListener(v -> {
            animateViewRotation(b.containerMain, 0f, -4.97f);
            animateViewTranslation(b.containerMain, 0, 650, 250, 450, 0, -450, 0, -450);
            b.menu.main.setOnClickListener(v1 -> b.menu.navCloseBtn.callOnClick());

        });

        b.menu.navCloseBtn.setOnClickListener(v -> {
            animateViewRotation(b.containerMain, -8.97f, 0f);
            animateViewTranslation(b.containerMain, 650, 0, 100, 0, -200, 0, -100, 0);
            b.menu.main.setClickable(false);
        });


        b.menu.homeBtn.setOnClickListener(v -> {
            b.menu.navCloseBtn.callOnClick();
        });


        b.menu.products.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new ProductsFragment());
            }, 200);

        });

        b.menu.components.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new Components());
            }, 200);
        });

        b.menu.wishlist.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new Wishlist());
            }, 200);

        });

        b.menu.myOrderBtn.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new MyOrders());
            }, 200);

        });

        b.menu.mySessionBtn.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new PlanSessionFragment());
            }, 200);
        });

        b.menu.ourStoresBtn.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new StoresLocation());
            }, 200);

        });


        b.menu.profileBtn.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new Profile());
            }, 200);

        });

        b.menu.chatlist.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new chatlist());
            }, 200);

        });

        b.menu.myCart.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new Cart());
            }, 200);

        });





        b.menu.rewards.setOnClickListener(v -> {
            new Handler().postDelayed(() -> {
                SwitchFragment(new Reward());
            }, 200);

        });

        b.viewAllGroups.setClickable(true);
        b.viewAllGroups.setOnClickListener(v -> {
            Fragment fragment = new allGroups();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
            );
            transaction.replace(R.id.drawerLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        b.viewAllSessions.setClickable(true);
        b.viewAllSessions.setOnClickListener(v -> {
            Fragment fragment = new allSessions();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
            );
            transaction.replace(R.id.drawerLayout, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        b.menu.logoutBtn.setOnClickListener(v -> {
            b.menu.navCloseBtn.callOnClick();
        });


        //b.notificationBtn.setOnClickListener(v -> SwitchFragment(new NotificationFragment()));

    }

    private void SwitchFragment(Fragment fragment) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_popup,
                0,
                0,
                R.anim.fragment_popdown);

        transaction.replace(R.id.fragment_view, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
        b.menu.navCloseBtn.callOnClick();

    }

    // Method to animate the rotation of a view
    private void animateViewRotation(View view, float startRotation, float endRotation) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);  // Duration in milliseconds
        rotationAnimator.start();
    }

    // Method to animate the translation (margins) of a view
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


//package com.w3itexperts.ombe.fragments;
//
//import android.animation.ObjectAnimator;
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatDelegate;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.LinearSnapHelper;
//import androidx.recyclerview.widget.RecyclerView;
//import androidx.viewpager2.widget.ViewPager2;
//
//import com.google.android.material.bottomsheet.BottomSheetBehavior;
//import com.w3itexperts.ombe.R;
//import com.w3itexperts.ombe.adapter.CategoriesAdapter;
//import com.w3itexperts.ombe.adapter.CoffeeAdapter;
//import com.w3itexperts.ombe.adapter.FeaturedAdapter;
//import com.w3itexperts.ombe.databinding.FragmentDetailsBinding;
//import com.w3itexperts.ombe.databinding.FragmentHomeBinding;
//import com.w3itexperts.ombe.methods.DataGenerator;
//import com.w3itexperts.ombe.methods.OffsetItemDecoration;
//import com.w3itexperts.ombe.modals.FeaturedModal;
//
//
//import java.util.List;
//
//public class home_fragment extends Fragment {
//    FragmentHomeBinding b;
//    private BottomSheetBehavior<View> bottomSheetBehavior;
//    private CoffeeAdapter adapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        b = FragmentHomeBinding.inflate(inflater, container, false);
//        return b.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Initialize the Bottom Sheet
//        View bottomSheet = getActivity().findViewById(R.id.bottom_sheet);
//        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
//
//        // Set the Bottom Sheet to expanded state and make it persistent
////        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
////        bottomSheetBehavior.setDraggable(false); // Disable dragging
//
//        // Optional: Disable hiding the Bottom Sheet when the outside is touched
//        bottomSheetBehavior.setHideable(false);
//
//        // Optional: Disable closing on outside touch if using DialogFragment
//        bottomSheet.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true; // Consume the touch event
//            }
//        });
//
//
//
//        SharedPreferences theme = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
//        boolean th = theme.getBoolean("isNightMode",false);
//
//        if (th) {
//            b.menu.moon.setBackground(getResources().getDrawable(R.drawable.roundbg));
//            b.menu.sun.setBackgroundColor(Color.TRANSPARENT);
//            b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.BLACK));
//            b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.WHITE));
//        } else {
//            b.menu.sun.setBackground(getResources().getDrawable(R.drawable.roundbg));
//            b.menu.moon.setBackgroundColor(Color.TRANSPARENT);
//            b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.WHITE));
//            b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.BLACK));
//        }
//
//        b.menu.switchBtn.setOnClickListener(v -> {
//            if (!th) {
//                // Switch to dark theme
//                b.menu.moon.setBackground(getResources().getDrawable(R.drawable.roundbg));
//                b.menu.sun.setBackgroundColor(Color.TRANSPARENT);
//                b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.BLACK));
//                b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.WHITE));
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                theme.edit().putBoolean("isNightMode", true).apply();
//            } else {
//                // Switch to light theme
//
//                b.menu.sun.setBackground(getResources().getDrawable(R.drawable.roundbg));
//                b.menu.moon.setBackgroundColor(Color.TRANSPARENT);
//                b.menu.sun.setImageTintList(ColorStateList.valueOf(Color.WHITE));
//                b.menu.moon.setImageTintList(ColorStateList.valueOf(Color.BLACK));
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                theme.edit().putBoolean("isNightMode", false).apply();
//
//            }
//        });
//
//
//        adapter = new CoffeeAdapter(DataGenerator.coffeeItems());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        b.coffeeView.setLayoutManager(layoutManager);
//        b.coffeeView.setAdapter(adapter);
//        new LinearSnapHelper().attachToRecyclerView(b.coffeeView);
//        b.coffeeView.scrollToPosition(30);
//        b.coffeeView.callOnClick();
//
//
//// Define your page margin and offset from resources
//        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.page_margin);
//        int offsetPx = getResources().getDimensionPixelOffset(R.dimen.page_offset);
//
//// Add an ItemDecoration for page margin
//        b.coffeeView.addItemDecoration(new OffsetItemDecoration(pageMarginPx, offsetPx));
//
//// Add a scroll listener to apply the transformation effect
//
////        b.coffeeView.addOnScrollListener(new RecyclerView.OnScrollListener() {
////            @Override
////            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
////                int center = (recyclerView.getWidth() / 2); // Center of the RecyclerView
////                int itemCount = layoutManager.getChildCount();
////
////                for (int i = 0; i < itemCount; i++) {
////                    View view = layoutManager.getChildAt(i);
////                    if (view != null) {
////                        int childCenter = (view.getLeft() + view.getRight()) / 2;
////                        int distanceFromCenter = Math.abs(center - childCenter);
////
////                        // Scale and adjust alpha based on distance from the center
////                        float scaleFactor = Math.max(0.7f, 1 - (float) distanceFromCenter / center);
////                        view.setScaleY(scaleFactor);
//////                        view.setAlpha(scaleFactor);
////                    }
////                }
////            }
////        });
//
//
//        CategoriesAdapter adapter = new CategoriesAdapter(DataGenerator.generateCategoriesList(), getContext(),getActivity());
//        b.categoryView.setAdapter(adapter);
//        b.categoryView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//
//        List<FeaturedModal> featuredList = DataGenerator.generateFeaturedList();
//        FeaturedAdapter featuredAdapter = new FeaturedAdapter(featuredList, getContext());
//
//        b.featuredView.setAdapter(featuredAdapter);
//        b.featuredView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//
//        b.menuBtn.setOnClickListener(v -> {
//            animateViewRotation(b.containerMain, 0f, -4.97f);
//            animateViewTranslation(b.containerMain, 0, 650, 250, 450, 0, -450, 0, -450);
//            b.menu.main.setOnClickListener(v1 -> b.menu.navCloseBtn.callOnClick());
//
//        });
//
//        b.menu.navCloseBtn.setOnClickListener(v -> {
//            animateViewRotation(b.containerMain, -8.97f, 0f);
//            animateViewTranslation(b.containerMain, 650, 0, 100, 0, -200, 0, -100, 0);
//            b.menu.main.setClickable(false);
//        });
//
//
//        b.menu.homeBtn.setOnClickListener(v -> {
//            b.menu.navCloseBtn.callOnClick();
//        });
//
//
//        b.menu.products.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new ProductsFragment());
//            }, 200);
//
//        });
//
//        b.menu.components.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new Components());
//            }, 200);
//        });
//
//        b.menu.wishlist.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new Wishlist());
//            }, 200);
//
//        });
//
//        b.menu.myOrderBtn.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new MyOrders());
//            }, 200);
//
//        });
//
//        b.menu.ourStoresBtn.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new StoresLocation());
//            }, 200);
//
//        });
//
//
//        b.menu.profileBtn.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new Profile());
//            }, 200);
//
//        });
//
//        b.menu.chatlist.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new chatlist());
//            }, 200);
//
//        });
//
//        b.menu.myCart.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new Cart());
//            }, 200);
//
//        });
//
//        b.menu.rewards.setOnClickListener(v -> {
//            new Handler().postDelayed(() -> {
//                SwitchFragment(new Reward());
//            }, 200);
//
//        });
//
//        b.menu.logoutBtn.setOnClickListener(v -> {
//            b.menu.navCloseBtn.callOnClick();
//        });
//
//
//        //b.notificationBtn.setOnClickListener(v -> SwitchFragment(new NotificationFragment()));
//
//
//    }
//
//    private void SwitchFragment(Fragment fragment) {
//
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.setCustomAnimations(
//                R.anim.fragment_popup,
//                0,
//                0,
//                R.anim.fragment_popdown);
//
//        transaction.replace(R.id.fragment_view, fragment);
//        transaction.addToBackStack(null);
//        transaction.commitAllowingStateLoss();
//        b.menu.navCloseBtn.callOnClick();
//
//    }
//
//    // Method to animate the rotation of a view
//    private void animateViewRotation(View view, float startRotation, float endRotation) {
//        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
//        rotationAnimator.setDuration(300);  // Duration in milliseconds
//        rotationAnimator.start();
//    }
//
//    // Method to animate the translation (margins) of a view
//    private void animateViewTranslation(View view, int startLeftMargin, int endLeftMargin, int startTopMargin, int endTopMargin,
//                                        int startRightMargin, int endRightMargin, int startBottomMargin, int endBottomMargin) {
//        ValueAnimator marginAnimator = ValueAnimator.ofFloat(0f, 1f);
//        marginAnimator.setDuration(300);  // Duration in milliseconds
//        marginAnimator.addUpdateListener(animation -> {
//            float fraction = animation.getAnimatedFraction();
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//            params.leftMargin = (int) (startLeftMargin + fraction * (endLeftMargin - startLeftMargin));
//            params.topMargin = (int) (startTopMargin + fraction * (endTopMargin - startTopMargin));
//            params.rightMargin = (int) (startRightMargin + fraction * (endRightMargin - startRightMargin));
//            params.bottomMargin = (int) (startBottomMargin + fraction * (endBottomMargin - startBottomMargin));
//            view.setLayoutParams(params);
//        });
//        marginAnimator.start();
//    }
//
//
//}
