package com.nomlybackend.nomlybackend.model.eateries;

public enum IncludedType {
    RESTAURANT("restaurant"),
    VEGETARIAN_RESTAURANT("vegetarian_restaurant"),
    VEGAN_RESTAURANT("vegan_restaurant");

    private final String type;

    IncludedType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }// Static method to map a string to the corresponding IncludedType enum

    public static IncludedType fromPreference(String preference) {
        return switch (preference.trim().toLowerCase()) {  // Normalize case and trim spaces
            case "vegetarian" -> VEGETARIAN_RESTAURANT;
            case "vegan" -> VEGAN_RESTAURANT;
            default -> RESTAURANT;  // Default to 'restaurant' if no match
        };
    }
}
