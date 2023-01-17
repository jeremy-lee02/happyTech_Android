package com.example.happytechhomepageui.Modals;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private User user;
    private HashMap<String,Integer> products;

    public Cart(User user) {
        this.user = user;
        this.products = new HashMap<>();
    }
    public Cart(){
        this.products = new HashMap<>();

    }

    public void addProduct(Product product,int quantity) {
        for(Map.Entry<String, Integer> p: products.entrySet()) {
            if (p.getValue().equals(product.getProductID())) {
                p.setValue(p.getValue() + quantity);
                return;
            }
        }
        products.put(product.getProductID().toString(),quantity);
    }

    public void setProducts (HashMap<String,Integer> products){
        this.products = products;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void clearCart(){
        this.products = new HashMap<>();
    }


    public HashMap<String,Integer> getProducts() {
        return products;
    }

}