package com.example.happytechhomepageui.Modals;

import java.util.HashMap;

public class Product {
    private int productID;
    private String name;
    private String description;
    private long price;
    private String category;



    public Product(int productID, String name, String description, long price, String category) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Product(){

    }
}