package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.activity.groupPage_Activity;
import com.w3itexperts.ombe.apimodals.userVoteDTO;
import com.w3itexperts.ombe.apimodals.usersDTO;
import com.w3itexperts.ombe.databinding.FragmentViewSessionBinding;
import com.w3itexperts.ombe.modals.RestaurantCard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSessionFragment extends Fragment {

    FragmentViewSessionBinding b;


    private double lat = 0.0;
    private double lng = 0.0;
    private int sessionId = -1;
    private boolean isSessionFinalized = false;

    private boolean swipeStarted = false;
    private String title, location, date, time, status;

    private List<String> eateryIds = new ArrayList<>();

    private List<RestaurantCard> restaurantCards = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentViewSessionBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionId = getArguments().getInt("sessionId", -1);

        fetchFinishedUsers(sessionId);
        checkIfUserFinishedSwiping();

//        if (SessionManager.getInstance(requireContext()).hasUserSwiped(sessionId)) {
//            swipeStarted = true;
//            b.swipeButton.setVisibility(View.GONE);
//            Log.d("SWIPE_STATUS", "User has already swiped for this session.");
//        }

        requireActivity().getSupportFragmentManager().setFragmentResultListener(
                "eateryIdsResult", this, (key, result) -> {
                    eateryIds = result.getStringArrayList("eateryIds");
                    Log.d("LEADERBOARD_DATA", "Received eateryIds: " + eateryIds);
                });

        requireActivity().getSupportFragmentManager().setFragmentResultListener(
                "restaurantCardsResult", this, (key, result) -> {
                    List<RestaurantCard> cards = result.getParcelableArrayList("restaurantCards");
                    if (cards != null) {
                        restaurantCards = cards;
                        Log.d("LEADERBOARD_DATA", "Received RestaurantCards: " + cards.size());
                        fetchLeaderboard(sessionId, eateryIds, cards);
                    } else {
                        Log.e("LEADERBOARD_DATA", "No RestaurantCards received.");
                    }
                });


        // Get session info from arguments
        Bundle args = getArguments();
        if (args != null) {

            Log.d("DEBUG_ARGS_BUNDLE", args.toString());

            title = args.getString("title", "Untitled");
            location = args.getString("location", "Unknown");
//            date = args.getString("date", "N/A");
//            time = args.getString("time", "N/A");

            String rawDate = args.getString("date", "N/A");
            String rawTime = args.getString("time", "N/A");

            date = rawDate;
            time = rawTime;

// Format if needed
            try {
                // Date format check: yyyy-MM-dd → MM/dd/yyyy
                if (rawDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat toDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    Date parsedDate = fromDate.parse(rawDate);
                    if (parsedDate != null) date = toDate.format(parsedDate);
                }

                // Time format check: HH:mm:ss → hh:mm a
                if (rawTime.matches(".*T\\d{2}:\\d{2}:\\d{2}")) {
                    rawTime = rawTime.split("T")[1]; // e.g. "T07:30:00" → "07:30:00"
                }

                if (rawTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
                    SimpleDateFormat fromTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat toTime = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    Date parsedTime = fromTime.parse(rawTime);
                    if (parsedTime != null) time = toTime.format(parsedTime);
                }
            } catch (Exception e) {
                e.printStackTrace(); // fallback to raw values
            }

            status = args.getString("status", "Ongoing");
            lat = args.getDouble("lat", 0.0);
            lng = args.getDouble("lng", 0.0);

            List<String> members = args.getStringArrayList("members");

//            if (members != null && members.size() > 0) {
//                b.user1.setVisibility(View.VISIBLE);
//                b.user1.setText(members.get(0));
//            }
//            if (members != null && members.size() > 1) {
//                b.user2.setVisibility(View.VISIBLE);
//                b.user2.setText(members.get(1));
//            }
//            if (members != null && members.size() > 2) {
//                b.user3.setVisibility(View.VISIBLE);
//                b.user3.setText(members.get(2));
//            }

            if (members != null && !members.isEmpty()) {
                FlexboxLayout container = b.memberContainer;  // ✅ Add this
                container.removeAllViews();

                for (String name : members) {
                    LinearLayout layout = new LinearLayout(requireContext());
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(20, 20, 20, 20);
                    layout.setGravity(Gravity.CENTER_HORIZONTAL);

                    ShapeableImageView img = new ShapeableImageView(requireContext());
                    LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(180, 180);
                    img.setLayoutParams(imgParams);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    img.setPadding(0, 0, 0, 0);
                    img.setShapeAppearanceModel(
                            img.getShapeAppearanceModel()
                                    .toBuilder()
                                    .setAllCornerSizes(90f)
                                    .build()
                    );

                    Glide.with(requireContext()).load(R.drawable.person4).into(img);

                    MaterialButton btn = new MaterialButton(requireContext());
                    LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(230, 90);
                    btn.setLayoutParams(btnParams);
                    btn.setText(name);
                    btn.setTextSize(10);
                    btn.setCornerRadius(5);
                    btn.setTextColor(getResources().getColor(android.R.color.white));

                    layout.addView(img);
                    layout.addView(btn);
                    container.addView(layout);
                }
            }

            Log.d("DEBUG_VIEWSESSION", "lat=" + lat + " lng=" + lng + " sessionId=" + sessionId);

        } else {
            Toast.makeText(getContext(), "Session data not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        setupUI();
        setupListeners();
    }

    private String formatTimeIfNeeded(String rawTime) {
        try {
            // If time contains a "T", it's in ISO format: "2025-04-16T02:19:00"
            if (rawTime.contains("T")) {
                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                Date date = isoFormat.parse(rawTime);
                return date != null ? displayFormat.format(date) : rawTime;
            }
        } catch (Exception e) {
            e.printStackTrace(); // fallback
        }
        return rawTime; // return as-is if not ISO or failed
    }

    private void setupUI() {
        b.sessionTitle.setText(title);
        b.sessionLocation.setText(location);
        b.timeCardText.setText(formatTimeIfNeeded(time));
        b.dateCardText.setText(formatDateVertically(date));
        b.statusCard.setText(status);

//        SessionManager sessionManager = SessionManager.getInstance(requireContext());
//        if (sessionManager.hasUserSwiped(sessionId)) {
//            b.swipeButton.setVisibility(View.GONE);
//        }

        // Populate done swiping list (dummy data for now)
        List<String> doneSwipingUsers = new ArrayList<>();
        doneSwipingUsers.add("ToniFoodie");
        doneSwipingUsers.add("ErikaFoody");
        doneSwipingUsers.add("JYFoodski");

        b.doneSwipingText.setText("Done swiping");
//        b.user1.setText(doneSwipingUsers.get(0));
//        b.user2.setText(doneSwipingUsers.get(1));
//        b.user3.setText(doneSwipingUsers.get(2));

        // Check session status
        if ("Done".equalsIgnoreCase(status)) {
            // Hide finalize, swipe, prompt, delete text
            b.finalizeButton.setVisibility(View.GONE);
            b.swipeButton.setVisibility(View.GONE);
            b.resultsPrompt.setVisibility(View.GONE);
            // b.deleteSessionText.setVisibility(View.GONE);
            b.editButton.setVisibility(View.GONE);

            b.leaderboardHeader.setVisibility(View.VISIBLE);
            // Show leaderboard immediately
            b.leaderboardLayout.setVisibility(View.VISIBLE);
            isSessionFinalized = true;

            fetchVotesBySessionId(sessionId);
        } else {
            // Still ongoing, animate the swipe button
            ObjectAnimator pulse = ObjectAnimator.ofFloat(b.swipeButton, "alpha", 1f, 0.5f, 1f);
            pulse.setDuration(1700);
            pulse.setRepeatMode(ValueAnimator.REVERSE);
            pulse.setRepeatCount(ValueAnimator.INFINITE);
            pulse.start();
        }
    }

    private void setupListeners() {
        b.backButton.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

//        b.editButton.setOnClickListener(v -> {
//            Bundle bundle = new Bundle();
//            bundle.putString("title", title);
//            bundle.putString("location", location);
//            bundle.putString("date", date);
//
//            if (!time.toUpperCase().contains("AM") && !time.toUpperCase().contains("PM")) {
//                time += " AM"; // or " PM" based on your logic if needed
//            }
//            bundle.putString("time", time);
//            bundle.putBoolean("isEdit", true);
//            bundle.putInt("sessionId", sessionId);
//            bundle.putInt("groupId", getArguments().getInt("groupId", -1));
//
//            if (b.sessionLocation.getTag(R.id.lat_tag) != null && b.sessionLocation.getTag(R.id.lng_tag) != null) {
//                bundle.putDouble("lat", (double) b.sessionLocation.getTag(R.id.lat_tag));
//                bundle.putDouble("lng", (double) b.sessionLocation.getTag(R.id.lng_tag));
//            }
//
//            PlanSessionFragment planSessionFragment = new PlanSessionFragment();
//            planSessionFragment.setArguments(bundle);
//
//            switchFragment(planSessionFragment);
//        });

        b.editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("location", location);
            bundle.putString("date", date);

            Object latTag = b.sessionLocation.getTag(R.id.lat_tag);
            Object lngTag = b.sessionLocation.getTag(R.id.lng_tag);

            if (latTag != null && lngTag != null) {
                bundle.putDouble("lat", (double) latTag);
                bundle.putDouble("lng", (double) lngTag);
            }

            // Handle time format (convert from 24h like "17:56:00" to "05:56 PM" if needed)
            try {
                if (!time.toUpperCase().contains("AM") && !time.toUpperCase().contains("PM")) {
                    SimpleDateFormat fromFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()); // 24-hour input
                    SimpleDateFormat toFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());    // 12-hour output
                    Date timeObj = fromFormat.parse(time);
                    if (timeObj != null) {
                        time = toFormat.format(timeObj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // Fallback: keep original
            }

            bundle.putString("time", time);
            bundle.putBoolean("isEdit", true);
            bundle.putInt("sessionId", sessionId);
            bundle.putInt("groupId", getArguments().getInt("groupId", -1));

            // Also include lat/lng if they exist
            if (b.sessionLocation.getTag(R.id.lat_tag) != null && b.sessionLocation.getTag(R.id.lng_tag) != null) {
                bundle.putDouble("lat", (double) b.sessionLocation.getTag(R.id.lat_tag));
                bundle.putDouble("lng", (double) b.sessionLocation.getTag(R.id.lng_tag));
            }

            PlanSessionFragment planSessionFragment = new PlanSessionFragment();
            planSessionFragment.setArguments(bundle);
            switchFragment(planSessionFragment);
        });

        b.swipeButton.setOnClickListener(v -> {

            swipeStarted = true;

            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("location", location);
            bundle.putString("date", date);
            bundle.putString("time", time);

            bundle.putDouble("lat", lat); // ✅ add this
            bundle.putDouble("lng", lng); // ✅ add this
            bundle.putInt("sessionId", sessionId); // ✅ add this


            SwipingFragment swipingFragment = new SwipingFragment();
            swipingFragment.setArguments(bundle);

            switchFragment(swipingFragment);
        });

        b.deleteSessionText.setOnClickListener(v -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Session")
                    .setMessage("Are you sure you want to delete this session?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        int sessionId = getArguments().getInt("sessionId", -1);
                        if (sessionId == -1) {
                            Toast.makeText(requireContext(), "Invalid session ID", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ApiClient.getApiService().deleteSession(sessionId).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(requireContext(), "✅ Session deleted", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(requireContext(), groupPage_Activity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    requireActivity().finish();
                                } else {
                                    Toast.makeText(requireContext(), "❌ Failed to delete session. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        b.finalizeButton.setOnClickListener(v -> {

            if (!swipeStarted) {
                Toast.makeText(requireContext(), "Please start swiping first!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (SwipingFragment.hasFinishedSwiping()) {
                int sessionId = getArguments().getInt("sessionId", -1);

                Log.d("API_DEBUG", "Calling PUT: sessions/session-completed/" + sessionId);

                // 🔁 Send finalize API call
                ApiClient.getApiService().markSessionCompleted(sessionId).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Log.d("FINALIZE", "Session marked as completed.");

                            // ✅ Start UI animations
                            Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                            b.finalizeButton.startAnimation(fadeOut);
                            b.swipeButton.startAnimation(fadeOut);
                            b.resultsPrompt.startAnimation(fadeOut);
                            b.deleteSessionText.startAnimation(fadeOut);

                            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {}

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    // 👋 Hide buttons
                                    b.finalizeButton.setVisibility(View.GONE);
                                    b.swipeButton.setVisibility(View.GONE);
                                    b.resultsPrompt.setVisibility(View.GONE);
                                    b.deleteSessionText.setVisibility(View.GONE);

                                    // ✅ Show leaderboard
                                    b.statusCard.setText("Done");
                                    Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                                    b.leaderboardLayout.setVisibility(View.VISIBLE);
                                    b.leaderboardLayout.startAnimation(fadeIn);

                                    isSessionFinalized = true;

                                    // 👇 Optional: call leaderboard population function
                                    fetchLeaderboard(sessionId, eateryIds, restaurantCards);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {}
                            });

                        } else {
                            Log.e("FINALIZE", "Failed to mark session complete: " + response.code());

                            try {
                                String errorMsg = response.errorBody() != null ? response.errorBody().string() : "No error body";
                                Log.e("FINALIZE", "Finalize failed: " + errorMsg);
                            } catch (Exception e) {
                                Log.e("FINALIZE", "Error parsing errorBody", e);
                            }

                            Toast.makeText(getContext(), "Finalizing failed. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("FINALIZE", "Error: " + t.getMessage());
                        Toast.makeText(getContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
            }
        });

//        b.finalizeButton.setOnClickListener(v -> {
//            if (SwipingFragment.hasFinishedSwiping()) {
//                // Animate hiding elements
//                Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
//                b.finalizeButton.startAnimation(fadeOut);
//                b.swipeButton.startAnimation(fadeOut);
//                b.resultsPrompt.startAnimation(fadeOut);
//                b.deleteSessionText.startAnimation(fadeOut);
//
//
//                // Actually hide them after animation
//                fadeOut.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {}
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//                        b.finalizeButton.setVisibility(View.GONE);
//                        b.swipeButton.setVisibility(View.GONE);
//                        b.resultsPrompt.setVisibility(View.GONE);
//                        b.deleteSessionText.setVisibility(View.GONE);
//
//                        // Change status
//                        b.statusCard.setText("Done");
//
//                        // Animate leaderboard in
//                        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
//                        b.leaderboardLayout.setVisibility(View.VISIBLE);
//                        b.leaderboardLayout.startAnimation(fadeIn);
//
//                        isSessionFinalized = true;
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {}
//                });
//            } else {
//                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
//            }
//        });

        //LAST ONE
//        b.finalizeButton.setOnClickListener(v -> {
//            if (!SwipingFragment.hasFinishedSwiping()) {
//                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            if (sessionId == -1) {
//                Toast.makeText(requireContext(), "Invalid session ID!", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            ApiClient.getApiService().markSessionCompleted(sessionId).enqueue(new Callback<Void>() {
//                @Override
//                public void onResponse(Call<Void> call, Response<Void> response) {
//                    if (response.isSuccessful()) {
//                        // ✅ Backend marked session as completed — now run animation
//                        runFinalizationAnimation();
//                    } else {
//                        Toast.makeText(requireContext(), "Failed to finalize session on server.", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Void> call, Throwable t) {
//                    Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        });


//        b.finalizeButton.setOnClickListener(v -> {
//            // Only navigate to SessionSummaryFragment if the user has finished swiping
//            if (SwipingFragment.hasFinishedSwiping()) {
//                // Prepare the session data to be passed to SessionSummaryFragment
//                Bundle bundle = new Bundle();
//                bundle.putString("title", title);  // Get the data from the session
//                bundle.putString("location", location);
//                bundle.putString("date", date);
//                bundle.putString("time", time);
//
//                // Create the SessionSummaryFragment and pass the session data
//                SessionSummaryFragment summaryFragment = new SessionSummaryFragment();
//                summaryFragment.setArguments(bundle);
//
//                // Start the transaction for navigating to SessionSummaryFragment
//                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
//
//                // Replace the current fragment with the SummaryFragment
//                transaction.replace(R.id.fragment_view, summaryFragment)
//                        .addToBackStack(null)  // Add it to the back stack so the user can go back
//                        .commit();
//            } else {
//                // Optionally, show a message or toast if the user hasn't finished swiping yet
//                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
//            }
//        });


        // MOST RECENT
//        b.finalizeButton.setOnClickListener(v -> {
//            if (SwipingFragment.hasFinishedSwiping()) {
//                // Extract data directly from the views
//                String title = b.sessionTitle.getText().toString().trim();
//                String location = b.sessionLocation.getText().toString().trim();
//                String date = b.dateCardText.getText().toString().trim();
//                String time = b.timeCardText.getText().toString().trim();
//
//                // Check if fields are not empty (already validated before)
//                if (!title.isEmpty() && !location.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
//                    // Directly go to SessionSummaryFragment without Bundle
//                    SessionSummaryFragment summaryFragment = new SessionSummaryFragment();
//
//                    // Navigate directly to the summary fragment
//                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                    transaction.setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
//                    transaction.replace(R.id.fragment_view, summaryFragment)
//                            .addToBackStack(null)  // Add to back stack for back navigation
//                            .commit();
//                } else {
//                    Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
//            }
//        });

        // finalize button, COMPLICATED
//        b.finalizeButton.setOnClickListener(v -> {
//            // Ensure that the user has finished swiping before proceeding
//            if (SwipingFragment.hasFinishedSwiping()) {
//                // Navigate directly to SessionSummaryFragment without passing any data
//                SessionSummaryFragment summaryFragment = new SessionSummaryFragment();
//
//                // Start the transaction to navigate to SessionSummaryFragment
//                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                transaction.setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
//                transaction.replace(R.id.fragment_view, summaryFragment)
//                        .addToBackStack(null)  // Add to back stack for back navigation
//                        .commit();
//            } else {
//                // Optionally show a message if the user hasn't finished swiping yet
//                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void runFinalizationAnimation() {
        Animation fadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        b.finalizeButton.startAnimation(fadeOut);
        b.swipeButton.startAnimation(fadeOut);
        b.resultsPrompt.startAnimation(fadeOut);
        b.deleteSessionText.startAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                b.finalizeButton.setVisibility(View.GONE);
                b.swipeButton.setVisibility(View.GONE);
                b.resultsPrompt.setVisibility(View.GONE);
                b.deleteSessionText.setVisibility(View.GONE);

                b.statusCard.setText("Done");

                Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                b.leaderboardLayout.setVisibility(View.VISIBLE);
                b.leaderboardLayout.startAnimation(fadeIn);

                isSessionFinalized = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private void switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
        transaction.replace(R.id.fragment_view, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private String formatDateVertically(String inputDate) {
        // Format "MM/DD/YYYY" → "DD\nMMM\nYYYY"
        String[] parts = inputDate.split("/");
        if (parts.length == 3) {
            String month = getMonthAbbreviation(Integer.parseInt(parts[0]));
            return String.format("%s\n%s\n%s", parts[1], month, parts[2]);
        }
        return inputDate;
    }

    private String getMonthAbbreviation(int monthNumber) {
        String[] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN",
                "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        return (monthNumber >= 1 && monthNumber <= 12) ? months[monthNumber - 1] : "???";
    }

    private void showFinishedUsers(List<String> usernames) {
        b.memberContainer.removeAllViews(); // reuse container
        for (String name : usernames) {
            LinearLayout layout = new LinearLayout(requireContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 20, 20, 20);
            layout.setGravity(Gravity.CENTER_HORIZONTAL);

            ShapeableImageView img = new ShapeableImageView(requireContext());
            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(180, 180);
            img.setLayoutParams(imgParams);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setShapeAppearanceModel(
                    img.getShapeAppearanceModel().toBuilder().setAllCornerSizes(90f).build()
            );
            Glide.with(requireContext()).load(R.drawable.person4).into(img);

            MaterialButton btn = new MaterialButton(requireContext());
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(230, 90);
            btn.setLayoutParams(btnParams);
            btn.setText(name);
            btn.setTextSize(10);
            btn.setCornerRadius(5);
            btn.setTextColor(getResources().getColor(android.R.color.white));

            layout.addView(img);
            layout.addView(btn);
            b.memberContainer.addView(layout);
        }
    }


    private void fetchFinishedUsers(int sessionId) {
        Log.d("FINISHED_USERS", "🔍 Starting fetchFinishedUsers() for sessionId: " + sessionId);

        ApiClient.getApiService().getFinishedUsers(sessionId).enqueue(new Callback<List<usersDTO>>() {
            @Override
            public void onResponse(Call<List<usersDTO>> call, Response<List<usersDTO>> response) {
                Log.d("FINISHED_USERS", "📡 Received response: " + response.code());

                if (response.isSuccessful() && response.body() != null) {
                    List<usersDTO> usersList = response.body();
                    Log.d("FINISHED_USERS", "✅ Success. Number of users: " + usersList.size());

                    List<String> usernames = new ArrayList<>();
                    for (usersDTO user : usersList) {
                        usernames.add(user.getUsername());
                        Log.d("FINISHED_USERS", "➡️ User added: " + user.getUsername());
                    }

                    if (usernames.isEmpty()) {
                        Log.d("FINISHED_USERS", "⚠️ No users found. Hiding memberContainer.");
                        b.memberContainer.setVisibility(View.GONE);
                    } else {
                        Log.d("FINISHED_USERS", "🎉 Showing finished users.");
                        b.memberContainer.setVisibility(View.VISIBLE);
                        showFinishedUsers(usernames);
                    }

                } else {
                    Log.e("FINISHED_USERS", "❌ Failed or empty response. Code: " + response.code());
                    b.memberContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<usersDTO>> call, Throwable t) {
                Log.e("FINISHED_USERS", "🚨 API error: " + t.getMessage(), t);
                b.memberContainer.setVisibility(View.GONE);
            }
        });
    }

    // for checking if user finished swiping or not, swipe button will disappear based on that
    private void checkIfUserFinishedSwiping() {
        int sessionId = getArguments().getInt("sessionId", -1);
        int currentUserId = SessionManager.getInstance(requireContext()).getCurrentUser().getUserId();

        ApiClient.getApiService().getFinishedUsers(sessionId).enqueue(new Callback<List<usersDTO>>() {
            @Override
            public void onResponse(Call<List<usersDTO>> call, Response<List<usersDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    boolean userHasFinished = false;
                    for (usersDTO user : response.body()) {
                        if (user.getUserId() == currentUserId) {
                            userHasFinished = true;
                            break;
                        }
                    }

                    if (userHasFinished) {
                        b.swipeButton.setVisibility(View.GONE);
                        Log.d("SWIPE_CHECK", "User finished swiping. Hiding button.");
                    } else {
                        b.swipeButton.setVisibility(View.VISIBLE);
                        Log.d("SWIPE_CHECK", "User still needs to swipe. Showing button.");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<usersDTO>> call, Throwable t) {
                Log.e("SWIPE_CHECK", "API error checking finished swipes", t);
            }
        });
    }


    // for displaying leaderboard after re-entering session
    private void fetchVotesBySessionId(int sessionId) {
        ApiClient.getApiService().getUsersVotesBySessionId(sessionId).enqueue(new Callback<List<userVoteDTO>>() {
            @Override
            public void onResponse(Call<List<userVoteDTO>> call, Response<List<userVoteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("LEADERBOARD_REGEN", "✅ Votes received: " + response.body().size());

                    // Count likes per eateryId
                    Map<String, Integer> voteMap = new HashMap<>();
                    for (userVoteDTO vote : response.body()) {
                        if (vote.isLiked()) {
                            String eateryId = vote.getEateryId();
                            voteMap.put(eateryId, voteMap.getOrDefault(eateryId, 0) + 1);
                        }
                    }

                    // Proceed to show leaderboard (uses existing method)
                    showLeaderboardWithImages(voteMap);
                } else {
                    Log.e("LEADERBOARD_REGEN", "❌ Failed to fetch votes. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<userVoteDTO>> call, Throwable t) {
                Log.e("LEADERBOARD_REGEN", "🚨 Error fetching votes", t);
            }
        });
    }

    private void fetchLeaderboard(int sessionId, List<String> eateryIds, List<RestaurantCard> cards) {
        ApiService apiService = ApiClient.getApiService();

        if (eateryIds == null || eateryIds.isEmpty()) return;

        Map<String, Integer> leaderboardMap = new HashMap<>();
        int totalRequests = eateryIds.size();
        int[] completedCount = {0}; // mutable counter

        for (String eateryId : eateryIds) {
            apiService.getUsersVotesByEateryId(eateryId).enqueue(new Callback<List<userVoteDTO>>() {
                @Override
                public void onResponse(Call<List<userVoteDTO>> call, Response<List<userVoteDTO>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        int likeCount = 0;
                        for (userVoteDTO vote : response.body()) {
                            if (vote.isLiked()) likeCount++;
                        }
                        leaderboardMap.put(eateryId, likeCount);
                    } else {
                        leaderboardMap.put(eateryId, 0); // fallback
                    }

                    completedCount[0]++;
                    if (completedCount[0] == totalRequests) {
                        showLeaderboardWithImages(leaderboardMap); // ✅ call when all done
                    }
                }

                @Override
                public void onFailure(Call<List<userVoteDTO>> call, Throwable t) {
                    leaderboardMap.put(eateryId, 0); // fallback
                    Log.e("LEADERBOARD", "Failed for eateryId=" + eateryId, t);

                    completedCount[0]++;
                    if (completedCount[0] == totalRequests) {
                        showLeaderboardWithImages(leaderboardMap); // ✅ call when all done
                    }
                }
            });
        }
    }

//    private void showLeaderboard(Map<String, Integer> voteMap, List<RestaurantCard> cards) {
//
//        Log.d("LEADERBOARD_DEBUG", "voteMap size: " + voteMap.size());
//        Log.d("LEADERBOARD_DEBUG", "cards size: " + (cards != null ? cards.size() : "null"));
//
//        // Sort vote map by descending likes
//        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(voteMap.entrySet());
//        sorted.sort((a, b) -> b.getValue() - a.getValue());
//
//        b.leaderboardLayout.removeAllViews(); // clear previous
//
//        for (Map.Entry<String, Integer> entry : sorted) {
//            String eateryId = entry.getKey();
//            int likes = entry.getValue();
//
//            // 🔍 Find the restaurant by eateryId
//            String name = "Unknown";
//            for (RestaurantCard card : cards) {
//                if (card.getEateryId().equals(eateryId)) {
//                    name = card.getName();
//                    break;
//                }
//            }
//
//            TextView row = new TextView(getContext());
//            row.setText(name + " — " + likes + " likes");
//            row.setTextSize(16f);
//            row.setPadding(16, 8, 16, 8);
//            row.setTypeface(Typeface.DEFAULT_BOLD);
//
//            b.leaderboardLayout.addView(row);
//        }
//    }

    private void showLeaderboardWithImages(Map<String, Integer> voteMap) {
        ApiService apiService = ApiClient.getApiService();

        // Step 1: Sort voteMap by likes (descending)
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(voteMap.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        // Step 2: Clear previous views
        b.leaderboardLayout.removeAllViews();

        // Step 3: For each eateryId, fetch image and display a card
        for (Map.Entry<String, Integer> entry : sorted) {
            String eateryId = entry.getKey();
            int likeCount = entry.getValue();

            // Fetch image
            apiService.getEateryImages(eateryId).enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    Bitmap bitmap = null;

                    if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                        String base64 = response.body().get(0); // Take first image
                        byte[] imageBytes = Base64.decode(base64, Base64.DEFAULT);
                        bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    }

                    addLeaderboardCard(eateryId, likeCount, bitmap, voteMap, restaurantCards);
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    Log.e("LEADERBOARD_IMG_FAIL", "Failed to load image for " + eateryId);
                    addLeaderboardCard(eateryId, likeCount, null, voteMap, restaurantCards);
                }
            });
        }
    }

    private void addLeaderboardCard(String eateryId, int likeCount, @Nullable Bitmap image, Map<String, Integer> voteMap, List<RestaurantCard> cards) {

        Log.d("LEADERBOARD_DEBUG", "voteMap size: " + voteMap.size());
        Log.d("LEADERBOARD_DEBUG", "cards size: " + cards.size());

        // Sort by votes descending
        List<Map.Entry<String, Integer>> sorted = new ArrayList<>(voteMap.entrySet());
        sorted.sort((a, b) -> b.getValue() - a.getValue());

        b.leaderboardLayout.removeAllViews(); // Clear previous

        for (Map.Entry<String, Integer> entry : sorted) {
            String currentEateryId = entry.getKey();
            int likes = entry.getValue();

            // 🔍 Find the restaurant card
            RestaurantCard matchingCard = null;
            for (RestaurantCard card : cards) {
                if (card.getEateryId().equals(currentEateryId)) {
                    matchingCard = card;
                    break;
                }
            }

            // Skip if not found
            if (matchingCard == null) continue;

            // Create vertical layout
            LinearLayout cardLayout = new LinearLayout(requireContext());
            cardLayout.setOrientation(LinearLayout.VERTICAL);
            cardLayout.setPadding(32, 32, 32, 32);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 48); // space between cards
            cardLayout.setLayoutParams(cardParams);
            cardLayout.setBackgroundColor(Color.parseColor("#FAF8F8"));

            // Restaurant name
            TextView nameText = new TextView(requireContext());
            nameText.setText(matchingCard.getName());
            nameText.setTextSize(18f);
            nameText.setTypeface(Typeface.DEFAULT_BOLD);
            nameText.setGravity(Gravity.CENTER_HORIZONTAL);

            // Cuisine + price
            TextView detailsText = new TextView(requireContext());

            String rawCuisine = matchingCard.getCuisine(); // e.g., "[fast_food_restaurant]"
            String cleanedCuisine = "";
            if (rawCuisine != null && !rawCuisine.isEmpty()) {
                cleanedCuisine = rawCuisine
                        .replaceAll("\\[|\\]", "")  // remove [ and ]
                        .replace("_", " ")          // replace underscores
                        .trim();

                // Capitalize first letter
                if (!cleanedCuisine.isEmpty()) {
                    cleanedCuisine = cleanedCuisine.substring(0, 1).toUpperCase() + cleanedCuisine.substring(1);
                }
            }

            detailsText.setText(cleanedCuisine + " • " + getPriceString(matchingCard.getPriceLevel()));

            //detailsText.setText(matchingCard.getCuisine() + " • " + getPriceString(matchingCard.getPriceLevel()));
            detailsText.setTextSize(14f);
            detailsText.setGravity(Gravity.CENTER_HORIZONTAL);

            // Likes
            TextView likesText = new TextView(requireContext());
            likesText.setText("❤️ " + likes + " likes");
            likesText.setTextSize(16f);
            likesText.setTextColor(Color.RED);
            likesText.setGravity(Gravity.CENTER_HORIZONTAL);

            // WORKS!!
            // Image
//            ImageView imageView = new ImageView(requireContext());
//            imageView.setImageBitmap(matchingCard.getImage());
//            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, 700);
//            imgParams.setMargins(0, 24, 0, 0);
//            imageView.setLayoutParams(imgParams);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            ShapeableImageView imageView = new ShapeableImageView(requireContext());
            imageView.setImageBitmap(matchingCard.getImage());

            imageView.setBackgroundColor(Color.parseColor("#FAF8F8"));

            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 1200); // more height
            imgParams.setMargins(0, 24, 0, 0);
            imageView.setLayoutParams(imgParams);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

// Rounded corners — 40f is nice and soft
            imageView.setShapeAppearanceModel(
                    imageView.getShapeAppearanceModel()
                            .toBuilder()
                            .setAllCornerSizes(40f)
                            .build()
            );



            //  ========
//            ShapeableImageView imageView = new ShapeableImageView(requireContext());
//            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, 400);  // increase height
//            imgParams.setMargins(0, 24, 0, 0);
//            imageView.setLayoutParams(imgParams);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            // Set rounded corners
//            imageView.setShapeAppearanceModel(
//                    imageView.getShapeAppearanceModel()
//                            .toBuilder()
//                            .setAllCornerSizes(40f) // more rounded
//                            .build()
//            );
//
//            if (image != null) {
//                imageView.setImageBitmap(image);
//            } else {
//                imageView.setImageResource(R.drawable.placeholder); // fallback image
//            }

//            // Add Image
//            ShapeableImageView imageView = new ShapeableImageView(requireContext());
//            LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(400, 700);
//            imageView.setLayoutParams(imgParams);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageView.setPadding(0, 0, 0, 20);
//            imageView.setShapeAppearanceModel(
//                    imageView.getShapeAppearanceModel().toBuilder().setAllCornerSizes(60f).build()
//            );
//            if (image != null) {
//                imageView.setImageBitmap(image);
//            } else {
//                imageView.setImageResource(R.drawable.placeholder); // fallback image
//            }

            // Add everything to layout
            cardLayout.addView(nameText);
            cardLayout.addView(detailsText);
            cardLayout.addView(likesText);
            cardLayout.addView(imageView);

            // Add to leaderboard
            b.leaderboardLayout.addView(cardLayout);


//        // Create the container
//        LinearLayout cardLayout = new LinearLayout(requireContext());
//        cardLayout.setOrientation(LinearLayout.VERTICAL);
//        cardLayout.setPadding(20, 20, 20, 20);
//        cardLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//
//        // Add Image
//        ShapeableImageView imageView = new ShapeableImageView(requireContext());
//        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(350, 350);
//        imageView.setLayoutParams(imgParams);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setPadding(0, 0, 0, 20);
//        imageView.setShapeAppearanceModel(
//                imageView.getShapeAppearanceModel().toBuilder().setAllCornerSizes(60f).build()
//        );
//        if (image != null) {
//            imageView.setImageBitmap(image);
//        } else {
//            imageView.setImageResource(R.drawable.placeholder); // fallback image
//        }
//
//        // Add title
//        TextView titleView = new TextView(requireContext());
//        titleView.setText("ID: " + eateryId); // Optional: Replace with actual name if needed
//        titleView.setTypeface(Typeface.DEFAULT_BOLD);
//        titleView.setTextSize(16f);
//        titleView.setGravity(Gravity.CENTER);
//
//        // Add vote count
//        TextView votesView = new TextView(requireContext());
//        votesView.setText(likeCount + " likes");
//        votesView.setTextSize(14f);
//        votesView.setGravity(Gravity.CENTER);
//
//        // Add views to layout
//        cardLayout.addView(imageView);
//        cardLayout.addView(titleView);
//        cardLayout.addView(votesView);
//
//        // Add to leaderboard layout
//        b.leaderboardLayout.addView(cardLayout);
        }
    }

    private String getPriceString(String rawPrice) {
        switch (rawPrice) {
            case "PRICE_LEVEL_FREE":
                return "FREE!";
            case "PRICE_LEVEL_INEXPENSIVE":
                return "$";
            case "PRICE_LEVEL_MODERATE":
                return "$$";
            case "PRICE_LEVEL_EXPENSIVE":
                return "$$$";
            case "PRICE_LEVEL_VERY_EXPENSIVE":
                return "$$$$";
            default:
                return ""; // unknown or unspecified
        }
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap, float cornerRadius) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }


}