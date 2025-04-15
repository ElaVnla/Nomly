package com.w3itexperts.ombe.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.card.MaterialCardView;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.activity.login_signin_Activity;
import com.w3itexperts.ombe.activity.Welcome;
import com.w3itexperts.ombe.databinding.FragmentProfileBinding;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.apimodals.sessions;

public class Profile extends Fragment {
    private FragmentProfileBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentProfileBinding.inflate(inflater, container, false);
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve current user details from SessionManager.
        users currentUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (currentUser != null) {
            // Display username and email.
            b.displayusername.setText(currentUser.getUsername());
            b.displayemail.setText(currentUser.getEmail());

            // Update creation date.
            String createdAt = currentUser.getCreatedAt(); // e.g., "2025-04-12T02:37:29"
            try {
                if (!TextUtils.isEmpty(createdAt)) {
                    String[] dateTimeParts = createdAt.split("T"); // Split date and time
                    if (dateTimeParts.length > 0) {
                        String datePart = dateTimeParts[0]; // "yyyy-MM-dd"
                        String[] dateComponents = datePart.split("-");
                        if (dateComponents.length == 3) {
                            b.dateYear.setText(dateComponents[0]);
                            String monthStr = convertMonth(dateComponents[1]);
                            b.dateMonth.setText(monthStr);
                            b.dateNo.setText(dateComponents[2]);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("Profile", "Error parsing creation date: " + e.getMessage());
            }

            // Update number of groups.
            int groupCount = (currentUser.getGroups() == null) ? 0 : currentUser.getGroups().size();
            b.NoOfGroups.setText(String.valueOf(groupCount));

            // Calculate active sessions count (only count sessions where completed == false).
            int activeSessions = 0;
            if (currentUser.getGroups() != null) {
                for (groupings grp : currentUser.getGroups()) {
                    if (grp.getSessions() != null) {
                        for (sessions sess : grp.getSessions()) {
                            if (!sess.isCompleted()) {
                                activeSessions++;
                            }
                        }
                    }
                }
            }
            b.noActiveSessions.setText(String.valueOf(activeSessions));

            // Update allergies (stored in "preferences").
            String preferences = currentUser.getPreferences();
            // Clear the Flexbox container first.
            b.tagContainer.removeAllViews();
            if (!TextUtils.isEmpty(preferences)) {
                String[] allergiesArr = preferences.split(",");
                for (String allergy : allergiesArr) {
                    if (!TextUtils.isEmpty(allergy.trim())) {
                        // Inflate a card view dynamically for each allergy.
                        MaterialCardView card = new MaterialCardView(getContext());
                        // Set layout parameters (convert 110dp x 150dp to pixels).
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(110), dpToPx(150));
                        params.setMargins(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
                        card.setLayoutParams(params);
                        card.setRadius(dpToPx(16));
                        card.setCardElevation(dpToPx(4));
                        card.setCardBackgroundColor(getResources().getColor(R.color.white));

                        // Create inner LinearLayout.
                        LinearLayout innerLayout = new LinearLayout(getContext());
                        innerLayout.setOrientation(LinearLayout.VERTICAL);
                        innerLayout.setGravity(Gravity.CENTER);
                        innerLayout.setPadding(dpToPx(12), dpToPx(12), dpToPx(12), dpToPx(12));

                        // Optionally, add an ImageView (for an allergy icon; using a placeholder here).
                        ImageView allergyIcon = new ImageView(getContext());
                        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(dpToPx(90), dpToPx(90));
                        allergyIcon.setLayoutParams(imageParams);
                        allergyIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        allergyIcon.setImageResource(R.drawable.lobstericon); // Replace with appropriate resource

                        // Create a TextView for the allergy name.
                        TextView allergyName = new TextView(getContext());
                        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        allergyName.setLayoutParams(textParams);
                        allergyName.setText(allergy.trim());
                        allergyName.setTextColor(getResources().getColor(R.color.color_primary));
                        allergyName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        // If you use a custom font, you can set it using:
                        // allergyName.setTypeface(ResourcesCompat.getFont(getContext(), R.font.semi_bold));

                        // Add the icon and name into the inner layout.
                        innerLayout.addView(allergyIcon);
                        innerLayout.addView(allergyName);

                        // Add inner layout to the card.
                        card.addView(innerLayout);

                        // Finally, add this card to your Flexbox container.
                        b.tagContainer.addView(card);
                    }
                }
            }
        } else {
            Log.e("Profile", "No current user found.");
            // Redirect to login if no user is found.
            startActivity(new Intent(getContext(), login_signin_Activity.class));
        }

        // Set up the edit profile button.
        b.editProfile.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fragment_popup, 0, 0, R.anim.fragment_popdown);
            transaction.replace(R.id.fragment_view, new EditProfile());
            transaction.addToBackStack(null);
            transaction.commitAllowingStateLoss();
        });

        // Set up the logout button.
        b.logout.setOnClickListener(v -> {
            // Clear user session and navigate to Welcome.
            SessionManager.getInstance(getContext()).setCurrentUser(null);
            startActivity(new Intent(getContext(), Welcome.class));
        });
    }

    // Helper method to convert dp to pixels.
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    // Helper method to convert month number strings to three-letter abbreviations.
    private String convertMonth(String monthNum) {
        switch (monthNum) {
            case "01": return "JAN";
            case "02": return "FEB";
            case "03": return "MAR";
            case "04": return "APR";
            case "05": return "MAY";
            case "06": return "JUN";
            case "07": return "JUL";
            case "08": return "AUG";
            case "09": return "SEP";
            case "10": return "OCT";
            case "11": return "NOV";
            case "12": return "DEC";
            default: return "";
        }
    }
}
