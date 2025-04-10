package com.w3itexperts.ombe.SessionService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.w3itexperts.ombe.apimodals.users;

public class SessionManager {
    private static final long SESSION_TIMEOUT = 10 * 60 * 1000; // 10 minutes
    private static final String PREFS_NAME = "userSession";
    private static final String KEY_CURRENT_USER = "currentUser";

    private Handler handler;
    private Runnable timeoutRunnable;
    private static SessionManager instance;

    // Store the logged in user details in memory for quick access
    private users currentUser;
    private SharedPreferences prefs;
    private final Gson gson = new Gson();

    public interface SessionTimeoutListener {
        void onSessionTimeout();
    }
    private SessionTimeoutListener listener;

    private SessionManager(Context context) {
        handler = new Handler(Looper.getMainLooper());
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        timeoutRunnable = new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onSessionTimeout();
                }
            }
        };

        // Try to load the current user from SharedPreferences if available.
        String userJson = prefs.getString(KEY_CURRENT_USER, null);
        if (userJson != null) {
            currentUser = gson.fromJson(userJson, users.class);
        }
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    public void setSessionTimeoutListener(SessionTimeoutListener listener) {
        this.listener = listener;
    }

    public void resetSessionTimer() {
        handler.removeCallbacks(timeoutRunnable);
        handler.postDelayed(timeoutRunnable, SESSION_TIMEOUT);
    }

    public void cancelSessionTimer() {
        handler.removeCallbacks(timeoutRunnable);
    }

    // Save user details when the user logs in.
    public void setCurrentUser(users user) {
        this.currentUser = user;
        // Serialize and store the user in SharedPreferences
        String userJson = gson.toJson(user);
        prefs.edit().putString(KEY_CURRENT_USER, userJson).apply();
    }

    // Retrieve user details from memory or SharedPreferences if needed.
    public users getCurrentUser() {
        if (currentUser == null) {
            String userJson = prefs.getString(KEY_CURRENT_USER, null);
            if (userJson != null) {
                currentUser = gson.fromJson(userJson, users.class);
            }
        }
        return currentUser;
    }
}
