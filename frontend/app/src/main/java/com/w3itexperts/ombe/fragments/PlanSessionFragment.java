package com.w3itexperts.ombe.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.sessions;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentPlanSessionBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Bundle args = getArguments();
        if (args != null && args.getBoolean("isEdit", false)) {
            b.titleText.setText("Change of Plans?");
            b.createSessionBtn.setText("Update Session");

            // Optional: pre-fill fields if passed in edit mode
            b.sessionTitle.setText(args.getString("title", ""));
            b.setLocation.setText(args.getString("location", ""));
//            b.dateDropdown.setText(args.getString("date", ""));
            String rawDate = args.getString("date", "");
            String formattedDate = formatToMMDDYYYY(rawDate);
            b.dateDropdown.setText(formattedDate);
            b.timeDropdown.setText(args.getString("time", ""));

            if (args.containsKey("lat") && args.containsKey("lng")) {
                b.setLocation.setTag(R.id.lat_tag, args.getDouble("lat"));
                b.setLocation.setTag(R.id.lng_tag, args.getDouble("lng"));
            }
        }

        getParentFragmentManager().setFragmentResultListener("locationPicked", this, (key, bundle) -> {
            double lat = bundle.getDouble("lat");
            double lng = bundle.getDouble("lng");
            String locationName = bundle.getString("locationName");

            b.setLocation.setText(locationName);
            b.setLocation.setTag(R.id.lat_tag, lat);
            b.setLocation.setTag(R.id.lng_tag, lng);
            validateForm();
        });


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
//                        int hourIn12Format = selectedHour % 12 == 0 ? 12 : selectedHour % 12;
                        int hourIn12Format = selectedHour > 12 ? selectedHour - 12 : (selectedHour == 0 ? 12 : selectedHour);
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d %s", hourIn12Format, selectedMinute, amPm);
                        b.timeDropdown.setText(formattedTime);
                        validateForm();
                    },
                    hour, minute, false);
            timePickerDialog.show();
        });

        b.setLocation.setOnClickListener(v -> {
            MapPickerFragment mapPickerFragment = new MapPickerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
                    .replace(R.id.fragment_view, mapPickerFragment)
                    .addToBackStack(null)
                    .commit();
        });

        // PREVIOUS BUTTON USED FOR CREATE SESSION

//        b.createSessionBtn.setOnClickListener(v -> {
//            if (isFormComplete()) {
//                Bundle sessionBundle = new Bundle();
//                sessionBundle.putString("title", b.sessionTitle.getText().toString().trim());
//                sessionBundle.putString("location", b.setLocation.getText().toString().trim());
//                sessionBundle.putString("date", b.dateDropdown.getText().toString().trim());
//                sessionBundle.putString("time", b.timeDropdown.getText().toString().trim());
//
//                ViewSessionFragment viewSessionFragment = new ViewSessionFragment();
//                viewSessionFragment.setArguments(sessionBundle);
//
//                requireActivity().getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
//                        .replace(R.id.fragment_view, viewSessionFragment)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });


        // CREATE SESSION VER1 - DOES NOT EDIT

