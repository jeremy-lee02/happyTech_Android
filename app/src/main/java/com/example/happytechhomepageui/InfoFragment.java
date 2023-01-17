package com.example.happytechhomepageui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Cart;
import com.example.happytechhomepageui.Modals.Order;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Modals.User;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FireBaseCallbackOrder;
import com.example.happytechhomepageui.viewmodels.CartAdapter;
import com.example.happytechhomepageui.viewmodels.OrderAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.ArrayList;
import java.util.List;


public class InfoFragment extends Fragment {
    FirebaseUser user;
    String uID;
    DatabaseHelper db;
    List<Order> Delivered;
    List<Order> Delivering;
    Button viewDelivered;
    Button viewDelivering;
    User currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        RecyclerView recyclerViewDelivered = (RecyclerView) view.findViewById(R.id.orderRecycleViewDone);
        RecyclerView recyclerViewDelivering = (RecyclerView) view.findViewById(R.id.orderRecycleView);
        TextView textOrder = (TextView) view.findViewById(R.id.textOrder);

        viewDelivered = view.findViewById(R.id.deliveredBtn);
        viewDelivering = view.findViewById(R.id.deliveringBtn);
        //Get the Order
        user = FirebaseAuth.getInstance().getCurrentUser();
        uID = user.getUid();
        db = new DatabaseHelper();
        Delivered = new ArrayList<>();
        Delivering = new ArrayList<>();
        List<Order> orders;
        orders = new ArrayList<>();
        TextView emptyOrder = view.findViewById(R.id.emptyOrder);
        db.getOrder(new FireBaseCallbackOrder() {
            @Override
            public void onCallback(List<Order> list) {
                for(Order order : list){
                    if (order.getCustomerId().equals(uID)){
                        orders.add(order);

                    };
                }
                for(Order order : orders){
                    if(order.isCompleted()){
                        Delivered.add(order);

                    }
                    if(!order.isCompleted()){
                        Delivering.add(order);

                    }
                }
                if (Delivering.isEmpty()){
                    emptyOrder.setText("You have no order to be delivered");
                    emptyOrder.setVisibility(View.VISIBLE);
                }
                if (!Delivering.isEmpty()){
                    emptyOrder.setVisibility(View.GONE);
                    OrderAdapter orderAdapter = new OrderAdapter(Delivering,uID,getFragmentManager(),getContext());
                    recyclerViewDelivering.setAdapter(orderAdapter);
                }
            }
        });
        viewDelivering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewDelivering.setVisibility(View.VISIBLE);
                recyclerViewDelivered.setVisibility(View.GONE);
                textOrder.setText("Delivering Order");
                viewDelivered.setBackgroundColor(Color.parseColor("#5C5CF3"));
                viewDelivering.setBackgroundColor(Color.parseColor("#0000FF"));

                if (Delivering.isEmpty()){
                    emptyOrder.setText("You have no order to be delivered");
                    emptyOrder.setVisibility(View.VISIBLE);
                }
                if (!Delivering.isEmpty()){
                    emptyOrder.setVisibility(View.GONE);
                    OrderAdapter orderAdapter = new OrderAdapter(Delivering,uID,getFragmentManager(),getContext());
                    recyclerViewDelivering.setAdapter(orderAdapter);

                }
            }
        });
        viewDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDelivering.setBackgroundColor(Color.parseColor("#5C5CF3"));
                viewDelivered.setBackgroundColor(Color.parseColor("#0000FF"));
                textOrder.setText("Delivered Order");
                recyclerViewDelivered.setVisibility(View.VISIBLE);
                recyclerViewDelivering.setVisibility(View.GONE);
                if (Delivered.isEmpty()){
                    emptyOrder.setText("You have no completed order");
                    emptyOrder.setVisibility(View.VISIBLE);
                }
                if (!Delivered.isEmpty()){
                    emptyOrder.setVisibility(View.GONE);
                    OrderAdapter orderAdapter = new OrderAdapter(Delivered,uID,getFragmentManager(),getContext());
                    recyclerViewDelivered.setAdapter(orderAdapter);
                }
            }
        });


        return view;
    }
}