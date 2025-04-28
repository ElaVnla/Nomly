package com.nomlybackend.nomlybackend.model.eateries.Nearby;

public enum ExcludedType {
    GAS_STATION("gas_station"),
    STEAK_HOUSE("steak_house"),
    SEAFOOD_RESTAURANT("seafood_restaurant");

    private final String type;

    ExcludedType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    // Static method to map a string to the corresponding ExcludedType enum
    public static ExcludedType fromPreference(String preference) {
        return switch (preference.trim().toLowerCase()) {  // Normalize case and trim spaces
            case "no beef" -> STEAK_HOUSE;
            case "no seafood" -> SEAFOOD_RESTAURANT;
            default -> null;  // Return null if no exclusion matches
        };
    }
}
