package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.w3itexperts.ombe.R;

public class createGroup_activity extends AppCompatActivity {

    private ImageView groupImageView;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group); // links to create_group.xml

        // 1. Reference to the ImageView
        groupImageView = findViewById(R.id.groupImageInput); // make sure your ImageView id is groupImage

        // 2. Set up the image picker launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        groupImageView.setImageURI(imageUri); // Display selected image
                    }
                });

        // 3. When ImageView is clicked, open gallery
        groupImageView.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            imagePickerLauncher.launch(pickIntent);
        });

        // 4. Button setup (navigate or handle create group logic)
        Button createGroupButton = findViewById(R.id.createGroupButton);
        createGroupButton.setOnClickListener(v -> {
            // You probably don't want to restart the same activity again.
            // Replace with the correct intent (e.g., go to GroupCodeActivity)
            // Example:
            // Intent intent = new Intent(createGroup_activity.this, GetGroupCodeActivity.class);
            // startActivity(intent);

            // For now, just a placeholder
            Intent intent = new Intent(createGroup_activity.this, createGroup_activity.class);
            startActivity(intent);
        });
    }
}
