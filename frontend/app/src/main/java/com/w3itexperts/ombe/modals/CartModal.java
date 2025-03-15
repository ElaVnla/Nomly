package com.w3itexperts.ombe.modals;

public class CartModal {
    private int coffeeImage;
    private String coffeeName;
    private String coffeePrice;
    private int quantity;

    public CartModal(int coffeeImage, String coffeeName, String coffeePrice, int quantity) {
        this.coffeeImage = coffeeImage;
        this.coffeeName = coffeeName;
        this.coffeePrice = coffeePrice;
        this.quantity = quantity;
    }

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

    public String getCoffeePrice() {
        return coffeePrice;
    }

    public void setCoffeePrice(String coffeePrice) {
        this.coffeePrice = coffeePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return Double.parseDouble(coffeePrice.replace("$", "")) * quantity;
    }
}
