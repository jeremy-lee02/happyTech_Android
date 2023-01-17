package com.example.happytechhomepageui.Modals;

import static java.lang.Integer.parseInt;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private String customerId;
    private String orderDate;
    private boolean completed;
    private HashMap<Product, Integer> orderProducts;
    private int subtotal;

    public Order(){

    }
    public Order(String customerId,Date orderDate, @NonNull HashMap<Product, Integer> orderProducts, boolean completed) {
        this.customerId = customerId;
        this.orderProducts = orderProducts;
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.orderDate = dateFormat.format(orderDate);
        this.completed = completed;
        for(Map.Entry<Product, Integer> product: orderProducts.entrySet()) {
            subtotal += product.getKey().getPrice() * product.getValue();
        }
    }



    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public HashMap<Product, Integer> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(HashMap<Product, Integer> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }

    public OrderData ToFireBaseData(){
        OrderData orderData = new OrderData(this);
        return orderData;
    }


}

class OrderData{
    public String customerId;
    public String orderDate;
    public boolean completed;
    public HashMap<String, String> orderProducts;
    public int subtotal;

    public OrderData(Order order) {
        this.customerId = order.getCustomerId();
        this.orderDate = order.getOrderDate();
        this.completed = order.isCompleted();
        this.subtotal = order.getSubtotal();
        this.orderProducts = new HashMap<String,String>();
        for(Map.Entry<Product, Integer> product: order.getOrderProducts().entrySet()) {
            this.orderProducts.put((product.getKey().getProductID()),product.getValue().toString());
        }
    }
    public OrderData(){}

}
