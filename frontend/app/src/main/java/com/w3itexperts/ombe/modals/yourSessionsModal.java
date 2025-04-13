package com.w3itexperts.ombe.modals;

public class yourSessionsModal {
    private String restaurantName;
    private String DateTimeAddress;
    private String GroupName;
    private String sessionStatus;

    private String sessionTitle;

    // Constructor here ====================================

    public yourSessionsModal(String restaurantName,String DateTimeAddress,String GroupName, String sessionStatus, String sessionTitle)
    {
        this.DateTimeAddress = DateTimeAddress;
        this.restaurantName = restaurantName;
        this.GroupName = GroupName;
        this.sessionStatus = sessionStatus;
        this.sessionTitle = sessionTitle;
    }

    // getter and setter here ==========================

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getDateTimeAddress() {
        return DateTimeAddress;
    }

    public String getGroupName() {
        return GroupName;
    }

    public String getSessionStatus() {
        return sessionStatus;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }
}
