package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import com.w3itexperts.ombe.adapter.allGroupsAdapter;
import com.w3itexperts.ombe.adapter.allSessionsAdapter;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentAllgroupsBinding;
import com.w3itexperts.ombe.databinding.FragmentAllsessionsBinding;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class allSessions extends Fragment {

    FragmentAllsessionsBinding b;

    private allSessionsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAllsessionsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // List to hold all sessions for filtering purposes.
        final List<yourSessionsModal> fullSessionList = new ArrayList<>();
        // We'll keep a reference to the current adapter so we can update it later if needed.
        final allSessionsAdapter[] adapterHolder = new allSessionsAdapter[1];

        // Make the API call to get all users.
        ApiService apiService = ApiClient.getApiService();
        apiService.getAllUsers().enqueue(new Callback<List<users>>() {
            @Override
            public void onResponse(Call<List<users>> call, Response<List<users>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Find the specific user with userId == 1.
                    users specificUser = null;
                    for (users user : response.body()) {
                        if (user.getUserId() == 1) {
                            specificUser = user;
                            break;
                        }
                    }
                    if (specificUser == null) {
                        Log.e("API_SESSIONS", "User with ID 1 not found.");
                        return;
                    }

                    // Aggregate sessions from all groups of this user.
                    List<yourSessionsModal> sessionModalList = new ArrayList<>();
                    if (specificUser.getGroups() != null) {
                        for (groupings grp : specificUser.getGroups()) {
                            if (grp.getSessions() != null) {
                                for (sessions sess : grp.getSessions()) {
                                    // Map fields from the session object.
                                    String restaurantName = sess.getLocation();           // Using location as "restaurant name"
                                    String dateTimeAddress = sess.getMeetingDateTime();     // Using meetingDateTime as date/time string
                                    String groupName = grp.getGroupName();                  // Group name from parent group
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

                    // Save the complete list for filtering.
                    fullSessionList.clear();
                    fullSessionList.addAll(sessionModalList);

                    // Setup the adapter with the session data.
                    allSessionsAdapter adapter = new allSessionsAdapter(sessionModalList);
                    adapterHolder[0] = adapter;
                    b.allsessionsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    b.allsessionsView.setAdapter(adapter);

                    Log.d("API_SESSIONS", "Displayed sessions count: " + sessionModalList.size());
                } else {
                    Log.e("API_SESSIONS", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<users>> call, Throwable t) {
                Log.e("API_SESSIONS", "API call failed: " + t.getMessage());
            }
        });

        // Search functionality: Filter sessions based on input from searchEditText when searchButton is clicked.
        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = b.searchEditText.getText().toString().trim().toLowerCase();
                List<yourSessionsModal> filteredList = new ArrayList<>();
                // Check each session to see if any key field contains the search query.
                for (yourSessionsModal modal : fullSessionList) {
                    if (modal.getRestaurantName().toLowerCase().contains(query)
                            || modal.getGroupName().toLowerCase().contains(query)
                            || modal.getDateTimeAddress().toLowerCase().contains(query)
                            || modal.getSessionTitle().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    }
                }
                // Update the RecyclerView adapter with the filtered list.
                allSessionsAdapter filteredAdapter = new allSessionsAdapter(filteredList);
                adapterHolder[0] = filteredAdapter;
                b.allsessionsView.setAdapter(filteredAdapter);
            }
        });
    }

    private void animateViewRotation(View view, float startRotation, float endRotation) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);  // Duration in milliseconds
        rotationAnimator.start();
    }

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
