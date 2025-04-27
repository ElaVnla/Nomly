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
import com.w3itexperts.ombe.SessionService.SessionManager;
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
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.modals.yourGroupsModal;
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import java.util.List;
import java.util.Locale;

//tonie add these
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


// how to use enqueue from retrofit https://medium.com/@alaxhenry0121/understanding-enqueue-and-execute-in-detail-205f5bee7cbb

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

        // I put them into functions to make it neater cuz it's currently damn messy if we put all the code under here

        //Setup stuff here ========================
        AppThemeSettingsSetup();
        MenuBarSettingsSetup();
        JoinAndCreateGroupSetup();
        NaivgationSetup();

        // Call the API to put the groupings and sessions and stuff
        GetGroupsAndSessionsAPI();
    }

    @Override
    public void onResume() {
        super.onResume();
        // This method will be called every time the fragment resumes
        // Makes it easier for the API to get the latest data when smt is added or not
        // calling to get any latest update
        GetGroupsAndSessionsAPI();
    }

    // THE API STUFF ARE HERE ==============================================
    private void GetGroupsAndSessionsAPI() {
        // Retrieve the stored current user from SessionManager
        // Always check whether there is an existing user, if there is no user logged in
        // log them out automatically. cannot access pages if user is not logged in
        final users currentLoggedInUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (currentLoggedInUser == null) {
            Log.e("NOMLYPROCESS", "No current user found in session. User is null");
            logoutUser();
            return;
        }

        // Perform the API call to refresh user data.
        try {
            ApiService apiService = ApiClient.getApiService();

            apiService.getUser(currentLoggedInUser.getUserId()).enqueue(new Callback<users>() {
                @Override
                public void onResponse(Call<users> call, Response<users> response) {
                    users UserFound;
                    if (response.isSuccessful() && response.body() != null) {
                        // Use refreshed user data.
                        UserFound = response.body();
                        // Update SessionManager to have their stuff
                        // kinda getting the latest data in case
                        SessionManager.getInstance(getContext()).setCurrentUser(UserFound);
                    } else {
                        Log.e("NOMLYPROCESS", "getUser error: " + response.code());
                        UserFound = currentLoggedInUser;
                    }

                    DisplayUI(UserFound);
                }

                @Override
                public void onFailure(Call<users> call, Throwable t) {
                    Log.e("NOMLYPROCESS", "API call failed: " + t.getMessage());

                    // Fallback
                    DisplayUI(currentLoggedInUser);
                }
            });
        } catch (Exception e) {
            Log.e("NOMLYPROCESS", "ERROR: When using getUser API - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }
    }

    // UI update logic remains the same.
    private void DisplayUI(users user) {
        List<groupings> GroupList = user.getGroups();
        List<yourGroupsModal> GroupsModalList = new ArrayList<>();
        List<yourSessionsModal> sessionsModalList = new ArrayList<>();

        ApiService apiService = ApiClient.getApiService();

        TextView groupsDisplayMessage = b.NoGrpMessage;
        RecyclerView yourGroupsView = b.yourGroupsView;
        TextView SessionDisplayMessage = b.NoSessionMessage;
        RecyclerView yourSessionsView = b.yourSessionView;

        try {
            if (GroupList != null && !GroupList.isEmpty()) {
                final int totalGroups = GroupList.size();

                for (groupings grp : GroupList) {
                    // in case it does not appear
                    groupsDisplayMessage.setVisibility(View.GONE);
                    yourGroupsView.setVisibility(View.VISIBLE);

                    final int groupId = grp.getGroupId();
                    apiService.getGrouping(groupId).enqueue(new retrofit2.Callback<groupings>() {
                        @Override
                        public void onResponse(retrofit2.Call<groupings> call, retrofit2.Response<groupings> response) {
                            groupings refreshedGroup;
                            if (response.isSuccessful() && response.body() != null) {
                                refreshedGroup = response.body();
                            } else {
                                refreshedGroup = grp;
                                Log.e("NOMLYPROCESS", "Failed to get group stuff Grp " + groupId + ": response code " + response.code());
                            }

                            // intialize new yourgroups modal
                            //tonie change from here till line  214
                            String base64Image = refreshedGroup.getImage();  // assuming it’s a String
                            byte[] imageBytes = null;
                            if (base64Image != null && !base64Image.isEmpty()) {
                                imageBytes = android.util.Base64.decode(base64Image, android.util.Base64.DEFAULT);
                            }
                            Bitmap decodedImage;

                            if (imageBytes != null && imageBytes.length > 0) {
                                decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            } else {
                                decodedImage = BitmapFactory.decodeResource(getResources(), R.drawable.tempgroupimg);
                            }

                            yourGroupsModal modal = new yourGroupsModal(
                                    String.valueOf(refreshedGroup.getNoUsers()),
                                    String.valueOf(refreshedGroup.getNoSessions()),
                                    decodedImage,
                                    refreshedGroup.getGroupName(),
                                    refreshedGroup.getGroupId()
                            );

                            GroupsModalList.add(modal);

                            if (GroupsModalList.size() == totalGroups) {
                                yourGroupsAdapter groupsAdapter = new yourGroupsAdapter(getContext(), GroupsModalList);
                                b.yourGroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                                b.yourGroupsView.setAdapter(groupsAdapter);
                                Log.d("NOMLYPROCESS", "Displayed groups count: " + GroupsModalList.size());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<groupings> call, Throwable t) {
                            // in case it does not appear
                            groupsDisplayMessage.setVisibility(View.GONE);
                            yourGroupsView.setVisibility(View.VISIBLE);

                            // display wtv we have

                            Log.e("NOMLYPROCESS", "Failed to get group's data for grp " + groupId + ": " + t.getMessage());
                            //tonie changed this part till line 240
                            Bitmap fallbackImage = BitmapFactory.decodeResource(getResources(), R.drawable.tempgroupimg);

                            yourGroupsModal modal = new yourGroupsModal(
                                    String.valueOf(grp.getNoUsers()),
                                    String.valueOf(grp.getNoSessions()),
                                    fallbackImage,
                                    grp.getGroupName(),
                                    grp.getGroupId()
                            );

                            GroupsModalList.add(modal);

                            if (GroupsModalList.size() == totalGroups) {
                                yourGroupsAdapter groupsAdapter = new yourGroupsAdapter(getContext(), GroupsModalList);
                                b.yourGroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                                b.yourGroupsView.setAdapter(groupsAdapter);
                                Log.d("NOMLYPROCESS", "Displayed groups count: " + GroupsModalList.size());
                            }
                        }
                    });
                }
            } else {
                Log.d("NOMLYPROCESS", "No groups found for user.");
                groupsDisplayMessage.setText("You have no groups. Join one now!");
                groupsDisplayMessage.setVisibility(View.VISIBLE);
                yourGroupsView.setVisibility(View.GONE);
            }
        }
        catch (Exception e)
        {
            Log.e("NOMLYPROCESS", "ERROR: Failed to display groups - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }

        try {
            if (GroupList != null && !GroupList.isEmpty()) {
                Log.e("NOMLYPROCESS", "it went in here sessions: grp count - " + GroupList.size());
                for (groupings grp : GroupList) {
                    if (grp.getSessions() != null) {
                        // in case it does not appear
                        SessionDisplayMessage.setVisibility(View.GONE);
                        yourSessionsView.setVisibility(View.VISIBLE);
                        for (com.w3itexperts.ombe.apimodals.sessions sess : grp.getSessions()) {
                            String restaurantName = sess.getLocation();
                            String dateTimeAddress = FormatDateTimeString(sess.getMeetingDateTime());
                            String groupName = grp.getGroupName();
                            String sessionStatus = sess.isCompleted() ? "Completed" : "Upcoming";
                            String sessionTitle = "Session @" + sess.getLocation();
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
            else {
                Log.d("NOMLYPROCESS", "No Sessions found for user.");
                SessionDisplayMessage.setText("You have no active sessions. Join a group and create a session now!");
                SessionDisplayMessage.setVisibility(View.VISIBLE);
                yourSessionsView.setVisibility(View.GONE);
            }
            yourSessionAdapter sessionAdapter = new yourSessionAdapter(sessionsModalList);
            b.yourSessionView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            b.yourSessionView.setAdapter(sessionAdapter);
            Log.d("API_MERGE", "Displayed sessions count: " + sessionsModalList.size());
        }
        catch (Exception e)
        {
            Log.e("NOMLYPROCESS", "ERROR: Failed to display sessions - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }
    }

    //logout user if user doesn't exist or has not been logged out due to reasons
    private void logoutUser() {
        SessionManager.getInstance(getContext()).setCurrentUser(null);
        Intent intent = new Intent(getContext(), com.w3itexperts.ombe.activity.Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void SwitchFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        // Add animation
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

    private void animateViewRotation(View view, float startRotation, float endRotation) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);
        rotationAnimator.start();
    }

    private void animateViewTranslation(View view, int startLeftMargin, int endLeftMargin, int startTopMargin, int endTopMargin,
                                        int startRightMargin, int endRightMargin, int startBottomMargin, int endBottomMargin) {
        ValueAnimator marginAnimator = ValueAnimator.ofFloat(0f, 1f);
        marginAnimator.setDuration(300);
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

    private void AppThemeSettingsSetup() {
        SharedPreferences theme = getActivity().getSharedPreferences("theme", Context.MODE_PRIVATE);
        boolean th = theme.getBoolean("isNightMode", false);
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
    }

    // gonna remove this after everyting is done
    private void MenuBarSettingsSetup() {
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
        b.menu.logoutBtn.setOnClickListener(v -> {
            b.menu.navCloseBtn.callOnClick();
        });
    }

    private void JoinAndCreateGroupSetup() {
        b.joinNewGroupButton.setClickable(true);
        b.joinNewGroupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), joinGroup_Activity.class);
            startActivity(intent);
        });

        b.createAGroupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), createGroup_activity.class);
            intent.putExtra("userId", SessionManager.getInstance(getContext()).getCurrentUser().getUserId());
            startActivity(intent);
        });
    }

    private void NaivgationSetup() {
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
    }

    public String FormatDateTimeString(String jsonDateString) {
        SimpleDateFormat CurrentFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        // convert to this format
        SimpleDateFormat OutputResult = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH);

        try {
            // Parse the JSON date string into a Date object
            Date date = CurrentFormat.parse(jsonDateString);
            return OutputResult.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            // Rparse fail return back the string
            return jsonDateString;
        }
    }
}




