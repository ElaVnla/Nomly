package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.CardAdapter;
import com.w3itexperts.ombe.databinding.FragmentPaymentMethodsBinding;
import com.w3itexperts.ombe.methods.DataGenerator;
import com.w3itexperts.ombe.methods.anim;
import com.w3itexperts.ombe.modals.CardModal;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethods extends Fragment {
    FragmentPaymentMethodsBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentPaymentMethodsBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.lblCod.setOnClickListener(v -> paymentMethodSelection(b.imgCodSelection,null));
        b.lblUpi.setOnClickListener(v -> paymentMethodSelection(b.imgUpiSelectionImg,b.upiItemView));
        b.lblWallet.setOnClickListener(v -> paymentMethodSelection(b.imgWalletSelect,b.walletItemView));
        b.lblNetBank.setOnClickListener(v -> paymentMethodSelection(b.imgNetBankSelection,b.netItemView));

        b.creditCardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        b.creditCardRecyclerView.setAdapter( new CardAdapter(getContext(), DataGenerator.cardList()));

        b.addCardBtn.setOnClickListener(v -> {

            Fragment fragment = new AddCard();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        });

        b.paymentContinueBtn.setOnClickListener(v -> {

            Fragment fragment = new CheckOut();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, fragment);
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        });

    }

    private void paymentMethodSelection(ImageView imgCodSelection , LinearLayout view) {

        b.imgCodSelection.setImageResource(R.drawable.addressinactive);
        b.imgUpiSelectionImg.setImageResource(R.drawable.addressinactive);
        b.imgWalletSelect.setImageResource(R.drawable.addressinactive);
        b.imgNetBankSelection.setImageResource(R.drawable.addressinactive);
        imgCodSelection.setImageResource(R.drawable.addressactive);

        b.upiItemView.setVisibility(View.GONE);
        b.walletItemView.setVisibility(View.GONE);
        b.netItemView.setVisibility(View.GONE);
        if (view!=null) {
            view.setVisibility(View.VISIBLE);
            anim.expand(view);
        }
    }

}
