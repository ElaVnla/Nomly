package com.w3itexperts.ombe.apimodals;

import java.time.LocalDateTime;
import java.util.List;

public class users {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String image;
    private String preferences;
    private String createdAt;
    private List<groupings> groups;

    // constructor here ====================================================

    public users(String username, String email, String password, String preferences)
    {

        this.username = username;
        this.password = password;
        this.preferences = preferences;
        this.email = email;
    }

    public users(int userId, String username, String password, String email, String image, String preferences, String createdAt, List<groupings> groups) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.preferences = preferences;
        this.createdAt = createdAt;
        this.groups = groups;
    }

    public users() {

    }

    // setter and getter here ===========================================

//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//    public void setUsername(String username) {
//        this.username = username;
//    }
//    public void setPassword(String password) {
//        this.password = password;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public void setGroups(List<groupings> groups) {
        this.groups = groups;
    }

    public List<groupings> getGroups() {
        return groups;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }



    public String getPassword() {
        return password;
    }



    public String getEmail() {
        return email;
    }



    public String getImage() {
        return image;
    }



    public String getPreferences() {
        return preferences;
    }



    public String getCreatedAt() {
        return createdAt;
    }



    @Override public String toString() { return "User{id=" + userId + ", username=" + username + ", email=" + email + "}"; }
}
