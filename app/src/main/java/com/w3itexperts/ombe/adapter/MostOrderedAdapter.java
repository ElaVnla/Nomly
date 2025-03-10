package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.MostOrderedModal;

import java.util.List;

public class MostOrderedAdapter extends RecyclerView.Adapter<MostOrderedAdapter.MostOrderedViewHolder> {

    private List<MostOrderedModal> mostOrderedList;
    private Context context;

    public MostOrderedAdapter(Context context, List<MostOrderedModal> mostOrderedList) {
        this.context = context;
        this.mostOrderedList = mostOrderedList;
    }

    @NonNull
    @Override
    public MostOrderedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.most_ordered_item, parent, false);
        return new MostOrderedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostOrderedViewHolder holder, int position) {
        MostOrderedModal modal = mostOrderedList.get(position);
        holder.coffeeImage.setImageResource(modal.getCoffeeImage());
        holder.coffeeName.setText(modal.getCoffeeName());
        holder.coffeeTag.setText(modal.getCoffeeTag());
    }

    @Override
    public int getItemCount() {
        return mostOrderedList.size();
    }

    public static class MostOrderedViewHolder extends RecyclerView.ViewHolder {

        ImageView coffeeImage;
        TextView coffeeName, coffeeTag;

        public MostOrderedViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeeTag = itemView.findViewById(R.id.coffeeTag);
        }
    }
}
