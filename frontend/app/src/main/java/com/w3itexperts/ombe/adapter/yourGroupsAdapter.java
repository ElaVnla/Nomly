package com.w3itexperts.ombe.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.google.android.material.card.MaterialCardView;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.List;

public class yourGroupsAdapter extends RecyclerView.Adapter<yourGroupsAdapter.yourGroupsViewHolder> {

    private List<yourGroupsModal> AllGroupsList;

    public yourGroupsAdapter(List<yourGroupsModal> AllGroupsList) {
        this.AllGroupsList = AllGroupsList;
    }

    @NonNull
    @Override
    public yourGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("SESSION_DATA", "Size: " + DataGenerator.AllSessionsList().size());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latestgroups, parent, false);
        return new yourGroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull yourGroupsViewHolder holder, int position) {
        int actualPosition = position % AllGroupsList.size();
        yourGroupsModal modal = AllGroupsList.get(actualPosition);
        holder.groupImage.setImageResource(modal.getgroupImage());
        holder.noOfSessions.setText(modal.getnoOfSessions());
        holder.NoOfMembers.setText(modal.getNoOfMembers());
        holder.groupName.setText(modal.getgroupName());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public static class yourGroupsViewHolder extends RecyclerView.ViewHolder {

        ImageView groupImage;
        TextView noOfSessions,NoOfMembers,groupName;

        yourGroupsViewHolder(@NonNull View itemView) {
            super(itemView);
            groupImage = itemView.findViewById(R.id.groupImage);
            noOfSessions = itemView.findViewById(R.id.noOfSessions);
            NoOfMembers = itemView.findViewById(R.id.NoOfMembers);
            groupName = itemView.findViewById(R.id.groupName);
        }
    }
}
