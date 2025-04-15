package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GroupingsDTO {

    private Integer groupId;
    private String groupName;
    private LocalDateTime createdAt;
    //    private List<Integer> userGroupIds;
    private String groupCode;
    private List<UsersDTO> users;
    private List<SessionsDTO> sessions;
    private byte[] image;


    public GroupingsDTO() {
    }

    public GroupingsDTO(Groupings grouping, boolean includeUsers) {
        this.groupId = grouping.getGroupId();
        this.groupName = grouping.getGroupName();
        this.createdAt = grouping.getCreatedAt();
        this.groupCode = grouping.getGroupCode();
        Images imageId = grouping.getImage();
        if ( imageId != null){
            this.image = imageId.getProfilePicture();
        }


//        if (grouping.getUsersGrouping() == null){
//            this.userGroupIds = new ArrayList<>();
//        }else{
//            this.userGroupIds = grouping.getUsersGrouping().stream().map(UsersGroupings::getUserGroupId).toList();
//        }
        if (includeUsers && grouping.getUsersGrouping() != null) {
            this.users = grouping.getUsersGrouping().stream()
                    .map(ug -> new UsersDTO(ug.getUser(), false))  // prevent loop
                    .toList();
        } else {
            this.users = new ArrayList<>();
        }

        if (grouping.getSessions() == null){
            this.sessions = new ArrayList<>();
        }else{
            this.sessions = grouping.getSessions().stream()
                    .map(session -> new SessionsDTO(session, false))
                    .toList();
        }
    }


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
    //    public List<Integer> getUserGroupIds() {
//        return userGroupIds;
//    }
//
//    public void setUserGroupIds(List<Integer> userGroupIds) {
//        this.userGroupIds = userGroupIds;
//    }


    public List<UsersDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UsersDTO> users) {
        this.users = users;
    }

    public List<SessionsDTO> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionsDTO> sessions) {
        this.sessions = sessions;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
