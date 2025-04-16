package com.w3itexperts.ombe.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.usersgroupings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class createGroup_activity extends AppCompatActivity {

    private ImageView groupImageView, backButton;
    private EditText groupNameInput;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Uri selectedImageUri;
    private int userId;
    private String base64Image = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);

        groupImageView = findViewById(R.id.groupImageInput);
        groupNameInput = findViewById(R.id.groupNameInput);
        backButton = findViewById(R.id.backbtnToHomeFromCreate);

        userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        backButton.setOnClickListener(v -> finish());

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        groupImageView.setImageURI(selectedImageUri);
                        convertImageToBase64(selectedImageUri);
                    }
                });

        groupImageView.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            imagePickerLauncher.launch(pickIntent);
        });

        Button createGroupButton = findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(v -> createGroup());
    }

    private void convertImageToBase64(Uri uri) {
        try {
            ContentResolver resolver = getContentResolver();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(resolver, uri);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        } catch (IOException e) {
            Toast.makeText(this, "Failed to encode image", Toast.LENGTH_SHORT).show();
            Log.e("IMAGE_ENCODE", "Error encoding image: ", e);
        }
    }

    private void createGroup() {
        String groupName = groupNameInput.getText().toString().trim();

        if (groupName.isEmpty()) {
            Toast.makeText(this, "Please enter a group name", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> groupData = new HashMap<>();
        groupData.put("groupName", groupName);
        if (base64Image != null) {
            groupData.put("profilePicture", base64Image);
        }

        // 👇 Add this debug log
        Log.d("CREATE_GROUP_DEBUG", "Creating group with: name=" + groupName + ", hasImage=" + (base64Image != null));
        ApiService apiService = ApiClient.getApiService();
        Call<groupings> call = apiService.addGrouping(groupData);

        call.enqueue(new Callback<groupings>() {
            @Override
            public void onResponse(Call<groupings> call, Response<groupings> response) {
                if (response.isSuccessful() && response.body() != null) {
                    groupings createdGroup = response.body();
                    int groupId = createdGroup.getGroupId();
                    String groupCode = createdGroup.getGroupCode();

                    Map<String, String> userGroupData = new HashMap<>();
                    userGroupData.put("userId", String.valueOf(userId));
                    userGroupData.put("groupId", String.valueOf(groupId));

                    apiService.addUserToGrouping(userGroupData).enqueue(new Callback<usersgroupings>() {
                        @Override
                        public void onResponse(Call<usersgroupings> call, Response<usersgroupings> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(createGroup_activity.this, getGroup_code.class);
                                intent.putExtra("groupId", groupId);
                                intent.putExtra("groupCode", groupCode);
                                Toast.makeText(createGroup_activity.this, "🎉 Group created!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(createGroup_activity.this, "Group created, but failed to add user", Toast.LENGTH_SHORT).show();
                                Log.e("ADD_USER_FAIL", "Error code: " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<usersgroupings> call, Throwable t) {
                            Toast.makeText(createGroup_activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("ADD_USER_API", "Failure: ", t);
                        }
                    });

                } else {
                    String errorMsg = "Failed to create group. Code: " + response.code();
                    try {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            errorMsg += " - " + errorBody.string();
                        }
                    } catch (IOException e) {
                        errorMsg += " - Could not parse error body.";
                    }
                    Toast.makeText(createGroup_activity.this, errorMsg, Toast.LENGTH_LONG).show();
                    Log.e("CREATE_GROUP", errorMsg);
                }
            }

            @Override
            public void onFailure(Call<groupings> call, Throwable t) {
                Toast.makeText(createGroup_activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("CREATE_GROUP_API", "Failure: ", t);
            }
        });
    }
}
