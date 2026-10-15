package com.example.messmate.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RateMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_meal);

        String name = getIntent().getStringExtra("mealName");
        if (name != null) {
            ((TextView) findViewById(R.id.txtRatedMealName)).setText(name);
        }

        findViewById(R.id.btnSubmitRating).setOnClickListener(v -> submit());
    }

    private void submit() {
        String mealId = getIntent().getStringExtra("mealId");

        if (mealId == null || mealId.isEmpty()
                || FirebaseAuth.getInstance().getCurrentUser() == null) {
            Toast.makeText(this, "Meal or user information is missing.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        float stars = ((RatingBar) findViewById(R.id.ratingOverall))
                .getRating();

        if (stars <= 0) {
            Toast.makeText(this, "Select an overall rating.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("mealId", mealId);
        data.put("mealName", getIntent().getStringExtra("mealName"));
        data.put("userId",
                FirebaseAuth.getInstance().getCurrentUser().getUid());
        data.put("rating", (double) stars);
        data.put("taste",
                ((SeekBar) findViewById(R.id.seekTaste)).getProgress());
        data.put("quality",
                ((SeekBar) findViewById(R.id.seekQuality)).getProgress());
        data.put("quantity",
                ((SeekBar) findViewById(R.id.seekQuantity)).getProgress());
        data.put("comment",
                ((EditText) findViewById(R.id.edtFeedback))
                        .getText().toString().trim());
        data.put("createdAt", System.currentTimeMillis());

        FirebaseFirestore.getInstance().collection("ratings").add(data)
                .addOnSuccessListener(result -> {
                    Toast.makeText(this, "Thank you for your feedback!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(error ->
                        Toast.makeText(this, error.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show());
    }
}