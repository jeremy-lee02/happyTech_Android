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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;
import com.example.happytechhomepageui.viewmodels.ProductAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

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
        RoundedImageView monitor = (RoundedImageView) view.findViewById(R.id.monitorImage);
        RoundedImageView keyboard = (RoundedImageView) view.findViewById(R.id.keyboardImage);
        RoundedImageView mouse = (RoundedImageView) view.findViewById(R.id.mouseImage);
        RoundedImageView headphone = (RoundedImageView) view.findViewById(R.id.headphone);
        // See All Products
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChange("all");
            }
        });
        // See all Monitors
        monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChange("Monitor");
            }
        });
        // See all Keyboards
        keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChange("Keyboard");
            }
        });
        // See all Mouse
        mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChange("Mouse");
            }
        });
        // See all Headphones
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChange("Headphone");
            }
        });

        // Featured Products
        db = new DatabaseHelper();
        db.getProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> list) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.featureProduct);
                ProductAdapter productAdapter =  new ProductAdapter(list, getFragmentManager());
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    // Change listener base on category
    private void onClickChange(String category) {
        assert getFragmentManager() != null;
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        ProductListFragment products = new ProductListFragment(category);
        fragmentTransaction.replace(R.id.frameLayout,products);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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