package com.nomlybackend.nomlybackend.model;

import com.nomlybackend.nomlybackend.model.eateries.Eateries;

public class UsersSessionsEateriesDTO {


    private Integer userSessionEateryId;

    private UsersDTO user;

    private SessionsDTO session;

    private Eateries eatery;

    private Boolean liked;

    public UsersSessionsEateriesDTO(UsersSessionsEateries usersSessionsEateries) {
        this.userSessionEateryId = usersSessionsEateries.getUserSessionEateryId();
        this.user = new UsersDTO(usersSessionsEateries.getUser());
        this.session = new SessionsDTO(usersSessionsEateries.getSession());
        this.eatery = usersSessionsEateries.getEatery();
        this.liked = usersSessionsEateries.getLiked();
    }


    public Integer getUserSessionEateryId() {
        return userSessionEateryId;
    }

    public void setUserSessionEateryId(Integer userSessionEateryId) {
        this.userSessionEateryId = userSessionEateryId;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }

    public SessionsDTO getSession() {
        return session;
    }

    public void setSession(SessionsDTO session) {
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
