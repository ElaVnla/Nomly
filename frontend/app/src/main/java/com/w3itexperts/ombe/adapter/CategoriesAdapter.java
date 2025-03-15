package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.activity.home;
import com.w3itexperts.ombe.fragments.DetailsFragment;
import com.w3itexperts.ombe.fragments.ProductsFragment;
import com.w3itexperts.ombe.modals.CategoriesModal;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private List<CategoriesModal> categoriesList;
    private Context context;
    FragmentActivity activity;
    public CategoriesAdapter(List<CategoriesModal> categoriesList, Context context, FragmentActivity activity) {
        this.categoriesList = categoriesList;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoriesModal category = categoriesList.get(position);

        holder.coffeeImage.setImageResource(category.getCoffeeImage());
        holder.coffeeName.setText(category.getCoffeeName());
        holder.menusName.setText(category.getMenusName());

        holder.itemView.setOnClickListener(v -> {
            // Replace with the code to open the new fragment
            ProductsFragment productsFragment = new ProductsFragment();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,    // enter
                    0,                        // exit (no exit animation needed when replacing)
                    0,                        // popEnter (no re-enter animation needed)
                    R.anim.fragment_popdown); // popExit

            transaction.replace(R.id.fragment_view, productsFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView coffeeImage;
        TextView coffeeName, menusName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            menusName = itemView.findViewById(R.id.menusName);
        }
    }
}
