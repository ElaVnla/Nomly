package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.groupPage_Activity;
import com.w3itexperts.ombe.databinding.FragmentViewSessionBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewSessionFragment extends Fragment {

    FragmentViewSessionBinding b;


    private double lat = 0.0;
    private double lng = 0.0;
    private int sessionId = -1;
    private boolean isSessionFinalized = false;
    private String title, location, date, time, status;

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

        // Get session info from arguments
        Bundle args = getArguments();
        if (args != null) {

            Log.d("DEBUG_ARGS_BUNDLE", args.toString());

            title = args.getString("title", "Untitled");
            location = args.getString("location", "Unknown");
            date = args.getString("date", "N/A");
            time = args.getString("time", "N/A");
            status = args.getString("status", "Ongoing");
            lat = args.getDouble("lat", 0.0);
            lng = args.getDouble("lng", 0.0);

            Log.d("DEBUG_VIEWSESSION", "lat=" + lat + " lng=" + lng + " sessionId=" + sessionId);

        } else {
            Toast.makeText(getContext(), "Session data not found.", Toast.LENGTH_SHORT).show();
            return;
        }

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        b.sessionTitle.setText(title);
        b.sessionLocation.setText(location);
        b.timeCardText.setText(time);
        b.dateCardText.setText(formatDateVertically(date));
        b.statusCard.setText(status);

        // Populate done swiping list (dummy data for now)
        List<String> doneSwipingUsers = new ArrayList<>();
        doneSwipingUsers.add("ToniFoodie");
        doneSwipingUsers.add("ErikaFoody");
        doneSwipingUsers.add("JYFoodski");

        b.doneSwipingText.setText("Done swiping");
        b.user1.setText(doneSwipingUsers.get(0));
        b.user2.setText(doneSwipingUsers.get(1));
        b.user3.setText(doneSwipingUsers.get(2));

        // Check session status
        if ("Done".equalsIgnoreCase(status)) {
            // Hide finalize, swipe, prompt, delete text
            b.finalizeButton.setVisibility(View.GONE);
            b.swipeButton.setVisibility(View.GONE);
            b.resultsPrompt.setVisibility(View.GONE);
            b.deleteSessionText.setVisibility(View.GONE);
            b.editButton.setVisibility(View.GONE);

            // Show leaderboard immediately
            b.leaderboardLayout.setVisibility(View.VISIBLE);
            isSessionFinalized = true;
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
            Intent intent = new Intent(requireContext(), groupPage_Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            requireActivity().finish(); // 🚪 Finish the current activity
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


        b.finalizeButton.setOnClickListener(v -> {
            if (!SwipingFragment.hasFinishedSwiping()) {
                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (sessionId == -1) {
                Toast.makeText(requireContext(), "Invalid session ID!", Toast.LENGTH_SHORT).show();
                return;
            }

            ApiClient.getApiService().markSessionCompleted(sessionId).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // ✅ Backend marked session as completed — now run animation
                        runFinalizationAnimation();
                    } else {
                        Toast.makeText(requireContext(), "Failed to finalize session on server.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


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


}