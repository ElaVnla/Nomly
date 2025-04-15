package com.nomlybackend.nomlybackend.model.eateries;

import java.util.List;
import java.util.Set;

public class PlacesDTO {
    private Place[] places;

    public Place[] getPlaces(){ return places; }

    public static class Place{
        static Set<String> defaultTypes = Set.of(
                "restaurant",
                "food",
                "point_of_interest",
                "establishment"
        );
        private String id;
        private LatLng location;
        private PriceLevel priceLevel;
        private DisplayName displayName;
        private List<String> types;
        private Double rating;
        private List<Photo> photos;
        private String formattedAddress;

        public static Set<String> getDefaultTypes() {
            return defaultTypes;
        }

        public static void setDefaultTypes(Set<String> defaultTypes) {
            Place.defaultTypes = defaultTypes;
        }

        public String getId() { return id; }

        public void setId(String id) { this.id = id; }

        public LatLng getLocation() {
            return location;
        }

        public void setLocation(LatLng location) {
            this.location = location;
        }

        public PriceLevel getPriceLevel() {
            return priceLevel;
        }

        public void setPriceLevel(PriceLevel priceLevel) {
            this.priceLevel = priceLevel;
        }

        public DisplayName getDisplayName() {
            return displayName;
        }

        public void setDisplayName(DisplayName displayName) {
            this.displayName = displayName;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public List<Photo> getPhotos() { return photos; }

        public void setPhotos(List<Photo> photos) { this.photos = photos; }

        public String formattedAddress() { return formattedAddress; }

        public void setFormattedAddress(String formattedAddress) { this.formattedAddress = formattedAddress; }

        public Eateries toEntity(){
            Eateries eatery = new Eateries();
            eatery.setEateryId(this.id);
            eatery.setDisplayName(this.displayName.text);
            eatery.setLatitude(this.location.latitude);
            eatery.setLongitude(this.location.longitude);
            if (this.priceLevel == null){
                eatery.setPriceLevel(PriceLevel.PRICE_LEVEL_UNSPECIFIED);
            } else {
                eatery.setPriceLevel(this.priceLevel);
            }
            List<String> filtered = this.types.stream()
                            .filter(type -> !defaultTypes.contains(type))
                            .toList();
            eatery.setCuisine(String.valueOf(filtered)); // can be csv of multiple "chinese_restaurant, vegetarian_restaurant"
            eatery.setRating(this.rating);
            eatery.setLocation(this.formattedAddress);
            eatery.setOperationHours("to be implemented");
            return eatery;
        }
    }
}

