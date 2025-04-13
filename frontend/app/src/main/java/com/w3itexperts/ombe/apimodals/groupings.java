package com.w3itexperts.ombe.apimodals;

import java.time.LocalDateTime;
import java.util.List;

public class groupings {
    private int groupId;
    private String groupName;
    private int GroupPic;
    private String createdAt;
    private String groupCode;
    private List<users> users;
    private List<sessions> sessions;

    // Constructor here ======================

    public groupings(int groupId, String groupName, String createdAt, List<users> users, List<sessions> sessions) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.createdAt = createdAt;
        this.users = users;
        this.sessions = sessions;
    }
    public groupings(int groupId, String groupName, int groupPic, String createdAt, String groupCode) {
        groupId = groupId;
        groupName = groupName;
        GroupPic = groupPic;
        this.createdAt = createdAt;
        this.groupCode = groupCode;
    }

    public groupings() {
        // empty constructor required for session creation
    }
    // getter and setter here ====================

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        groupName = groupName;
    }

    public int getGroupPic() {
        return GroupPic;
    }

    public void setGroupPic(int groupPic) {
        GroupPic = groupPic;
    }

    public List<com.w3itexperts.ombe.apimodals.users> getUsers() {
        return users;
    }

    public int getNoUsers()
    {
        return this.users.size();
    }

    public int getNoSessions()
    {
        return this.sessions.size();
    }

    public void setUsers(List<com.w3itexperts.ombe.apimodals.users> users) {
        this.users = users;
    }

    public List<com.w3itexperts.ombe.apimodals.sessions> getSessions() {
        return sessions;
    }

    public void setSessions(List<com.w3itexperts.ombe.apimodals.sessions> sessions) {
        this.sessions = sessions;
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
}