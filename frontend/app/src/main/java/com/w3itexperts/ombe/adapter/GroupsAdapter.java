//package com.w3itexperts.ombe.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.w3itexperts.ombe.R;
//import com.w3itexperts.ombe.modals.CoffeeItem;
//
//import java.util.List;
//
//public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupsViewHolder> {
//    private List<CoffeeItem> coffeeItems;
//
//    public CoffeeAdapter(List<CoffeeItem> coffeeItems) {
//        this.coffeeItems = coffeeItems;
//    }
//
//    @NonNull
//    @Override
//    public CoffeeAdapter.CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_slider_view, parent, false);
//        return new CoffeeAdapter.CoffeeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CoffeeAdapter.CoffeeViewHolder holder, int position) {
//        // Calculate the actual position of the item in the list to handle looping
//        int actualPosition = position % coffeeItems.size();
//        CoffeeItem item = coffeeItems.get(actualPosition);
//
//        holder.titleTextView.setText(item.getTitle());
//        holder.priceTextView.setText(item.getPrice());
//        holder.imageView.setImageResource(item.getImageResId());
//    }
//
//    @Override
//    public int getItemCount() {
//        // Return a large number to simulate infinite scrolling
//        return Integer.MAX_VALUE;
//    }
//
//    static class CoffeeViewHolder extends RecyclerView.ViewHolder {
//        ImageView imageView;
//        TextView titleTextView;
//        TextView priceTextView;
//
//        CoffeeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imageView);
//            titleTextView = itemView.findViewById(R.id.titleTextView);
//            priceTextView = itemView.findViewById(R.id.priceTextView);
//        }
//    }
//}
