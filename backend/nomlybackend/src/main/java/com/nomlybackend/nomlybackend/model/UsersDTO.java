package com.nomlybackend.nomlybackend.model;

import org.apache.catalina.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersDTO {
    private Integer userId;
    private String username;
    private String email;
    private String password;
    private String preferences;
    private LocalDateTime createdAt;
//    private List<Integer> userGroupIds;
    private List<GroupingsDTO> groups;
    private byte[] image;

    public UsersDTO(){}

    public UsersDTO(Users user){
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.preferences = user.getPreferences();
        this.createdAt = user.getCreatedAt();
        this.groups = new ArrayList<>();
        Images imageId = user.getImage();
        if ( imageId != null ){ this.image = imageId.getProfilePicture(); }
    }

    public UsersDTO(Users user, boolean includeGroups) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.preferences = user.getPreferences();
        this.createdAt = user.getCreatedAt();
        Images imageId = user.getImage();
        if ( imageId != null){ this.image = imageId.getProfilePicture(); }
//        if (user.getUserGroupings() == null){
//            this.userGroupIds = new ArrayList<>();
//        }else{
//            this.userGroupIds = user.getUserGroupings().stream().map(UsersGroupings::getUserGroupId).toList();
//        }
        if (includeGroups && user.getUserGroupings() != null) {
            this.groups = user.getUserGroupings().stream()
                    .map(ug -> new GroupingsDTO(ug.getGrouping(), false))
                    .toList();
        } else {
            this.groups = new ArrayList<>();
        }

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

//    public List<Integer> getUserGroupIds() {
//        return userGroupIds;
//    }
//
//    public void setUserGroupIds(List<Integer> userGroupIds) {
//        this.userGroupIds = userGroupIds;
//    }


    public List<GroupingsDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupingsDTO> groups) {
        this.groups = groups;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}