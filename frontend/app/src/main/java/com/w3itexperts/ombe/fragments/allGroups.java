package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator; import android.animation.ValueAnimator; import android.content.Intent; import android.os.Bundle; import android.util.Log; import android.view.LayoutInflater; import android.view.View; import android.view.ViewGroup; import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull; import androidx.annotation.Nullable; import androidx.fragment.app.Fragment; import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.APIservice.ApiClient; import com.w3itexperts.ombe.APIservice.ApiService; import com.w3itexperts.ombe.R; import com.w3itexperts.ombe.SessionService.SessionManager; import com.w3itexperts.ombe.activity.Welcome; import com.w3itexperts.ombe.adapter.allGroupsAdapter;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.apimodals.groupings; import com.w3itexperts.ombe.apimodals.users; import com.w3itexperts.ombe.databinding.FragmentAllgroupsBinding; import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.ArrayList; import java.util.List;

import retrofit2.Call; import retrofit2.Callback; import retrofit2.Response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

        APIUserSetup();
        // Set an onClickListener for the search button to filter the list
        // The search filters groups by their name
        // as long as it contins a certain letter(s) similar, it will display
        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = b.searchEditText.getText().toString().trim().toLowerCase();
                Log.d("NOMLYPROCESS", "Query: " + query);

                List<yourGroupsModal> filteredSearchList = new ArrayList<>();
                Log.d("NOMLYPROCESS", "check full group list: " + fullGroupList.size());
                for (yourGroupsModal modal : fullGroupList) {
                    Log.d("NOMLYPROCESS", "Checking group name: '" + modal.getGroupName() + "'");
                    if (modal.getGroupName().toLowerCase().contains(query)) {
                        filteredSearchList.add(modal);
                    }
                }

                Log.d("NOMLYPROCESS", "Full list size: " + fullGroupList.size());
                Log.d("NOMLYPROCESS", "Filtered list size: " + filteredSearchList.size());


                // Create and set the new adapter
                allGroupsAdapter filteredAdapter = new allGroupsAdapter(getContext(),filteredSearchList);
                adapterHolder[0] = filteredAdapter;
                b.allgroupsView.setAdapter(filteredAdapter);
                Log.d("NOMLYPROCESS", "Adapter updated with filtered list");
            }
        });
    }

    private void APIUserSetup()
    {
        try {
            // Retrieve current user from SessionManager.
            final users currentLoggedInUser = SessionManager.getInstance(getContext()).getCurrentUser();
            if (currentLoggedInUser == null) {
                Log.e("NOMLYPROCESS", "No current user found in session!");
                logoutUser();
                return;
            }

            // Refresh the user data from the API so you have the latest details
            ApiService apiService = ApiClient.getApiService();
            apiService.getUser(currentLoggedInUser.getUserId()).enqueue(new Callback<users>() {
                @Override
                public void onResponse(Call<users> call, Response<users> response) {
                    users FoundUser;
                    if (response.isSuccessful() && response.body() != null) {
                        FoundUser = response.body();
                        // Update SessionManager with the new data
                        SessionManager.getInstance(getContext()).setCurrentUser(FoundUser);
                    } else {
                        Log.e("NOMLYPROCESS", "getUser error: " + response.code());
                        FoundUser = currentLoggedInUser;
                    }
                    updateUI(FoundUser);
                }

                @Override
                public void onFailure(Call<users> call, Throwable t) {
                    Log.e("NOMLYPROCESS", "API call failed: " + t.getMessage());
                    updateUI(currentLoggedInUser);
                }
            });

        }
        catch (Exception e) {
            Log.e("NOMLYPROCESS", "ERROR: When using getUser API - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
        }

    }

    // Update the UI
    private void updateUI(users user) {

        List<groupings> groupsList = user.getGroups();
        List<yourGroupsModal> groupsModalList = new ArrayList<>();
        ApiService apiService = ApiClient.getApiService();

        TextView groupsDisplayMessage = b.NoGrpMessage;
        RecyclerView yourGroupsView = b.allgroupsView;

        try {
            if (groupsList != null && !groupsList.isEmpty()) {
                final int totalGroups = groupsList.size();

                // For each group, fetch the updated details.
                for (groupings grp : groupsList) {
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
                                // Fallback
                                refreshedGroup = grp;
                                Log.e("NOMLYPROCESS", "Failed to refresh group " + groupId + ": response code " + response.code());
                            }

                            String base64Image = refreshedGroup.getImage();  // assuming getImage() returns a String
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

                            groupsModalList.add(modal);

                            // Once we have processed all groups, update the RecyclerView.
                            if (groupsModalList.size() == totalGroups) {
                                // Update the RecyclerView adapter for groups.
                                allGroupsAdapter adapter = new allGroupsAdapter(getContext(),groupsModalList);
                                fullGroupList = groupsModalList;
                                b.allgroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                                b.allgroupsView.setAdapter(adapter);
                                Log.d("NOMLYPROCESS", "Displayed groups count: " + groupsModalList.size());
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<groupings> call, Throwable t) {
                            // in case it does not appear
                            groupsDisplayMessage.setVisibility(View.GONE);
                            yourGroupsView.setVisibility(View.VISIBLE);
                            // If the API call fails, use fallback data.
                            Log.e("NOMLYPROCESS", "Failure refreshing group " + groupId + ": " + t.getMessage());
                            Bitmap fallbackImage = BitmapFactory.decodeResource(getResources(), R.drawable.tempgroupimg);
                            yourGroupsModal modal = new yourGroupsModal(
                                    String.valueOf(grp.getNoUsers()),
                                    String.valueOf(grp.getNoSessions()),
                                    fallbackImage,
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
                Log.d("NOMLYPROCESS", "No groups found for user.");
                groupsDisplayMessage.setText("You have no groups. Join one now!");
                groupsDisplayMessage.setVisibility(View.VISIBLE);
                yourGroupsView.setVisibility(View.GONE);
            }
        }catch (Exception e)
        {
            Log.e("NOMLYPROCESS", "ERROR: Failed to display groups - " + e.getMessage());
            Toast.makeText(getContext(), "404 ERROR: Contact Admin Support", Toast.LENGTH_SHORT).show();
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