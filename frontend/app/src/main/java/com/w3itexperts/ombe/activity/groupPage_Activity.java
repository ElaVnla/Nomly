package com.w3itexperts.ombe.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;


public class groupPage_Activity extends AppCompatActivity {

    private TextView groupNameInput, noOfPeopleGroup, dateCreatedGroup;
    private ImageView groupPhotoInput;
    private FlexboxLayout flexboxMembers;
    private RecyclerView sessionsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page_main);

        // ✅ Pencil button opens edit group details
        ImageView editGroupButton = findViewById(R.id.editGroupButton);
        editGroupButton.setOnClickListener(v -> {
            Intent intent = new Intent(groupPage_Activity.this, editGroup_activity.class);
            startActivity(intent);
        });


        groupNameInput = findViewById(R.id.groupNameInput);
        noOfPeopleGroup = findViewById(R.id.noOfPeopleGroup);
        dateCreatedGroup = findViewById(R.id.dateCreatedGroup);
        groupPhotoInput = findViewById(R.id.groupPhotoInput);
        flexboxMembers = findViewById(R.id.container).findViewById(R.id.flexboxMembers);
        sessionsRecyclerView = findViewById(R.id.yourGroupSessions);

        // TEMPORARY MOCK DATA (Replace with backend call later) I NEED THE STRING
        groupNameInput.setText("Team Gardeners");
        noOfPeopleGroup.setText("3");
        dateCreatedGroup.setText("18 Mar 2025");
        Glide.with(this).load(R.drawable.person4).into(groupPhotoInput);

        List<Member> mockMembers = new ArrayList<>(); // maybe can get string
        mockMembers.add(new Member("ToniFoodie", R.drawable.person4));


        loadMembers(mockMembers);

        sessionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sessionsRecyclerView.setAdapter(new SessionsAdapter(getMockSessions()));
    }

    private void loadMembers(List<Member> members) {
        flexboxMembers.removeAllViews();
        for (Member member : members) {
            LinearLayout memberLayout = new LinearLayout(this);
            memberLayout.setOrientation(LinearLayout.VERTICAL);
            memberLayout.setGravity(LinearLayout.HORIZONTAL);
            memberLayout.setPadding(12, 12, 12, 12);

            ImageView profileImage = new ImageView(this);
            profileImage.setLayoutParams(new ViewGroup.LayoutParams(160, 160));
            profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(this).load(member.imageResId).into(profileImage);

            MaterialButton nameButton = new MaterialButton(this);
            ViewGroup.LayoutParams nameParams = new ViewGroup.LayoutParams(220, 80);
            nameButton.setLayoutParams(nameParams);
            nameButton.setText(member.name);
            nameButton.setTextSize(12);
            nameButton.setPadding(2, 2, 2, 2);
            nameButton.setCornerRadius(5);

            memberLayout.addView(profileImage);
            memberLayout.addView(nameButton);
            flexboxMembers.addView(memberLayout);
        }
    }

    private List<Session> getMockSessions() {
        List<Session> sessions = new ArrayList<>();
        sessions.add(new Session("Lunchy Date", "19 February 25, 3pm, Tampines", "Ongoing")); // change to dateTime and Location
        sessions.add(new Session("Dinner date", "20 February 25, 1pm, Botanic Gardens", "Done"));
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
