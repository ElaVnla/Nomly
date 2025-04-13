package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.OnBordingModal;

import java.util.List;

public class OnBordingAdapter extends RecyclerView.Adapter<OnBordingAdapter.OnBordingViewHolder> {

    private List<OnBordingModal> onboardingList;
    private LayoutInflater inflater;

    public OnBordingAdapter(Context context, List<OnBordingModal> onboardingList) {
        this.onboardingList = onboardingList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OnBordingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.on_bording_item_layout, parent, false);
        return new OnBordingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnBordingViewHolder holder, int position) {
        OnBordingModal currentItem = onboardingList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResId());
        holder.titleView.setText(currentItem.getTitle());
        holder.subTitleView.setText(currentItem.getSubtitle());
    }

    @Override
    public int getItemCount() {
        return onboardingList.size();
    }

    public static class OnBordingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;
        TextView subTitleView;

        public OnBordingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            titleView = itemView.findViewById(R.id.titleView);
            subTitleView = itemView.findViewById(R.id.subTitleView);
        }
    }
}
