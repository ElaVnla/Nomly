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
import android.graphics.Bitmap;

import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder> {

    private List<RestaurantCard> cards;

    public CardStackAdapter(List<RestaurantCard> cards) {
        this.cards = cards;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, cuisine, price;
        //ImageView image1, image2, image3, image4;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_title);
            //location = itemView.findViewById(R.id.card_location);
            cuisine = itemView.findViewById(R.id.card_cuisine);
            price = itemView.findViewById(R.id.card_price);
            image = itemView.findViewById(R.id.card_image);
//            image1 = itemView.findViewById(R.id.card_image1);
//            image2 = itemView.findViewById(R.id.card_image2);
//            image3 = itemView.findViewById(R.id.card_image3);
//            image4 = itemView.findViewById(R.id.card_image4);
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
        // holder.location.setText(card.getLocation());
        // holder.price.setText(card.getPriceLevel());
        // holder.cuisine.setText(card.getCuisine());
        String rawCuisine = card.getCuisine(); // e.g. "[fast_food_restaurant]"
        if (rawCuisine != null && !rawCuisine.isEmpty()) {
            String cleanedCuisine = rawCuisine
                    .replaceAll("\\[|\\]", "")           // remove [ and ]
                    .replace("_", " ")                   // replace underscores with spaces
                    .trim();

            // Capitalize first letter
            if (!cleanedCuisine.isEmpty()) {
                cleanedCuisine = cleanedCuisine.substring(0, 1).toUpperCase() + cleanedCuisine.substring(1);
            }

            holder.cuisine.setText(cleanedCuisine);
        } else {
            holder.cuisine.setText(""); // fallback for null or empty
        }

//        String rawCuisine = card.getCuisine(); // e.g. "[fast_food_restaurant]"
//        String cleanedCuisine = rawCuisine
//                .replaceAll("\\[|\\]", "") // remove [ and ]
//                .replace("_", " ")         // replace _ with space
//                .trim();                   // remove whitespace
//        cleanedCuisine = cleanedCuisine.substring(0, 1).toUpperCase() + cleanedCuisine.substring(1);
//
//        holder.cuisine.setText(cleanedCuisine);

        String rawPrice = card.getPriceLevel();
        String mappedPrice;

        switch (rawPrice) {
            case "PRICE_LEVEL_FREE":
                mappedPrice = "FREE!";
                break;
            case "PRICE_LEVEL_INEXPENSIVE":
                mappedPrice = "$";
                break;
            case "PRICE_LEVEL_MODERATE":
                mappedPrice = "$$";
                break;
            case "PRICE_LEVEL_EXPENSIVE":
                mappedPrice = "$$$";
                break;
            case "PRICE_LEVEL_VERY_EXPENSIVE":
                mappedPrice = "$$$$";
                break;
            default:
                mappedPrice = ""; // PRICE_LEVEL_UNSPECIFIED or unknown
        }

        holder.price.setText(mappedPrice);

        holder.image.setImageBitmap(card.getImage());

//        List<Bitmap> images = card.getImages();
//        if (images.size() > 0) holder.image1.setImageBitmap(images.get(0));
//        if (images.size() > 1) holder.image2.setImageBitmap(images.get(1));
//        if (images.size() > 2) holder.image3.setImageBitmap(images.get(2));
//        if (images.size() > 3) holder.image4.setImageBitmap(images.get(3));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public RestaurantCard getCardAt(int position) {
        return cards.get(position);
    }

    public RestaurantCard getCardByEateryId(String id) {
        for (RestaurantCard card : cards) {
            if (card.getEateryId().equals(id)) {
                return card;
            }
        }
        return null;
    }
}
