package com.w3itexperts.ombe.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.fragments.ViewSessionFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// IMPORTANT: please do not edit anything out or change any of the existing codes,
// you can add at the end tho.
//but make sure my pages remain stable thanks.

public class groupPage_Activity extends AppCompatActivity {

    private TextView groupNameInput, noOfPeopleGroup, dateCreatedGroup;
    private ShapeableImageView groupPhotoInput;
    private FlexboxLayout flexboxMembers;
    private RecyclerView sessionsRecyclerView;
    private ApiService apiService;
    private int groupId, userId;

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

        userId = SessionManager.getInstance(this).getCurrentUser().getUserId();
        apiService = ApiClient.getApiService();

        Intent intent = getIntent();
        boolean isFakeDemo = intent.getBooleanExtra("fromFakeDemo", false);
        groupId = intent.getIntExtra("groupId", -1);

        if (isFakeDemo) {
            loadMockGroupData();
        } else if (groupId != -1) {
            fetchRealGroupData(groupId);
        } else {
            groupNameInput.setText("Could not load group!");
        }

        // Edit group
        findViewById(R.id.editGroupButton).setOnClickListener(v -> {
            Intent editIntent = new Intent(this, editGroup_activity.class);
            editIntent.putExtra("groupId", groupId);
            startActivity(editIntent);
        });

        // Back button
        findViewById(R.id.backbtnToHomePage).setOnClickListener(v -> {
            Intent homeIntent = new Intent(this, home.class);
            homeIntent.putExtra("loadHomeFragment", true);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(homeIntent);
            finish();
        });

        // Leave group button
        findViewById(R.id.leaveGroupButton).setOnClickListener(v -> showLeaveConfirmation());

        MaterialButton createSessionBtn = findViewById(R.id.createSessionBtn);

        createSessionBtn.setOnClickListener(v -> {
            if (groupId == -1) {
                Log.e("CREATE_SESSION", "Group ID missing!");
                return;
            }

            Intent createIntent = new Intent(groupPage_Activity.this, SessionActivity.class);
            createIntent.putExtra("groupId", groupId);
            startActivity(createIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (groupId != -1) {
            fetchRealGroupData(groupId);
        }
    }

    private void showLeaveConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Leave Group")
                .setMessage("Are you sure you want to leave this group?")
                .setPositiveButton("Yes", (dialog, which) -> leaveGroup())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void leaveGroup() {
        Map<String, String> data = new HashMap<>();
        data.put("userId", String.valueOf(userId));
        data.put("groupId", String.valueOf(groupId));

        apiService.removeUserFromGrouping(data).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful() && Boolean.TRUE.equals(response.body())) {
                    // Successfully left group
                    Intent intent = new Intent(groupPage_Activity.this, home.class);
                    intent.putExtra("loadHomeFragment", true);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Log.e("LEAVE_GROUP", "❌ Failed to leave group. Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("LEAVE_GROUP", "❌ API error: " + t.getMessage());
            }
        });
    }



