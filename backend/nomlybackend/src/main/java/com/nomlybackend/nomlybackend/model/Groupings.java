package com.nomlybackend.nomlybackend.model;


import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Groupings")
public class Groupings extends Profile{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupId")
    private Integer groupId;

    @Column(name = "GroupName")
    private String groupName;

    @Column (name = "groupCode")
    private String groupCode;

    @OneToMany(mappedBy = "grouping", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsersGroupings> usersGrouping;

    @OneToMany(mappedBy = "grouping", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sessions> sessions;



    public Groupings() {
    }

    public Groupings(String groupName, String groupCode) {
        super.setCreatedAt();
        this.groupName = groupName;
        this.groupCode = groupCode;
    }

    public Integer getGroupId() { return groupId; }

    public String getGroupName() { return groupName; }

    public void setGroupName(String groupName) { this.groupName = groupName; }

    public List<UsersGroupings> getUsersGrouping() { return usersGrouping; }

    public void setUsersGrouping(List<UsersGroupings> usersGrouping) { this.usersGrouping = usersGrouping; }

    public List<Sessions> getSessions() { return sessions; }

    public void setSessions(List<Sessions> sessions) { this.sessions = sessions; }

    public void setGroupId(Integer groupId) { this.groupId = groupId; }

    public String getGroupCode() { return groupCode; }

    public void setGroupCode(String groupCode) { this.groupCode = groupCode; }
}