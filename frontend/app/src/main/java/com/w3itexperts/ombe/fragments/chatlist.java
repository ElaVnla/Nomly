package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.ContactItemBinding;
import com.w3itexperts.ombe.databinding.FragmentChatlistBinding;

public class chatlist extends Fragment {
    FragmentChatlistBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentChatlistBinding.inflate(getLayoutInflater());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        b.searchBtn.setOnClickListener(v -> {
        });
        String[] personNames = {
                "John Smith",
                "Emily Johnson",
                "Michael Brown",
                "Sophia Davis",
                "James Wilson",
                "Olivia Martinez",
                "Daniel Taylor",
                "Emma Anderson",
                "Lucas Lee",
                "Ava Harris"
        };

        String[] lastMessages = {
                "Hey, how are you?",
                "Don't forget our meeting tomorrow.",
                "Can you send me the documents?",
                "I'm on my way, see you soon!",
                "Let’s catch up this weekend.",
                "What time works best for you?",
                "Happy Birthday! Hope you have a great day!",
                "Just finished the project, let's review it.",
                "Call me when you're free.",
                "Thanks for your help today!"
        };


        int[] drawable = {
                R.drawable.person1,
                R.drawable.person2,
                R.drawable.person3,
                R.drawable.person1,
                R.drawable.person2,
                R.drawable.person3,
                R.drawable.person1,
                R.drawable.person2,
                R.drawable.person3,
                R.drawable.person1
        };
        b.searchBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, new SearchView());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        });


        for (int i = 0; i < personNames.length; i++) {
            ContactItemBinding cb = ContactItemBinding.inflate(getLayoutInflater());
            cb.name.setText(personNames[i]);
            cb.lastmsg.setText(lastMessages[i]);
            cb.image.setImageResource(drawable[i]);
            b.chatListLayout.addView(cb.getRoot());

            cb.getRoot().setOnClickListener(v -> {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(
                        R.anim.fragment_popup,
                        0,
                        0,
                        R.anim.fragment_popdown);

                transaction.replace(R.id.fragment_view, new chat());
                transaction.addToBackStack(null);
                transaction.commitAllowingStateLoss();
            });

        }
    }
}
