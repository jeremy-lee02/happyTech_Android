package com.example.happytechhomepageui.Services;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DatabaseHelper {
    private DatabaseReference db;

    public DatabaseHelper() {
    }
    ///FOR PRODUCT
    //Get PRODUCT
    public List<Product> getProducts(FirebaseCallbackProduct firebaseCallback){
        List<Product> productList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product product = new Product(id,name,description,price,category, available);
                    productList.add(product);
                }
                firebaseCallback.onCallback(productList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return productList;
    }

    //Get PRODUCT For searchview
    public List<Product> getSearchProducts(FirebaseCallbackProduct firebaseCallback){
        List<Product> productList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product product = new Product(id,name,description,price,category, available);
                    productList.add(product);
                }
                firebaseCallback.onCallback(productList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return productList;
    }
    //Get FEATURE PRODUCT
    public List<Product> getFeatureProducts(FirebaseCallbackProduct firebaseCallback){
        List<Product> featureProductList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.orderByKey().limitToLast(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product featureProduct = new Product(id,name,description,price,category, available);
                    featureProductList.add(featureProduct);
                }
                firebaseCallback.onCallback(featureProductList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return featureProductList;
    }

    // Get all monitors
    public List<Product> getMonitors(FirebaseCallbackProduct firebaseCallback){
        List<Product> productList = new ArrayList<Product>();
        List<Product> monitorList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product product = new Product(id,name,description,price,category, available);
                    productList.add(product);
                }
                for (Product monitors:productList
                     ) {
                    if (monitors.getCategory().equals("Monitor")){
                        monitorList.add(monitors);
                    }
                }
                firebaseCallback.onCallback(monitorList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return monitorList;
    }
    // Get all keyboard
    public List<Product> getKeyboard(FirebaseCallbackProduct firebaseCallback){
        List<Product> productList = new ArrayList<Product>();
        List<Product> keyboardList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product product = new Product(id,name,description,price,category, available);
                    productList.add(product);
                }
                for (Product keyboard:productList
                ) {
                    if (keyboard.getCategory().equals("Keyboard")){
                        keyboardList.add(keyboard);
                    }
                }
                firebaseCallback.onCallback(keyboardList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return keyboardList;
    }
    // Get all mouse
    public List<Product> getMouse(FirebaseCallbackProduct firebaseCallback){
        List<Product> productList = new ArrayList<Product>();
        List<Product> mouseList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product product = new Product(id,name,description,price,category, available);
                    productList.add(product);
                }
                for (Product monitors:productList
                ) {
                    if (monitors.getCategory().equals("Mouse")){
                        mouseList.add(monitors);
                    }
                }
                firebaseCallback.onCallback(mouseList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return mouseList;
    }
    // Get all headphone
    public List<Product> getHeadphone(FirebaseCallbackProduct firebaseCallback){
        List<Product> productList = new ArrayList<Product>();
        List<Product> headphoneList = new ArrayList<Product>();
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    String id = dsp.getKey();
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    int available = Integer.parseInt(dsp.child("available").getValue().toString()) ;
                    Product product = new Product(id,name,description,price,category, available);
                    productList.add(product);
                }
                for (Product monitors:productList
                ) {
                    if (monitors.getCategory().equals("Headphone")){
                        headphoneList.add(monitors);
                    }
                }
                firebaseCallback.onCallback(headphoneList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return headphoneList;
    }




    // No need full CRUD for users
//    //Add PRODUCT
//    public void addProduct(int productID, String name, String description, long price, String category) {
//        db = FirebaseDatabase.getInstance().getReference("Products");
//        Product product = new Product(productID,  name,  description,  price, category);
//        db.child(Integer.toString(productID)).setValue(product);
//    }
//    //Update PRODUCT
//    public void updateProduct(int productID, String name, String description, long price, String category){
//        db = FirebaseDatabase.getInstance().getReference("Products");
//        HashMap product = new HashMap();
//        product.put("productID", productID);
//        product.put("name", name);
//        product.put("description", description);
//        product.put("price", price);
//        product.put("category", category);
//
//        db.child(Integer.toString(productID)).updateChildren(product).addOnCompleteListener(new OnCompleteListener() {
//            @Override
//            public void onComplete(@NonNull Task task) {
//                //TODO
//            }
//        });
//
//    }
//    //Delete PRODUCT
//    public void deleteProduct(int productID){
//        db = FirebaseDatabase.getInstance().getReference("Products");
//        db.child(Integer.toString(productID)).removeValue();
//    }
}
