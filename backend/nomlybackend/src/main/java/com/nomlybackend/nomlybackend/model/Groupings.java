package com.nomlybackend.nomlybackend.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Groupings")
public class Groupings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupId")
    private Integer groupId;

    @Column(name = "GroupName")
    private String groupName;
    @Column (name = "CreatedAt")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "grouping", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsersGroupings> usersGrouping;



    public Groupings() {}

    public Groupings(String groupName, LocalDateTime createdAt) {
        this.groupName = groupName;
        this.createdAt = createdAt;
    }

    public Integer getGroupId() {
        return groupId;
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

    public List<UsersGroupings> getUsersGrouping() {
        return usersGrouping;
    }

    public void setUsersGrouping(List<UsersGroupings> usersGrouping) {
        this.usersGrouping = usersGrouping;
    }


}
