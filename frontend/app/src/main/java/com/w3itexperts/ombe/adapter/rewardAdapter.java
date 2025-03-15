package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.rewardModal;

import java.util.List;

public class rewardAdapter extends RecyclerView.Adapter<rewardAdapter.ViewHolder> {

    private List<rewardModal> rewardList;
    private Context context;

    public rewardAdapter(List<rewardModal> rewardList, Context context) {
        this.rewardList = rewardList;
        this.context = context;
    }

    @NonNull
    @Override
    public rewardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rewards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rewardAdapter.ViewHolder holder, int position) {
        rewardModal reward = rewardList.get(position);
        holder.rewardTitle.setText(reward.getRewardTitle());
        holder.rewardDateTime.setText(reward.getRewardDateTime());
        holder.rewardPoints.setText(reward.getRewardPoints());
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView rewardTitle, rewardDateTime, rewardPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardTitle = itemView.findViewById(R.id.rewardTitle);
            rewardDateTime = itemView.findViewById(R.id.rewardDateTime);
            rewardPoints = itemView.findViewById(R.id.rewardPoints);
        }
    }
}
