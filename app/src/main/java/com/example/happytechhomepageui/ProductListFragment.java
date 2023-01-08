package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        fragmentProductListBinding = FragmentProductListBinding.inflate(inflater, container,false);
        return fragmentProductListBinding.getRoot();
    }
}