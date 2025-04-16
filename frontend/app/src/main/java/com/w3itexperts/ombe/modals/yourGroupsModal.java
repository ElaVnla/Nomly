package com.w3itexperts.ombe.modals;

import android.graphics.Bitmap;

public class yourGroupsModal {
    private Bitmap groupImage;
    private String noOfSessions;
    private String noOfMembers;
    private String groupName;
    private int groupId;

    // Constructor
    public yourGroupsModal(String noOfMembers, String noOfSessions, Bitmap groupImage, String groupName, int groupId) {
        this.noOfMembers = noOfMembers;
        this.noOfSessions = noOfSessions;
        this.groupImage = groupImage;
        this.groupName = groupName;
        this.groupId = groupId;
    }

    // Getters
    public Bitmap getGroupImage() {
        return groupImage;
    }

    public String getNoOfMembers() {
        return noOfMembers;
    }

    public String getNoOfSessions() {
        return noOfSessions;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupId() {
        return groupId;
    }
}
