package com.example.happytechhomepageui.viewmodels;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Order;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.R;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FireBaseCallbackImage;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {

    private HashMap<Product, Integer> productList;
    private FragmentManager fragmentManager;
    private Context context;
    DatabaseHelper db;

    public OrderDetailAdapter(HashMap<Product, Integer> productList, FragmentManager fragmentManager, Context context) {
        this.productList = productList;
        this.fragmentManager = fragmentManager;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_details_item, parent, false);
        return new OrderDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.OrderDetailViewHolder holder, int position) {
        Product product = (Product) productList.keySet().toArray()[position];
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        db = new DatabaseHelper();
        try {
            db.getImage(product.getName(), new FireBaseCallbackImage() {
                @Override
                public void onCallback(Bitmap bitmap) {
                    holder.image.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (product.getName().length() > 20){
            holder.productName.setText(product.getName().substring(0,20)+"...");
        }
        else{
            holder.productName.setText(product.getName());
        }
        holder.quantity.setText(productList.get(product).toString());
        holder.price.setText(price + " VND");
        holder.quantity.setText(productList.get(product).toString());

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder{
        TextView productName;
        TextView quantity;
        TextView price;
        ImageView image;

        public OrderDetailViewHolder(@NonNull View itemView){
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.productImageView);
            productName = (TextView) itemView.findViewById(R.id.productNameTextView);
            price = (TextView) itemView.findViewById(R.id.productPriceTextView);
            quantity = (TextView) itemView.findViewById(R.id.num);
        }

    }
}
