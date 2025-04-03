package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentViewSessionBinding;

import java.util.ArrayList;
import java.util.List;

public class SessionSummaryFragment extends Fragment {

    private FragmentViewSessionBinding b;
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

        // Set default data or data you wish to display
        String defaultTitle = "Session Title";
        String defaultLocation = "Session Location";
        String defaultDate = "01/01/2025";
        String defaultTime = "12:00 PM";

        // Call setupUI to display data in the UI
        setupUI(defaultTitle, defaultLocation, defaultDate, defaultTime);
    }
    private void setupUI(String title, String location, String date, String time) {
        b.sessionTitle.setText(title);
        b.sessionLocation.setText(location);
        b.timeCardText.setText(time);
        b.dateCardText.setText(formatDateVertically(date));
        b.statusCard.setText("Completed");  // Change status to "Completed"

        // Populate done swiping list (dummy data for now)
        List<String> doneSwipingUsers = new ArrayList<>();
        doneSwipingUsers.add("ToniFoodie");
        doneSwipingUsers.add("ErikaFoody");
        doneSwipingUsers.add("JYFoodski");

        b.doneSwipingText.setText("Done swiping");
        b.user1.setText(doneSwipingUsers.get(0));
        b.user2.setText(doneSwipingUsers.get(1));
        b.user3.setText(doneSwipingUsers.get(2));

        // Add the leaderboard (most swiped card) section
        String leaderboardTitle = "Lola’s Cafe";  // This would be dynamically set based on the most swiped card
        String leaderboardLocation = "Tampines Mall";
        String leaderboardPrice = "$";  // Modify this if you want to show more specific price data

//        b.leaderboardTitle.setText(leaderboardTitle);
//        b.leaderboardLocation.setText(leaderboardLocation);
//        b.leaderboardPrice.setText(leaderboardPrice);
//
//        // Use the item card layout for the leaderboard
//        b.cardImage.setImageResource(R.drawable.example_image);  // Replace with the actual image for the most swiped card
//        b.cardTitle.setText(leaderboardTitle);
//        b.cardLocation.setText(leaderboardLocation);
//        b.cardPrice.setText(leaderboardPrice);
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
