package com.w3itexperts.ombe.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.w3itexperts.ombe.R;

public class getGroup_code extends AppCompatActivity {

    private TextView codeText;
    private String groupCode;
    private int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_group_code);

        codeText = findViewById(R.id.codeText);
        Button viewGroupButton = findViewById(R.id.viewGroupButton);
        Button copyCodeButton = findViewById(R.id.copyCodeButton);

        // Get the code from intent
        groupCode = getIntent().getStringExtra("groupCode");
        groupId = getIntent().getIntExtra("groupId", -1);

        if (groupCode != null) {
            codeText.setText(groupCode);
        } else {
            codeText.setText("Code not found");
        }

        viewGroupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getGroup_code.this, groupPage_Activity.class);
            intent.putExtra("groupId", groupId); // important!
            startActivity(intent);
            finish();
        });

        copyCodeButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Group Code", groupCode);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Copied to clipboard!", Toast.LENGTH_SHORT).show();
        });
    }
}
