package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.w3itexperts.ombe.APIservice.ApiClient;
import com.w3itexperts.ombe.APIservice.ApiService;
import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.SessionService.SessionManager;
import com.w3itexperts.ombe.apimodals.groupings;
import com.w3itexperts.ombe.apimodals.users;
import com.w3itexperts.ombe.apimodals.usersgroupings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class joinGroup_Activity extends AppCompatActivity {

    private EditText codeInput;
    private Button joinGroupButton;
    private ImageView backButton;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_code);

        codeInput = findViewById(R.id.enterCode);
        joinGroupButton = findViewById(R.id.joinGroupButton);
        backButton = findViewById(R.id.backbtnToHomeFromEnterCode);

        userId = SessionManager.getInstance(this).getCurrentUser().getUserId();

        // ⬅️ Go back to previous screen
        backButton.setOnClickListener(v -> {
            finish(); // closes this activity and returns to the previous one (home_fragment)
        });

        joinGroupButton.setOnClickListener(v -> {
            String groupCode = codeInput.getText().toString().trim();
            if (groupCode.isEmpty()) {
                Toast.makeText(this, "⚠️ Please enter a group code", Toast.LENGTH_SHORT).show();
                return;
            }

            joinGroupByCode(groupCode);
        });
    }

    private void joinGroupByCode(String code) {
        ApiService apiService = ApiClient.getApiService();
        Call<groupings> getGroupCall = apiService.getGroupingByCode(code);

        getGroupCall.enqueue(new Callback<groupings>() {
            @Override
            public void onResponse(Call<groupings> call, Response<groupings> response) {
                if (response.isSuccessful() && response.body() != null) {
                    groupings group = response.body();
                    int groupId = group.getGroupId();
                    List<users> existingUsers = group.getUsers();

                    boolean isAlreadyMember = false;
                    if (existingUsers != null) {
                        for (users u : existingUsers) {
                            if (u.getUserId() == userId) {
                                isAlreadyMember = true;
                                break;
                            }
                        }
                    }

                    if (isAlreadyMember) {
                        Toast.makeText(joinGroup_Activity.this, "🚫 You’re already in this group!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    // Join the group
                    Map<String, String> data = new HashMap<>();
                    data.put("userId", String.valueOf(userId));
                    data.put("groupId", String.valueOf(groupId));

                    apiService.addUserToGrouping(data).enqueue(new Callback<usersgroupings>() {
                        @Override
                        public void onResponse(Call<usersgroupings> call, Response<usersgroupings> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(joinGroup_Activity.this, "🎉 Successfully joined the group!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(joinGroup_Activity.this, groupPage_Activity.class);
                                intent.putExtra("groupId", groupId);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(joinGroup_Activity.this, "😢 Couldn't join the group", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<usersgroupings> call, Throwable t) {
                            Toast.makeText(joinGroup_Activity.this, "❌ Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    Toast.makeText(joinGroup_Activity.this, "❌ Invalid group code!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<groupings> call, Throwable t) {
                Toast.makeText(joinGroup_Activity.this, "💔 Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
