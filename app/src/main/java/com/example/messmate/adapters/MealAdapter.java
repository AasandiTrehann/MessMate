package com.example.messmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messmate.R;
import com.example.messmate.models.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealHolder> {

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    private final List<Meal> meals = new ArrayList<>();
    private final OnMealClickListener listener;

    public MealAdapter(OnMealClickListener listener) {
        this.listener = listener;
    }

    public void setMeals(List<Meal> items) {
        meals.clear();
        if (items != null) meals.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal, parent, false);
        return new MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        Meal meal = meals.get(position);

        holder.name.setText(meal.getName());
        holder.category.setText(meal.getCategory());
        holder.description.setText(meal.getDescription());
        holder.rating.setText(String.format(
                Locale.getDefault(), "%.1f rating", meal.getRating()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onMealClick(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealHolder extends RecyclerView.ViewHolder {
        TextView name, category, description, rating;

        MealHolder(View view) {
            super(view);
            name = view.findViewById(R.id.txtMealName);
            category = view.findViewById(R.id.txtMealCategory);
            description = view.findViewById(R.id.txtMealDescription);
            rating = view.findViewById(R.id.txtMealRating);
        }
    }
}