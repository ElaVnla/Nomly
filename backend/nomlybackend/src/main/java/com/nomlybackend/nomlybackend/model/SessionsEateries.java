package com.nomlybackend.nomlybackend.model;

import com.nomlybackend.nomlybackend.model.eateries.Eateries;
import jakarta.persistence.*;

@Entity
@Table(name = "SessionsEateries")
public class SessionsEateries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SessionEateryId")
    private Integer sessionEateryId;

    @ManyToOne
    @JoinColumn(name = "SessionId")
    private Sessions session;

    @ManyToOne
    @JoinColumn(name = "EateryId")
    private Eateries eatery;


    public SessionsEateries() {
    }

    public SessionsEateries(Sessions session, Eateries eatery) {
        this.session = session;
        this.eatery = eatery;
    }

    public Integer getSessionEateryId() {
        return sessionEateryId;
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
}
