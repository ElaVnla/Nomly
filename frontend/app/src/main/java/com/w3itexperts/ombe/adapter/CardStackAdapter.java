package com.w3itexperts.ombe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.RestaurantCard;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<RestaurantCard> cards;

    public CardStackAdapter(List<RestaurantCard> cards) {
        this.cards = cards;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, location, price;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            location = itemView.findViewById(R.id.card_location);
            price = itemView.findViewById(R.id.card_price);
            image = itemView.findViewById(R.id.card_image);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantCard card = cards.get(position);
        holder.title.setText(card.getName());
        //holder.location.setText(card.getLocation());
        holder.price.setText(card.getPriceLevel());
        holder.image.setImageBitmap(card.getImage());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public RestaurantCard getCardAt(int position) {
        return cards.get(position);
    }
}
