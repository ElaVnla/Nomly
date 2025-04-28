package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.activity.Welcome;
import com.w3itexperts.ombe.activity.login_signin_Activity;
import com.w3itexperts.ombe.adapter.allSessionsAdapter;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentAllsessionsBinding;
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// EXTERNAL SOURCES: Stackoverflow - how to call retrofit api, how to pass, chatgpt debug when data not passing
// https://square.github.io/retrofit/
public class allSessions extends Fragment {

    private FragmentAllsessionsBinding b;
    // Hold the complete sessions list for search/filtering.
    private List<yourSessionsModal> fullSessionList = new ArrayList<>();
    private allSessionsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentAllsessionsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SessionAPISetup();

        // Set up search functionality to filter the sessions list.
        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchsess = b.searchEditText.getText().toString().trim().toLowerCase();
                Log.d("NOMLYPROCESS", "Search sess: '" + searchsess + "'");
                // If query is empty, reset the adapter to show the full list
                if (searchsess.isEmpty()) {
                    allSessionsAdapter adapter = new allSessionsAdapter(fullSessionList);
                    b.allsessionsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    b.allsessionsView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return;
                }
                List<yourSessionsModal> filteredList = new ArrayList<>();
                // Filter by checking if any key field contains the query ===================
                for (yourSessionsModal modal : fullSessionList) {
                    if (modal.getRestaurantName() != null &&
                            modal.getRestaurantName().trim().toLowerCase().contains(searchsess)) {
                        filteredList.add(modal);
                    } else if (modal.getGroupName() != null &&
                            modal.getGroupName().trim().toLowerCase().contains(searchsess)) {
                        filteredList.add(modal);
                    } else if (modal.getDateTimeAddress() != null &&
                            modal.getDateTimeAddress().trim().toLowerCase().contains(searchsess)) {
                        filteredList.add(modal);
                    } else if (modal.getSessionTitle() != null &&
                            modal.getSessionTitle().trim().toLowerCase().contains(searchsess)) {
                        filteredList.add(modal);
                    }
                }
                Log.d("NOMLYPROCESS", "Filtered list size: " + filteredList.size());
                // Update the adapter with the filtered list.
                allSessionsAdapter filteredAdapter = new allSessionsAdapter(filteredList);
                b.allsessionsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                b.allsessionsView.setAdapter(filteredAdapter);
                filteredAdapter.notifyDataSetChanged();
            }
        });
    }

    private void SessionAPISetup()
    {
        try {
            // Retrieve current user from SessionManager.
            final users CurrentlyLoggedUser = SessionManager.getInstance(getContext()).getCurrentUser();
            if (CurrentlyLoggedUser == null) {
                Log.e("NOMLYPROCESS", "No current user found in session!");
                logoutUser();
                return;
            }

            // Refresh the user data from the API to get the latest details.
            ApiService apiService = ApiClient.getApiService();
            apiService.getUser(CurrentlyLoggedUser.getUserId()).enqueue(new Callback<users>() {
                @Override
                public void onResponse(Call<users> call, Response<users> response) {
                    users UserFound;
                    if (response.isSuccessful() && response.body() != null) {
                        UserFound = response.body();
                        // Update SessionManager with the new data
                        SessionManager.getInstance(getContext()).setCurrentUser(UserFound);
                    } else {
                        Log.e("NOMLYPROCESS", "getUser error: " + response.code());
                        UserFound = CurrentlyLoggedUser;
                    }
                    updateUI(UserFound);
                }

                @Override
                public void onFailure(Call<users> call, Throwable t) {
                    Log.e("NOMLYPROCESS", "API call failed: " + t.getMessage());
                    updateUI(CurrentlyLoggedUser);
                }
            });
        }
        catch (Exception e) {
            Log.e("NOMLYPROCESS", "ERROR: When using getUser API - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(users user) {
        // Aggregate sessions from all groups belonging to the user.
        List<groupings> groupsList = user.getGroups();
        List<yourSessionsModal> sessionModalList = new ArrayList<>();
        if (groupsList != null) {
            for (groupings grp : groupsList) {
                if (grp.getSessions() != null) {
                    for (sessions sess : grp.getSessions()) {
                        // store session object info stuff here
                        String restaurantName = sess.getLocation();
                        String dateTimeAddress = FormatDateTimeString(sess.getMeetingDateTime());
                        String groupName = grp.getGroupName();
                        String sessionStatus = sess.isCompleted() ? "Completed" : "Upcoming";
                        String sessionTitle = "Session @" + sess.getLocation();

                        yourSessionsModal modal = new yourSessionsModal(
                                restaurantName,
                                dateTimeAddress,
                                groupName,
                                sessionStatus,
                                sessionTitle
                        );
                        sessionModalList.add(modal);
                    }
                }
            }
        }
        // Update fullSessionList for filtering.
        fullSessionList = sessionModalList;
        // Set up the adapter with the fresh data.
        adapter = new allSessionsAdapter(sessionModalList);
        b.allsessionsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        b.allsessionsView.setAdapter(adapter);
        Log.d("NOMLYPROCESS", "Displayed sessions count: " + sessionModalList.size());
    }

    private void logoutUser() {
        SessionManager.getInstance(getContext()).setCurrentUser(null);
        Intent intent = new Intent(getContext(), Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }


    // generate by chatgpt, edit by erika, jingyu
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
            // Return the original string if parsing fails
            return jsonDateString;
        }
    }

}