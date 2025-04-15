package com.nomlybackend.nomlybackend.model;

import jakarta.persistence.*;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Users")
public class Users extends Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "UserId")
    private Integer userId;
    @Column (name = "Username")
    private String username;
    @Column (name = "Email")
    private String email;
    @Column (name = "Password")
    private String password;
    @Column (name = "Preferences")
    private String preferences;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UsersGroupings> userGroupings;


    public Users(){
        super();
    }

    public Users(String username, String email, String password, String preferences) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.preferences = preferences;
        setCreatedAt();
    }


    public Integer getUserId() {
        return userId;
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

    public List<UsersGroupings> getUserGroupings() {
        return userGroupings;
    }

    public void setUserGroupings(List<UsersGroupings> userGroupings) {
        this.userGroupings = userGroupings;
    }
}
