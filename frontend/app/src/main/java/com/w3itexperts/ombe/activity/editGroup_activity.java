package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.w3itexperts.ombe.R;

public class editGroup_activity extends AppCompatActivity {

    private ImageView groupImageChangeInput;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_group_details); // this is your current XML file

        // 1. Back button
        findViewById(R.id.backbtnToGroupPage).setOnClickListener(v -> {
            Intent intent = new Intent(editGroup_activity.this, groupPage_Activity.class);
            startActivity(intent);
            finish();
        });

        // 2. Setup image picker
        groupImageChangeInput = findViewById(R.id.groupImageChangeInput);

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        groupImageChangeInput.setImageURI(imageUri); // show the image
                    }
                });

        groupImageChangeInput.setOnClickListener(v -> {
            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");
            imagePickerLauncher.launch(pickIntent);
        });

        // 3. Save button
        Button saveDetailsButton = findViewById(R.id.saveDetailsButton);
        saveDetailsButton.setOnClickListener(v -> {
            // TODO: Save group name + image URI to DB or preferences
            Intent intent = new Intent(editGroup_activity.this, groupPage_Activity.class);
            startActivity(intent);
            finish();
        });
    }
}
