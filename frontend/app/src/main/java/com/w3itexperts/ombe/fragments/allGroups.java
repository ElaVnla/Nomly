package com.w3itexperts.ombe.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.activity.Welcome;
import com.w3itexperts.ombe.activity.login_signin_Activity;
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
    private List<yourGroupsModal> fullGroupList = new ArrayList<>();

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
        // Make sure your layout includes these views with matching IDs.


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
                allGroupsAdapter filteredAdapter = new allGroupsAdapter(filteredList);
                adapterHolder[0] = filteredAdapter;
                b.allgroupsView.setAdapter(filteredAdapter);
                Log.d("SEARCH", "Adapter updated with filtered list.");
            }
        });

    }

    private void updateUI(users user) {
        // Process Groups: map the user's groups into a list of yourGroupsModal items.
        List<groupings> groupsList = user.getGroups();
        List<yourGroupsModal> groupsModalList = new ArrayList<>();
        if (groupsList != null) {
            for (groupings grp : groupsList) {
                yourGroupsModal modal = new yourGroupsModal(
                        String.valueOf(grp.getNoUsers()),      // NoOfMembers (update if needed)
                        String.valueOf(grp.getNoSessions()),     // NoOfSessions (update if needed)
                        R.drawable.tempgroupimg,                 // Group image resource
                        grp.getGroupName()                       // Group name
                );
                groupsModalList.add(modal);
            }
        }

        // Update the RecyclerView adapter for groups.
        allGroupsAdapter adapter = new allGroupsAdapter(groupsModalList);
        fullGroupList = groupsModalList;
        b.allgroupsView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        b.allgroupsView.setAdapter(adapter);
        Log.d("API_GROUPS", "Displayed groups count: " + groupsModalList.size());
    }

    private void logoutUser() {
        // Clear session info and navigate to login.
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
