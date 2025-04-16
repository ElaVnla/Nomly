package com.w3itexperts.ombe.apimodals;

import java.util.List;

public class groupings {
    private int groupId;
    private String groupName;
    private int groupPic; // Renamed to follow Java conventions
    private String createdAt;
    private String groupCode;
    private String image; // ✅ Base64 string from backend
    private List<users> users;
    private List<sessions> sessions;

    // ================== Constructors ==================

    public groupings(int groupId, String groupName, String createdAt, List<users> users, List<sessions> sessions) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.createdAt = createdAt;
        this.users = users;
        this.sessions = sessions;
    }

    public groupings(int groupId, String groupName, int groupPic, String createdAt, String groupCode) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPic = groupPic;
        this.createdAt = createdAt;
        this.groupCode = groupCode;
    }

    public groupings() {
        // Empty constructor required for deserialization / default init
    }

    // ================== Getters & Setters ==================

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getGroupPic() {
        return groupPic;
    }

    public void setGroupPic(int groupPic) {
        this.groupPic = groupPic;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<users> getUsers() {
        return users;
    }

    public void setUsers(List<users> users) {
        this.users = users;
    }

    public List<sessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<sessions> sessions) {
        this.sessions = sessions;
    }

    public int getNoUsers() {
        return (users != null) ? users.size() : 0;
    }

    public int getNoSessions() {
        return (sessions != null) ? sessions.size() : 0;
    }
}
