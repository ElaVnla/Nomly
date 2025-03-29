package com.nomlybackend.nomlybackend;

public class Restaurant {
    private PlusCode restaurantId;
    private LatLong location;
    private OpeningHours regularOpeningHours;
    private PriceRange priceRange;
    private double cuisine;
}

class PlusCode {
    private String globalCode;
    private String compoundCode;
}

class OpeningHours {
    private Period[] periods;
    private String weekdayDescriptions;
    private SecondaryHoursType secondaryHoursType; // can omit?
    private SpecialDay specialDays; // can omit?
    private String nextOpenTime;
    private String nextCloseTime;
    private boolean openNow;
}