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
import com.w3itexperts.ombe.activity.createGroup_activity;
import com.w3itexperts.ombe.adapter.CategoriesAdapter;
import com.w3itexperts.ombe.adapter.CoffeeAdapter;
import com.w3itexperts.ombe.adapter.FeaturedAdapter;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.adapter.yourSessionAdapter;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.databinding.FragmentHomeBinding;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.methods.OffsetItemDecoration;
import com.w3itexperts.ombe.modals.FeaturedModal;

import android.content.Intent;
import com.w3itexperts.ombe.activity.joinGroup_Activity;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.modals.yourGroupsModal;
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.util.ArrayList;
import java.util.List;



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

        // Define your page margin and offset from resources
        int pageMarginPx = getResources().getDimensionPixelOffset(R.dimen.page_margin);
        int offsetPx = getResources().getDimensionPixelOffset(R.dimen.page_offset);


//        adapter = new yourGroupsAdapter(DataGenerator.AllGroupsList());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);





        //b.yourGroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        b.yourGroupsView.setAdapter(new yourGroupsAdapter(DataGenerator.AllGroupsList()));

//        b.yourSessionView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        b.yourSessionView.setAdapter(new yourSessionAdapter(DataGenerator.AllSessionsList()));


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

        // JOIN A GROUP BUTTON LINKED TO JOIN GROUP PAGE

        b.joinNewGroupButton.setClickable(true);
        b.joinNewGroupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), joinGroup_Activity.class);
            startActivity(intent);
        });

        b.createAGroupButton.setClickable(true);
        b.createAGroupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), createGroup_activity.class);
            startActivity(intent);
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


        // API STUFF HERE =====================================
        // Retrieve the stored current user from SessionManager.
        final users storedUser = com.w3itexperts.ombe.SessionService.SessionManager
                .getInstance(getContext()).getCurrentUser();

        if (storedUser == null) {
            Log.e("API_MERGE", "No current user found in session!");
            logoutUser(); // This method should force a logout (e.g., navigate to login)
            return;
        }
        // Refresh the user data from the API so that you have the latest details.
        ApiService apiService = ApiClient.getApiService();
        apiService.getUser(storedUser.getUserId()).enqueue(new Callback<users>() {
            @Override
            public void onResponse(Call<users> call, Response<users> response) {
                users refreshedUser;
                if (response.isSuccessful() && response.body() != null) {
                    // Use refreshed user data.
                    refreshedUser = response.body();
                    // Update SessionManager so the new data is available systemwide.
                    com.w3itexperts.ombe.SessionService.SessionManager.getInstance(getContext())
                            .setCurrentUser(refreshedUser);
                } else {
                    // API call failed or returned no updated data; fall back to stored user.
                    Log.e("API_MERGE", "getUserById error: " + response.code());
                    refreshedUser = storedUser;
                }

                // Now update the UI using the (refreshed) user data.
                updateUI(refreshedUser);
            }

            @Override
            public void onFailure(Call<users> call, Throwable t) {
                Log.e("API_MERGE", "API call failed: " + t.getMessage());
                // Fallback: update UI with stored user data.
                updateUI(storedUser);
            }
        });
    }

    // Helper method that updates the UI based on the user's groups and sessions.
    private void updateUI(users user) {
        // Process Groups: refresh data for each grouping via API.
        List<groupings> groupsList = user.getGroups();
        // This list will hold our refreshed modal objects.
        List<yourGroupsModal> groupsModalList = new ArrayList<>();

        if (groupsList != null && !groupsList.isEmpty()) {
            ApiService apiService = ApiClient.getApiService();
            final int totalGroups = groupsList.size();

            // For each group, fetch the updated details.
            for (groupings grp : groupsList) {
                final int groupId = grp.getGroupId();
                apiService.getGrouping(groupId).enqueue(new retrofit2.Callback<groupings>() {
                    @Override
                    public void onResponse(retrofit2.Call<groupings> call, retrofit2.Response<groupings> response) {
                        groupings refreshedGroup;
                        if (response.isSuccessful() && response.body() != null) {
                            refreshedGroup = response.body();
                        } else {
                            // Fallback: use the original grouping data.
                            refreshedGroup = grp;
                            Log.e("API_GROUPS", "Failed to refresh group " + groupId + ": response code " + response.code());
                        }

                        yourGroupsModal modal = new yourGroupsModal(
                                String.valueOf(refreshedGroup.getNoUsers()),     // Updated number of users
                                String.valueOf(refreshedGroup.getNoSessions()),    // Updated number of sessions
                                R.drawable.tempgroupimg,                           // Replace if needed
                                refreshedGroup.getGroupName(),                     // Group name from refreshed data
                                refreshedGroup.getGroupId()
                        );
                        groupsModalList.add(modal);

                        // Once we have processed all groups, update the RecyclerView.
                        if (groupsModalList.size() == totalGroups) {
                            yourGroupsAdapter groupsAdapter = new yourGroupsAdapter(getContext(), groupsModalList);
                            b.yourGroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            b.yourGroupsView.setAdapter(groupsAdapter);
                            Log.d("API_MERGE", "Displayed groups count: " + groupsModalList.size());
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<groupings> call, Throwable t) {
                        // If the API call fails, use fallback data.
                        Log.e("API_GROUPS", "Failure refreshing group " + groupId + ": " + t.getMessage());
                        yourGroupsModal modal = new yourGroupsModal(
                                String.valueOf(grp.getNoUsers()),
                                String.valueOf(grp.getNoSessions()),
                                R.drawable.tempgroupimg,
                                grp.getGroupName(),
                                grp.getGroupId()
                        );
                        groupsModalList.add(modal);

                        if (groupsModalList.size() == totalGroups) {
                            yourGroupsAdapter groupsAdapter = new yourGroupsAdapter(getContext(), groupsModalList);
                            b.yourGroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            b.yourGroupsView.setAdapter(groupsAdapter);
                            Log.d("API_MERGE", "Displayed groups count: " + groupsModalList.size());
                        }
                    }
                });
            }
        } else {
            Log.d("API_MERGE", "No groups found for user.");
        }

        // Process Sessions - aggregate sessions from the original groups (or, if available, refresh similar to groups)
        // This part remains synchronous as before:
        List<yourSessionsModal> sessionsModalList = new ArrayList<>();
        if (groupsList != null) {
            for (groupings grp : groupsList) {
                if (grp.getSessions() != null) {
                    for (com.w3itexperts.ombe.apimodals.sessions sess : grp.getSessions()) {
                        String restaurantName = sess.getLocation();           // Using location as the restaurant name
                        String dateTimeAddress = sess.getMeetingDateTime();     // Using meetingDateTime for date/time
                        String groupName = grp.getGroupName();                  // Use parent's group name
                        String sessionStatus = sess.isCompleted() ? "Completed" : "Upcoming";
                        String sessionTitle = "Session " + sess.getSessionId(); // Dummy title; update if needed

                        yourSessionsModal sessionModal = new yourSessionsModal(
                                restaurantName,
                                dateTimeAddress,
                                groupName,
                                sessionStatus,
                                sessionTitle
                        );
                        sessionsModalList.add(sessionModal);
                    }
                }
            }
        }

        yourSessionAdapter sessionAdapter = new yourSessionAdapter(sessionsModalList);
        b.yourSessionView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        b.yourSessionView.setAdapter(sessionAdapter);
        Log.d("API_MERGE", "Displayed sessions count: " + sessionsModalList.size());
    }


    // Optional logout method if no user session is found.
    private void logoutUser() {
        // Clear session (set currentUser to null in SessionManager)
        com.w3itexperts.ombe.SessionService.SessionManager.getInstance(getContext()).setCurrentUser(null);
        Intent intent = new Intent(getContext(), com.w3itexperts.ombe.activity.Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
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



