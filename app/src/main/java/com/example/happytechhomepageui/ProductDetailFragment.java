package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    int count = 1;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public String checkItem(Product p){
        if (p.getAvailable_num() == 1){
            return "There is 1 available item in stock";
        }
        if (p.getAvailable_num() == 0 ){
            return "Out of Stock";
        }
        return "There are " + p.getAvailable_num() + " available items in stock";
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
        TextView available = (TextView) view.findViewById(R.id.productInStock);
        Button addToCart = (Button) view.findViewById(R.id.addToCartButton);

        productName.setText(product.getName());
        available.setText(checkItem(product));
//        productAvailable.setText(product.getAvailable_num()+"");
        productDescription.setText(product.getDescription());
        // Cast price to String and format to VND
        // Ex: 500000 to 500.000 VND
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        productPrice.setText(price + " VND");


        //Initialize database
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");
        referenceUser = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        uID = user.getUid();
        //Update Button
        AppCompatButton incrementButton = view.findViewById(R.id.incrementButton);
        AppCompatButton decrementButton = view.findViewById(R.id.decrementButton);
        EditText amount = view.findViewById(R.id.amount);


        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newAmount  = Integer.parseInt(amount.getText().toString());
                if(newAmount < product.getAvailable_num()){
                    newAmount++;
                    amount.setText(""+newAmount);
                    product.setQuantity(newAmount);
                }else {
                    amount.setText(""+ product.getAvailable_num());
                    Toast.makeText(getContext(),"Only " + product.getAvailable_num() + " left in stock!", Toast.LENGTH_LONG).show();
                }
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newAmount  = Integer.parseInt(amount.getText().toString());
                if(newAmount <= 1){
                    newAmount = 1;
                    product.setQuantity(newAmount);
                }
                else{
                    newAmount--;
                    product.setQuantity(newAmount);
                }
                amount.setText(""+newAmount);
            }
        });
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
                int newAmount  = Integer.parseInt(amount.getText().toString());
                if (newAmount < 1){
                    Toast.makeText(getContext(),"Minimum for quantity is 1", Toast.LENGTH_LONG).show();
                    amount.setText(""+1);
                    return;
                }
                if (newAmount > product.getAvailable_num()){
                    Toast.makeText(getContext(),"Only " + product.getAvailable_num() + " left in stock!", Toast.LENGTH_LONG).show();
                    amount.setText(""+ product.getAvailable_num());
                    return;
                }
                product.setQuantity(newAmount);
                reference.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Cart cart = snapshot.getValue(Cart.class);
                        if (cart == null) {
                            cart = new Cart();
                            cart.setUser(cartUser);
                        }
                        // Check if product is exist
                        boolean productExists = false;
                        for (Product p : cart.getProducts()) {
                            if (p.getProductID().equals(product.getProductID())) {
                                if (p.getQuantity() + product.getQuantity() >= p.getAvailable_num()){
                                    p.setQuantity(p.getAvailable_num());
                                    productExists = true;
                                    break;
                                }
                                if(p.getQuantity() + product.getQuantity() < p.getAvailable_num()) {
                                    p.setQuantity(p.getQuantity() + product.getQuantity());
                                    productExists = true;
                                    break;
                                }
                            }
                        }
                        if (!productExists) {
                            cart.addProduct(product);
                        }
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