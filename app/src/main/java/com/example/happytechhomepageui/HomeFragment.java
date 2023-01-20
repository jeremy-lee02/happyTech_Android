package com.example.happytechhomepageui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

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
import com.example.happytechhomepageui.viewmodels.SuggestionAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private DatabaseHelper db;
    ImageSlider imageSlider;
    TextView product;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private SuggestionAdapter adapter;
    private List<Product> suggesProduct;
    private TextView notFound;
    DatabaseReference databaseReference2 = FirebaseDatabase.getInstance("https://test-auth-android-eee23-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Cart");

    private FragmentManager fragmentManager;


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
        notFound = view.findViewById(R.id.errorCode);
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
        db.getFeatureProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> list) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.featureProduct);
                ProductAdapter productAdapter =  new ProductAdapter(list, getFragmentManager(), "HomeFragment");
                recyclerView.setAdapter(productAdapter);
                recyclerView.setLayoutManager(linearLayoutManager)
                ;
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

        // Create function for IMGSlider
        imageSlider = (ImageSlider) getView().findViewById(R.id.image_slider);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://st2.depositphotos.com/4285885/6818/i/450/depositphotos_68182063-stock-photo-fire-text-special-offer.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046124031742578789/unknown.png?width=732&height=300", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046126568411504660/unknown.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046126463772020856/unknown.png", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.discordapp.net/attachments/1036490378154618984/1046126701517733958/unknown.png", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        // Create function for search bar
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.search_recycler_view);

        db = new DatabaseHelper();

        suggesProduct = new ArrayList<>();
        adapter = new SuggestionAdapter(suggesProduct, getFragmentManager(), suggestion -> {
            // Handle suggestion click
        });
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView.setVisibility(View.GONE);
                notFound.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Get the filtered list of products and update the adapter
                db.getProducts(new FirebaseCallbackProduct() {
                    @Override
                    public void onCallback(List<Product> list) {
                        for (Product product : list) {
                            if (newText.isEmpty()) {
                                recyclerView.setVisibility(View.GONE);
                                notFound.setVisibility(View.GONE);
                            }else {
                                if (product.getName().toLowerCase().contains(newText.toLowerCase())){
                                    Log.d("iscontain?", String.valueOf(product.getName().toLowerCase().contains(newText.toLowerCase())));
                                    recyclerView.setVisibility(View.VISIBLE);
                                    notFound.setVisibility(View.GONE);
                                    filterProductsFromServer(newText);
                                }else {
                                    recyclerView.setVisibility(View.GONE);
                                    notFound.setVisibility(View.VISIBLE);
                                    notFound.setText("* No item found with '" + newText + "'");
                                    notFound.setTextColor(getResources().getColor(R.color.red));
                                }
                            }
                        }
                    }
                });

                return false;
            }
        });

        searchView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {}
        });
        getDataFromServer();
    }

    private void getDataFromServer() {
        db.getProducts(productList -> {
            suggesProduct.addAll(productList);
            adapter.notifyDataSetChanged();
        });
    }

    private void filterProductsFromServer(String query) {
        db.getProducts(new FirebaseCallbackProduct() {
            @Override
            public void onCallback(List<Product> productList) {
                List<Product> filteredProducts = new ArrayList<>();
                for (Product product : productList) {
                    if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                        filteredProducts.add(product);
                    }
                }
                adapter.updateSuggestions(filteredProducts);
            }
        });
    }
}


