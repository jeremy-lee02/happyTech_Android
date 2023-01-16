package com.example.happytechhomepageui.viewmodels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.ProductDetailFragment;
import com.example.happytechhomepageui.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> productList;
    private FragmentManager fragmentManager;

    public CartAdapter(List<Product> productList, FragmentManager fragmentManger) {
        this.productList = productList;
        this.fragmentManager = fragmentManger;
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
        Product product = productList.get(position);
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(price + " VND");
        holder.quantity.setText(product.getQuantity() +"");
        holder.available.setText(checkItem(product));
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

        //OnClick the checkbox
        
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

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
//            productImageView = itemView.findViewById(R.id.productImageView);
            available = itemView.findViewById(R.id.available);
            quantity  = itemView.findViewById(R.id.updateNum);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
        }

    }

}
