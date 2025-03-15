package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.databinding.ChatSentLayoutBinding;
import com.w3itexperts.ombe.databinding.FragmentChatBinding;

public class chat extends Fragment {
    FragmentChatBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentChatBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.moreOptionBtn.setOnClickListener(v -> Toast.makeText(getContext(), "More Option Click", Toast.LENGTH_SHORT).show());

        b.sentBtn.setOnClickListener(v -> {
            ChatSentLayoutBinding chatSB = ChatSentLayoutBinding.inflate(getLayoutInflater());
            chatSB.massage.setText(b.message.getText().toString());
            b.containerMain.addView(chatSB.getRoot());
        });
    }
}
