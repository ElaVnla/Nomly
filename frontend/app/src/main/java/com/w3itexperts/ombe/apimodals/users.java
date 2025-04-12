package com.w3itexperts.ombe.apimodals;

import java.time.LocalDateTime;
import java.util.List;

public class users {
    private int userId;
    private String username;
    private String password;
    private String email;
    private int ProfilePic;
    private String preferences;
    private String createdAt;
    private List<groupings> groups;

    public users(String username, String email, String password, String preferences)
    {

        this.username = username;
        this.password = password;
        this.preferences = preferences;
        this.email = email;
    }

    public users(int userId, String username, String password, String email, int porfilePic, String preferences, String createdAt, List<groupings> groups) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.ProfilePic = porfilePic;
        this.preferences = preferences;
        this.createdAt = createdAt;
        this.groups = groups;
    }

    public users() {

    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
    public void setProfilePic(int profilePic) {
        this.ProfilePic = profilePic;
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



    public int getProfilePic() {
        return ProfilePic;
    }



    public String getPreferences() {
        return preferences;
    }



    public String getCreatedAt() {
        return createdAt;
    }



    @Override public String toString() { return "User{id=" + userId + ", username=" + username + ", email=" + email + "}"; }
}
