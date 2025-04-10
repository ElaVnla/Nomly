package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

        // Retrieve current user from SessionManager.
        final users storedUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (storedUser == null) {
            Log.e("API_SESSIONS", "No current user found in session!");
            logoutUser();
            return;
        }

        // Refresh the user data from the API to get the latest details.
        ApiService apiService = ApiClient.getApiService();
        apiService.getUser(storedUser.getUserId()).enqueue(new Callback<users>() {
            @Override
            public void onResponse(Call<users> call, Response<users> response) {
                users refreshedUser;
                if (response.isSuccessful() && response.body() != null) {
                    refreshedUser = response.body();
                    // Update SessionManager with the new data.
                    SessionManager.getInstance(getContext()).setCurrentUser(refreshedUser);
                } else {
                    Log.e("API_SESSIONS", "getUser error: " + response.code());
                    refreshedUser = storedUser;
                }
                updateUI(refreshedUser);
            }

            @Override
            public void onFailure(Call<users> call, Throwable t) {
                Log.e("API_SESSIONS", "API call failed: " + t.getMessage());
                updateUI(storedUser);
            }
        });

        // Set up search functionality to filter the sessions list.
        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = b.searchEditText.getText().toString().trim().toLowerCase();
                Log.d("SEARCH_SESSIONS", "Search query: '" + query + "'");
                // If query is empty, reset the adapter to show the full list.
                if (query.isEmpty()) {
                    allSessionsAdapter adapter = new allSessionsAdapter(fullSessionList);
                    b.allsessionsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    b.allsessionsView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    return;
                }
                List<yourSessionsModal> filteredList = new ArrayList<>();
                // Filter by checking if any key field contains the query.
                for (yourSessionsModal modal : fullSessionList) {
                    if (modal.getRestaurantName() != null &&
                            modal.getRestaurantName().trim().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    } else if (modal.getGroupName() != null &&
                            modal.getGroupName().trim().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    } else if (modal.getDateTimeAddress() != null &&
                            modal.getDateTimeAddress().trim().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    } else if (modal.getSessionTitle() != null &&
                            modal.getSessionTitle().trim().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    }
                }
                Log.d("SEARCH_SESSIONS", "Filtered list size: " + filteredList.size());
                // Update the adapter with the filtered list.
                allSessionsAdapter filteredAdapter = new allSessionsAdapter(filteredList);
                b.allsessionsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                b.allsessionsView.setAdapter(filteredAdapter);
                filteredAdapter.notifyDataSetChanged();
            }
        });
    }

    private void updateUI(users user) {
        // Aggregate sessions from all groups belonging to the user.
        List<groupings> groupsList = user.getGroups();
        List<yourSessionsModal> sessionModalList = new ArrayList<>();
        if (groupsList != null) {
            for (groupings grp : groupsList) {
                if (grp.getSessions() != null) {
                    for (sessions sess : grp.getSessions()) {
                        // Map fields from the session object.
                        String restaurantName = sess.getLocation();           // Using location as "restaurant name"
                        String dateTimeAddress = sess.getMeetingDateTime();     // Using meetingDateTime as a string
                        String groupName = grp.getGroupName();                  // Parent group name
                        String sessionStatus = sess.isCompleted() ? "Completed" : "Upcoming";
                        String sessionTitle = "Session " + sess.getSessionId(); // Dummy title; adjust if needed

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
        Log.d("API_SESSIONS", "Displayed sessions count: " + sessionModalList.size());
    }

    private void logoutUser() {
        SessionManager.getInstance(getContext()).setCurrentUser(null);
        Intent intent = new Intent(getContext(), Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}
