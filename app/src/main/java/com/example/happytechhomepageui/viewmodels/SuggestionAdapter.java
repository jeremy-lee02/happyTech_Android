package com.example.happytechhomepageui.viewmodels;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.ProductDetailFragment;
import com.example.happytechhomepageui.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {
    private List<Product> suggestions;
    private OnSuggestionClickListener listener;

    public SuggestionAdapter(List<Product> productList ,OnSuggestionClickListener listener) {
        this.suggestions = productList;
        this.listener = listener;
    }

    public void updateSuggestions(List<Product> suggestions) {
        this.suggestions = suggestions;
        notifyDataSetChanged();
    }

    public int getItemCount() {
        return suggestions.size();
    }
    @NonNull
    @Override
    public SuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suggestion, parent, false);
        return new SuggestionViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestionViewHolder holder, int position) {
        Product product = suggestions.get(position);
        //Format number
        Locale locale = new Locale("vi", "VN");
        NumberFormat vn = NumberFormat.getInstance(locale);
        holder.suggestionText.setText(product.getName());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new ProductDetailFragment();
//                Bundle args = new Bundle();
//                args.putSerializable("product", product);
//                fragment.setArguments(args);
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.replace(R.id.frameLayout, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();
//            }
//        });
    }

    public class SuggestionViewHolder extends RecyclerView.ViewHolder {
        private TextView suggestionText;

        public SuggestionViewHolder(@NonNull View itemView, OnSuggestionClickListener listener) {
            super(itemView);
            suggestionText = itemView.findViewById(R.id.suggestion_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSuggestionClick(String.valueOf(suggestions.get(getAdapterPosition())));
                }
            });
        }


    }

    public interface OnSuggestionClickListener {
        void onSuggestionClick(String suggestion);
    }
}
