package com.example.happytechhomepageui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happytechhomepageui.Modals.Cart;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Modals.User;
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
    DatabaseReference referenceUser;
    String uID;
    private Cart cart;
    DatabaseHelper db;
    List <Product> allProducts;
    Button order;
    User currentUser;

    public CartFragment() {
        // Required empty public constructor
    }

    // Parse HashMap to ArrayList
    private HashMap<Product,Integer> getProductList(HashMap<String,Integer> products) {
        HashMap<Product,Integer> productList = new HashMap<>();
        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = getProductFromId(productId);
            if (product != null) {
                productList.put(product,quantity);
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
        referenceUser = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        reference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");
        uID = user.getUid();
        order = view.findViewById(R.id.order);
        TextView cartText = view.findViewById(R.id.cartTextView);

        reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cart = snapshot.getValue(Cart.class);
                // Check if cart is empty
//                if (cart == null){
//                    Cart cart = new Cart(currentUser);
//                    reference.child(FirebaseAuth.getInstance().getCurrentUser()).setValue(cart);
//                }
                if (cart.getProducts().isEmpty()){
                    order.setVisibility(View.GONE);
                    cartText.setText("There is no item in cart");
                }else {
                    cartText.setText("");
                    order.setVisibility(View.VISIBLE);
                }
                CartAdapter cartAdapter = new CartAdapter(getProductList(cart.getProducts()),cart, uID, getFragmentManager(), getContext());
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        // Get User
        referenceUser.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentUser = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display an AlertDialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("Check Out");
                dialog.setMessage("" +
                        "Please check you details correctly.\n" +
                        "  - Phone Number: " + currentUser.getPhoneNumber() + "\n" +
                        "  - Address: " + currentUser.getAddress());
                dialog.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.addOrder(uID,getProductList(cart.getProducts()));
                        cart.clearCart();
                        reference.child(uID).setValue(cart);
                        Fragment fragment = new CartFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, fragment);
                        transaction.commit();
                        Toast.makeText(getContext(), "Purchase successful, check your information icon for more details", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("Update details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment fragment = new ProfileFragment();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                dialog.show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}