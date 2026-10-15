package com.example.messmate.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.example.messmate.activities.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private View root;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);

        int hour = Integer.parseInt(
                new SimpleDateFormat("H", Locale.getDefault())
                        .format(new Date()));

        String greeting = hour < 12 ? "Good morning"
                : hour < 17 ? "Good afternoon" : "Good evening";

        text(R.id.txtGreeting, greeting);
        text(R.id.txtIntro, "Here's what's waiting for you today.");

        loadProfile();
        loadSpecial();
        loadRecommendation();

        view.findViewById(R.id.txtViewMeal)
                .setOnClickListener(v -> openTab(R.id.nav_menu));
        view.findViewById(R.id.txtViewFullMenu)
                .setOnClickListener(v -> openTab(R.id.nav_menu));
        view.findViewById(R.id.txtSeeAllRecommendations)
                .setOnClickListener(v -> openTab(R.id.nav_recommendation));
        view.findViewById(R.id.imgProfile)
                .setOnClickListener(v -> openTab(R.id.nav_profile));

        view.findViewById(R.id.cardBreakfast)
                .setOnClickListener(v -> openTab(R.id.nav_menu));
        view.findViewById(R.id.cardLunch)
                .setOnClickListener(v -> openTab(R.id.nav_menu));
        view.findViewById(R.id.cardDinner)
                .setOnClickListener(v -> openTab(R.id.nav_menu));
    }

    private void text(int id, String value) {
        TextView t = root.findViewById(id);
        if (t != null && value != null) t.setText(value);
    }

    private void openTab(int id) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).selectTab(id);
        }
    }

    private void loadProfile() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (!isAdded() || !doc.exists()) return;

                    String name = doc.getString("name");
                    if (name != null) text(R.id.txtUserName, name);
                });
    }

    private void loadSpecial() {
        String defaultId = "meal_lunch_1";
        String defaultName = "Paneer Butter Masala & Naan";
        text(R.id.txtSpecialMeal, defaultName);
        text(R.id.txtMealCategory, "Lunch");
        text(R.id.txtMatchReason, "Rich and creamy cottage cheese curry cooked in butter tomato gravy.");
        text(R.id.txtRating, "4.8 rating");
        text(R.id.txtMatchScore, "98% MATCH");
        setupCardClick(R.id.todaySpecialCard, defaultId, defaultName);

        FirebaseFirestore.getInstance().collection("meals")
                .whereEqualTo("isSpecial", true).limit(1).get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded() || snapshot.isEmpty()) return;

                    var meal = snapshot.getDocuments().get(0);
                    String name = meal.getString("name");
                    text(R.id.txtSpecialMeal, name);
                    text(R.id.txtMealCategory, meal.getString("category"));
                    text(R.id.txtMatchReason, meal.getString("description"));

                    Double rating = meal.getDouble("rating");
                    if (rating != null) {
                        text(R.id.txtRating, String.format(
                                Locale.getDefault(), "%.1f rating", rating));
                    }

                    Long score = meal.getLong("matchScore");
                    if (score != null) {
                        text(R.id.txtMatchScore, score + "% MATCH");
                    }

                    setupCardClick(R.id.todaySpecialCard, meal.getId(), name);
                });
    }

    private void loadRecommendation() {
        String defaultId = "meal_biryani_1";
        String defaultName = "Vegetable Dum Biryani";
        text(R.id.txtRecommendationMeal, defaultName);
        text(R.id.txtRecommendationReason, "Chef's special biryani crafted with authentic spices.");
        text(R.id.txtRecommendationScore, "96%");
        setupCardClick(R.id.recommendationCard, defaultId, defaultName);

        FirebaseFirestore.getInstance().collection("meals")
                .whereEqualTo("isRecommended", true).limit(1).get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded() || snapshot.isEmpty()) return;

                    var meal = snapshot.getDocuments().get(0);
                    String name = meal.getString("name");
                    text(R.id.txtRecommendationMeal, name);
                    text(R.id.txtRecommendationReason,
                            meal.getString("recommendationReason"));

                    Long score = meal.getLong("matchScore");
                    if (score != null) {
                        text(R.id.txtRecommendationScore, score + "%");
                    }

                    setupCardClick(R.id.recommendationCard, meal.getId(), name);
                });
    }

    private void setupCardClick(int cardId, String mealId, String mealName) {
        View card = root.findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(
                        requireContext(), com.example.messmate.activities.MealDetailActivity.class);
                intent.putExtra("mealId", mealId);
                intent.putExtra("mealName", mealName);
                startActivity(intent);
            });
        }
    }
}