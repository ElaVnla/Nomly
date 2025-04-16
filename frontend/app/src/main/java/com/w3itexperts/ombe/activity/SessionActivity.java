package com.w3itexperts.ombe.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.w3itexperts.ombe.R;
import com.w3itexperts.ombe.fragments.PlanSessionFragment;


public class SessionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        if (savedInstanceState == null) {
            // Check if we got session data via Intent
            Intent intent = getIntent();
            if (intent != null && intent.hasExtra("title")) {
                // Launch ViewSessionFragment with passed data
                Bundle args = new Bundle();
                args.putString("title", intent.getStringExtra("title"));
                args.putString("location", intent.getStringExtra("location"));
                args.putString("date", intent.getStringExtra("date"));
                args.putString("time", intent.getStringExtra("time"));
                args.putString("status", intent.getStringExtra("status"));
                args.putInt("sessionId", intent.getIntExtra("sessionId", -1));
                args.putInt("groupId", intent.getIntExtra("groupId", -1));
                args.putDouble("lat", intent.getDoubleExtra("lat", 0.0));
                args.putDouble("lng", intent.getDoubleExtra("lng", 0.0));
                args.putStringArrayList("members", intent.getStringArrayListExtra("members"));

                com.w3itexperts.ombe.fragments.ViewSessionFragment viewSessionFragment = new com.w3itexperts.ombe.fragments.ViewSessionFragment();
                viewSessionFragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_view, viewSessionFragment)
                        .commit();
            } else {
                // Default path: go to PlanSessionFragment
                PlanSessionFragment planSessionFragment = new PlanSessionFragment();
                Bundle args = new Bundle();
                args.putInt("groupId", intent.getIntExtra("groupId", -1)); // ✅ Pass along the groupId
                planSessionFragment.setArguments(args);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_view, planSessionFragment)
                        .commit();
            }
        }
    }
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_session);
//
//        if (savedInstanceState == null) {
//            // Start with PlanSessionFragment
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_view, new PlanSessionFragment())
//                    .commit();
//        }
//    }
}
