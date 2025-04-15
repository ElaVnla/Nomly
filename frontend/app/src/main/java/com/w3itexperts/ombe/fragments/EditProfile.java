package com.w3itexperts.ombe.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.databinding.FragmentEditProfileBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends Fragment {
    private FragmentEditProfileBinding b;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Set<String> allergySet = new HashSet<>();
    private String encodedImage = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentEditProfileBinding.inflate(inflater, container, false);
        // Set back button listener.
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve current user details.
        users currentUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (currentUser != null) {
            // Pre-populate username and email.
            b.usernameEditText.setText(currentUser.getUsername());
            b.emailEditText.setText(currentUser.getEmail());

            // Pre-populate the allergy tags from preferences.
            String preferences = currentUser.getPreferences();
            if (!TextUtils.isEmpty(preferences)) {
                String[] allergies = preferences.split(",");
                b.tagContainer.removeAllViews();
                allergySet.clear();
                for (String allergy : allergies) {
                    if (!TextUtils.isEmpty(allergy)) {
                        addTag(allergy.trim());
                    }
                }
            }

            // Set the profile image (if available), otherwise a default.
            // Assuming that if an image was previously set, it is stored as a base64 string.
            // If you are storing a drawable resource as the default, use that as a fallback.
            if (!TextUtils.isEmpty(currentUser.getProfilePic())) {
                // If the stored image is in Base64, you might convert it back to a bitmap if needed.
                // Here we simply set encodedImage so that it is passed to the update API.
                encodedImage = currentUser.getProfilePic();
                // Optionally, you can decode and display the image.
            } else {
                b.profileImage.setImageResource(R.drawable.person1);
            }
        } else {
            Toast.makeText(getContext(), "No current user found", Toast.LENGTH_SHORT).show();
        }

        // When the Save Profile button is clicked, validate and send updates.
        b.saveProfile.setOnClickListener(v -> {
            String updatedUsername = b.usernameEditText.getText().toString().trim();
            String updatedEmail = b.emailEditText.getText().toString().trim();
            String enteredPassword = b.enterpassword.getText().toString().trim();
            String confirmPassword = b.confirmpassword.getText().toString().trim();

            // Validate that username and email are not empty.
            if (TextUtils.isEmpty(updatedUsername) || TextUtils.isEmpty(updatedEmail)) {
                String msg = "Username and Email cannot be empty";
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                Log.d("EDIT_PROFILE", msg);
                return;
            }

            // Determine updated password: if user did not enter a new password, retain current password.
            String updatedPassword;
            if (TextUtils.isEmpty(enteredPassword) && TextUtils.isEmpty(confirmPassword)) {
                updatedPassword = currentUser.getPassword();
            } else {
                if (!enteredPassword.equals(confirmPassword)) {
                    String msg = "Passwords do not match";
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("EDIT_PROFILE", msg);
                    return;
                }
                updatedPassword = enteredPassword;
            }

            // Gather allergy preferences from the allergySet (if any).
            String updatedPreferences = TextUtils.join(",", allergySet);

            // Prepare the map payload that the backend expects.
            Map<String, String> updateBody = new HashMap<>();
            updateBody.put("username", updatedUsername);
            updateBody.put("email", updatedEmail);
            updateBody.put("password", updatedPassword);
            updateBody.put("preferences", updatedPreferences);
            updateBody.put("image", encodedImage);

            ApiService apiService = ApiClient.getApiService();
            Log.d("EDIT_PROFILE", "Calling updateUser with payload: " + updateBody.toString());
            // Call the updateUser API.
            apiService.updateUser(currentUser.getUserId(), updateBody).enqueue(new Callback<users>() {
                @Override
                public void onResponse(Call<users> call, Response<users> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        String msg = "Profile updated successfully";
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("EDIT_PROFILE", msg);
                        // Update the session with the new user details.
                        SessionManager.getInstance(getContext()).setCurrentUser(response.body());
                        // Optionally, redirect back to Profile page.
                        getActivity().onBackPressed();
                    } else {
                        String msg = "Profile update failed: " + response.code();
                        try {
                            String errorBody = response.errorBody().string();
                            Log.d("EDIT_PROFILE", "Error body: " + errorBody);
                        } catch (IOException e) {
                            Log.d("EDIT_PROFILE", "Error reading error body: " + e.getMessage());
                        }
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        Log.d("EDIT_PROFILE", msg);
                    }
                }

                @Override
                public void onFailure(Call<users> call, Throwable t) {
                    String msg = "Profile update failed: " + t.getMessage();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    Log.d("EDIT_PROFILE", msg);
                }
            });
        });

        // Allergy input: add tag on "done" action.
        b.allergyInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
                            event.getAction() == KeyEvent.ACTION_DOWN)) {
                String allergyText = b.allergyInput.getText().toString().trim();
                if (!TextUtils.isEmpty(allergyText) && !allergySet.contains(allergyText)) {
                    addTag(allergyText);
                    b.allergyInput.setText("");
                    Log.d("EDIT_PROFILE", "Added allergy tag: " + allergyText);
                }
                return true;
            }
            return false;
        });

        // Edit profile image: open gallery to pick an image.
        b.editProfileImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });
    }

    // Add allergy tag as a MaterialCardView-like element.
    private void addTag(String text) {
        allergySet.add(text);
        LinearLayout tagLayout = new LinearLayout(getContext());
        tagLayout.setOrientation(LinearLayout.HORIZONTAL);
        tagLayout.setPadding(10, 5, 10, 5);
        tagLayout.setBackground(getResources().getDrawable(R.drawable.tag_background));
        tagLayout.setElevation(8);
        tagLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        MaterialButton tagButton = new MaterialButton(getContext());
        tagButton.setText(text);
        tagButton.setTextSize(12);
        tagButton.setPadding(20, 10, 20, 10);
        tagButton.setBackgroundColor(getResources().getColor(R.color.color_secondary));
        tagButton.setTextColor(getResources().getColor(R.color.white));
        tagButton.setAllCaps(false);
        tagButton.setCornerRadius(10);
        tagButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView closeButton = new ImageView(getContext());
        closeButton.setImageResource(R.drawable.ic_close);
        closeButton.setColorFilter(getResources().getColor(R.color.black));
        closeButton.setPadding(8, 5, 8, 5);
        closeButton.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        closeButton.setOnClickListener(v -> {
            b.tagContainer.removeView(tagLayout);
            allergySet.remove(text);
            Log.d("EDIT_PROFILE", "Removed allergy tag: " + text);
        });

        tagLayout.addView(tagButton);
        tagLayout.addView(closeButton);
        b.tagContainer.addView(tagLayout);
    }

    // Handle result from the gallery image picker.
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    // Set the image in the ImageView for preview
                    b.profileImage.setImageBitmap(bitmap);
                    // Convert the selected image to a Base64 encoded string.
                    encodedImage = ImageToB64(bitmap);
                    Log.d("EDIT_PROFILE", "Image converted to Base64 string.");
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error processing image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Helper method: convert Bitmap to Base64 encoded String.
    private String ImageToB64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Compress the bitmap; adjust format/quality as needed.
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

}
