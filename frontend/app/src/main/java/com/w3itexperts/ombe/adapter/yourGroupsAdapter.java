package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.groupPage_Activity;
import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.List;

public class yourGroupsAdapter extends RecyclerView.Adapter<yourGroupsAdapter.yourGroupsViewHolder> {

    private List<yourGroupsModal> AllGroupsList;
    private Context context;

    public yourGroupsAdapter(Context context, List<yourGroupsModal> AllGroupsList) {
        this.context = context;
        this.AllGroupsList = AllGroupsList;
    }

    @NonNull
    @Override
    public yourGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.latestgroups, parent, false);
        return new yourGroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull yourGroupsViewHolder holder, int position) {
        yourGroupsModal modal = AllGroupsList.get(position);

        Bitmap groupBitmap = modal.getGroupImage();
        if (groupBitmap != null) {
            holder.groupImage.setImageBitmap(groupBitmap);
        } else {
            holder.groupImage.setImageResource(R.drawable.tempgroupimg); // fallback
        }

        holder.noOfSessions.setText(modal.getNoOfSessions());
        holder.NoOfMembers.setText(modal.getNoOfMembers());
        holder.groupName.setText(modal.getGroupName());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, groupPage_Activity.class);
            intent.putExtra("groupId", modal.getGroupId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return AllGroupsList.size();
    }

    public static class yourGroupsViewHolder extends RecyclerView.ViewHolder {

        ImageView groupImage;
        TextView noOfSessions, NoOfMembers, groupName;

        public yourGroupsViewHolder(@NonNull View itemView) {
            super(itemView);
            groupImage = itemView.findViewById(R.id.groupImage);
            noOfSessions = itemView.findViewById(R.id.noOfSessions);
            NoOfMembers = itemView.findViewById(R.id.NoOfMembers);
            groupName = itemView.findViewById(R.id.groupName);
        }
    }
}