//        b.createSessionBtn.setOnClickListener(v -> {
//            if (isFormComplete()) {
//                users currentUser = SessionManager.getInstance(requireContext()).getCurrentUser();
//                if (currentUser == null) {
//                    Toast.makeText(requireContext(), "You're not logged in!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                int groupId = getArguments() != null ? getArguments().getInt("groupId", -1) : -1;
//                if (groupId == -1) {
//                    Toast.makeText(requireContext(), "Group ID missing!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                String sessionName = b.sessionTitle.getText().toString().trim();
//                String location = b.setLocation.getText().toString().trim();
//                String rawDate = b.dateDropdown.getText().toString().trim(); // e.g., 04/12/2025
//                String rawTime = b.timeDropdown.getText().toString().trim(); // e.g., 08:20 PM
//
//                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
//                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
//
//                String meetingDateTimeFormatted;
//                try {
//                    Date parsedMeetingDate = inputFormat.parse(rawDate + " " + rawTime);
//                    meetingDateTimeFormatted = apiFormat.format(parsedMeetingDate);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Toast.makeText(requireContext(), "Invalid date/time format", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Build payload
//                Map<String, String> payload = new HashMap<>();
//                payload.put("session", sessionName);
//                payload.put("groupId", String.valueOf(groupId));
//                payload.put("location", location);
//                payload.put("latitude", "0.0");
//                payload.put("longitude", "0.0");
//                payload.put("meetingDateTime", meetingDateTimeFormatted);
//
//                ApiService apiService = ApiClient.getApiService();
//
//                Log.d("SESSION_PAYLOAD", payload.toString());
//
//                apiService.addSession(payload).enqueue(new Callback<sessions>() {
//                    @Override
//                    public void onResponse(Call<sessions> call, Response<sessions> response) {
//                        if (response.isSuccessful() && response.body() != null) {
//                            sessions created = response.body();
//
//                            // Split meetingDateTime back for display
//                            String[] parts = created.getMeetingDateTime().split(" ");
//                            String displayDate = parts.length > 0 ? parts[0] : "";
//                            String displayTime = parts.length > 1 ? parts[1] : "";
//
//                            Bundle bundle = new Bundle();
//                            bundle.putString("title", created.getSessionName());
//                            bundle.putString("location", created.getLocation());
//                            bundle.putString("date", displayDate);
//                            bundle.putString("time", displayTime);
//                            bundle.putString("status", created.isCompleted() ? "Done" : "Ongoing");
//
//                            ViewSessionFragment viewSessionFragment = new ViewSessionFragment();
//                            viewSessionFragment.setArguments(bundle);
//
//                            requireActivity().getSupportFragmentManager().beginTransaction()
//                                    .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
//                                    .replace(R.id.fragment_view, viewSessionFragment)
//                                    .addToBackStack(null)
//                                    .commit();
//                        } else {
//                            Toast.makeText(requireContext(), "Failed to create session: " + response.code(), Toast.LENGTH_SHORT).show();
//                            try {
//                                String errorBody = response.errorBody().string();
//                                Log.e("SESSION_ERROR", "Server says: " + errorBody);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<sessions> call, Throwable t) {
//                        Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

        // CREATE SESSION VER2, DOES NOT WORK

