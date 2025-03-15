package com.w3itexperts.ombe.fragments;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentToggleButtonsBinding;

public class ToggleButtons extends Fragment {
    FragmentToggleButtonsBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentToggleButtonsBinding.inflate(inflater, container, false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        b.toggleSelect0.setOnClickListener(v -> multiSelect(b.toggleSelect0));
//        b.toggleSelect1.setOnClickListener(v -> multiSelect(b.toggleSelect1));
//        b.toggleSelect2.setOnClickListener(v -> multiSelect(b.toggleSelect2));
//        b.toggleSelect3.setOnClickListener(v -> multiSelect(b.toggleSelect3));
//        b.toggleSelect4.setOnClickListener(v -> multiSelect(b.toggleSelect4));

//        android:textColor="@color/unselected_button_text_color"
//        app:strokeColor="@color/unselected_button_stroke_color"
//
//        android:textColor="@color/selected_button_text_color"
//        app:strokeColor="@color/selected_button_stroke_color"
//        app:backgroundTint="@color/selected_button_color"

        b.toggleSelectApple.setOnClickListener(v -> toggle(b.toggleSelectApple,R.color.selected_button_text_color,R.color.selected_button_stroke_color,R.color.selected_button_color,R.color.unselected_button_text_color,R.color.unselected_button_stroke_color));
        b.toggleSelectBanana.setOnClickListener(v -> toggle(b.toggleSelectBanana,R.color.selected_button_text_color,R.color.selected_button_stroke_color,R.color.selected_button_color,R.color.unselected_button_text_color,R.color.unselected_button_stroke_color));
        b.toggleSelectOrange.setOnClickListener(v -> toggle(b.toggleSelectOrange,R.color.selected_button_text_color,R.color.selected_button_stroke_color,R.color.selected_button_color,R.color.unselected_button_text_color,R.color.unselected_button_stroke_color));
//
//
//        android:textColor="@color/colorUnselectedText"
//        app:strokeColor="@color/colorOutline"

//        android:textColor="@color/colorSelectedText"
//        app:backgroundTint="@color/colorSelectedBackground"
//        app:strokeColor="@color/colorSelectedOutline"

        b.toggleSelectTomatoes.setOnClickListener(v -> multiSelect(b.toggleSelectTomatoes,R.color.colorSelectedText,R.color.colorSelectedOutline,R.color.colorSelectedBackground,R.color.colorSelectedText,R.color.colorOutline));
        b.toggleSelectPotatoes.setOnClickListener(v -> multiSelect(b.toggleSelectPotatoes,R.color.colorSelectedText,R.color.colorSelectedOutline,R.color.colorSelectedBackground,R.color.colorSelectedText,R.color.colorOutline));
        b.toggleSelectCarrots.setOnClickListener(v ->   multiSelect(b.toggleSelectCarrots,R.color.colorSelectedText,R.color.colorSelectedOutline,R.color.colorSelectedBackground,R.color.colorSelectedText,R.color.colorOutline));

        b.toggleSelectTomatoes.setSelected(true);
        b.toggleSelectPotatoes.setSelected(false);
        b.toggleSelectCarrots.setSelected(true);


//        unselect
//        app:strokeColor="@color/colorOutlineIcon" unslt

//        select
//        app:backgroundTint="@color/colorSelectedBackgroundIcon"
//        app:strokeColor="@color/colorOutlineIcon"

        b.sun.setOnClickListener(v -> toggle(b.sun,R.color.selected_button_text_color,R.color.colorOutlineIcon,R.color.colorSelectedBackgroundIcon,R.color.unselected_button_text_color,R.color.colorOutlineIcon));
        b.cloud.setOnClickListener(v -> toggle(b.cloud,R.color.selected_button_text_color,R.color.colorOutlineIcon,R.color.colorSelectedBackgroundIcon,R.color.unselected_button_text_color,R.color.colorOutlineIcon));
        b.opIcon.setOnClickListener(v -> toggle(b.opIcon,R.color.selected_button_text_color,R.color.colorOutlineIcon,R.color.colorSelectedBackgroundIcon,R.color.unselected_button_text_color,R.color.colorOutlineIcon));




    }

    private void toggle(MaterialButton toggleSelect,int sltBtnTxtColor,int sltBtnStrokeColor,int sltBtnColor,int unSltBtnTextColor,int unSltStrokeColor) {

      LinearLayout viewParent = (LinearLayout) toggleSelect.getParent();

        for (int i = 0; i < viewParent.getChildCount(); i++) {
            View view = viewParent.getChildAt(i);
            MaterialButton button = view.findViewWithTag(toggleSelect.getTag());

            if (button != null) {
                if (button == toggleSelect) {
                    // Selected button
                    button.setBackgroundColor(ContextCompat.getColor(getContext(), sltBtnColor));
                    button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), sltBtnStrokeColor)));
                    button.setStrokeWidth(5);
                    button.setTextColor(ContextCompat.getColor(getContext(), sltBtnTxtColor));

                } else {
                    // Other buttons
                    button.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_transparent));
                    button.setTextColor(ContextCompat.getColor(getContext(), unSltBtnTextColor));
                    button.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), unSltStrokeColor)));
                    button.setStrokeWidth(2);
                }
            }
        }


    }


    private void multiSelect(MaterialButton toggleSelect,int sltBtnTxtColor,int sltBtnStrokeColor,int sltBtnColor,int unSltBtnTextColor,int unSltStrokeColor) {

        if (toggleSelect.isSelected()) {
            toggleSelect.setSelected(false);
            toggleSelect.setBackgroundColor(ContextCompat.getColor(getContext(), sltBtnColor));
            toggleSelect.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), sltBtnStrokeColor)));
            toggleSelect.setStrokeWidth(5);
            toggleSelect.setTextColor(ContextCompat.getColor(getContext(), sltBtnTxtColor));
        } else {
            toggleSelect.setSelected(true);
            toggleSelect.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_transparent));
            toggleSelect.setTextColor(ContextCompat.getColor(getContext(), unSltBtnTextColor));
            toggleSelect.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(getContext(), unSltStrokeColor)));
            toggleSelect.setStrokeWidth(1);
        }
    }
}
