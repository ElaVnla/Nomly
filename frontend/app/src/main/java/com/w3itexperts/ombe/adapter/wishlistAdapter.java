package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.fragments.DetailsFragment;
import com.w3itexperts.ombe.fragments.Profile;
import com.w3itexperts.ombe.modals.wishlistModal;

import java.util.List;

public class wishlistAdapter extends RecyclerView.Adapter<wishlistAdapter.ViewHolder> {

    private List<wishlistModal> wishlist;
    private Context context;

    public wishlistAdapter(List<wishlistModal> wishlist, Context context) {
        this.wishlist = wishlist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        wishlistModal item = wishlist.get(position);

        holder.coffeeImage.setImageResource(item.getCoffeeImage());
        holder.coffeeName.setText(item.getCoffeeName());
        holder.coffeeTag.setText(item.getCoffeeTag());
        holder.coffeePrice.setText(item.getCoffeePrice());

        // Set initial icon color based on wishlist status
        if (item.isInWishlist()) {
            holder.wishlistAddRemoveBtn.setIconTint(ColorStateList.valueOf(Color.RED));
        } else {
            holder.wishlistAddRemoveBtn.setIconTint(ColorStateList.valueOf(Color.GRAY));
        }

        holder.wishlistAddRemoveBtn.setOnClickListener(view -> {
            // Toggle wishlist status
            if (item.isInWishlist()) {
                item.setInWishlist(false);
                holder.wishlistAddRemoveBtn.setIconTint(ColorStateList.valueOf(Color.GRAY));
            } else {
                item.setInWishlist(true);
                holder.wishlistAddRemoveBtn.setIconTint(ColorStateList.valueOf(Color.RED));
            }
        });


        holder.itemView.setOnClickListener(view -> {
            Fragment fragment = new DetailsFragment();
            FragmentTransaction transaction = ((home) context).getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();


        });


    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImage;
        TextView coffeeName, coffeeTag, coffeePrice;
        MaterialButton wishlistAddRemoveBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeeTag = itemView.findViewById(R.id.coffeeTag);
            coffeePrice = itemView.findViewById(R.id.coffeePrice);
            wishlistAddRemoveBtn = itemView.findViewById(R.id.wishlistAddRemoveBtn);
        }
    }
}