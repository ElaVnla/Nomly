package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class createGroup_activity extends AppCompatActivity {

    private ImageView groupImageView, backButton;
    private EditText groupNameInput;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    private Uri selectedImageUri;
    private int userId;

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

        // ⬅️ Handle Back Button to Home Fragment
        backButton.setOnClickListener(v -> {
            finish(); // Just finishes this activity and returns to home_fragment
        });

        // 📸 Image picker logic
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        groupImageView.setImageURI(selectedImageUri);
                    }
                });

        groupImageView.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            imagePickerLauncher.launch(pickIntent);
        });

        // 🚀 Create Group
        Button createGroupButton = findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(v -> createGroup());
    }

    private void createGroup() {
        String groupName = groupNameInput.getText().toString().trim();

        if (groupName.isEmpty()) {
            Toast.makeText(this, "Please enter a group name", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> groupData = new HashMap<>();
        groupData.put("groupName", groupName);

        ApiService apiService = ApiClient.getApiService();
        Call<groupings> call = apiService.addGrouping(groupData);

        call.enqueue(new Callback<groupings>() {
            @Override
            public void onResponse(Call<groupings> call, Response<groupings> response) {
                if (response.isSuccessful() && response.body() != null) {
                    groupings createdGroup = response.body();
                    int groupId = createdGroup.getGroupId();
                    String groupCode = createdGroup.getGroupCode();

                    // Step 2: Add user to the group
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
                                String errorMessage = "Group made but failed to add user. Code: " + response.code();
                                Toast.makeText(createGroup_activity.this, errorMessage, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<usersgroupings> call, Throwable t) {
                            Toast.makeText(createGroup_activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                            t.printStackTrace();
                        }
                    });

                } else {
                    Toast.makeText(createGroup_activity.this, "Failed to create group!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<groupings> call, Throwable t) {
                Toast.makeText(createGroup_activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
