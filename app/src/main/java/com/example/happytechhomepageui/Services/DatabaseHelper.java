package com.example.happytechhomepageui.Services;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.happytechhomepageui.Modals.Order;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.repo.FireBaseCallbackImage;
import com.example.happytechhomepageui.repo.FireBaseCallbackOrder;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
    ///FOR ORDER
    public void addOrder(String customerId, HashMap<Product, Integer> orderProducts) {
        db = FirebaseDatabase.getInstance().getReference("Orders");
        Date date = Calendar.getInstance().getTime();
        Order order = new Order(customerId, date ,orderProducts, false);
        db.push().setValue(order.ToFireBaseData());
    }

    public void deleteOrder(int orderId){
        db = FirebaseDatabase.getInstance().getReference("Orders");
        db.child(Integer.toString(orderId)).removeValue();
    }

    public void completeOrder(int orderID){
        db = FirebaseDatabase.getInstance().getReference("Orders");
        db.child(Integer.toString(orderID)).child("completed").setValue("true");
    }
    public List<Order> getOrderByCustomerID(String customerID, List<Order> orderList){
        List<Order> result = new ArrayList<>();
        for (Order order: orderList
        ) {
            if(customerID.equals(order.getCustomerId())){
                result.add(order);
            }
        }
        return result;
    }

    public List<Order> getOrderByDate(Date date, List<Order> orderList){
        List<Order> result = new ArrayList<>();
        for (Order order: orderList
        ) {
            Date orderDate= new Date();
            try {
                orderDate=new SimpleDateFormat("dd/MM/yyyy").parse(order.getOrderDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(date.equals(order.getOrderDate())){
                result.add(order);
            }
        }
        return result;
    }

    public List<Order> getOrder(FireBaseCallbackOrder firebaseCallback){
//        List<Product> productList = new ArrayList<Product>();
        List<Order> orderList = new ArrayList<Order>();
        this.getProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> list) {
                db = FirebaseDatabase.getInstance().getReference("Orders");
                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dsp : snapshot.getChildren())
                        {
                            String customerId = (dsp.child("customerId").getValue().toString());
                            Date orderDate = new Date();
                            try {
                                orderDate = new SimpleDateFormat("dd-mm-yyyy").parse(dsp.child("orderDate").getValue().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            boolean completed = Boolean.parseBoolean(dsp.child("completed").getValue().toString());
                            HashMap <Product,Integer> orderProducts = new HashMap<>();
                            for(DataSnapshot product: dsp.child("orderProducts").getChildren()) {
                                orderProducts.put(list.get(Integer.parseInt(product.getKey())-1), Integer.valueOf(product.getValue().toString()));
                            }
                            Order order = new Order(customerId,orderDate,orderProducts,completed);
                            orderList.add(order);
                        }

                        firebaseCallback.onCallback(orderList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        return orderList;
    }




    // No need full CRUD for users
//    //Add PRODUCT
//    public void addProduct(int productID, String name, String description, long price, String category) {
//        db = FirebaseDatabase.getInstance().getReference("Products");
//        Product product = new Product(productID,  name,  description,  price, category);
//        db.child(Integer.toString(productID)).setValue(product);
//    }
    //Update PRODUCT
    public void updateProduct(int productID, String name, String description, long price, String category, int available){
        db = FirebaseDatabase.getInstance().getReference("Products");
        HashMap product = new HashMap();
        product.put("productID", productID);
        product.put("name", name);
        product.put("description", description);
        product.put("price", price);
        product.put("category", category);
        product.put("available", available);

        db.child(Integer.toString(productID)).updateChildren(product).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                //TODO
            }
        });
    }
    // Get Image
    public Bitmap getImage(String imgName, FireBaseCallbackImage onCallback) throws IOException {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("imgs/"+imgName + ".png");
        final File localfile = File.createTempFile(imgName,".png");
        Log.v("IMGNAME", imgName);
        Bitmap bitmap = null;
        storageRef.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                onCallback.onCallback(bitmap);
                localfile.delete();
            }
        });
        return bitmap;
    }

//    //Delete PRODUCT
//    public void deleteProduct(int productID){
//        db = FirebaseDatabase.getInstance().getReference("Products");
//        db.child(Integer.toString(productID)).removeValue();
//    }
}
