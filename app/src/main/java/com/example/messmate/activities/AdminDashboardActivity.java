package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        refreshDashboard();

        findViewById(R.id.btnManageMenu).setOnClickListener(v ->
                startActivity(new Intent(this, ManageMealActivity.class)));

        findViewById(R.id.btnAddMeal).setOnClickListener(v ->
                startActivity(new Intent(this, ManageMealActivity.class)));

        findViewById(R.id.btnViewAnalytics).setOnClickListener(v ->
                getSupportFragmentManager().beginTransaction()
                        .replace(android.R.id.content,
                                new AnalyticsFragmentContainer())
                        .addToBackStack(null)
                        .commit());

        findViewById(R.id.txtAdminLogout).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void refreshDashboard() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meals").get().addOnSuccessListener(snapshot -> {
            if (!isFinishing()) {
                ((TextView) findViewById(R.id.txtMealsCount))
                        .setText(String.valueOf(snapshot.size()));
            }
        });

        db.collection("ratings").get().addOnSuccessListener(snapshot -> {
            double sum = 0;

            for (var document : snapshot) {
                Double rating = document.getDouble("rating");
                if (rating != null) sum += rating;
            }

            double average = snapshot.isEmpty() ? 0 : sum / snapshot.size();

            if (!isFinishing()) {
                ((TextView) findViewById(R.id.txtAverageRating))
                        .setText(String.format(Locale.getDefault(),
                                "%.1f", average));
            }
        });
    }

    public static class AnalyticsFragmentContainer
            extends androidx.fragment.app.Fragment {

        @androidx.annotation.Nullable
        @Override
        public android.view.View onCreateView(
                @androidx.annotation.NonNull android.view.LayoutInflater inflater,
                @androidx.annotation.Nullable android.view.ViewGroup container,
                @androidx.annotation.Nullable Bundle savedInstanceState) {

            return inflater.inflate(
                    R.layout.fragment_analytics, container, false);
        }
    }
}