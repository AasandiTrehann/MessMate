package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class MealDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);

        String id = getIntent().getStringExtra("mealId");
        String name = getIntent().getStringExtra("mealName");

        if (name != null) {
            setText(R.id.txtMealName, name);
        }

        if (id != null && !id.isEmpty()) {
            FirebaseFirestore.getInstance()
                    .collection("meals").document(id).get()
                    .addOnSuccessListener(document -> {
                        if (!document.exists()) return;

                        setText(R.id.txtMealName,
                                document.getString("name"));
                        setText(R.id.txtMealCategory,
                                document.getString("category"));
                        setText(R.id.txtMealDescription,
                                document.getString("description"));

                        Double rating = document.getDouble("rating");
                        if (rating != null) {
                            setText(R.id.txtMealRating,
                                    String.format(Locale.getDefault(),
                                            "%.1f", rating));
                        }

                        String imageUrl = document.getString("imageUrl");
                        if (imageUrl != null && !imageUrl.isEmpty()) {
                            android.widget.ImageView imageView = findViewById(R.id.imgMeal);
                            if (imageView != null) {
                                com.bumptech.glide.Glide.with(this)
                                        .load(imageUrl)
                                        .centerCrop()
                                        .into(imageView);
                                findViewById(R.id.txtMealImagePlaceholder)
                                        .setVisibility(android.view.View.GONE);
                            }
                        }
                    });
        }

        findViewById(R.id.btnRateMeal).setOnClickListener(v -> {
            Intent intent = new Intent(this, RateMealActivity.class);
            intent.putExtra("mealId", id);
            intent.putExtra("mealName", name);
            startActivity(intent);
        });
    }

    private void setText(int id, String value) {
        if (value != null) {
            ((TextView) findViewById(id)).setText(value);
        }
    }
}