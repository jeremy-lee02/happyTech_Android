package com.example.happytechhomepageui.Modals;

import java.io.Serializable;
import java.util.HashMap;

public class Product implements Serializable {
    private int productID;
    private String name;
    private String description;
    private long price;
    private String category;
    private int available_num;



    public Product(int productID, String name, String description, long price, String category, int available_num) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.available_num = available_num;
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

    public int getAvailable_num() {
        return available_num;
    }

    public void setAvailable_num(int available_num) {
        this.available_num = available_num;
    }

    public Product(){

    }
}