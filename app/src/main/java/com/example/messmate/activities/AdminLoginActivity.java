package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        findViewById(R.id.btnAdminLogin).setOnClickListener(v -> login());
        findViewById(R.id.txtBackToStudent).setOnClickListener(v -> finish());
    }

    private void login() {
        String email = ((EditText) findViewById(R.id.edtAdminEmail))
                .getText().toString().trim();
        String password = ((EditText) findViewById(R.id.edtAdminPassword))
                .getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter admin credentials.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        if ("aish1@gmail.com".equalsIgnoreCase(email) && "aish123".equals(password)) {
                            createAdminAccount(auth, email, password);
                            return;
                        }
                        Toast.makeText(this, "Admin login failed.",
                                Toast.LENGTH_LONG).show();
                        return;
                    }

                    String uid = auth.getCurrentUser().getUid();

                    if ("aish1@gmail.com".equalsIgnoreCase(email)) {
                        Map<String, Object> adminData = new HashMap<>();
                        adminData.put("name", "Admin");
                        adminData.put("email", email);
                        adminData.put("role", "admin");
                        FirebaseFirestore.getInstance().collection("users").document(uid)
                                .set(adminData, SetOptions.merge())
                                .addOnCompleteListener(t -> openAdminDashboard());
                        return;
                    }

                    FirebaseFirestore.getInstance()
                            .collection("users").document(uid).get()
                            .addOnSuccessListener(document -> {
                                if (document.exists()
                                        && "admin".equalsIgnoreCase(
                                        document.getString("role"))) {
                                    openAdminDashboard();
                                } else {
                                    auth.signOut();
                                    Toast.makeText(this,
                                            "This account is not an administrator.",
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                });
    }

    private void createAdminAccount(FirebaseAuth auth, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && auth.getCurrentUser() != null) {
                        String uid = auth.getCurrentUser().getUid();
                        Map<String, Object> adminData = new HashMap<>();
                        adminData.put("name", "Admin");
                        adminData.put("email", email);
                        adminData.put("role", "admin");

                        FirebaseFirestore.getInstance().collection("users").document(uid)
                                .set(adminData, SetOptions.merge())
                                .addOnCompleteListener(t -> openAdminDashboard());
                    } else {
                        Toast.makeText(this, "Admin login failed.", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void openAdminDashboard() {
        startActivity(new Intent(this, AdminDashboardActivity.class));
        finish();
    }
}
