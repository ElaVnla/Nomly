package com.w3itexperts.ombe.apimodals;

// front end
public class usersDTO {
    private int userId;
    private String username;
    private String email;

    // Optional: if you need more
    private String preferences;
    private String createdAt;
    private String image;

    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public String getPreferences() { return preferences; }
    public String getCreatedAt() { return createdAt; }
    public String getImage() { return image; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
    public void setImage(String image) { this.image = image; }
}