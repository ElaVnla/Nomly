package com.nomlybackend.nomlybackend.model;


import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Sessions")
public class Sessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "SessionId")
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name = "GroupId")
    private Groupings grouping;

    @Column(name = "Location")
    private String location;

    @Column(name = "LatLong")
    private String latlong;


    @Column(name = "MeetingDateTime")
    private LocalDateTime meetingDateTime;

    @Column(name = "CreatedAt")
    private LocalDateTime createdAt;

    @Column(name = "Completed")
    private Boolean completed;

    public Sessions() {
    }

    public Sessions(Groupings grouping, String location, String latlong,LocalDateTime meetingDateTime, LocalDateTime createdAt, Boolean completed) {
        this.grouping = grouping;
        this.location = location;
        this.latlong = latlong;
        this.meetingDateTime = meetingDateTime;
        this.createdAt = createdAt;
        this.completed = completed;
    }

    public Integer getSessionId() {
        return sessionId;
    }

    public Groupings getGrouping() {
        return grouping;
    }

    public void setGrouping(Groupings grouping) {
        this.grouping = grouping;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latlong;
    }

    public String getLatlong() {
        return latlong;
    }

    public void setLatlong(String latlong) {
        this.latlong = latlong;
    }

    public LocalDateTime getMeetingDateTime() {
        return meetingDateTime;
    }

    public void setMeetingDateTime(LocalDateTime meetingDateTime) {
        this.meetingDateTime = meetingDateTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
