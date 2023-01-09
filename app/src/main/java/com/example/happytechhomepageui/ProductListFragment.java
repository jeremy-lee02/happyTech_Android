package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.happytechhomepageui.databinding.FragmentProductListBinding;


public class ProductListFragment extends Fragment {

    FragmentProductListBinding fragmentProductListBinding;

    public ProductListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater, container,false);
        View view = inflater.inflate(R.layout.fragment_product_list, container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.productListRecyclerView);

        return view;
    }
}