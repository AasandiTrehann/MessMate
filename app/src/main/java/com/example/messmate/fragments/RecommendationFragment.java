package com.example.messmate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.example.messmate.activities.MealDetailActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RecommendationFragment extends Fragment {

    private View root;

    public RecommendationFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(
                R.layout.fragment_recommendation, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);

        view.findViewById(R.id.txtRefreshRecommendations)
                .setOnClickListener(v -> loadRecommendations());

        loadRecommendations();
    }

    private void loadRecommendations() {
        showMealData("meal_lunch_1", "Paneer Butter Masala & Naan",
                "4.8/5", "Matches your vegetarian preference and top rated meals.",
                R.id.txtTopMeal, R.id.txtTopMatchScore, R.id.txtRecommendationExplanation, R.id.topRecommendationCard);

        showMealData("meal_biryani_1", "Vegetable Dum Biryani",
                "4.9/5", "Chef's special biryani crafted with authentic spices.",
                R.id.txtSecondMeal, R.id.txtSecondScore, R.id.txtSecondReason, R.id.secondRecommendationCard);

        showMealData("meal_breakfast_1", "Aloo Paratha & Curd",
                "4.7/5", "Popular breakfast choice that matches your preferences.",
                R.id.txtThirdMeal, R.id.txtThirdScore, R.id.txtThirdReason, R.id.thirdRecommendationCard);

        FirebaseFirestore.getInstance().collection("meals")
                .limit(20).get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded() || snapshot.isEmpty()) return;

                    List<com.google.firebase.firestore.DocumentSnapshot> meals =
                            new ArrayList<>(snapshot.getDocuments());

                    meals.sort((a, b) -> Double.compare(
                            score(b), score(a)));

                    showMeal(meals.get(0), R.id.txtTopMeal,
                            R.id.txtTopMatchScore,
                            R.id.txtRecommendationExplanation,
                            R.id.topRecommendationCard);

                    if (meals.size() > 1) {
                        showMeal(meals.get(1), R.id.txtSecondMeal,
                                R.id.txtSecondScore, R.id.txtSecondReason,
                                R.id.secondRecommendationCard);
                    }

                    if (meals.size() > 2) {
                        showMeal(meals.get(2), R.id.txtThirdMeal,
                                R.id.txtThirdScore, R.id.txtThirdReason,
                                R.id.thirdRecommendationCard);
                    }
                });
    }

    private void showMealData(String mealId, String name, String score, String reason,
                              int nameId, int scoreId, int reasonId, int cardId) {
        setText(nameId, name);
        setText(scoreId, score);
        setText(reasonId, reason);

        View card = root.findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), MealDetailActivity.class);
                intent.putExtra("mealId", mealId);
                intent.putExtra("mealName", name);
                startActivity(intent);
            });
        }
    }

    private double score(
            com.google.firebase.firestore.DocumentSnapshot meal) {
        Double rating = meal.getDouble("rating");
        return rating == null ? 0 : rating;
    }

    private void showMeal(
            com.google.firebase.firestore.DocumentSnapshot meal,
            int nameId, int scoreId, int reasonId, int cardId) {

        String name = meal.getString("name");
        String description = meal.getString("description");
        Double rating = meal.getDouble("rating");

        setText(nameId, name);
        setText(scoreId, rating == null
                ? "New meal" : String.format("%.1f/5", rating));
        setText(reasonId, description == null
                ? "Explore this meal from today's menu." : description);

        View card = root.findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> {
                Intent intent = new Intent(
                        requireContext(), MealDetailActivity.class);
                intent.putExtra("mealId", meal.getId());
                intent.putExtra("mealName", name);
                startActivity(intent);
            });
        }
    }

    private void setText(int id, String value) {
        TextView text = root.findViewById(id);
        if (text != null && value != null) text.setText(value);
    }
}