package com.w3itexperts.ombe.modals;

public class MyOrderModal {
    private String coffeeName;
    private String coffeeTag;
    private String coffeePrice;
    private String coffeeRating;
    private int coffeeImage;
    private String orderStatus; // "Ongoing" or "Completed"

    // Getters and Setters
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

    public String getCoffeeRating() {
        return coffeeRating;
    }

    public void setCoffeeRating(String coffeeRating) {
        this.coffeeRating = coffeeRating;
    }

    public int getCoffeeImage() {
        return coffeeImage;
    }

    public void setCoffeeImage(int coffeeImage) {
        this.coffeeImage = coffeeImage;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
