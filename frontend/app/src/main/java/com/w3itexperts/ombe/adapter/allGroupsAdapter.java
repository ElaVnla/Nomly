package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.groupPage_Activity;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.modals.yourGroupsModal;

import java.util.List;

public class allGroupsAdapter extends RecyclerView.Adapter<allGroupsAdapter.allGroupsViewHolder> {

    private List<yourGroupsModal> AllGroupsList;

    private Context context;

    public allGroupsAdapter(Context context, List<yourGroupsModal> AllGroupsList) {
        this.context = context;
        this.AllGroupsList = AllGroupsList;
    }

    @NonNull
    @Override
    public allGroupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("NOMLYPROCESS", "Size: " + DataGenerator.AllSessionsList().size());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allgroups, parent, false);
        return new allGroupsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allGroupsViewHolder holder, int position) {
        int actualPosition = position % AllGroupsList.size();
        yourGroupsModal modal = AllGroupsList.get(actualPosition);
        //tonie changed this
        if (modal.getGroupImage() != null) {
            holder.groupImage.setImageBitmap(modal.getGroupImage());
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
