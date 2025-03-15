package com.w3itexperts.ombe.modals;

public class wishlistModal {
    private int coffeeImage;
    private String coffeeName;
    private String coffeeTag;
    private String coffeePrice;
    private boolean isInWishlist;

    // Getter and Setter methods

    public int getCoffeeImage() {
        return coffeeImage;
    }

    public void setCoffeeImage(int coffeeImage) {
        this.coffeeImage = coffeeImage;
    }

    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }

    public String getCoffeeTag() {
        return coffeeTag;
    }

    public void setCoffeeTag(String coffeeTag) {
        this.coffeeTag = coffeeTag;
    }

    public String getCoffeePrice() {
        return coffeePrice;
    }

    public void setCoffeePrice(String coffeePrice) {
        this.coffeePrice = coffeePrice;
    }

    public boolean isInWishlist() {
        return isInWishlist;
    }

    public void setInWishlist(boolean inWishlist) {
        isInWishlist = inWishlist;
    }
}
