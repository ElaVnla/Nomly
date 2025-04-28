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
import com.w3itexperts.ombe.modals.yourSessionsModal;

import java.util.List;

public class allSessionsAdapter extends RecyclerView.Adapter<allSessionsAdapter.allSessionsViewHolder> {

    private List<yourSessionsModal> AllSessionsList;

    public allSessionsAdapter(List<yourSessionsModal> AllSessionsList) {
        this.AllSessionsList = AllSessionsList;
    }

    @NonNull
    @Override
    public allSessionsAdapter.allSessionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("NOMLYPROCESS", "Size: " + DataGenerator.AllSessionsList().size());

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.allsessions, parent, false);
        return new allSessionsAdapter.allSessionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull allSessionsAdapter.allSessionsViewHolder holder, int position) {
        int actualPosition = position % AllSessionsList.size();
        yourSessionsModal modal = AllSessionsList.get(actualPosition);
        holder.sessionTitle.setText(modal.getSessionTitle());
        holder.sessionStatus.setText(modal.getSessionStatus());
        holder.groupName.setText(modal.getGroupName());
        holder.datetime.setText(modal.getDateTimeAddress());
    }

    @Override
    public int getItemCount() {
        return AllSessionsList.size();
    }

    public static class allSessionsViewHolder extends RecyclerView.ViewHolder {

        TextView sessionTitle,groupName,datetime,sessionStatus;

        allSessionsViewHolder(@NonNull View itemView) {
            super(itemView);
            datetime = itemView.findViewById(R.id.datetime);
            groupName = itemView.findViewById(R.id.groupName);
            sessionStatus = itemView.findViewById(R.id.sessionstatus);
            sessionTitle = itemView.findViewById(R.id.sessionTitle);
        }
    }
}
