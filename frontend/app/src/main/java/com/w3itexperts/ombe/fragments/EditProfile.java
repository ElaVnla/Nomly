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
import android.view.inputmethod.EditorInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.w3itexperts.ombe.methods.EncryptionUtil;

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
    private static final int pickimg = 1;
    private Set<String> allergySet = new HashSet<>();
    private String encodedImage = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentEditProfileBinding.inflate(inflater, container, false);
        b.backbtn.setOnClickListener(v -> getActivity().onBackPressed());
        return b.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        users currentUser = SessionManager.getInstance(getContext()).getCurrentUser();
        if (currentUser != null) {
            // get user info then we display their info
            b.usernameEditText.setText(currentUser.getUsername());
            b.emailEditText.setText(currentUser.getEmail());

            String preferences = currentUser.getPreferences();
            if (!TextUtils.isEmpty(preferences)) {
                String[] allergies = preferences.split(",");
                b.tagContainer.removeAllViews();
                allergySet.clear();
                for (String allergy : allergies) {
                    if (!TextUtils.isEmpty(allergy)) addTag(allergy.trim());
                }
            }

            // Load image from new .getImage() field
            // Base code - Jingyu
            // Update and reconstruct to pass image/decode properly with assist with CHATGPT
            // https://stackoverflow.com/questions/469695/decode-base64-data-in-java
            String base64Image = currentUser.getImage();
            encodedImage = base64Image;

            if (!TextUtils.isEmpty(base64Image)) {
                try {
                    if (base64Image.contains(",")) {
                        base64Image = base64Image.split(",")[1];
                    }
                    byte[] decodedBytes = Base64.decode(base64Image.trim(), Base64.DEFAULT);
                    Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    b.profileImage.setImageBitmap(decodedBitmap);
                } catch (Exception e) {
                    Log.e("NOMLYPROCESS", "Error decoding profile image: " + e.getMessage());
                    b.profileImage.setImageResource(R.drawable.defaultprofile);
                }
            } else {
                b.profileImage.setImageResource(R.drawable.defaultprofile);
            }
        } else {
            Toast.makeText(getContext(), "No current user found", Toast.LENGTH_SHORT).show();
        }

        b.saveProfile.setOnClickListener(v -> {
            String updatedUsername = b.usernameEditText.getText().toString().trim();
            String updatedEmail = b.emailEditText.getText().toString().trim();
            String enteredPassword = b.enterpassword.getText().toString().trim();
            String confirmPassword = b.confirmpassword.getText().toString().trim();

            if (TextUtils.isEmpty(updatedUsername) || TextUtils.isEmpty(updatedEmail)) {
                Toast.makeText(getContext(), "Username and Email cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            String updatedPassword = TextUtils.isEmpty(enteredPassword) && TextUtils.isEmpty(confirmPassword)
                    ? currentUser.getPassword()
                    : (enteredPassword.equals(confirmPassword) ? enteredPassword : null);

            if (updatedPassword == null) {
                Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // generated by chatgpt - edited by jingyu, copy paste from erika code
            // Before updating, encrypt the password like in create_account
            String finalPassword = updatedPassword;
            if (!TextUtils.isEmpty(enteredPassword) && !TextUtils.isEmpty(confirmPassword)) {
                try {
                    finalPassword = EncryptionUtil.encrypt(updatedPassword);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error encrypting password", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Update the allergy and other fields
            String updatedPreferences = TextUtils.join(",", allergySet);
            Map<String, String> updateBody = new HashMap<>();
            updateBody.put("username", updatedUsername);
            updateBody.put("email", updatedEmail);
            updateBody.put("password", finalPassword);
            updateBody.put("preferences", updatedPreferences);
            updateBody.put("profilePicture", encodedImage);


            ApiService apiService = ApiClient.getApiService();
            apiService.updateUser(currentUser.getUserId(), updateBody).enqueue(new Callback<users>() {
                @Override
                public void onResponse(Call<users> call, Response<users> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        SessionManager.getInstance(getContext()).setCurrentUser(response.body());
                        getActivity().onBackPressed();
                    } else {
                        try {
                            Log.d("NOMLYPROCESS", "Error: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.d("NOMLYPROCESS", "Error reading error body: " + e.getMessage());
                        }
                        Toast.makeText(getContext(), "Update failed: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<users> call, Throwable t) {
                    Toast.makeText(getContext(), "Update failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // https://stackoverflow.com/questions/27981167/implement-custom-spinner-on-drop-down-menu-select
        //https://www.digitalocean.com/community/tutorials/android-spinner-drop-down-list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Select your allergy", "No Beef", "No Seafood", "Vegetarian", "Vegan"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b.allergySpinner.setAdapter(adapter);

        b.allergySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAllergy = parent.getItemAtPosition(position).toString();
                if (!selectedAllergy.equals("Select your allergy") && !allergySet.contains(selectedAllergy)) {
                    addTag(selectedAllergy);
                    b.allergySpinner.setSelection(0); // Reset back to "Select your allergy"
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        b.editProfileImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, pickimg);
        });
    }

    //https://stackoverflow.com/questions/1851633/how-to-add-a-button-dynamically-in-android
// https://www.geeksforgeeks.org/how-to-generate-dynamic-multiple-buttons-in-android/
    private void addTag(String text) {
        allergySet.add(text);
        LinearLayout taglyt = new LinearLayout(getContext());
        taglyt.setOrientation(LinearLayout.HORIZONTAL);
        taglyt.setPadding(10, 5, 10, 5);
        taglyt.setBackground(getResources().getDrawable(R.drawable.tag_background));
        taglyt.setElevation(8);
        taglyt.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        MaterialButton btntag = new MaterialButton(getContext());
        btntag.setText(text);
        btntag.setTextSize(12);
        btntag.setPadding(20, 10, 20, 10);
        btntag.setBackgroundColor(getResources().getColor(R.color.color_secondary));
        btntag.setTextColor(getResources().getColor(R.color.white));
        btntag.setAllCaps(false);
        btntag.setCornerRadius(10);
        btntag.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        ImageView BtnCls = new ImageView(getContext());
        BtnCls.setImageResource(R.drawable.ic_close);
        BtnCls.setColorFilter(getResources().getColor(R.color.black));
        BtnCls.setPadding(8, 5, 8, 5);
        BtnCls.setLayoutParams(new LinearLayout.LayoutParams(50, 50));
        BtnCls.setOnClickListener(v -> {
            b.tagContainer.removeView(taglyt);
            allergySet.remove(text);
        });

        taglyt.addView(btntag);
        taglyt.addView(BtnCls);
        b.tagContainer.addView(taglyt);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pickimg && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    b.profileImage.setImageBitmap(bitmap);
                    encodedImage = imageToB64(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error processing image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // generated by chatgpt - edited by jingyu
    private String imageToB64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }
}
