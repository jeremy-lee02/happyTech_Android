package com.example.happytechhomepageui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.Services.DatabaseHelper;
import com.example.happytechhomepageui.repo.FirebaseCallbackProduct;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseHelper db;
    ImageSlider imageSlider;


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
        Button cat1 = (Button) view.findViewById(R.id.cat1);
        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                ProductListFragment products = new ProductListFragment();
                fragmentTransaction.replace(R.id.frameLayout,products);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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

        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046124120582140044/unknown.png?width=732&height=300", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046122977554608148/unknown.png?width=732&height=267", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046124031742578789/unknown.png?width=732&height=300", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046125468862140436/unknown.png?width=732&height=305", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);





    }
}