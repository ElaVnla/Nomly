package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentComponentsBinding;

public class Components extends Fragment {
    FragmentComponentsBinding b;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentComponentsBinding.inflate(inflater,container,false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.accordionList.setOnClickListener(v->  SwitchFragment(new AccordionList()));
        b.bottomSheet.setOnClickListener(v->  SwitchFragment(new BottomSheet()));
        b.modalBox.setOnClickListener(v->  SwitchFragment(new ModalBox()));
        b.buttonStyle.setOnClickListener(v->  SwitchFragment(new ButtomStyle()));
        b.badges.setOnClickListener(v->  SwitchFragment(new Badges()));
        b.charts.setOnClickListener(v->  SwitchFragment(new Charts()));
        b.inputs.setOnClickListener(v->  SwitchFragment(new Inputs()));
        b.listtStyle.setOnClickListener(v->  SwitchFragment(new ListStyles()));
        b.pricings.setOnClickListener(v->  SwitchFragment(new Pricings()));
        b.snackbar.setOnClickListener(v->  SwitchFragment(new SnackbarFragment()));
        b.socials.setOnClickListener(v->  SwitchFragment(new Socials()));
        b.swipeable.setOnClickListener(v->  SwitchFragment(new Swipeable()));
        b.tabs.setOnClickListener(v->  SwitchFragment(new Tabs()));
        b.toggle.setOnClickListener(v->  SwitchFragment(new ToggleButtons()));
        b.tables.setOnClickListener(v->  SwitchFragment(new Tables()));

    }

    private void SwitchFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(
                R.anim.fragment_popup,
                0,
                0,
                R.anim.fragment_popdown);
        transaction.replace(R.id.fragment_view, fragment);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }
}
