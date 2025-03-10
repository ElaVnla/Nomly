package com.w3itexperts.ombe.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentAccordionListBinding;
import com.w3itexperts.ombe.methods.anim;

public class AccordionList extends Fragment {
    FragmentAccordionListBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentAccordionListBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        b.item1.setOnClickListener(v -> toggleItemsVisibility(b.items, b.imgArrow,b.ca));
        b.item2.setOnClickListener(v -> toggleItemsVisibility(b.items2, b.imgArrow2,b.ca));
        b.item3.setOnClickListener(v -> toggleItemsVisibility(b.items3, b.imgArrow3,b.ca));

        b.itemAvb0.setOnClickListener(v -> toggleItemsVisibility(b.itemsAvb0, b.imgArrowAvb0,b.awb));
        b.itemAvb1.setOnClickListener(v -> toggleItemsVisibility(b.itemsAvb1, b.imgArrowAvb1,b.awb));
        b.itemAvb2.setOnClickListener(v -> toggleItemsVisibility(b.itemsAvb2, b.imgArrowAvb2,b.awb));






    }

    private void items(LinearLayout items, ImageView imgArrow) {
//        b.items.setVisibility(View.GONE);

//        b.imgArrow.setImageResource(R.drawable.ic_arrow_down);
        if (items.getVisibility() == View.GONE) {
            items.setVisibility(View.VISIBLE);
            imgArrow.setImageResource(R.drawable.ic_arrow_up);
            anim.expand(items);

        } else {
            for (int i = 0; i < b.container.getChildCount(); i++) {
                View view = b.container.getChildAt(i);
                LinearLayout layout = view.findViewWithTag("tag");
//              LinearLayout click = view.findViewWithTag("click");
                ImageView img = view.findViewWithTag("imgArrow");
                layout.setVisibility(View.VISIBLE);
                img.setImageResource(R.drawable.ic_arrow_down);
                anim.collapse(layout);

            }
        }
    }

    private void toggleItemsVisibility(LinearLayout items, ImageView imgArrow,LinearLayout container) {
        // Check the visibility state of the provided 'items' LinearLayout

        if (items.getVisibility() == View.GONE) {

            for (int i = 0; i < container.getChildCount(); i++) {
                View view = container.getChildAt(i);

                // Find child views by tag
                LinearLayout layout = view.findViewWithTag(items.getTag());
                ImageView img = view.findViewWithTag(imgArrow.getTag());

                if (layout != null && img != null) {
                    // Collapse the layout and set the arrow down
                    if (items.getId() != layout.getId()) {
                        anim.collapse(layout);  // Assuming 'anim' is an instance of some animation utility
                        img.setImageResource(R.drawable.ic_arrow_down);
                    }
                }
            }
            // Make the 'items' layout visible
            items.setVisibility(View.VISIBLE);
            imgArrow.setImageResource(R.drawable.ic_arrow_up);
            anim.expand(items);  // Assuming 'anim' is an instance of some animation utility


        } else {
            // Iterate through all children of the container to reset their state
            for (int i = 0; i < container.getChildCount(); i++) {
                View view = container.getChildAt(i);

                // Find child views by tag
                LinearLayout layout = view.findViewWithTag(items.getTag());
                ImageView img = view.findViewWithTag(imgArrow.getTag());

                if (layout != null && img != null) {
                    // Collapse the layout and set the arrow down
                    if (items.getId() != layout.getId()) {
                        anim.collapse(layout);  // Assuming 'anim' is an instance of some animation utility
                        img.setImageResource(R.drawable.ic_arrow_down);
                    }
                }
            }
        }
    }


}
