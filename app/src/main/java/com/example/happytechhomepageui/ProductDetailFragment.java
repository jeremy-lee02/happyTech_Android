package com.example.happytechhomepageui;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.happytechhomepageui.Modals.Product;

import java.text.NumberFormat;
import java.util.Locale;


public class ProductDetailFragment extends Fragment {


    public ProductDetailFragment() {
        // Required empty public constructor
    }
    int count = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        // Inflate the layout for this fragment
        Product product = (Product) getArguments().getSerializable("product");
        //Initialize product view
        TextView productName = (TextView) view.findViewById(R.id.product_name);
//        TextView productAvailable = (TextView) view.findViewById(R.id.thePriceOfProduct);
        TextView productDescription = (TextView) view.findViewById(R.id.productDescription);
        TextView productPrice = (TextView) view.findViewById(R.id.thePriceOfProduct);

        productName.setText(product.getName());
//        productAvailable.setText(product.getAvailable_num()+"");
        productDescription.setText(product.getDescription());
        // Cast price to String and format to VND
        // Ex: 500000 to 500.000 VND
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        String price  = vn.format(product.getPrice());
        productPrice.setText(price + " VND");

        AppCompatButton incrementButton = view.findViewById(R.id.incrementButton);
        AppCompatButton decrementButton = view.findViewById(R.id.decrementButton);
        EditText amount = view.findViewById(R.id.amount);


        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                amount.setText(""+count);
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count <= 0)
                    count = 0;
                else
                    count--;
                amount.setText(""+count);
            }
        });

        return view;
    }
}