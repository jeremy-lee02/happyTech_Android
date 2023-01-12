package com.example.happytechhomepageui.viewmodels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.ProductDetailFragment;
import com.example.happytechhomepageui.ProductListFragment;
import com.example.happytechhomepageui.R;

import java.io.Serializable;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {
    private List<Product> productList;
    private FragmentManager fragmentManager;

    public ProductAdapter(List<Product> productList, FragmentManager fragmentManger) {
        this.productList = productList;
        this.fragmentManager = fragmentManger;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(String.valueOf(product.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new ProductDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("product", product);
                fragment.setArguments(args);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frameLayout_productList, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

//        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
//            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);


        }
    }
}
