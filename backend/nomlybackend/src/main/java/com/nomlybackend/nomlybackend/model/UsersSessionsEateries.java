package com.nomlybackend.nomlybackend.model;


import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import jakarta.persistence.*;

@Entity
@Table(name = "UsersSessionsEateries")
public class UsersSessionsEateries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserSessionEateryId")
    private Integer userSessionEateryId;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "SessionId")
    private Sessions session;

    @ManyToOne
    @JoinColumn(name = "EateryId")
    private Eateries eatery;

    @Column(name ="Liked")
    private Boolean liked;

    public UsersSessionsEateries() {
    }

    public UsersSessionsEateries(Users user, Sessions session, Eateries eatery, Boolean liked) {
        this.liked = liked;
        this.eatery = eatery;
        this.session = session;
        this.user = user;
    }

    public Integer getUserSessionEateryId() {
        return userSessionEateryId;
    }


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Sessions getSession() {
        return session;
    }

    public void setSession(Sessions session) {
        this.session = session;
    }

    public Eateries getEatery() {
        return eatery;
    }

    public void setEatery(Eateries eatery) {
        this.eatery = eatery;
    }


    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
