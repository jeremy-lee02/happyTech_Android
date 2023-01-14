package com.example.happytechhomepageui.Modals;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private User user;
    private List<Product> products;

    public Cart(User user) {
        this.user = user;
        this.products = new ArrayList<>();
    }
    public Cart(){
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if(products == null){
            products = new ArrayList<>();
        }
        for (Product p : products) {
            if (p.getProductID().equals(product.getProductID())) {
                p.setQuantity(p.getQuantity() + product.getQuantity());
                return;
            }
        }
        products.add(product);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

}
