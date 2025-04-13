package com.w3itexperts.ombe.apimodals;

public class usersUpdate {

    private String username;
    private String password;
    private String email;
    private String preferences;

    // constructor ====================================================
    public usersUpdate(String username, String password, String email, String preferences) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.preferences = preferences;
    }

    // getter and setter ====================================

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}
