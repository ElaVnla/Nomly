package com.w3itexperts.ombe.SessionService;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.w3itexperts.ombe.activity.Welcome;
import com.w3itexperts.ombe.apimodals.users;

import java.util.HashMap;

// This is a session manager file
// This handles the storing of session once the user logs in
// To make it easier, user's details are also stored here (Global) so that they can accessed throughout the other pages
// We handle the session timeout here etc
// Following tutorial: https://ketulpatel.code.blog/2020/04/07/session-management-in-android-studio/
public class SessionManager {
    // Declare important variables =====================================================================
    // session timeout variable, set to 10 minutes
    // If no user activity detected within 10 minutes, automatically log them out
    private static final long SessionTimeoutDuration = 10 * 60 * 1000;
    private static final String KEY_CURRENT_USER = "currentUser";

    // To store user-specified configuration details, such as settings,
    // and to keep the user logged in to the app
    // useful in situations where we don't need to store a lot of data
    private SharedPreferences prefs;
    private SharedPreferences.Editor SessionEditor;
    private Context _context;
    private static final String PrefName = "UserSessionPrefs";
    private static final String IsLogin = "IsLoggedIn";

    // idk if i should store separately or store as a class but imma just put here first
    // security issue: not safe to store password like this especially if it's not encrypted
    private static final String SessionEmail = "email";
    private static final String SessionPassword = "password";

    // This ensures only a single instance exist during the app's liefcycle
    // This allow all parts of the app to use the same instance
    // Since there is only one user suppose to be logged in, in make sense to have
    // only one single instance
    // Must add "synchronized": prevent multiple instances being created - if more than one
    // thread got created, only one will work
    private static SessionManager instance;

    // Store user here for easier access and for session throughout the whole app
    private users currentUser;
    private final Gson GsonString = new Gson();

    // We add this because we want to handle this session across all the pages
    // Instead of let's say log out manually etc let the sessionn stuff be handled here
    // beautifully manages the session timeout instead of detecting it every single pages
    // Helps auto logout after inactivity
    private Handler handler;
    private Runnable timeoutRunnable;
    private SessionTimeoutListener listener;

    // Session functions here ==========================================
    private SessionManager(Context context)
    {
        this._context = context.getApplicationContext(); // avoiding memory leaks
        prefs = _context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
        SessionEditor = prefs.edit(); // prepare to write to the sharedpreferences

        // Handler and runnable - https://learn.microsoft.com/en-us/dotnet/api/android.os.handler?view=net-android-35.0
        // Handler "allows us to send and process Message and Runnable objects associated with a thread's MessageQueue"
        this.handler = new Handler(Looper.getMainLooper());
        // Will be triggered automatically when the session times out
        this.timeoutRunnable = () -> {
            if (listener != null) {
                listener.onSessionTimeout();
            }
            LoggedOutUser(); // call function to auto log out the user
        };

        // If there is a user present that we store in SharedPreferences, then we load it
        // if not then it should be empty
        // we use this to see if they log in previously
        String userJson = prefs.getString(KEY_CURRENT_USER, null);
        if (userJson != null) {
            // if user data found we deserialize the data
            currentUser = GsonString.fromJson(userJson, users.class);
        }
    }

    // This ensures only a single instance exist during the app's liefcycle
    // This allow all parts of the app to use the same instance
    // Since there is only one user suppose to be logged in, in make sense to have
    // only one single instance
    // Must add "synchronized": prevent multiple instances being created - if more than one
    // thread got created, only one will work

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context.getApplicationContext());
        }
        return instance;
    }

    // User functions stuf here ===============================================================

    // we store the email/password and user object here
    public void createLoginSession(users user) {
        SessionEditor.putBoolean(IsLogin, true);
        // Might not need this, see how
        // SessionEditor.putString(SessionEmail, email);
        // SessionEditor.putString(SessionPassword, password);

        this.currentUser = user;
        String userJson = GsonString.toJson(user);
        SessionEditor.putString(KEY_CURRENT_USER, userJson);
        SessionEditor.apply();
    }

    // Check if the user is logged in, return true if he or she is
    // return false if user not logged in
    public boolean IsLoggedIn() {
        return prefs.getBoolean(IsLogin, false);
    }

    public void checkLogin() {
        if (!this.IsLoggedIn()) {
            // if user is not logged in, bring them back to welcome page
            // not allowed to access others pages if they have not logged in
            redirectToWelcomePage();
        }
    }

    // we call the page where they redirect
    private void redirectToWelcomePage() {
        Intent i = new Intent(_context, Welcome.class);
        // clear any existing stack then start a new one
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }

    // When we want to get the user stuff
    public users getCurrentUser() {
        if (currentUser == null) {
            String userJson = prefs.getString(KEY_CURRENT_USER, null);
            if (userJson != null) {
                currentUser = GsonString.fromJson(userJson, users.class);
            }
        }
        return currentUser;
    }

    // When we set the user into the sessionmanager during login/signin
    public void setCurrentUser(users user) {
        this.currentUser = user;
        String userJson = GsonString.toJson(user);
        SessionEditor.putString(KEY_CURRENT_USER, userJson);
        SessionEditor.apply();
    }

    //    public HashMap<String , String> getUserDetails(){
    //        HashMap<String, String> user = new HashMap<String, String>();
    //
    //        user.put(SessionEmail, prefs.getString(SessionEmail,null));
    //        user.put(SessionPassword, prefs.getString(SessionPassword,null));
    //
    //        return user;
    //    }

    public void LoggedOutUser() {
        SessionEditor.clear();
        SessionEditor.commit();

        redirectToWelcomePage();
    }

    // Session stuff thingy here =======================================================
    public interface SessionTimeoutListener {
        void onSessionTimeout();
    }

    // call this to reset the session timer on the specific activity
    public void resetSessionTimer() {
        handler.removeCallbacks(timeoutRunnable);
        handler.postDelayed(timeoutRunnable, SessionTimeoutDuration);
    }

    // if we want to stop the session timer manually
    // use this function
    public void cancelSessionTimer() {
        handler.removeCallbacks(timeoutRunnable);
    }


    // if user has finished swiping
    public void markSwipingCompleted(int sessionId) {
        if (currentUser == null) return;

        String key = "swiped_session_" + sessionId + "_user_" + currentUser.getUserId();
        SessionEditor.putBoolean(key, true);
        SessionEditor.apply();
    }

    public boolean hasUserSwiped(int sessionId) {
        if (currentUser == null) return false;

        String key = "swiped_session_" + sessionId + "_user_" + currentUser.getUserId();
        return prefs.getBoolean(key, false);
    }

}
