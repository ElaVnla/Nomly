package com.w3itexperts.ombe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.SwipeableModal;

import java.util.List;

public class SwipeableAdapter extends RecyclerView.Adapter<SwipeableAdapter.SwipeViewHolder> {

    private List<SwipeableModal> itemList;
    private Context context;

    public SwipeableAdapter(List<SwipeableModal> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipeable, parent, false);
        return new SwipeViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {
        SwipeableModal item = itemList.get(position);

        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.image.setImageResource(item.getProfileImage());

        holder.btnDelete.setOnClickListener(v -> {
            Toast.makeText(context, "Deleted: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            deleteItem(position);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Edit: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        });

        holder.btnShare.setOnClickListener(v -> {
            Toast.makeText(context, "Share: " + item.getTitle(), Toast.LENGTH_SHORT).show();
//            deleteItem(position);
        });

        holder.btnSave.setOnClickListener(v -> {
            Toast.makeText(context, "Save: " + item.getTitle(), Toast.LENGTH_SHORT).show();
//            deleteItem(position);
        });

    }
    public void removeItem(int position) {
        itemList.remove(position);  // Remove the item from the data list
        notifyItemRemoved(position);  // Notify the adapter that an item was removed
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void deleteItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    static class SwipeViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        ImageView image;
        MaterialCardView itemView;
        LinearLayout deleteLayout, editRemoveLayout;
        TextView btnDelete, btnEdit,btnShare,btnSave;

        SwipeViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.date = itemView.findViewById(R.id.date);
            this.image = itemView.findViewById(R.id.image);
            this.itemView = itemView.findViewById(R.id.item_view);
            this.deleteLayout = itemView.findViewById(R.id.delete_layout);
            this.editRemoveLayout = itemView.findViewById(R.id.edit_remove_layout);
            this.btnDelete = itemView.findViewById(R.id.btn_delete);
            this.btnEdit = itemView.findViewById(R.id.btn_edit);
            this.btnShare = itemView.findViewById(R.id.btn_share);
            this.btnSave = itemView.findViewById(R.id.btn_seva);
        }
    }
}

/*

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.SwipeableModal;

import android.widget.Button;

import java.util.List;


public class SwipeableAdapter extends RecyclerView.Adapter<SwipeableAdapter.SwipeViewHolder> {

    private List<SwipeableModal> itemList;
    private Context context;

    public SwipeableAdapter(List<SwipeableModal> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipeable, parent, false);
        return new SwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder holder, int position) {
        SwipeableModal item = itemList.get(position);

        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.image.setImageResource(item.getProfileImage());

        holder.btnDelete.setOnClickListener(v -> {
            Toast.makeText(context, "Deleted: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            deleteItem(position);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Toast.makeText(context, "Edit: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        });

        holder.btnRemove.setOnClickListener(v -> {
            Toast.makeText(context, "Removed: " + item.getTitle(), Toast.LENGTH_SHORT).show();
            deleteItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void deleteItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    static class SwipeViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        ImageView image;
        MaterialCardView itemView;
        LinearLayout deleteLayout, editRemoveLayout;
        Button btnDelete, btnEdit, btnRemove;

        SwipeViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.date = itemView.findViewById(R.id.date);
            this.image = itemView.findViewById(R.id.image);
            this.itemView = itemView.findViewById(R.id.item_view);
            this.deleteLayout = itemView.findViewById(R.id.delete_layout);
            this.editRemoveLayout = itemView.findViewById(R.id.edit_remove_layout);
            this.btnDelete = itemView.findViewById(R.id.btn_delete);
            this.btnEdit = itemView.findViewById(R.id.btn_edit);
            this.btnRemove = itemView.findViewById(R.id.btn_remove);
        }
    }
}
*/
