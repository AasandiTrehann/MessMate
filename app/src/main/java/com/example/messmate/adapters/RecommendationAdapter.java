package com.example.messmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messmate.R;
import com.example.messmate.models.Recommendation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecommendationAdapter
        extends RecyclerView.Adapter<RecommendationAdapter.Holder> {

    public interface OnRecommendationClickListener {
        void onClick(Recommendation recommendation);
    }

    private final List<Recommendation> items = new ArrayList<>();
    private final OnRecommendationClickListener listener;

    public RecommendationAdapter(OnRecommendationClickListener listener) {
        this.listener = listener;
    }

    public void setItems(List<Recommendation> recommendations) {
        items.clear();
        if (recommendations != null) items.addAll(recommendations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recommendation, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Recommendation item = items.get(position);

        holder.name.setText(item.getMeal().getName());
        holder.reason.setText(item.getReason());
        holder.score.setText(String.format(
                Locale.getDefault(), "%.0f%%", item.getScore()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onClick(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView name, reason, score;

        Holder(View view) {
            super(view);
            name = view.findViewById(R.id.txtRecommendationMeal);
            reason = view.findViewById(R.id.txtRecommendationReason);
            score = view.findViewById(R.id.txtRecommendationScore);
        }
    }
}