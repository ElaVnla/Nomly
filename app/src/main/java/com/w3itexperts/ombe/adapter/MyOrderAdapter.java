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

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.fragments.PaymentMethods;
import com.w3itexperts.ombe.fragments.Review;
import com.w3itexperts.ombe.fragments.TrackingOrder;
import com.w3itexperts.ombe.modals.MyOrderModal;

import java.util.ArrayList;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {

    private Context context;
    private List<MyOrderModal> orderList;
    private List<MyOrderModal> orderListFull;
    FragmentActivity activity;

    public MyOrderAdapter(Context context, List<MyOrderModal> orderList , FragmentActivity activity) {
        this.context = context;
        this.orderList = orderList;
        this.activity = activity;
        this.orderListFull = new ArrayList<>(orderList);
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ongoing_item, parent, false);
        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        MyOrderModal order = orderList.get(position);
        holder.coffeeName.setText(order.getCoffeeName());
        holder.coffeeTag.setText(order.getCoffeeTag());
        holder.coffeePrice.setText(order.getCoffeePrice());
        holder.coffeeRating.setText(order.getCoffeeRating());
        holder.coffeeImage.setImageResource(order.getCoffeeImage());

        // Set button text based on order status
        if (order.getOrderStatus().equals("Ongoing")) {
            holder.trackOrderWriteReviewBtn.setText("Track Order");
        } else {
            holder.trackOrderWriteReviewBtn.setText("Write Review");
        }

        // Set onClickListener for the button
        holder.trackOrderWriteReviewBtn.setOnClickListener(v -> {

          if(order.getOrderStatus().equals("Ongoing")){
              Fragment fragment = new TrackingOrder();
              FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

              transaction.setCustomAnimations(
                      R.anim.fragment_popup,
                      0,
                      0,
                      R.anim.fragment_popdown);

              transaction.replace(R.id.fragment_view, fragment);
              transaction.addToBackStack(null);
              transaction.commitAllowingStateLoss();

          }else {
              Fragment fragment = new Review();
              FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

              transaction.setCustomAnimations(
                      R.anim.fragment_popup,
                      0,
                      0,
                      R.anim.fragment_popdown);

              transaction.replace(R.id.fragment_view, fragment);
              transaction.addToBackStack(null);
              transaction.commitAllowingStateLoss();
          }



        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Method to filter the orders by status
    public void filter(String status) {
        orderList.clear();
        if (status.equals("All")) {
            orderList.addAll(orderListFull); // Show all items
        } else {
            for (MyOrderModal order : orderListFull) {
                if (order.getOrderStatus().equals(status)) {
                    orderList.add(order);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class MyOrderViewHolder extends RecyclerView.ViewHolder {
        ImageView coffeeImage;
        TextView coffeeName, coffeeTag, coffeePrice, coffeeRating;
        MaterialButton trackOrderWriteReviewBtn;

        public MyOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            coffeeImage = itemView.findViewById(R.id.coffeeImage);
            coffeeName = itemView.findViewById(R.id.coffeeName);
            coffeeTag = itemView.findViewById(R.id.coffeeTag);
            coffeePrice = itemView.findViewById(R.id.coffeePrice);
            coffeeRating = itemView.findViewById(R.id.coffeeRating);
            trackOrderWriteReviewBtn = itemView.findViewById(R.id.trackOrder_writeReviewBtn);
        }
    }
}
