package com.w3itexperts.ombe.modals;

public class yourGroupsModal {
    private int groupImage;
    private String noOfSessions;
    private String NoOfMembers;
    private String groupName;

    private int groupId; //new id



    public yourGroupsModal(String NoOfMembers, String noOfSessions, int groupImage, String groupName, int groupId) {
        this.NoOfMembers = NoOfMembers;
        this.noOfSessions = noOfSessions;
        this.groupImage = groupImage;
        this.groupName = groupName;
        this.groupId = groupId;
    }

    // Getter and Setter for group Images
    public int getgroupImage() {
        return groupImage;
    }

//    public void setgroupImage(int groupImage) {
//        this.groupImage = groupImage;
//    }

    // Getter and Setter for No of Groups
    public String getNoOfMembers() {
        return NoOfMembers;
    }

//    public void setNoOfGroups(String NoOfGroups) {
//        this.NoOfGroups = NoOfGroups;
//    }

    // Getter and Setter for no of sessions
    public String getnoOfSessions() {
        return noOfSessions;
    }

//    public void setnoOfSessions(String noOfSessions) {
//        this.noOfSessions = noOfSessions;
//    }

    // Getter and Setter for no of group of Name
    public String getgroupName() {
        return groupName;
    }

//    public void setgroupName(String groupName) {
//        this.groupName = groupName;
//    }

    public int getGroupId() {
        return groupId;
    }
}
