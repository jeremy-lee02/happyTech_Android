package com.example.happytechhomepageui.Modals;

public class Product {
    private  int productID;
    private String name;
    private String description;
    private long price;

    public Product(){

    }

    public Product(int productID, String name, String description, long price) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}