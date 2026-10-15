package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, password, confirm, hostel, room;
    private AutoCompleteTextView food;
    private Button signup;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.edtName);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        confirm = findViewById(R.id.edtConfirmPassword);
        hostel = findViewById(R.id.edtHostel);
        room = findViewById(R.id.edtRoom);
        food = findViewById(R.id.actFoodType);
        signup = findViewById(R.id.btnSignUp);

        food.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                new String[]{"Vegetarian", "Eggetarian", "Non-Vegetarian"}
        ));
        food.setText("Vegetarian", false);

        signup.setOnClickListener(v -> register());
        findViewById(R.id.txtLogin).setOnClickListener(v -> finish());
    }

    private void register() {
        String n = name.getText().toString().trim();
        String e = email.getText().toString().trim();
        String p = password.getText().toString();
        String c = confirm.getText().toString();

        if (TextUtils.isEmpty(n) || TextUtils.isEmpty(e)
                || TextUtils.isEmpty(p) || TextUtils.isEmpty(c)) {
            Toast.makeText(this, "Complete all required fields.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (p.length() < 6) {
            password.setError("Use at least 6 characters");
            return;
        }

        if (!p.equals(c)) {
            confirm.setError("Passwords do not match");
            return;
        }

        signup.setEnabled(false);

        auth.createUserWithEmailAndPassword(e, p).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                signup.setEnabled(true);
                String error = task.getException() == null
                        ? "Registration failed."
                        : task.getException().getLocalizedMessage();
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                return;
            }

            Map<String, Object> data = new HashMap<>();
            data.put("name", n);
            data.put("email", e);
            data.put("hostel", hostel.getText().toString().trim());
            data.put("room", room.getText().toString().trim());
            data.put("foodType", food.getText().toString().trim());
            data.put("role", "student");
            data.put("createdAt", System.currentTimeMillis());

            String uid = auth.getCurrentUser().getUid();

            db.collection("users").document(uid).set(data)
                    .addOnCompleteListener(saved -> {
                        signup.setEnabled(true);

                        if (saved.isSuccessful()) {
                            startActivity(new Intent(
                                    this, PreferenceActivity.class));
                            finish();
                        } else {
                            Toast.makeText(this,
                                    "Account created, but profile save failed. Check Firestore rules.",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}