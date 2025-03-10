package com.w3itexperts.ombe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.modals.CardModal;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardModal> cardList;
    private Context context;
    private int selectedPosition = -1;

    public CardAdapter(Context context, List<CardModal> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CardModal card = cardList.get(position);

        holder.cardTypeNameIcon.setText(card.getCardTypeName());
        holder.cardNumber.setText(card.getCardNumber());
        holder.cardHolderName.setText(card.getCardHolderName());
        holder.expDateYear.setText(card.getExpDate());
        holder.cvv.setText(card.getCvv());

        if (selectedPosition == position) {
            holder.cradSelection.setImageResource(R.drawable.addressactive);
        } else {
            holder.cradSelection.setImageResource(R.drawable.addressinactive);
        }

        holder.cradSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Update the selected position
                if (selectedPosition == position) {
                    selectedPosition = -1;
                } else {
                    selectedPosition = position;
                }
                notifyDataSetChanged(); // Refresh the list to update the selection state
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView cardTypeNameIcon, cardNumber, cardHolderName, expDateYear, cvv;
        ImageView cradSelection;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardTypeNameIcon = itemView.findViewById(R.id.cardTypeNameIcon);
            cardNumber = itemView.findViewById(R.id.cardNumber);
            cardHolderName = itemView.findViewById(R.id.cardHolderName);
            expDateYear = itemView.findViewById(R.id.expDateYear);
            cvv = itemView.findViewById(R.id.cvv);
            cradSelection = itemView.findViewById(R.id.cradSelection);
        }
    }
}
