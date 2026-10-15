package com.example.messmate.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ManageMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_meal);

        findViewById(R.id.btnSaveMeal).setOnClickListener(v -> saveMeal());
    }

    private String value(int id) {
        return ((EditText) findViewById(id))
                .getText().toString().trim();
    }

    private void saveMeal() {
        String name = value(R.id.edtMealName);
        String category = value(R.id.edtMealCategory);

        if (name.isEmpty() || category.isEmpty()) {
            Toast.makeText(this,
                    "Meal name and category are required.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        RadioGroup food = findViewById(R.id.radioMealFoodType);
        RadioGroup spice = findViewById(R.id.radioMealSpice);

        int f = food.getCheckedRadioButtonId();
        int s = spice.getCheckedRadioButtonId();

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("category", category);
        data.put("description", value(R.id.edtMealDescription));
        data.put("tags", value(R.id.edtMealTags));
        data.put("foodType", f == R.id.radioMealNonVeg
                ? "Non-Vegetarian"
                : f == R.id.radioMealEgg ? "Eggetarian" : "Vegetarian");
        data.put("spiceLevel", s == R.id.radioMealMild
                ? "Mild" : s == R.id.radioMealSpicy ? "Spicy" : "Medium");
        data.put("available",
                ((Switch) findViewById(R.id.switchMealAvailable)).isChecked());
        data.put("rating", 0.0);
        data.put("createdAt", System.currentTimeMillis());

        FirebaseFirestore.getInstance().collection("meals").add(data)
                .addOnSuccessListener(result -> {
                    Toast.makeText(this, "Meal saved.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(error ->
                        Toast.makeText(this, error.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show());
    }
}