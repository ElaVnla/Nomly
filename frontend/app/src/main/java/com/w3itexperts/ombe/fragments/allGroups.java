package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator; import android.animation.ValueAnimator; import android.content.Intent; import android.os.Bundle; import android.util.Log; import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup; import android.widget.FrameLayout;

import androidx.annotation.NonNull; import androidx.annotation.Nullable; import androidx.fragment.app.Fragment; import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.APIservice.ApiClient; import com.w3itexperts.ombe.APIservice.ApiService; import com.w3itexperts.ombe.R; import com.w3itexperts.ombe.SessionService.SessionManager; import com.w3itexperts.ombe.activity.Welcome; import com.w3itexperts.ombe.adapter.allGroupsAdapter;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.apimodals.groupings; import com.w3itexperts.ombe.apimodals.users; import com.w3itexperts.ombe.databinding.FragmentAllgroupsBinding; import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.ArrayList; import java.util.List;

import retrofit2.Call; import retrofit2.Callback; import retrofit2.Response;

public class allGroups extends Fragment {
    private FragmentAllgroupsBinding b;
    private allGroupsAdapter adapter;
    private List<yourGroupsModal> fullGroupList = new ArrayList<>();

    // A holder for the adapter, used in the search filtering logic.
    final allGroupsAdapter[] adapterHolder = new allGroupsAdapter[1];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAllgroupsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve current user from SessionManager.
        final users storedUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (storedUser == null) {
            Log.e("API_GROUPS", "No current user found in session!");
            logoutUser();
            return;
        }

        // Refresh the user data from the API so you have the latest details.
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
                    Log.e("API_GROUPS", "getUser error: " + response.code());
                    refreshedUser = storedUser;
                }
                updateUI(refreshedUser);
            }

            @Override
            public void onFailure(Call<users> call, Throwable t) {
                Log.e("API_GROUPS", "API call failed: " + t.getMessage());
                updateUI(storedUser);
            }
        });

        // Set an onClickListener for the search button to filter the list.
        // The search filters groups by their name.
        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = b.searchEditText.getText().toString().trim().toLowerCase();
                Log.d("SEARCH", "Query: " + query);

                List<yourGroupsModal> filteredList = new ArrayList<>();
                Log.d("SEARCH", "check full group list: " + fullGroupList.size());
                for (yourGroupsModal modal : fullGroupList) {
                    Log.d("SEARCH", "Checking group name: '" + modal.getgroupName() + "'");
                    if (modal.getgroupName().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    }
                }

                Log.d("SEARCH", "Full list size: " + fullGroupList.size());
                Log.d("SEARCH", "Filtered list size: " + filteredList.size());

                // Check the adapterHolder status.
                if (adapterHolder[0] == null) {
                    Log.d("SEARCH", "adapterHolder[0] is null.");
                } else {
                    Log.d("SEARCH", "adapterHolder[0] is NOT null.");
                }

                // Create and set the new adapter
                allGroupsAdapter filteredAdapter = new allGroupsAdapter(getContext(),filteredList);
                adapterHolder[0] = filteredAdapter;
                b.allgroupsView.setAdapter(filteredAdapter);
                Log.d("SEARCH", "Adapter updated with filtered list.");
            }
        });
    }

    // Update the UI by mapping the user's groups into yourGroupsModal items.
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
                            // Update the RecyclerView adapter for groups.
                            allGroupsAdapter adapter = new allGroupsAdapter(getContext(),groupsModalList);
                            fullGroupList = groupsModalList;
                            b.allgroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                            b.allgroupsView.setAdapter(adapter);
                            Log.d("API_GROUPS", "Displayed groups count: " + groupsModalList.size());
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
                            allGroupsAdapter groupsAdapter = new allGroupsAdapter(getContext(), groupsModalList);
                            b.allgroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            b.allgroupsView.setAdapter(groupsAdapter);
                            Log.d("API_MERGE", "Displayed groups count: " + groupsModalList.size());
                        }
                    }
                });
            }
        } else {
            Log.d("API_MERGE", "No groups found for user.");
        }

    }

    // Logs the user out and navigates to the Welcome screen.
    private void logoutUser() {
        SessionManager.getInstance(getContext()).setCurrentUser(null);
        Intent intent = new Intent(getContext(), Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    // Helper method to animate rotation of a view.
    private void animateViewRotation(View view, float startRotation, float endRotation) {
        ObjectAnimator rotationAnimator = ObjectAnimator.ofFloat(view, "rotation", startRotation, endRotation);
        rotationAnimator.setDuration(300);  // Duration in milliseconds
        rotationAnimator.start();
    }

    // Helper method to animate translation (margins) of a view.
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