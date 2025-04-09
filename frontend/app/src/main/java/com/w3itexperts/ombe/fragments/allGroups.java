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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.allGroupsAdapter;
import com.w3itexperts.ombe.adapter.yourGroupsAdapter;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentAddCardBinding;
import com.w3itexperts.ombe.databinding.FragmentAllgroupsBinding;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class allGroups extends Fragment {

    FragmentAllgroupsBinding b;

    private allGroupsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAllgroupsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Prepare a list to hold the full groups data for filtering later.
        final List<yourGroupsModal> fullGroupList = new ArrayList<>();
        // Adapter reference as a field so we can update it during searches.
        final allGroupsAdapter[] adapterHolder = new allGroupsAdapter[1];

        // Call the API to get all users.
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
                        Log.e("API_GROUPS", "User with ID 1 not found.");
                        return;
                    }

                    // Use only the groups of the specific user.
                    List<groupings> groupsList = specificUser.getGroups();
                    List<yourGroupsModal> modalList = new ArrayList<>();
                    if (groupsList != null) {
                        for (groupings grp : groupsList) {
                            yourGroupsModal modal = new yourGroupsModal(
                                    String.valueOf(grp.getNoUsers()),      // NoOfMembers (update if you have real data)
                                    String.valueOf(grp.getNoSessions()),     // noOfSessions (update if you have real data)
                                    R.drawable.tempgroupimg,                 // Replace with actual resource if available
                                    grp.getGroupName()                       // Assuming getGroupName() returns the group name
                            );
                            modalList.add(modal);
                        }
                    }

                    // Save the full list to use for filtering.
                    fullGroupList.clear();
                    fullGroupList.addAll(modalList);

                    // Set adapter with dynamic API data on allgroupsView.
                    allGroupsAdapter adapter = new allGroupsAdapter(modalList);
                    adapterHolder[0] = adapter;  // store adapter reference for filtering later.
                    b.allgroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    b.allgroupsView.setAdapter(adapter);

                    Log.d("API_GROUPS", "Displayed groups count: " + modalList.size());
                } else {
                    Log.e("API_GROUPS", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<users>> call, Throwable t) {
                Log.e("API_GROUPS", "API call failed: " + t.getMessage());
            }
        });

        // Set an onClickListener for the search button to filter the list.
        // Make sure your layout includes these views with matching IDs.
        b.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = b.searchEditText.getText().toString().trim().toLowerCase();
                List<yourGroupsModal> filteredList = new ArrayList<>();

                // Loop through the full list of groups and add items whose name contains the search query.
                for (yourGroupsModal modal : fullGroupList) {
                    if (modal.getgroupName().toLowerCase().contains(query)) {
                        filteredList.add(modal);
                    }
                }

                // Update the adapter: create a new adapter instance (or update the existing adapter's data if implemented).
                allGroupsAdapter filteredAdapter = new allGroupsAdapter(filteredList);
                adapterHolder[0] = filteredAdapter;
                b.allgroupsView.setAdapter(filteredAdapter);
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
