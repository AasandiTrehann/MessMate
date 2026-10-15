package com.example.messmate.repository;

import com.example.messmate.models.Meal;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MealRepository {

    public interface MealsCallback {
        void onSuccess(List<Meal> meals);
        void onError(Exception error);
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void getAvailableMeals(MealsCallback callback) {
        db.collection("meals")
                .whereEqualTo("available", true)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<Meal> meals = new ArrayList<>();

                    for (var doc : snapshot.getDocuments()) {
                        Meal meal = doc.toObject(Meal.class);
                        if (meal != null) {
                            meal.setId(doc.getId());
                            meals.add(meal);
                        }
                    }

                    callback.onSuccess(meals);
                })
                .addOnFailureListener(callback::onError);
    }

    public void addMeal(Meal meal,
                        com.google.android.gms.tasks.OnCompleteListener<Void> callback) {
        db.collection("meals").document(meal.getId())
                .set(meal).addOnCompleteListener(callback);
    }
}