package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.databinding.FragmentProductListBinding;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.example.happytechhomepageui.viewmodels.ProductAdapter;

import java.util.List;


public class ProductListFragment extends Fragment {
    private DatabaseHelper db;

    public ProductListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_list, container,false);
        db = new DatabaseHelper();

        db.getProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> list) {
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.productListRecyclerView);
                ProductAdapter productAdapter =  new ProductAdapter(list);
                recyclerView.setAdapter(productAdapter);

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.productListRecyclerView);

        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));
    }
}