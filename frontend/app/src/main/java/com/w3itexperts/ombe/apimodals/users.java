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

    public List<groupings> getGroups() {
        return groups;
    }

    public void setGroups(List<groupings> groups) {
        this.groups = groups;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public int getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(int porfilePic) {
        ProfilePic = porfilePic;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        preferences = preferences;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override public String toString() { return "User{id=" + userId + ", username=" + username + ", email=" + email + "}"; }
}
