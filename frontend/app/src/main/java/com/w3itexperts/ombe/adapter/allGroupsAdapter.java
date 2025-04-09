package com.w3itexperts.ombe.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.List;

public class allGroupsAdapter extends RecyclerView.Adapter<allGroupsAdapter.allGroupsViewHolder> {

    private List<yourGroupsModal> AllGroupsList;

    public allGroupsAdapter(List<yourGroupsModal> AllGroupsList) {
        this.AllGroupsList = AllGroupsList;
    }

    @NonNull
    @Override
    public allGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("SESSION_DATA", "Size: " + DataGenerator.AllSessionsList().size());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allgroups, parent, false);
        return new allGroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allGroupsViewHolder holder, int position) {
        int actualPosition = position % AllGroupsList.size();
        yourGroupsModal modal = AllGroupsList.get(actualPosition);
        holder.groupImage.setImageResource(modal.getgroupImage());
        holder.noOfSessions.setText(modal.getnoOfSessions());
        holder.NoOfMembers.setText(modal.getNoOfMembers());
        holder.groupName.setText(modal.getgroupName());
    }

    @Override
    public int getItemCount() {
        return AllGroupsList.size();
    }

    public static class allGroupsViewHolder extends RecyclerView.ViewHolder {

        ImageView groupImage;
        TextView noOfSessions,NoOfMembers,groupName;

        allGroupsViewHolder(@NonNull View itemView) {
            super(itemView);
            groupImage = itemView.findViewById(R.id.groupImage);
            noOfSessions = itemView.findViewById(R.id.noOfSessions);
            NoOfMembers = itemView.findViewById(R.id.NoOfMembers);
            groupName = itemView.findViewById(R.id.groupName);
        }
    }
}
