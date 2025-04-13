package com.nomlybackend.nomlybackend.model;

import com.nomlybackend.nomlybackend.model.eateries.Eateries;

public class SessionsEateriesDTO {


    private Integer sessionEateryId;

    private SessionsDTO session;

    private Eateries eatery;

    public SessionsEateriesDTO(SessionsEateries sessionsEateries) {
        this.sessionEateryId = sessionsEateries.getSessionEateryId();
        this.session = new SessionsDTO(sessionsEateries.getSession());
        this.eatery = sessionsEateries.getEatery();
    }

    public Integer getSessionEateryId() {
        return sessionEateryId;
    }

    public void setSessionEateryId(Integer sessionEateryId) {
        this.sessionEateryId = sessionEateryId;
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
}
