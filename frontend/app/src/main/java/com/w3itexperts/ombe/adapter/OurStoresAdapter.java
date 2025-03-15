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
import com.w3itexperts.ombe.modals.OurStoresModal;

import java.util.List;

public class OurStoresAdapter extends RecyclerView.Adapter<OurStoresAdapter.ViewHolder> {
    private List<OurStoresModal> storesList;
    private Context context;

    public OurStoresAdapter(Context context, List<OurStoresModal> storesList) {
        this.context = context;
        this.storesList = storesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_near_me, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OurStoresModal store = storesList.get(position);
        holder.coffeeImage.setImageResource(store.getCoffeeImage());
        holder.storeName.setText(store.getStoreName());
        holder.storeTiming.setText(store.getStoreTiming());
        holder.storeDistance.setText(store.getStoreDistance());

        holder.directions.setOnClickListener(v -> {
            // Handle directions click
        });
    }

    @Override
    public int getItemCount() {
        return storesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImage;
        TextView storeName, storeTiming, storeDistance, directions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            storeName = itemView.findViewById(R.id.storeName);
            storeTiming = itemView.findViewById(R.id.storeTiming);
            storeDistance = itemView.findViewById(R.id.storeDistance);
            directions = itemView.findViewById(R.id.directions);
        }
    }
}
