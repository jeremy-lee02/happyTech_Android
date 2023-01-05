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
                    int id = Integer.parseInt(dsp.getKey());
                    long price = (parseLong(dsp.child("price").getValue().toString()));
                    String name = dsp.child("name").getValue().toString();
                    String description = dsp.child("description").getValue().toString();
                    Product product = new Product(id,name,description,price);
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
    //Add PRODUCT
    public void addProduct(int productID, String name, String description, long price) {
        db = FirebaseDatabase.getInstance().getReference("Products");
        Product product = new Product(productID,  name,  description,  price);
        db.child(Integer.toString(productID)).setValue(product);
    }
    //Update PRODUCT
    public void updateProduct(int productID, String name, String description, long price){
        db = FirebaseDatabase.getInstance().getReference("Products");
        HashMap product = new HashMap();
        product.put("productID", productID);
        product.put("name", name);
        product.put("description", description);
        product.put("price", price);
        db.child(Integer.toString(productID)).updateChildren(product).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                //TODO
            }
        });

    }
    //Delete PRODUCT
    public void deleteProduct(int productID){
        db = FirebaseDatabase.getInstance().getReference("Products");
        db.child(Integer.toString(productID)).removeValue();
    }
}
