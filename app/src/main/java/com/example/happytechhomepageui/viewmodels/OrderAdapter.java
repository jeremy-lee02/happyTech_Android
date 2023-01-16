package com.example.happytechhomepageui.viewmodels;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OderViewHolder> {
    @NonNull
    @Override
    public OderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OderViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class OderViewHolder extends RecyclerView.ViewHolder{

        public OderViewHolder(@NonNull View itemView){
            super(itemView);
        }

    }
}
