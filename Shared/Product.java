package Shared;

public class Product {
    private ProductType type;
    private double price;
    private int yearOfProduction;
    private String color;
    private String condition;
    private boolean isSold;

    private enum ProductType{
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


    public Product(ProductType type, double price, int yearOfProduction, String color, String condition) {
        this.type = type;
        this.price = price;
        this.yearOfProduction = yearOfProduction;
        this.color = color;
        this.condition = condition;
        this.isSold = false;
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

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }
}