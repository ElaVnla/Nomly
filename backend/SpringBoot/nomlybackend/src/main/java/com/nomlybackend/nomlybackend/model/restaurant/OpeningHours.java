package com.nomlybackend.nomlybackend.model.restaurant;

import jakarta.persistence.Embeddable;

@Embeddable
public class OpeningHours {
    private double startTime;
    private double endTime;
    private String weekdayDescriptions;
    private String nextOpenTime;
    private String nextCloseTime;
    private boolean openNow;

    public OpeningHours(double startTime, double endTime, String weekdayDescriptions, String nextOpenTime, String nextCloseTime, boolean openNow) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekdayDescriptions = weekdayDescriptions;
        this.nextOpenTime = nextOpenTime;
        this.nextCloseTime = nextCloseTime;
        this.openNow = openNow;
    }

    public OpeningHours(){

    }
}
