package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.w3itexperts.ombe.R;

public class joinGroup_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_code); // links to enter_code.xml

        Button joinGroupButton = findViewById(R.id.joinGroupButton);
        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to GroupMainPageActivity
                Intent intent = new Intent(joinGroup_Activity.this, groupPage_Activity.class);
                startActivity(intent);
            }
        });
    }
}
