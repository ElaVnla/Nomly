package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.CartModal;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartModal> cartItems;
    private Context context;
    private TextView totalPriceTextView, allprice, dicPrice;

    public CartAdapter(List<CartModal> cartItems, Context context, TextView totalPriceTextView, TextView allprice, TextView dicPrice) {
        this.cartItems = cartItems;
        this.context = context;
        this.totalPriceTextView = totalPriceTextView;
        this.allprice = allprice;
        this.dicPrice = dicPrice;
        calculateTotalPrice();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModal item = cartItems.get(position);

        holder.coffeeImage.setImageResource(item.getCoffeeImage());
        holder.coffeeName.setText(item.getCoffeeName());
        holder.coffeePrice.setText(item.getCoffeePrice());
        holder.inDecrement.setText(String.valueOf(item.getQuantity()));

        holder.increment.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.inDecrement.setText(String.valueOf(item.getQuantity()));
            calculateTotalPrice();
        });

        holder.decrement.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.inDecrement.setText(String.valueOf(item.getQuantity()));
                calculateTotalPrice();
            }
        });

        holder.remove.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            calculateTotalPrice();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImage;
        TextView coffeeName, coffeePrice, inDecrement;
        MaterialButton increment, decrement, remove;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeePrice = itemView.findViewById(R.id.coffeePrice);
            inDecrement = itemView.findViewById(R.id.inDecrement);
            increment = itemView.findViewById(R.id.increment);
            decrement = itemView.findViewById(R.id.decrement);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    private void calculateTotalPrice() {
        double total = 0;
        double onPrice = 0;
        double dis = 0;
        for (CartModal item : cartItems) {
            total += item.getTotalPrice();
            onPrice += item.getTotalPrice() + 2;
            dis += item.getTotalPrice() - 2;

        }

        allprice.setText(String.format("$%.2f", onPrice));
        dicPrice.setText(String.format("$%.2f", total - dis));
        totalPriceTextView.setText(String.format("$%.2f", total));
    }
}
