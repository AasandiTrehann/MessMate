package com.example.messmate.recommendation;

import com.example.messmate.models.Meal;
import com.example.messmate.models.Recommendation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class RecommendationEngine {

    public List<Recommendation> recommend(
            List<Meal> meals,
            String preferredFood,
            String preferredSpice,
            String favoriteFoods,
            String dislikedFoods) {

        List<Recommendation> results = new ArrayList<>();

        if (meals == null) return results;

        for (Meal meal : meals) {
            if (meal == null || !meal.isAvailable()) continue;

            String foodType = safe(meal.getFoodType());
            String spice = safe(meal.getSpiceLevel());
            String name = safe(meal.getName());
            String description = safe(meal.getDescription());

            String favorites = safe(favoriteFoods).toLowerCase(Locale.ROOT);
            String dislikes = safe(dislikedFoods).toLowerCase(Locale.ROOT);

            if (!dislikes.isEmpty()
                    && (name.toLowerCase(Locale.ROOT).contains(dislikes)
                    || description.toLowerCase(Locale.ROOT).contains(dislikes))) {
                continue;
            }

            double score = 50;
            List<String> reasons = new ArrayList<>();

            if (!safe(preferredFood).isEmpty()
                    && foodType.equalsIgnoreCase(preferredFood)) {
                score += 20;
                reasons.add("matches your food preference");
            }

            if (!safe(preferredSpice).isEmpty()
                    && spice.equalsIgnoreCase(preferredSpice)) {
                score += 10;
                reasons.add("matches your spice preference");
            }

            if (!favorites.isEmpty()
                    && (name.toLowerCase(Locale.ROOT).contains(favorites)
                    || description.toLowerCase(Locale.ROOT).contains(favorites))) {
                score += 10;
                reasons.add("matches your favourite foods");
            }

            score += Math.min(10, Math.max(0, meal.getRating() * 2));
            score = Math.min(100, score);

            String reason = reasons.isEmpty()
                    ? "Recommended from the available menu."
                    : "Recommended because it " + String.join(" and ", reasons) + ".";

            results.add(new Recommendation(meal, score, reason));
        }

        results.sort(Comparator.comparingDouble(Recommendation::getScore)
                .reversed());

        return results;
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}