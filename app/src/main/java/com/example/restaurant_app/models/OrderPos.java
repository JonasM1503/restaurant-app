package com.example.restaurant_app.models;

/**
 *
 * @author Jonas Mitschke
 * @content definition of orderIdPos-class
 */
public class OrderPos {
    private String foodId;
    private String drinkId;
    private int quantity;


// constructors
    public OrderPos() {}

    public OrderPos(Food food, int quantity) {
        this.foodId = food.getFoodId();
        this.quantity = quantity;
    }

    public OrderPos(Drink drink, int quantity) {
        this.drinkId = drink.getDrinkId();
        this.quantity = quantity;
    }

// getters
    public String getFoodId() { return this.foodId; }

    public String getDrinkId() { return this.drinkId; }

    public int getQuantity() {
        return quantity;
    }

    // setters
    public void setFoodId(String foodId) {
        if (this.drinkId != null) {
            this.drinkId = null;
        }
        this.foodId = foodId;
    }

    public void setDrinkId(String drinkId) {
        if (this.foodId != null) {
            this.foodId = null;
        }
        this.drinkId = drinkId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
