package com.example.happytechhomepageui.viewmodels;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.CartFragment;
import com.example.happytechhomepageui.Modals.Cart;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.ProductDetailFragment;
import com.example.happytechhomepageui.ProfileFragment;
import com.example.happytechhomepageui.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private HashMap<Product, Integer> productList;
    private FragmentManager fragmentManager;
    private String uID;
    private Cart cart;
    private Context context;
    DatabaseReference reference;


    public CartAdapter(HashMap<Product, Integer> productList,Cart cart,String uID, FragmentManager fragmentManger, Context context) {
        this.productList = productList;
        this.fragmentManager = fragmentManger;
        this.uID = uID;
        this.cart = cart;
        this.context = context;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);

        return new CartViewHolder(view);
    }
    // Check if Item is available or not
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
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = (Product) productList.keySet().toArray()[position];
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        if (product.getName().length() > 20){
            holder.productNameTextView.setText(product.getName().substring(0,20)+"...");
        }
        else{
            holder.productNameTextView.setText(product.getName());
        }
        holder.productPriceTextView.setText(price + " VND");
        holder.quantity.setText(productList.get(product).toString());
        holder.available.setText(checkItem(product));
        reference = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");

        // OnClick the product
        holder.productNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ProductDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("product", product);
                fragment.setArguments(args);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        // Delete Product
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display an AlertDialog
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Do you want to delete?");
                dialog.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        productList.remove(product);
                        cart.setProducts(updateCart());
                        reference.child(uID).setValue(cart);
                        Fragment fragment = new CartFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.frameLayout, fragment);
                        transaction.commit();
                        Toast.makeText(context, "Delete successful", Toast.LENGTH_LONG).show();
                    }
                });
                dialog.setNegativeButton("Update details", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Fragment fragment = new CartFragment();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.frameLayout, fragment);
                        transaction.commit();
                    }
                });
                dialog.show();
            }
        });
        // Update product
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(productList.get(product) == product.getAvailable_num()){
                    Toast.makeText(context, "Only " + product.getAvailable_num() + " left in stock!", Toast.LENGTH_LONG).show();
                }
                else {
                    productList.put(product, productList.get(product) + 1);
                    cart.setProducts(updateCart());
                    reference.child(uID).setValue(cart);
                    holder.quantity.setText(productList.get(product).toString());
                }
            }
        });
        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (productList.get(product) <= 0) {
                    // Display an AlertDialog
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setTitle("Do you want to delete?");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            productList.remove(product);
                            cart.setProducts(updateCart());
                            reference.child(uID).setValue(cart);
                            Fragment fragment = new CartFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frameLayout, fragment);
                            transaction.commit();
                            Toast.makeText(context, "Delete successful", Toast.LENGTH_LONG).show();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Fragment fragment = new CartFragment();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.frameLayout, fragment);
                            transaction.commit();
                        }
                    });
                    dialog.show();
                }
                else {
                    productList.put(product, productList.get(product) - 1);
                    cart.setProducts(updateCart());
                    reference.child(uID).setValue(cart);
                    holder.quantity.setText(productList.get(product).toString());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class CartViewHolder extends RecyclerView.ViewHolder {

        //        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView available;
        TextView quantity;
        Button increment;
        Button decrement;
        ImageButton delete;
        TextView updateNum;
        TextView cartTextView;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
//            productImageView = itemView.findViewById(R.id.productImageView);
            available = itemView.findViewById(R.id.available);
            quantity  = itemView.findViewById(R.id.updateNum);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            increment = itemView.findViewById(R.id.updateIncrement);
            decrement = itemView.findViewById(R.id.updateDecrement);
            delete = itemView.findViewById(R.id.deleteItem);
            updateNum = itemView.findViewById(R.id.updateNum);
            cartTextView = itemView.findViewById(R.id.cartTextView);


        }

    }
    public HashMap<String,Integer> updateCart (){
        HashMap<String,Integer> result = new HashMap<>();
        for (Map.Entry<Product, Integer> entry : productList.entrySet()) {
            result.put(entry.getKey().getProductID(),entry.getValue());
        }
        return  result;
    }

}
