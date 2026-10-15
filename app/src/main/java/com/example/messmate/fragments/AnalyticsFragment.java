package com.example.messmate.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class AnalyticsFragment extends Fragment {

    private View root;

    public AnalyticsFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(
                R.layout.fragment_analytics, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);
        loadAnalytics();
    }

    private void loadAnalytics() {
        FirebaseFirestore.getInstance().collection("ratings").get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    int[] counts = new int[6];
                    double total = 0;
                    int totalRatings = 0;

                    for (var doc : snapshot.getDocuments()) {
                        Double rating = doc.getDouble("rating");
                        if (rating == null) continue;

                        int stars = Math.max(1,
                                Math.min(5, Math.round(rating.floatValue())));
                        counts[stars]++;
                        total += rating;
                        totalRatings++;
                    }

                    double average = totalRatings == 0
                            ? 0 : total / totalRatings;

                    setText(R.id.txtAnalyticsAverage,
                            String.format(Locale.getDefault(), "%.1f/5", average));
                    setText(R.id.txtTotalRatings,
                            String.valueOf(totalRatings));

                    setText(R.id.txtOneStarPercent,
                            percentage(counts[1], totalRatings));
                    setText(R.id.txtTwoStarPercent,
                            percentage(counts[2], totalRatings));
                    setText(R.id.txtThreeStarPercent,
                            percentage(counts[3], totalRatings));
                    setText(R.id.txtFourStarPercent,
                            percentage(counts[4], totalRatings));
                    setText(R.id.txtFiveStarPercent,
                            percentage(counts[5], totalRatings));

                    setProgress(R.id.progressOneStar, counts[1], totalRatings);
                    setProgress(R.id.progressTwoStar, counts[2], totalRatings);
                    setProgress(R.id.progressThreeStar, counts[3], totalRatings);
                    setProgress(R.id.progressFourStar, counts[4], totalRatings);
                    setProgress(R.id.progressFiveStar, counts[5], totalRatings);

                    setText(R.id.txtPositiveFeedback,
                            percentage(counts[4] + counts[5], totalRatings));
                    setText(R.id.txtNeedsAttention,
                            "Meals rated below 3 stars");
                    setText(R.id.txtNeedsAttentionValue,
                            String.valueOf(counts[1] + counts[2]));
                });
    }

    private String percentage(int count, int total) {
        return total == 0 ? "0%" :
                Math.round(count * 100f / total) + "%";
    }

    private void setText(int id, String value) {
        TextView text = root.findViewById(id);
        if (text != null) text.setText(value);
    }

    private void setProgress(int id, int count, int total) {
        ProgressBar bar = root.findViewById(id);
        if (bar != null) {
            bar.setMax(Math.max(1, total));
            bar.setProgress(count);
        }
    }
}