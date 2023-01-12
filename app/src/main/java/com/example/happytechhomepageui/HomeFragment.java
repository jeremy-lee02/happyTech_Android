package com.example.happytechhomepageui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.example.happytechhomepageui.viewmodels.ProductAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseHelper db;
    ImageSlider imageSlider;
    TextView product;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container,false);
        product = (TextView) view.findViewById(R.id.seeAll);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ProductListFragment products = new ProductListFragment();
                fragmentTransaction.replace(R.id.frameLayout,products);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        db = new DatabaseHelper();

        db.getProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> list) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.featureProduct);
                ProductAdapter productAdapter =  new ProductAdapter(list);
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageSlider = (ImageSlider) getView().findViewById(R.id.image_slider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel("https://st2.depositphotos.com/4285885/6818/i/450/depositphotos_68182063-stock-photo-fire-text-special-offer.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046124031742578789/unknown.png?width=732&height=300", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046126568411504660/unknown.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046126463772020856/unknown.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046126701517733958/unknown.png", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

    }
}