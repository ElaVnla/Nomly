package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class editGroup_activity extends AppCompatActivity {

    private ImageView groupImageChangeInput;
    private EditText groupNameEditText;
    private Button saveDetailsButton;
    private Uri selectedImageUri;
    private int groupId;

    private String base64Image = null;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group_details);

        groupId = getIntent().getIntExtra("groupId", -1);

        groupImageChangeInput = findViewById(R.id.groupImageChangeInput);
        groupNameEditText = findViewById(R.id.groupNameChangeInput);
        saveDetailsButton = findViewById(R.id.saveDetailsButton);

        findViewById(R.id.backbtnToGroupPage).setOnClickListener(v -> {
            Intent intent = new Intent(editGroup_activity.this, groupPage_Activity.class);
            intent.putExtra("groupId", groupId);
            startActivity(intent);
            finish();
        });

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        selectedImageUri = result.getData().getData();
                        groupImageChangeInput.setImageURI(selectedImageUri);
                        convertImageToBase64(selectedImageUri);
                    }
                });

        groupImageChangeInput.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            imagePickerLauncher.launch(pickIntent);
        });

        saveDetailsButton.setOnClickListener(v -> updateGroupDetails());
    }

    private void convertImageToBase64(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
        } catch (IOException e) {
            Toast.makeText(this, "Image conversion failed", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void updateGroupDetails() {
        String newName = groupNameEditText.getText().toString().trim();

        if (newName.isEmpty()) {
            Toast.makeText(this, "Please enter a group name", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> groupData = new HashMap<>();
        groupData.put("groupName", newName);
        if (base64Image != null) {
            groupData.put("profilePicture", base64Image);
        }

        ApiService apiService = ApiClient.getApiService();
        Call<groupings> call = apiService.updateGrouping(groupId, groupData);

        call.enqueue(new Callback<groupings>() {
            @Override
            public void onResponse(Call<groupings> call, Response<groupings> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(editGroup_activity.this, "Group updated!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(editGroup_activity.this, groupPage_Activity.class);
                    intent.putExtra("groupId", groupId);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(editGroup_activity.this, "Update failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<groupings> call, Throwable t) {
                Toast.makeText(editGroup_activity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
