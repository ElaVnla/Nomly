package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentViewSessionBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ViewSessionFragment extends Fragment {

    FragmentViewSessionBinding b;
    private String title, location, date, time;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentViewSessionBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get session info from arguments
        Bundle args = getArguments();
        if (args != null) {
            title = args.getString("title", "Untitled");
            location = args.getString("location", "Unknown");
            date = args.getString("date", "N/A");
            time = args.getString("time", "N/A");
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
        b.statusCard.setText("Ongoing");

        // Populate done swiping list (dummy data for now)
        List<String> doneSwipingUsers = new ArrayList<>();
        doneSwipingUsers.add("ToniFoodie");
        doneSwipingUsers.add("ErikaFoody");
        doneSwipingUsers.add("JYFoodski");

        b.doneSwipingText.setText("Done swiping");
        b.user1.setText(doneSwipingUsers.get(0));
        b.user2.setText(doneSwipingUsers.get(1));
        b.user3.setText(doneSwipingUsers.get(2));
    }

    private void setupListeners() {
        b.backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        b.editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", title);
            bundle.putString("location", location);
            bundle.putString("date", date);
            bundle.putString("time", time);

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

            SwipingFragment swipingFragment = new SwipingFragment();
            swipingFragment.setArguments(bundle);

            switchFragment(swipingFragment);
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
        b.finalizeButton.setOnClickListener(v -> {
            // Ensure that the user has finished swiping before proceeding
            if (SwipingFragment.hasFinishedSwiping()) {
                // Navigate directly to SessionSummaryFragment without passing any data
                SessionSummaryFragment summaryFragment = new SessionSummaryFragment();

                // Start the transaction to navigate to SessionSummaryFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
                transaction.replace(R.id.fragment_view, summaryFragment)
                        .addToBackStack(null)  // Add to back stack for back navigation
                        .commit();
            } else {
                // Optionally show a message if the user hasn't finished swiping yet
                Toast.makeText(requireContext(), "Please finish swiping before finalizing.", Toast.LENGTH_SHORT).show();
            }
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