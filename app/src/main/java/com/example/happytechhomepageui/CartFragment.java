package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.happytechhomepageui.Modals.Cart;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.example.happytechhomepageui.viewmodels.CartAdapter;
import com.example.happytechhomepageui.viewmodels.ProductAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CartFragment extends Fragment {
    FirebaseUser user;
    DatabaseReference reference;
    String uID;
    private Cart cart;
    DatabaseHelper db;
    List <Product> allProducts;

    public CartFragment() {
        // Required empty public constructor
    }

    // Parse HashMap to ArrayList
    private List<Product> getProductList(HashMap<String,Integer> products) {
        List<Product> productList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = getProductFromId(productId);
            if (product != null) {
                product.setQuantity(quantity);
                productList.add(product);
            }
        }
        return productList;
    }
    // Get the product by ID
    private Product getProductFromId(String productId) {
        for (Product p: allProducts
             ) {
            if (p.getProductID().equals(productId)){
                return p;
            }
        }
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.cartItemRecyclerView);
        db = new DatabaseHelper();
        db.getProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> list) {
                allProducts = list;
            }
        });

        //Get the Cart
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");
        uID = user.getUid();
        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cart = snapshot.getValue(Cart.class);
                CartAdapter cartAdapter = new CartAdapter(getProductList(cart.getProducts()), getFragmentManager());
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        // Inflate the layout for this fragment
        return view;
    }
}