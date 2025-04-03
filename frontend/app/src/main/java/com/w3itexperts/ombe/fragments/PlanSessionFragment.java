package com.w3itexperts.ombe.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.databinding.FragmentPlanSessionBinding;

import java.util.Calendar;
import java.util.Locale;

public class PlanSessionFragment extends Fragment {

    private FragmentPlanSessionBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentPlanSessionBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Listeners to monitor form changes
        b.sessionTitle.addTextChangedListener(simpleWatcher());
        b.setLocation.addTextChangedListener(simpleWatcher());

        b.dateDropdown.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedMonth + 1, selectedDay, selectedYear);
                        b.dateDropdown.setText(selectedDate);
                        validateForm();
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        b.timeDropdown.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                    (view12, selectedHour, selectedMinute) -> {
                        String amPm = selectedHour >= 12 ? "PM" : "AM";
                        int hourIn12Format = selectedHour % 12 == 0 ? 12 : selectedHour % 12;
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourIn12Format, selectedMinute, amPm);
                        b.timeDropdown.setText(formattedTime);
                        validateForm();
                    },
                    hour, minute, false);
            timePickerDialog.show();
        });

        b.startSwipingBtn.setOnClickListener(v -> {
            if (isFormComplete()) {
                Bundle args = new Bundle();
                args.putString("title", b.sessionTitle.getText().toString().trim());
                args.putString("location", b.setLocation.getText().toString().trim());
                args.putString("date", b.dateDropdown.getText().toString().trim());
                args.putString("time", b.timeDropdown.getText().toString().trim());

                ViewSessionFragment viewSessionFragment = new ViewSessionFragment();
                viewSessionFragment.setArguments(args);

                requireActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
                        .replace(R.id.fragment_view, viewSessionFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        validateForm(); // Initial state
    }

    private TextWatcher simpleWatcher() {
        return new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override public void afterTextChanged(Editable s) {
                validateForm();
            }
        };
    }

    private void validateForm() {
        b.startSwipingBtn.setEnabled(isFormComplete());
    }

    private boolean isFormComplete() {
        return !b.sessionTitle.getText().toString().trim().isEmpty()
                && !b.setLocation.getText().toString().trim().isEmpty()
                && !b.dateDropdown.getText().toString().trim().isEmpty()
                && !b.timeDropdown.getText().toString().trim().isEmpty();
    }
}
