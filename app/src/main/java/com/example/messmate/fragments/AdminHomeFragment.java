package com.example.messmate.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.example.messmate.activities.ManageMealActivity;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHomeFragment extends Fragment {

    private View root;

    public AdminHomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(
                R.layout.fragment_admin_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);

        view.findViewById(R.id.btnHomeAddMeal).setOnClickListener(v ->
                startActivity(new android.content.Intent(
                        requireContext(), ManageMealActivity.class)));

        view.findViewById(R.id.btnHomeManageMenu).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content,
                                new ManageMenuFragment())
                        .addToBackStack(null)
                        .commit());

        view.findViewById(R.id.btnHomeAnalytics).setOnClickListener(v ->
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(android.R.id.content,
                                new AnalyticsFragment())
                        .addToBackStack(null)
                        .commit());

        loadStatistics();
    }

    private void loadStatistics() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("meals").get().addOnSuccessListener(snapshot -> {
            if (isAdded()) {
                setText(R.id.txtHomeMealCount,
                        String.valueOf(snapshot.size()));
            }
        });

        db.collection("ratings").get().addOnSuccessListener(snapshot -> {
            if (!isAdded()) return;

            double sum = 0;
            for (var doc : snapshot.getDocuments()) {
                Double rating = doc.getDouble("rating");
                if (rating != null) sum += rating;
            }

            double average = snapshot.isEmpty() ? 0 : sum / snapshot.size();

            setText(R.id.txtHomeFeedbackCount,
                    String.valueOf(snapshot.size()));
            setText(R.id.txtHomeRating,
                    String.format(java.util.Locale.getDefault(),
                            "%.1f/5", average));

            if (!snapshot.isEmpty()) {
                var latest = snapshot.getDocuments().get(0);
                setText(R.id.txtLatestFeedbackComment,
                        latest.getString("comment"));
                setText(R.id.txtLatestFeedbackMeal,
                        latest.getString("mealName"));

                Double rating = latest.getDouble("rating");
                if (rating != null) {
                    setText(R.id.txtLatestFeedbackRating,
                            String.format("%.1f/5", rating));
                }
            }
        });
    }

    private void setText(int id, String value) {
        TextView text = root.findViewById(id);
        if (text != null && value != null) text.setText(value);
    }
}