//        b.createSessionBtn.setOnClickListener(v -> {
//            if (isFormComplete()) {
//                users currentUser = SessionManager.getInstance(requireContext()).getCurrentUser();
//                if (currentUser == null) {
//                    Toast.makeText(requireContext(), "You're not logged in!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                int groupId = getArguments() != null ? getArguments().getInt("groupId", -1) : -1;
//                if (groupId == -1) {
//                    Toast.makeText(requireContext(), "Group ID missing!", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                String sessionName = b.sessionTitle.getText().toString().trim();
//                String location = b.setLocation.getText().toString().trim();
//                String rawDate = b.dateDropdown.getText().toString().trim();
//                String rawTime = b.timeDropdown.getText().toString().trim();
//
//                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
//                SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
//                String meetingDateTimeFormatted;
//
//                try {
//                    Date parsedDate = inputFormat.parse(rawDate + " " + rawTime);
//                    meetingDateTimeFormatted = apiFormat.format(parsedDate);
//                } catch (Exception e) {
//                    Toast.makeText(requireContext(), "Invalid date/time format", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                ApiService apiService = ApiClient.getApiService();
//                boolean isEdit = getArguments() != null && getArguments().getBoolean("isEdit", false);
//
//                Map<String, String> payload = new HashMap<>();
//                payload.put("session", sessionName);
//                payload.put("groupId", String.valueOf(groupId));
//                payload.put("location", location);
//                payload.put("latitude", "0.0");
//                payload.put("longitude", "0.0");
//                payload.put("meetingDateTime", meetingDateTimeFormatted);
//
//                if (isEdit) {
//                    int sessionId = getArguments().getInt("sessionId", -1);
//                    payload.put("sessionId", String.valueOf(sessionId));  // ✅ required for PUT
//
//                    apiService.updateSession(payload).enqueue(new Callback<sessions>() {
//                        @Override
//                        public void onResponse(Call<sessions> call, Response<sessions> response) {
//                            if (response.isSuccessful()) {
//                                Toast.makeText(requireContext(), "✅ Session updated!", Toast.LENGTH_SHORT).show();
//
//                                // Optional: go back to ViewSessionFragment
//                                Bundle bundle = new Bundle();
//                                bundle.putString("title", sessionName);
//                                bundle.putString("location", location);
//                                bundle.putString("date", rawDate);
//
//                                bundle.putString("time", rawTime);
//                                bundle.putString("status", "Ongoing");
//                                bundle.putInt("sessionId", sessionId);
//
//                                ViewSessionFragment fragment = new ViewSessionFragment();
//                                fragment.setArguments(bundle);
//
//                                requireActivity().getSupportFragmentManager().beginTransaction()
//                                        .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
//                                        .replace(R.id.fragment_view, fragment)
//                                        .commit();
//                            } else {
//                                Toast.makeText(requireContext(), "❌ Update failed", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<sessions> call, Throwable t) {
//                            Toast.makeText(requireContext(), "API Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                } else {
//                    // 🟢 Creating a new session
//                    apiService.addSession(payload).enqueue(new Callback<sessions>() {
//                        @Override
//                        public void onResponse(Call<sessions> call, Response<sessions> response) {
//                            if (response.isSuccessful() && response.body() != null) {
//                                sessions created = response.body();
//
//                                // Parse date/time for display
//                                String[] parts = created.getMeetingDateTime().split("T");
//                                String displayDate = parts.length > 0 ? parts[0] : "";
//                                String displayTime = parts.length > 1 ? parts[1] : "";
//
//                                Bundle bundle = new Bundle();
//                                bundle.putString("title", created.getSessionName());
//                                bundle.putString("location", created.getLocation());
//                                bundle.putString("date", displayDate);
//                                bundle.putString("time", displayTime);
//                                bundle.putString("status", created.isCompleted() ? "Done" : "Ongoing");
//                                bundle.putInt("sessionId", created.getSessionId()); // ✅ pass it along!
//
//                                ViewSessionFragment viewSessionFragment = new ViewSessionFragment();
//                                viewSessionFragment.setArguments(bundle);
//
//                                requireActivity().getSupportFragmentManager().beginTransaction()
//                                        .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
//                                        .replace(R.id.fragment_view, viewSessionFragment)
//                                        .addToBackStack(null)
//                                        .commit();
//                            } else {
//                                Toast.makeText(requireContext(), "Failed to create session", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<sessions> call, Throwable t) {
//                            Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });

        // CREATE BTN VER 3

        b.createSessionBtn.setOnClickListener(v -> {
            if (!isFormComplete()) return;

            users currentUser = SessionManager.getInstance(requireContext()).getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(requireContext(), "You're not logged in!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isEdit = args != null && args.getBoolean("isEdit", false);
            int sessionId = args != null ? args.getInt("sessionId", -1) : -1;
            int groupId = args != null ? args.getInt("groupId", -1) : -1;

            Log.d("CHECK_EDIT_FLAG", "isEdit = " + isEdit + ", sessionId = " + sessionId);
            Toast.makeText(requireContext(), "Edit Mode: " + isEdit, Toast.LENGTH_SHORT).show();

            if (groupId == -1) {
                Toast.makeText(requireContext(), "Group ID missing!", Toast.LENGTH_SHORT).show();
                return;
            }

            String sessionName = b.sessionTitle.getText().toString().trim();
            String location = b.setLocation.getText().toString().trim();
            String rawDate = b.dateDropdown.getText().toString().trim();
            String rawTime = b.timeDropdown.getText().toString().trim();

            if (!rawTime.matches(".*(AM|PM).*")) {
                Toast.makeText(requireContext(), "Please re-pick the time", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault());
            SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

            String meetingDateTimeFormatted;
            try {
                // ✅ Add log statements before parsing
                Log.d("DEBUG_DATE", "rawDate: " + rawDate);
                Log.d("DEBUG_TIME", "rawTime: " + rawTime);
                Log.d("DEBUG_COMBINED", "combined: " + rawDate + " " + rawTime);

                Date parsedDate = inputFormat.parse(rawDate + " " + rawTime);
                meetingDateTimeFormatted = apiFormat.format(parsedDate);
            } catch (Exception e) {
                // ✅ Catch and log full error to Logcat
                Log.e("DATE_PARSE_ERROR", "Failed to parse date/time", e);
                Toast.makeText(requireContext(), "Invalid date/time format", Toast.LENGTH_SHORT).show();
                return;
            }

            Object latTag = b.setLocation.getTag(R.id.lat_tag);
            Object lngTag = b.setLocation.getTag(R.id.lng_tag);

            if (latTag == null && args != null && args.containsKey("lat")) {
                latTag = args.getDouble("lat");
                b.setLocation.setTag(R.id.lat_tag, latTag);
            }
            if (lngTag == null && args != null && args.containsKey("lng")) {
                lngTag = args.getDouble("lng");
                b.setLocation.setTag(R.id.lng_tag, lngTag);
            }

            if (latTag == null || lngTag == null) {
                Toast.makeText(requireContext(), "Please re-pick the location", Toast.LENGTH_SHORT).show();
                return;
            }

            double lat = (double) latTag;
            double lng = (double) lngTag;

            Map<String, String> payload = new HashMap<>();
            payload.put("session", sessionName);
            payload.put("groupId", String.valueOf(groupId));
            payload.put("location", location);
            payload.put("latitude", String.valueOf(lat));
            payload.put("longitude", String.valueOf(lng));
            payload.put("meetingDateTime", meetingDateTimeFormatted);

            ApiService apiService = ApiClient.getApiService();

            if (isEdit && sessionId != -1) {
                payload.put("sessionId", String.valueOf(sessionId));

                Log.d("UPDATE_FLOW", "Editing session with ID: " + sessionId);
                Log.d("UPDATE_SESSION_PAYLOAD", payload.toString());

                apiService.updateSession(sessionId, payload).enqueue(new Callback<sessions>() {
                    @Override
                    public void onResponse(Call<sessions> call, Response<sessions> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(requireContext(), "✅ Session updated!", Toast.LENGTH_SHORT).show();
                            // Send user back to ViewSessionFragment
                            Bundle bundle = new Bundle();
                            bundle.putString("session", sessionName);
                            bundle.putString("location", location);
                            bundle.putString("date", rawDate);
                            bundle.putString("time", rawTime);
                            bundle.putString("status", "Ongoing");
                            bundle.putInt("sessionId", sessionId);
                            bundle.putInt("groupId", groupId);
                            bundle.putDouble("lat", lat);
                            bundle.putDouble("lng", lng);

                            Log.d("DEBUG_PLANSESSION", "Passing lat=" + lat + ", lng=" + lng);

                            ViewSessionFragment viewSessionFragment = new ViewSessionFragment();
                            viewSessionFragment.setArguments(bundle);

                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
                                    .replace(R.id.fragment_view, viewSessionFragment)
                                    .commit();
                        } else {
                            Toast.makeText(requireContext(), "Update failed: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<sessions> call, Throwable t) {
                        Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                apiService.addSession(payload).enqueue(new Callback<sessions>() {
                    @Override
                    public void onResponse(Call<sessions> call, Response<sessions> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            sessions created = response.body();

                            String displayDate = "";
                            String displayTime = "";
                            try {
                                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                                Date parsedDate = isoFormat.parse(created.getMeetingDateTime());
                                if (parsedDate != null) {
                                    displayDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(parsedDate);
                                    displayTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(parsedDate);
                                }
                            } catch (Exception e) {
                                e.printStackTrace(); // fallback if parsing fails
                            }

//                            String[] parts = created.getMeetingDateTime().split(" ");
//                            String displayDate = parts.length > 0 ? parts[0] : "";
//                            String displayTime = parts.length > 1 ? parts[1] : "";

                            Bundle bundle = new Bundle();
                            bundle.putString("title", created.getSessionName());
                            bundle.putString("location", created.getLocation());
                            bundle.putString("date", displayDate);
                            bundle.putString("time", displayTime);
                            bundle.putString("status", created.isCompleted() ? "Done" : "Ongoing");
                            bundle.putInt("sessionId", created.getSessionId());
                            bundle.putInt("groupId", groupId);
                            bundle.putDouble("lat", lat);
                            bundle.putDouble("lng", lng);

                            Log.d("DEBUG_PLANSESSION", "Passing lat=" + lat + ", lng=" + lng);

                            ViewSessionFragment viewSessionFragment = new ViewSessionFragment();
                            viewSessionFragment.setArguments(bundle);

                            requireActivity().getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown)
                                    .replace(R.id.fragment_view, viewSessionFragment)
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            Toast.makeText(requireContext(), "Failed to create session", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<sessions> call, Throwable t) {
                        Toast.makeText(requireContext(), "API error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        validateForm(); // Initial state

        b.cancelBtn.setOnClickListener(v -> {
            // ✅ Simply close this fragment and go back
            requireActivity().onBackPressed(); // OR use popBackStack()
        });
    }

    private String formatToMMDDYYYY(String inputDate) {
        try {
//            SimpleDateFormat backendFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//            SimpleDateFormat uiFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            SimpleDateFormat backendFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            SimpleDateFormat uiFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            Date parsed = backendFormat.parse(inputDate);
            return uiFormat.format(parsed);
        } catch (Exception e) {
            e.printStackTrace();
            return inputDate; // fallback if parsing fails
        }
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
        b.createSessionBtn.setEnabled(isFormComplete());
    }

    private boolean isFormComplete() {
        return !b.sessionTitle.getText().toString().trim().isEmpty()
                && !b.setLocation.getText().toString().trim().isEmpty()
                && !b.dateDropdown.getText().toString().trim().isEmpty()
                && !b.timeDropdown.getText().toString().trim().isEmpty();
    }
}
