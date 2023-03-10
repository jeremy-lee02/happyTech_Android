package com.example.happytechhomepageui.viewmodels;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FireBaseCallbackImage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>  {
    private List<Product> productList;
    private FragmentManager fragmentManager;
    private String currentFragment;
    DatabaseHelper db;
    public ProductAdapter(List<Product> productList, FragmentManager fragmentManger, String currentFragment) {
        this.productList = productList;
        this.fragmentManager = fragmentManger;
        this.currentFragment = currentFragment;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
        LinearLayout linearLayout = view.findViewById(R.id.productItem);
        if (currentFragment.equals("HomeFragment")) {
            linearLayout.getLayoutParams().width = 400;
        } else if (currentFragment.equals("ProductListFragment")) {
            linearLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        //Format number
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(price + " VND");
        db = new DatabaseHelper();
        try {
            db.getImage(product.getName(), new FireBaseCallbackImage() {
                @Override
                public void onCallback(Bitmap bitmap) {
                    holder.productImageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);


        }
    }
}
