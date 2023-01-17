package com.example.happytechhomepageui.viewmodels;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Order;
import com.example.happytechhomepageui.OrderDetailFragment;
import com.example.happytechhomepageui.R;
import com.google.firebase.database.DatabaseReference;


import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OderViewHolder> {
    private String uID;
    private List<Order> orders;
    private FragmentManager fragmentManager;
    private Context context;
    DatabaseReference reference;


    public OrderAdapter(List<Order> orders,String uID, FragmentManager fragmentManger, Context context) {
        this.orders = orders;
        this.uID = uID;
        this.context = context;
        this.fragmentManager = fragmentManger;
    }
    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OderViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.totalPrice.setText(Integer.toString(order.getSubtotal()));
        holder.date.setText(order.getOrderDate());

        if (order.isCompleted()){
            holder.status.setText("Complete");
        }else {
            holder.status.setText("Delivering");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new OrderDetailFragment();
                Bundle args = new Bundle();
                args.putSerializable("order", order);
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
        return orders.size();
    }

    public static class OderViewHolder extends RecyclerView.ViewHolder{
        TextView date;
        TextView totalPrice;
        TextView status;
        public OderViewHolder(@NonNull View itemView){
            super(itemView);
            date = itemView.findViewById(R.id.orderDate);
            totalPrice = itemView.findViewById(R.id.orderProductTotalPrice);
            status = itemView.findViewById(R.id.status);
        }

    }
}
