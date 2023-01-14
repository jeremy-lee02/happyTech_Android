package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.happytechhomepageui.Modals.Cart;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Modals.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.Locale;


public class ProductDetailFragment extends Fragment {
    FirebaseUser user;
    DatabaseReference reference;
    DatabaseReference referenceUser;
    String uID;
    private Cart cart;
    User cartUser;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        // Inflate the layout for this fragment
        Product product = (Product) getArguments().getSerializable("product");
        //Initialize product view
        TextView productName = (TextView) view.findViewById(R.id.product_name);
//        TextView productAvailable = (TextView) view.findViewById(R.id.thePriceOfProduct);
        TextView productDescription = (TextView) view.findViewById(R.id.productDescription);
        TextView productPrice = (TextView) view.findViewById(R.id.thePriceOfProduct);
        Button addToCart = (Button) view.findViewById(R.id.addToCartButton);

        productName.setText(product.getName());
//        productAvailable.setText(product.getAvailable_num()+"");
        productDescription.setText(product.getDescription());
        // Cast price to String and format to VND
        // Ex: 500000 to 500.000 VND
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        productPrice.setText(price + " VND");
        product.setQuantity(1);

        //Initialize database
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");
        referenceUser = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        uID = user.getUid();
        // Store current user
        referenceUser.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartUser = snapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("User", error.getMessage());
            }
        });

        // Modify Button (Add to Cart and Buy Now)
        // TODO: Add to Cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Add to Cart
                reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Cart cart = snapshot.getValue(Cart.class);
                        if (cart == null) {
                            cart = new Cart();
                            cart.setUser(cartUser);
                        }
                        cart.addProduct(product);
                        reference.child(uID).setValue(cart, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if (databaseError != null) {
                                    // Handle the error
                                    Log.d("Cart", databaseError.getMessage());
                                } else {
                                    // Update was successful
                                    Toast.makeText(getContext(), "Product added to cart", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle the error
                        Log.d("Cart", error.getMessage());
                    }
                });
            }
        });

        // TODO: Buy Now

        return view;
    }
}