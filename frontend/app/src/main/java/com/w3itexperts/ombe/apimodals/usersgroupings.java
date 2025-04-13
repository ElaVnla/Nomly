package com.w3itexperts.ombe.apimodals;

import java.time.LocalDateTime;

public class usersgroupings {
    private int UserGroupId;
    private int GroupId;
    private int UserId;
    private String JoinedAt;

    // Constructor here =========================================================

    public usersgroupings(int userGroupId, int groupId, int userId, String joinedAt) {
        this.UserGroupId = userGroupId;
        this.GroupId = groupId;
        this.UserId = userId;
        this.JoinedAt = joinedAt;
    }

    public usersgroupings(int userId, int groupId) {
        this.UserId = userId;
        this.GroupId = groupId;
   }

   // setter and getter here ====================================
    public int getUserGroupId() {
        return UserGroupId;
    }

    public void setUserGroupId(int userGroupId) {
        UserGroupId = userGroupId;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getJoinedAt() {
        return JoinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        JoinedAt = joinedAt;
    }
}
