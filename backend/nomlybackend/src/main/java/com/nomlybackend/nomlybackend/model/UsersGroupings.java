package com.nomlybackend.nomlybackend.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "UsersGroupings")
public class UsersGroupings {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserGroupId")
    private Integer userGroupId;

    @ManyToOne
    @JoinColumn(name = "UserId")
    @JsonIgnore
    private Users user;

    @ManyToOne
    @JoinColumn(name = "GroupId")
    @JsonIgnore
    private Groupings grouping;


    @Column (name = "JoinedAt")
    private LocalDateTime joinedAt;


    public UsersGroupings() {
    }

    public UsersGroupings(Users user, Groupings grouping, LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
        this.grouping = grouping;
        this.user = user;
    }

    public Integer getUserGroupId() {
        return userGroupId;
    }

    public Users getUser() {
        return user;
    }

    public Groupings getGrouping() {
        return grouping;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

}