    private void loadMockGroupData() {
        groupNameInput.setText("Team Gardeners");
        noOfPeopleGroup.setText("3");
        dateCreatedGroup.setText("18 Mar 2025");
        Glide.with(this).load(R.drawable.person4).into(groupPhotoInput);

        List<Member> mockMembers = List.of(
                new Member("ToniFoodie", R.drawable.person4),
                new Member("Aminah123", R.drawable.person4),
                new Member("Salim", R.drawable.person4)
        );
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
                    groupNameInput.setText(group.getGroupName());
                    dateCreatedGroup.setText(group.getCreatedAt());
                    noOfPeopleGroup.setText(String.valueOf(group.getUsers().size()));
                    Glide.with(groupPage_Activity.this).load(R.drawable.person4).into(groupPhotoInput);

                    List<Member> members = new ArrayList<>();
                    for (users u : group.getUsers()) {
                        members.add(new Member(u.getUsername(), R.drawable.person4));
                    }
                    loadMembers(members);

//                    List<Session> sessionList = new ArrayList<>();
//                    for (sessions s : group.getSessions()) {
//                        sessionList.add(new Session("Session #" + s.getSessionId(),
//                                s.getMeetingDateTime() + ", " + s.getLocation(),
//                                s.isCompleted() ? "Done" : "Ongoing"));
//                    }

                    List<Session> sessionList = new ArrayList<>();
                    for (sessions s : group.getSessions()) {
                        String[] dateTime = s.getMeetingDateTime().split("T");
                        String date = dateTime.length > 0 ? dateTime[0] : "N/A";
                        String time = dateTime.length > 1 ? dateTime[1] : "N/A";

                        Session session = new Session(
                                s.getSessionName(),
                                date,
                                time,
                                s.getLocation(),
                                s.isCompleted() ? "Done" : "Ongoing"
                        );

                        // ✅ Set sessionId and groupId manually
                        session.sessionId = s.getSessionId();
                        session.groupId = groupId;
                        session.lat = s.getLatitude();     // ✅ Add this
                        session.lng = s.getLongitude();    // ✅ Add this

                        sessionList.add(session);
                    }

                    sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(groupPage_Activity.this));
                    sessionsRecyclerView.setAdapter(new SessionsAdapter(sessionList));
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
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(20, 20, 20, 20);

            ImageView img = new ImageView(this);
            img.setLayoutParams(new ViewGroup.LayoutParams(180, 180));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(member.imageResId).into(img);

            MaterialButton btn = new MaterialButton(this);
            btn.setLayoutParams(new ViewGroup.LayoutParams(230, 90));
            btn.setText(member.name);
            btn.setTextSize(10);
            btn.setCornerRadius(5);
            btn.setTextColor(getResources().getColor(android.R.color.white));

            layout.addView(img);
            layout.addView(btn);
            flexboxMembers.addView(layout);
        }
    }

    private List<Session> getMockSessions() {
        return List.of(
                new Session("Lunchy Date", "19 Feb 25, 3pm, Tampines", "Ongoing"),
                new Session("Dinner Date", "20 Feb 25, 7pm, Botanic Gardens", "Done")
        );
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
        String title, details, location, date, time, status;
        int sessionId;
        int groupId;

        double lat;
        double lng;
        Session(String title, String details, String status) {
            this.title = title;
            this.details = details;
            this.status = status;
        }

        Session(String title, String date, String time, String location, String status) {
//            this.title = title;
//            this.details = details;
//            this.status = status;
            this.title = title;
            this.location = location;
            this.date = date;
            this.time = time;
            this.status = status;
        }
        Session(String title, String location, String date, String time, String status, int sessionId, int groupId, double lat, double lng) {
//            this.title = title;
//            this.details = details;
//            this.status = status;
            this.title = title;
            this.location = location;
            this.date = date;
            this.time = time;
            this.status = status;
            this.sessionId = sessionId;
            this.groupId = groupId;
            this.lat = lat;
            this.lng = lng;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sessions, parent, false);
            return new SessionViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SessionViewHolder h, int pos) {
            Session s = sessions.get(pos);
            h.title.setText(s.title);
            h.details.setText(String.format("%s, %s, %s", s.date, s.time, s.location));
            h.statusButton.setText(s.status);

            //  New: Click to open ViewSessionFragment
            h.statusButton.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), SessionActivity.class);
                intent.putExtra("title", s.title);
                intent.putExtra("date", s.date);
                intent.putExtra("time", s.time);
                intent.putExtra("location", s.location);
                intent.putExtra("status", s.status);
                intent.putExtra("sessionId", s.sessionId);
                intent.putExtra("groupId", groupId);
                intent.putExtra("lat", s.lat);        // ✅ Add this
                intent.putExtra("lng", s.lng);        // ✅ Add this

                v.getContext().startActivity(intent);
            });

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
