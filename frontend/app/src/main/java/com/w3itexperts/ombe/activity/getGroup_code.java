package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.w3itexperts.ombe.R;

public class getGroup_code extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_group_code); // links to get_group_code.xml

        Button viewGroupButton = findViewById(R.id.viewGroupButton);
        viewGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Navigate to GroupMainPageActivity
                Intent intent = new Intent(getGroup_code.this, groupPage_Activity.class);
                startActivity(intent);
            }
        });
    }
}
