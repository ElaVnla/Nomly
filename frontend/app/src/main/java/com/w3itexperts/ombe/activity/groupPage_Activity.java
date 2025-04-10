package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.users;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class groupPage_Activity extends AppCompatActivity {

    private TextView groupNameInput, noOfPeopleGroup, dateCreatedGroup;
    private ShapeableImageView groupPhotoInput;
    private FlexboxLayout flexboxMembers;
    private RecyclerView sessionsRecyclerView;
    private ApiService apiService;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page_main);

        groupNameInput = findViewById(R.id.groupNameInput);
        noOfPeopleGroup = findViewById(R.id.noOfPeopleGroup);
        dateCreatedGroup = findViewById(R.id.dateCreatedGroup);
        groupPhotoInput = findViewById(R.id.groupPhotoInput);
        flexboxMembers = findViewById(R.id.flexboxMembers);
        sessionsRecyclerView = findViewById(R.id.yourGroupSessions);

        // Get groupId from intent
        Intent intent = getIntent();
        boolean isFakeDemo = intent.getBooleanExtra("fromFakeDemo", false);
        groupId = intent.getIntExtra("groupId", -1);

        Log.d("GROUP_PAGE", "Received groupId: " + groupId + ", isFakeDemo: " + isFakeDemo);

        // Edit group button
        ImageView editGroupButton = findViewById(R.id.editGroupButton);
        editGroupButton.setOnClickListener(v -> {
            Intent editIntent = new Intent(groupPage_Activity.this, editGroup_activity.class);
            editIntent.putExtra("groupId", groupId);
            startActivity(editIntent);
        });

        // Back to home page
        ImageView backBtn = findViewById(R.id.backbtnToHomePage);
        backBtn.setOnClickListener(v -> {
            Intent homeIntent = new Intent(groupPage_Activity.this, home.class);
            homeIntent.putExtra("loadHomeFragment", true);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
            finish();
        });

        apiService = ApiClient.getApiService();

        if (isFakeDemo) {
            Log.d("GROUP_PAGE", "Loading mock group data...");
            loadMockGroupData();
        } else if (groupId != -1) {
            Log.d("GROUP_PAGE", "Fetching real group data for ID: " + groupId);
            fetchRealGroupData(groupId);
        } else {
            Log.w("GROUP_PAGE", "No valid groupId or demo flag. Falling back.");
            groupNameInput.setText("Could not load group!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (groupId != -1) {
            fetchRealGroupData(groupId); // Optional: refresh after returning from edit
        }
    }

    private void loadMockGroupData() {
        groupNameInput.setText("Team Gardeners");
        noOfPeopleGroup.setText("3");
        dateCreatedGroup.setText("18 Mar 2025");
        Glide.with(this).load(R.drawable.person4).into(groupPhotoInput);

        List<Member> mockMembers = new ArrayList<>();
        mockMembers.add(new Member("ToniFoodie", R.drawable.person4));
        mockMembers.add(new Member("Aminah123", R.drawable.person4));
        mockMembers.add(new Member("Salim", R.drawable.person4));
        loadMembers(mockMembers);

        sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sessionsRecyclerView.setAdapter(new SessionsAdapter(getMockSessions()));
    }

    private void fetchRealGroupData(int groupId) {
        apiService.getGrouping(groupId).enqueue(new Callback<groupings>() {
            @Override
            public void onResponse(Call<groupings> call, Response<groupings> response) {
                if (response.isSuccessful() && response.body() != null) {
                    groupings group = response.body();
                    Log.d("GROUP_PAGE", "Fetched group: " + group.getGroupName());

                    groupNameInput.setText(group.getGroupName());
                    dateCreatedGroup.setText(group.getCreatedAt());
                    noOfPeopleGroup.setText(String.valueOf(group.getUsers().size()));

                    // TODO: Enable this when backend supports groupPic
//                    String groupPicUrl = group.getGroupPic();
//                    if (groupPicUrl != null && !groupPicUrl.isEmpty()) {
//                        Glide.with(groupPage_Activity.this).load(groupPicUrl).into(groupPhotoInput);
//                    } else {
                    Glide.with(groupPage_Activity.this).load(R.drawable.person4).into(groupPhotoInput);
//                    }

                    List<Member> members = new ArrayList<>();
                    for (users u : group.getUsers()) {
                        members.add(new Member(u.getUsername(), R.drawable.person4));
                    }
                    loadMembers(members);

                    List<Session> sessionList = new ArrayList<>();
                    for (sessions s : group.getSessions()) {
                        String title = "Session #" + s.getSessionId();
                        String details = s.getMeetingDateTime() + ", " + s.getLocation();
                        String status = s.isCompleted() ? "Done" : "Ongoing";
                        sessionList.add(new Session(title, details, status));
                    }

                    sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(groupPage_Activity.this));
                    sessionsRecyclerView.setAdapter(new SessionsAdapter(sessionList));
                } else {
                    Log.e("GROUP_PAGE", "Failed to fetch group. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<groupings> call, Throwable t) {
                Log.e("GROUP_PAGE", "API call failed", t);
            }
        });
    }

    private void loadMembers(List<Member> members) {
        flexboxMembers.removeAllViews();
        for (Member member : members) {
            LinearLayout memberLayout = new LinearLayout(this);
            memberLayout.setOrientation(LinearLayout.VERTICAL);
            memberLayout.setGravity(LinearLayout.HORIZONTAL);
            memberLayout.setPadding(20, 20, 20, 20);

            ImageView profileImage = new ImageView(this);
            profileImage.setLayoutParams(new ViewGroup.LayoutParams(180, 180));
            profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(member.imageResId).into(profileImage);

            MaterialButton nameButton = new MaterialButton(this);
            ViewGroup.LayoutParams nameParams = new ViewGroup.LayoutParams(230, 90);
            nameButton.setLayoutParams(nameParams);
            nameButton.setText(member.name);
            nameButton.setTextSize(10);
            nameButton.setPadding(20, 20, 20, 20);
            nameButton.setCornerRadius(5);
            nameButton.setTextColor(getResources().getColor(android.R.color.white));

            memberLayout.addView(profileImage);
            memberLayout.addView(nameButton);
            flexboxMembers.addView(memberLayout);
        }
    }

    private List<Session> getMockSessions() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("Lunchy Date", "19 February 25, 3pm, Tampines", "Ongoing"));
        sessions.add(new Session("Dinner Date", "20 February 25, 7pm, Botanic Gardens", "Done"));
        return sessions;
    }

    private static class Member {
        String name;
        int imageResId;

        Member(String name, int imageResId) {
            this.name = name;
            this.imageResId = imageResId;
        }
    }

    private static class Session {
        String title, details, status;

        Session(String title, String details, String status) {
            this.title = title;
            this.details = details;
            this.status = status;
        }
    }

    private class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.SessionViewHolder> {
        List<Session> sessions;

        SessionsAdapter(List<Session> sessions) {
            this.sessions = sessions;
        }

        @NonNull
        @Override
        public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sessions, parent, false);
            return new SessionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
            Session session = sessions.get(position);
            holder.title.setText(session.title);
            holder.details.setText(session.details);
            holder.statusButton.setText(session.status);
        }

        @Override
        public int getItemCount() {
            return sessions.size();
        }

        class SessionViewHolder extends RecyclerView.ViewHolder {
            TextView title, details;
            MaterialButton statusButton;

            SessionViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.event_title);
                details = itemView.findViewById(R.id.event_details);
                statusButton = itemView.findViewById(R.id.status_button);
            }
        }
    }
}
