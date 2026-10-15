package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PreferenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        findViewById(R.id.btnSavePreferences)
                .setOnClickListener(v -> save());

        loadExistingPreferences();
    }

    private void loadExistingPreferences() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore.getInstance().collection("users")
                .document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (isFinishing() || !doc.exists()) return;

                    String foodType = doc.getString("foodType");
                    if ("Non-Vegetarian".equalsIgnoreCase(foodType)) {
                        ((RadioGroup) findViewById(R.id.radioFoodType)).check(R.id.radioNonVegetarian);
                    } else if ("Eggetarian".equalsIgnoreCase(foodType)) {
                        ((RadioGroup) findViewById(R.id.radioFoodType)).check(R.id.radioEggetarian);
                    } else if ("Vegetarian".equalsIgnoreCase(foodType)) {
                        ((RadioGroup) findViewById(R.id.radioFoodType)).check(R.id.radioVegetarian);
                    }

                    String spiceLevel = doc.getString("spiceLevel");
                    if ("Mild".equalsIgnoreCase(spiceLevel)) {
                        ((RadioGroup) findViewById(R.id.radioSpiceLevel)).check(R.id.radioMild);
                    } else if ("Spicy".equalsIgnoreCase(spiceLevel)) {
                        ((RadioGroup) findViewById(R.id.radioSpiceLevel)).check(R.id.radioSpicy);
                    } else if ("Medium".equalsIgnoreCase(spiceLevel)) {
                        ((RadioGroup) findViewById(R.id.radioSpiceLevel)).check(R.id.radioMedium);
                    }

                    String favorite = doc.getString("favoriteFoods");
                    if (favorite != null) {
                        ((EditText) findViewById(R.id.edtFavouriteFoods)).setText(favorite);
                    }

                    String disliked = doc.getString("dislikedFoods");
                    if (disliked != null) {
                        ((EditText) findViewById(R.id.edtDislikedFoods)).setText(disliked);
                    }

                    Boolean north = doc.getBoolean("northIndian");
                    if (north != null) {
                        ((CheckBox) findViewById(R.id.checkNorthIndian)).setChecked(north);
                    }

                    Boolean south = doc.getBoolean("southIndian");
                    if (south != null) {
                        ((CheckBox) findViewById(R.id.checkSouthIndian)).setChecked(south);
                    }

                    Boolean chinese = doc.getBoolean("chinese");
                    if (chinese != null) {
                        ((CheckBox) findViewById(R.id.checkChinese)).setChecked(chinese);
                    }
                });
    }

    private void save() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            finish();
            return;
        }

        RadioGroup food = findViewById(R.id.radioFoodType);
        RadioGroup spice = findViewById(R.id.radioSpiceLevel);

        int f = food.getCheckedRadioButtonId();
        int s = spice.getCheckedRadioButtonId();

        String foodType = f == R.id.radioNonVegetarian
                ? "Non-Vegetarian"
                : f == R.id.radioEggetarian ? "Eggetarian" : "Vegetarian";

        String spiceLevel = s == R.id.radioMild
                ? "Mild" : s == R.id.radioSpicy ? "Spicy" : "Medium";

        Map<String, Object> data = new HashMap<>();
        data.put("foodType", foodType);
        data.put("spiceLevel", spiceLevel);
        data.put("favoriteFoods",
                ((EditText) findViewById(R.id.edtFavouriteFoods))
                        .getText().toString().trim());
        data.put("dislikedFoods",
                ((EditText) findViewById(R.id.edtDislikedFoods))
                        .getText().toString().trim());
        data.put("northIndian",
                ((CheckBox) findViewById(R.id.checkNorthIndian)).isChecked());
        data.put("southIndian",
                ((CheckBox) findViewById(R.id.checkSouthIndian)).isChecked());
        data.put("chinese",
                ((CheckBox) findViewById(R.id.checkChinese)).isChecked());

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid).set(data, com.google.firebase.firestore.SetOptions.merge())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this,
                                "Could not save preferences. Please try again.",
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}