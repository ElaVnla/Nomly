package com.nomlybackend.nomlybackend.model;


import jakarta.persistence.*;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Sessions")
public class Sessions extends CreateDatabaseEntry{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "SessionId")
    private Integer sessionId;

    @ManyToOne
    @JoinColumn(name = "GroupId")
    private Groupings grouping;

    @Column(name = "SessionName")
    private String sessionName;

    @Column(name = "Location")
    private String location;

    @Column(name = "Latitude")
    private Double latitude;

    @Column(name = "Longitude")
    private Double longitude;

    @Column(name = "MeetingDateTime")
    private LocalDateTime meetingDateTime;

    @Column(name = "Completed")
    private Boolean completed;

    public Sessions() {
    }


    public Sessions(Groupings grouping, String sessionName, String location, Double latitude, Double longitude, LocalDateTime meetingDateTime, Boolean completed) {
        this.grouping = grouping;
        this.sessionName = sessionName;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.meetingDateTime = meetingDateTime;
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

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() { return latitude; }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public LocalDateTime getMeetingDateTime() {
        return meetingDateTime;
    }

    public void setMeetingDateTime(LocalDateTime meetingDateTime) {
        this.meetingDateTime = meetingDateTime;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
