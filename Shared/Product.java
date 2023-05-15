package Shared;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 2L;
    private ProductType type;
    private ProductState state;
    private double price;
    private int yearOfProduction;
    private String color;
    private String condition;

    public enum ProductType{
        BOOK,
        ELECTRONICS,
        CLOTHING,
        FURNITURE,
        BEAUTY,
        SPORTS,
        TOYS,
        FOOD,
        OTHER
    }

    public enum ProductState {
        AVAILABLE,
        PENDING,
        SOLD
    }


    public Product(ProductType type, double price, int yearOfProduction, String color, String condition, ProductState state) {
        this.type = type;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.color = color;
        this.condition = condition;
        this.state = state;
    }

    // Getters and setters

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }
}
