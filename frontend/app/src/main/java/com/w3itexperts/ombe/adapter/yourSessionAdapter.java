package com.w3itexperts.ombe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.yourGroupsModal;
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.util.List;

public class yourSessionAdapter extends RecyclerView.Adapter<yourSessionAdapter.yourSessionViewHolder> {
    private List<yourSessionsModal> AllSessionsList;

    public yourSessionAdapter(List<yourSessionsModal> AllSessionList)
    {
        this.AllSessionsList = AllSessionList;
    }

    @NonNull
    @Override
    public yourSessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.incomingsessions, parent, false);
        return new yourSessionAdapter.yourSessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull yourSessionAdapter.yourSessionViewHolder holder, int position) {
        int actualPosition = position % AllSessionsList.size();
        yourSessionsModal modal = AllSessionsList.get(actualPosition);
        holder.DateTimeAddress.setText(modal.getDateTimeAddress());
        holder.restaurantName.setText(modal.getRestaurantName());
        holder.GroupName.setText(modal.getGroupName());
        //holder.sessionStatus.setText(modal.getSessionStatus());
        //holder.sessionTitle.setText(modal.getSessionTitle());
    }

    @Override
    public int getItemCount() {
        return AllSessionsList.size();
    }


    public static class yourSessionViewHolder extends RecyclerView.ViewHolder {
        TextView DateTimeAddress,restaurantName,GroupName,sessionStatus, sessionTitle;

        yourSessionViewHolder(@NonNull View itemView) {
            super(itemView);
            DateTimeAddress = itemView.findViewById(R.id.DateTimeAddress);
            restaurantName = itemView.findViewById(R.id.restaurantName);
            GroupName = itemView.findViewById(R.id.GroupName);
            //sessionStatus = itemView.findViewById(R.id.sessionstatus);
            //sessionTitle = itemView.findViewById(R.id.sessionTitle);
        }
    }
}
