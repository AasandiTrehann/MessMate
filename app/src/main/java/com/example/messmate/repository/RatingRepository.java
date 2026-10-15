package com.example.messmate.repository;

import com.example.messmate.models.MealRating;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class RatingRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface RatingsCallback {
        void onSuccess(List<MealRating> ratings);
        void onError(Exception error);
    }

    public void submitRating(MealRating rating,
                             com.google.android.gms.tasks.OnCompleteListener<Void> callback) {
        db.collection("ratings").document()
                .set(rating).addOnCompleteListener(callback);
    }

    public void getRatings(RatingsCallback callback) {
        db.collection("ratings").get()
                .addOnSuccessListener(snapshot -> {
                    List<MealRating> ratings = new ArrayList<>();

                    for (var doc : snapshot.getDocuments()) {
                        MealRating rating = doc.toObject(MealRating.class);
                        if (rating != null) {
                            rating.setId(doc.getId());
                            ratings.add(rating);
                        }
                    }

                    callback.onSuccess(ratings);
                })
                .addOnFailureListener(callback::onError);
    }
}