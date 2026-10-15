package com.example.messmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messmate.R;
import com.example.messmate.models.MealRating;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedbackAdapter
        extends RecyclerView.Adapter<FeedbackAdapter.Holder> {

    private final List<MealRating> items = new ArrayList<>();

    public void setItems(List<MealRating> ratings) {
        items.clear();
        if (ratings != null) items.addAll(ratings);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_feedback, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        MealRating rating = items.get(position);

        holder.meal.setText(rating.getMealName());
        holder.comment.setText(rating.getComment());
        holder.rating.setText(String.format(
                Locale.getDefault(), "%.1f/5", rating.getRating()));

        String date = new SimpleDateFormat(
                "dd MMM yyyy", Locale.getDefault())
                .format(new Date(rating.getCreatedAt()));

        holder.date.setText(date);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView meal, comment, rating, date;

        Holder(View view) {
            super(view);
            meal = view.findViewById(R.id.txtFeedbackMeal);
            comment = view.findViewById(R.id.txtFeedbackComment);
            rating = view.findViewById(R.id.txtFeedbackRating);
            date = view.findViewById(R.id.txtFeedbackDate);
        }
    }
}