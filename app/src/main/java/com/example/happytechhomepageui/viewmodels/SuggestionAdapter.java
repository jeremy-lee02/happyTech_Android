package com.example.happytechhomepageui.viewmodels;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happytechhomepageui.Modals.Product;
import com.example.happytechhomepageui.R;

import java.util.List;

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionAdapter.SuggestionViewHolder> {
    private List<Product> suggestions;
    private OnSuggestionClickListener listener;

    public SuggestionAdapter(OnSuggestionClickListener listener) {
        List<Product> suggestionslist= null;
        this.suggestions = suggestionslist;
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
        holder.bind(String.valueOf(suggestions.get(position)));
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

        public void bind(String suggestion) {
            suggestionText.setText(suggestion);
        }
    }

    public interface OnSuggestionClickListener {
        void onSuggestionClick(String suggestion);
    }
}
