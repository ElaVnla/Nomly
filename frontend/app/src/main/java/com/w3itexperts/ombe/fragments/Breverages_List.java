package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.w3itexperts.ombe.adapter.itemViewAdapter;
import com.w3itexperts.ombe.databinding.FragmentBreveragesBinding;
import com.w3itexperts.ombe.methods.DataGenerator;

public class Breverages_List extends Fragment {
    FragmentBreveragesBinding b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentBreveragesBinding.inflate(inflater,container,false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemViewAdapter adapter = new itemViewAdapter(getContext(), DataGenerator.ItemViewList(),getActivity());
        b.itemView.setAdapter(adapter );
        b.itemView.setLayoutManager(new LinearLayoutManager(getContext()));


    }

}