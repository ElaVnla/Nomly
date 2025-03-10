package com.w3itexperts.ombe.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentDetailsBinding;

public class DetailsFragment extends Fragment {
    FragmentDetailsBinding b;
    private BottomSheetBehavior<View> bottomSheetBehavior;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentDetailsBinding.inflate(inflater, container, false);
        return b.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());

        // Initialize the Bottom Sheet
        View bottomSheet = getActivity().findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Set the Bottom Sheet to expanded state and make it persistent
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
//        bottomSheetBehavior.setDraggable(false); // Disable dragging

        // Optional: Disable hiding the Bottom Sheet when the outside is touched
        bottomSheetBehavior.setHideable(false);

        // Optional: Disable closing on outside touch if using DialogFragment
        bottomSheet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true; // Consume the touch event
            }
        });

        b.placeOrderBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(
                    R.anim.fragment_popup,
                    0,
                    0,
                    R.anim.fragment_popdown);

            transaction.replace(R.id.fragment_view, new Cart());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();

        });

        SeekBar sizeSeekBar = b.sizeSeekBar;
        TextView sizeSmall = b.sizeSmall;
        TextView sizeMedium = b.sizeMedium;
        TextView sizeLarge = b.sizeLarge;
        TextView sizeXtraLarge = b.sizeXtraLarge;

        sizeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                resetTextColors();
                switch (progress) {
                    case 0:
                        sizeSmall.setTextColor(getResources().getColor(R.color.black));
                        sizeSmall.setTypeface(null, Typeface.BOLD);
                        break;
                    case 1:
                        sizeMedium.setTextColor(getResources().getColor(R.color.black));
                        sizeMedium.setTypeface(null, Typeface.BOLD);
                        break;
                    case 2:
                        sizeLarge.setTextColor(getResources().getColor(R.color.black));
                        sizeLarge.setTypeface(null, Typeface.BOLD);
                        break;
                    case 3:
                        sizeXtraLarge.setTextColor(getResources().getColor(R.color.black));
                        sizeXtraLarge.setTypeface(null, Typeface.BOLD);
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            private void resetTextColors() {
                sizeSmall.setTextColor(getResources().getColor(R.color.text_color_secondary));
                sizeMedium.setTextColor(getResources().getColor(R.color.text_color_secondary));
                sizeLarge.setTextColor(getResources().getColor(R.color.text_color_secondary));
                sizeXtraLarge.setTextColor(getResources().getColor(R.color.text_color_secondary));

                sizeSmall.setTypeface(null, Typeface.NORMAL);
                sizeMedium.setTypeface(null, Typeface.NORMAL);
                sizeLarge.setTypeface(null, Typeface.NORMAL);
                sizeXtraLarge.setTypeface(null, Typeface.NORMAL);
            }
        });

        b.nagitive.setOnClickListener(v -> calculateQty(-1));
        b.positive.setOnClickListener(v -> calculateQty(1));


    }

    private void calculateQty(int i) {
        b.qty.getText().toString();
        if (i == -1) {
            if (b.qty.getText().toString().equals("1")) {
                Toast.makeText(getContext(), "The one below is not possible.", Toast.LENGTH_SHORT).show();

            } else {
                int qty = Integer.parseInt(b.qty.getText().toString());
                int nqty = qty - 1;
                b.qty.setText("" + nqty);
            }

        } else {
            int qty = Integer.parseInt(b.qty.getText().toString());
            int pqty = qty + 1;
            b.qty.setText("" + pqty);

        }

    }
}
