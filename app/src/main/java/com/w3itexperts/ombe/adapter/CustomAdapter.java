package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.Item;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public CustomAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_swipeable, parent, false);
        return new RecyclerView.ViewHolder(view) {};  // Using anonymous ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View itemView = holder.itemView;  // Access the itemView directly
        TextView title = itemView.findViewById(R.id.title);
        TextView date = itemView.findViewById(R.id.date);
        ImageView image = itemView.findViewById(R.id.image);

        Item item = itemList.get(position);
        title.setText(item.getTitle());
        date.setText(item.getDate());
        image.setImageResource(item.getImageResId());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
