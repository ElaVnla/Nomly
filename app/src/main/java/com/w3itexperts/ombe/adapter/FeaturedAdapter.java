package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.fragments.DetailsFragment;
import com.w3itexperts.ombe.modals.FeaturedModal;

import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    private List<FeaturedModal> featuredList;
    private Context context;

    public FeaturedAdapter(List<FeaturedModal> featuredList, Context context) {
        this.featuredList = featuredList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeaturedModal item = featuredList.get(position);

        holder.coffeeImage.setImageResource(item.getCoffeeImage());
        holder.coffeeName.setText(item.getCoffeeName());
        holder.coffeeRating.setText(item.getCoffeeRating());
        holder.coffeePrice.setText(item.getCoffeePrice());
        holder.coffeePts.setText(item.getCoffeePts());

        holder.itemView.setOnClickListener(v -> {

            Fragment fragment = new DetailsFragment();
            FragmentTransaction transaction = ((home) context).getSupportFragmentManager().beginTransaction();

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
        return featuredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImage;
        TextView coffeeName, coffeeRating, coffeePrice, coffeePts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeeRating = itemView.findViewById(R.id.coffeeRating);
            coffeePrice = itemView.findViewById(R.id.coffeePrice);
            coffeePts = itemView.findViewById(R.id.coffeePts);
        }
    }
}
