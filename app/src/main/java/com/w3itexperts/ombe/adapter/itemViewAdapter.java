package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.fragments.DetailsFragment;
import com.w3itexperts.ombe.modals.FeaturedModal;

import java.util.List;

public class itemViewAdapter extends RecyclerView.Adapter<itemViewAdapter.ViewHolder> {

    private List<FeaturedModal> itemList;
    private Context context;
    FragmentActivity activity;
    public itemViewAdapter(Context context, List<FeaturedModal> itemList ,FragmentActivity activity) {
        this.context = context;
        this.itemList = itemList;
        this.activity = activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_breverages_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeaturedModal item = itemList.get(position);
        holder.coffeeName.setText(item.getCoffeeName());
        holder.coffeeTag.setText(item.getCoffeeTag());
        holder.coffeePrice.setText(item.getCoffeePrice());
        holder.coffeeRating.setText(item.getCoffeeRating());
        holder.coffeeImage.setImageResource(item.getCoffeeImage());

        holder.buyCoffee.setOnClickListener(v -> {

            Fragment fragment = new DetailsFragment();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,    // enter
                    0,                        // exit (no exit animation needed when replacing)
                    0,                        // popEnter (no re-enter animation needed)
                    R.anim.fragment_popdown); // popExit

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView coffeeImage;
        public TextView coffeeName, coffeeTag, coffeePrice, coffeeRating;
        public MaterialButton buyCoffee;

        public ViewHolder(View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeeTag = itemView.findViewById(R.id.coffeeTag);
            coffeePrice = itemView.findViewById(R.id.coffeePrice);
            coffeeRating = itemView.findViewById(R.id.coffeeRating);
            buyCoffee = itemView.findViewById(R.id.buyCoffee);
        }
    }
}
