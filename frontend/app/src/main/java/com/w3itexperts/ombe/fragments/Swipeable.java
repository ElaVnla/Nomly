package com.w3itexperts.ombe.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.adapter.CustomAdapter;
import com.w3itexperts.ombe.adapter.SwipeToDeleteCallback;
import com.w3itexperts.ombe.adapter.SwipeToRevealCallback;
import com.w3itexperts.ombe.adapter.SwipeableAdapter;
import com.w3itexperts.ombe.databinding.FragmentSwipeableBinding;
import com.w3itexperts.ombe.modals.Item;
import com.w3itexperts.ombe.modals.SwipeableModal;
import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yanzhenjie.recyclerview.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class Swipeable extends Fragment {
    FragmentSwipeableBinding b;
    private CustomAdapter customAdapter;
    private List<Item> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentSwipeableBinding.inflate(inflater, container, false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        List<SwipeableModal> swipItems = new ArrayList<>();
        swipItems.add(new SwipeableModal("Socials Icons", "15 Jan 2024", R.drawable.person1));
        swipItems.add(new SwipeableModal("Another Item", "16 Jan 2024", R.drawable.person2));

        SwipeableAdapter adapter = new SwipeableAdapter(swipItems, getContext());
        b.itemSwipeable.setLayoutManager(new LinearLayoutManager(getContext()));
        b.itemSwipeable.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper1 = new ItemTouchHelper(new SwipeToRevealCallback(adapter, getContext()));
        itemTouchHelper1.attachToRecyclerView(b.itemSwipeable);

       }
}
