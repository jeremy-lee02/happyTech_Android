package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happytechhomepageui.Modals.Order;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Modals.User;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.example.happytechhomepageui.viewmodels.OrderDetailAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class OrderDetailFragment extends Fragment {
    FirebaseUser user;
    DatabaseReference reference;
    DatabaseReference referenceUser;
    String uID;
    List<Product> allProducts;
    DatabaseHelper db;

    public OrderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_detail, container, false);
        Order order = (Order) getArguments().getSerializable("order");
        // Initialize View
        TextView fullName = (TextView) view.findViewById(R.id.orderName);
        TextView phone = (TextView) view.findViewById(R.id.orderPhone);
        TextView address = (TextView) view.findViewById(R.id.orderAddress);
        TextView total = (TextView) view.findViewById(R.id.orderTotalPrice);
        TextView date = (TextView) view.findViewById(R.id.orderDate);
        TextView status = (TextView) view.findViewById(R.id.orderStatus);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.order_detail_item);

        //Get User
        user = FirebaseAuth.getInstance().getCurrentUser();
        referenceUser = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        uID = user.getUid();
        referenceUser.child(uID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                fullName.setText(user.getFirstName() + " " + user.getFirstName());
                phone.setText(user.getPhoneNumber());
                address.setText(user.getAddress());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Set Order details
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price = vn.format(order.getSubtotal());
        total.setText(price + " VND");
        date.setText(order.getOrderDate());
        if (order.isCompleted()){
            status.setText("Completed");
        }else {
            status.setText("Your order is delivered soon");
        }

        // SetUp recycleView
        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(order.getOrderProducts(), getFragmentManager(), getContext());
        recyclerView.setAdapter(orderDetailAdapter);
        return view;


    }

}