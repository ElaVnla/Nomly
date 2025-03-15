package com.w3itexperts.ombe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.AddressModal;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {

    private Context context;
    private List<AddressModal> addressList;
    private int selectedPosition = -1;

    public AddressAdapter(Context context, List<AddressModal> addressList) {
        this.context = context;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AddressModal addressModal = addressList.get(position);

        holder.addressTypeIcon.setImageResource(addressModal.getAddressTypeIcon());
        holder.addressTypeLabel.setText(addressModal.getAddressTypeLabel());
        holder.fullAddress.setText(addressModal.getFullAddress());

        // Change the select image based on whether the item is selected
        if (selectedPosition == position) {
            holder.select.setImageResource(R.drawable.addressactive);
        } else {
            holder.select.setImageResource(R.drawable.addressinactive);
        }

        holder.itemClickBtn.setOnClickListener(v -> {
            // Update the selected position and notify the adapter to refresh the list
            selectedPosition = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {
        ImageView addressTypeIcon, select;
        TextView addressTypeLabel, fullAddress;
        LinearLayout itemClickBtn;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            addressTypeIcon = itemView.findViewById(R.id.addressTypeIcon);
            addressTypeLabel = itemView.findViewById(R.id.addressTypeLable);
            fullAddress = itemView.findViewById(R.id.fullAddress);
            select = itemView.findViewById(R.id.select);
            itemClickBtn = itemView.findViewById(R.id.itemClickBtn);
        }
    }
}
