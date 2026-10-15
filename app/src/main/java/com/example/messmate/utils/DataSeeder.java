package com.example.messmate.utils;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DataSeeder {

    public static void seedInitialDataIfEmpty() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meals").limit(1).get().addOnSuccessListener(snapshot -> {
            if (snapshot.isEmpty()) {
                seedMeals(db);
                seedRatings(db);
            }
        });
    }

    private static void seedMeals(FirebaseFirestore db) {
        // Meal 1: Breakfast
        Map<String, Object> m1 = new HashMap<>();
        m1.put("name", "Aloo Paratha & Curd");
        m1.put("category", "Breakfast");
        m1.put("description", "Stuffed golden wheat parathas served hot with fresh curd, butter, and spicy pickle.");
        m1.put("price", 45.0);
        m1.put("rating", 4.7);
        m1.put("available", true);
        m1.put("isSpecial", false);
        m1.put("isRecommended", true);
        m1.put("matchScore", 92);
        m1.put("recommendationReason", "Popular breakfast choice that matches your preferences.");
        m1.put("imageUrl", "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=600&auto=format&fit=crop");
        db.collection("meals").document("meal_breakfast_1").set(m1);

        // Meal 2: Lunch (Special)
        Map<String, Object> m2 = new HashMap<>();
        m2.put("name", "Paneer Butter Masala & Naan");
        m2.put("category", "Lunch");
        m2.put("description", "Rich and creamy cottage cheese curry cooked in butter tomato gravy served with garlic naan.");
        m2.put("price", 90.0);
        m2.put("rating", 4.8);
        m2.put("available", true);
        m2.put("isSpecial", true);
        m2.put("isRecommended", true);
        m2.put("matchScore", 98);
        m2.put("recommendationReason", "Matches your vegetarian preference and top rated meals.");
        m2.put("imageUrl", "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=600&auto=format&fit=crop");
        db.collection("meals").document("meal_lunch_1").set(m2);

        // Meal 3: Dinner
        Map<String, Object> m3 = new HashMap<>();
        m3.put("name", "Dal Makhani & Jeera Rice");
        m3.put("category", "Dinner");
        m3.put("description", "Slow-cooked black lentils in butter & cream served with fragrant cumin rice.");
        m3.put("price", 75.0);
        m3.put("rating", 4.6);
        m3.put("available", true);
        m3.put("isSpecial", false);
        m3.put("isRecommended", true);
        m3.put("matchScore", 89);
        m3.put("recommendationReason", "Classic comforting dinner meal high in protein.");
        m3.put("imageUrl", "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=600&auto=format&fit=crop");
        db.collection("meals").document("meal_dinner_1").set(m3);

        // Meal 4: Snacks
        Map<String, Object> m4 = new HashMap<>();
        m4.put("name", "Samosa & Masala Chai");
        m4.put("category", "Snacks");
        m4.put("description", "Crispy golden samosas with spiced potato filling served with mint chutney & hot tea.");
        m4.put("price", 35.0);
        m4.put("rating", 4.5);
        m4.put("available", true);
        m4.put("isSpecial", false);
        m4.put("isRecommended", false);
        m4.put("matchScore", 85);
        m4.put("recommendationReason", "Perfect evening snack combo.");
        m4.put("imageUrl", "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600&auto=format&fit=crop");
        db.collection("meals").document("meal_snacks_1").set(m4);

        // Meal 5: Special Biryani
        Map<String, Object> m5 = new HashMap<>();
        m5.put("name", "Vegetable Dum Biryani");
        m5.put("category", "Lunch");
        m5.put("description", "Aromatic basmati rice dum cooked with fresh vegetables and saffron spices, served with raita.");
        m5.put("price", 95.0);
        m5.put("rating", 4.9);
        m5.put("available", true);
        m5.put("isSpecial", true);
        m5.put("isRecommended", true);
        m5.put("matchScore", 96);
        m5.put("recommendationReason", "Chef's special biryani crafted with authentic spices.");
        m5.put("imageUrl", "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=600&auto=format&fit=crop");
        db.collection("meals").document("meal_biryani_1").set(m5);
    }

    private static void seedRatings(FirebaseFirestore db) {
        long now = System.currentTimeMillis();

        Map<String, Object> r1 = new HashMap<>();
        r1.put("mealId", "meal_lunch_1");
        r1.put("mealName", "Paneer Butter Masala & Naan");
        r1.put("userId", "demo_student_1");
        r1.put("rating", 5.0);
        r1.put("taste", 5);
        r1.put("quality", 5);
        r1.put("quantity", 4);
        r1.put("comment", "Absolutely delicious! Paneer was super soft and the gravy was rich.");
        r1.put("createdAt", now - 3600000L);
        db.collection("ratings").add(r1);

        Map<String, Object> r2 = new HashMap<>();
        r2.put("mealId", "meal_breakfast_1");
        r2.put("mealName", "Aloo Paratha & Curd");
        r2.put("userId", "demo_student_2");
        r2.put("rating", 4.5);
        r2.put("taste", 4);
        r2.put("quality", 5);
        r2.put("quantity", 5);
        r2.put("comment", "Great parathas! Served piping hot with fresh curd.");
        r2.put("createdAt", now - 86400000L);
        db.collection("ratings").add(r2);

        Map<String, Object> r3 = new HashMap<>();
        r3.put("mealId", "meal_biryani_1");
        r3.put("mealName", "Vegetable Dum Biryani");
        r3.put("userId", "demo_student_3");
        r3.put("rating", 5.0);
        r3.put("taste", 5);
        r3.put("quality", 5);
        r3.put("quantity", 5);
        r3.put("comment", "Outstanding flavor! Spice level was just right.");
        r3.put("createdAt", now - 172800000L);
        db.collection("ratings").add(r3);
    }
}
