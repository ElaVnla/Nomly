package com.w3itexperts.ombe.apimodals;

import java.time.LocalDateTime;

public class sessions {
    private int sessionId;
    private Object grouping;
    private int GroupId;
    private String location;
    private String latlong;
    private String meetingDateTime;
    private String createdAt;
    private boolean completed;

    public sessions(int sessionId, Object grouping, String location, String latlong, String meetingDateTime, String createdAt, boolean completed) {
        this.sessionId = sessionId;
        this.grouping = grouping;
        this.location = location;
        this.latlong = latlong;
        this.meetingDateTime = meetingDateTime;
        this.createdAt = createdAt;
        this.completed = completed;
    }
    public sessions(int sessionId, int groupId, String location, String latLong, String meetingDateTime, String createdAt, boolean completed) {
        this.sessionId = sessionId;
        this.GroupId = groupId;
        this.location = location;
        this.latlong = latLong;
        this.meetingDateTime = meetingDateTime;
        this.createdAt = createdAt;
        this.completed = completed;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        sessionId = sessionId;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public String getLocation() {
        return location;
    }

    public Object getGrouping() {
        return grouping;
    }

    public void setGrouping(Object grouping) {
        this.grouping = grouping;
    }

    public void setLocation(String location) {
        location = location;
    }

    public String getLatLong() {
        return latlong;
    }

    public void setLatLong(String latLong) {
        latlong = latLong;
    }

    public String getMeetingDateTime() {
        return meetingDateTime;
    }

    public void setMeetingDateTime(String meetingDateTime) {
        meetingDateTime = meetingDateTime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
