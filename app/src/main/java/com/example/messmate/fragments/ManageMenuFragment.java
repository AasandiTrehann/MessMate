package com.example.messmate.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ManageMenuFragment extends Fragment {

    private View root;
    private final Map<String, String> selectedMeals = new HashMap<>();

    public ManageMenuFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(
                R.layout.fragment_manage_menu, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);

        loadCategory("Breakfast", R.id.txtBreakfastSelected,
                R.id.btnSelectBreakfast);
        loadCategory("Lunch", R.id.txtLunchSelected,
                R.id.btnSelectLunch);
        loadCategory("Dinner", R.id.txtDinnerSelected,
                R.id.btnSelectDinner);
        loadCategory("Snacks", R.id.txtSnacksSelected,
                R.id.btnSelectSnacks);

        view.findViewById(R.id.btnSaveMenu).setOnClickListener(v ->
                saveMenu());
    }

    private void loadCategory(String category, int textId, int buttonId) {
        FirebaseFirestore.getInstance().collection("meals")
                .whereEqualTo("category", category)
                .whereEqualTo("available", true).limit(1).get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    if (!snapshot.isEmpty()) {
                        var meal = snapshot.getDocuments().get(0);
                        selectedMeals.put(category, meal.getId());
                        setText(textId, meal.getString("name"));
                    } else {
                        setText(textId, "No meal selected");
                    }
                });

        root.findViewById(buttonId).setOnClickListener(v ->
                chooseMeal(category, textId));
    }

    private void chooseMeal(String category, int textId) {
        FirebaseFirestore.getInstance().collection("meals")
                .whereEqualTo("category", category)
                .whereEqualTo("available", true).get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded()) return;

                    if (snapshot.isEmpty()) {
                        Toast.makeText(requireContext(),
                                "Add an available " + category + " meal first.",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String[] names = new String[snapshot.size()];
                    String[] ids = new String[snapshot.size()];

                    for (int i = 0; i < snapshot.size(); i++) {
                        var doc = snapshot.getDocuments().get(i);
                        names[i] = doc.getString("name");
                        ids[i] = doc.getId();
                    }

                    new android.app.AlertDialog.Builder(requireContext())
                            .setTitle("Select " + category)
                            .setItems(names, (dialog, which) -> {
                                selectedMeals.put(category, ids[which]);
                                setText(textId, names[which]);
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                });
    }

    private void saveMenu() {
        Map<String, Object> menu = new HashMap<>();
        menu.put("meals", new HashMap<>(selectedMeals));
        menu.put("updatedAt", System.currentTimeMillis());

        String date = new java.text.SimpleDateFormat(
                "yyyy-MM-dd", java.util.Locale.getDefault())
                .format(new java.util.Date());

        FirebaseFirestore.getInstance().collection("dailyMenus")
                .document(date).set(menu)
                .addOnSuccessListener(unused ->
                        Toast.makeText(requireContext(),
                                "Daily menu saved.",
                                Toast.LENGTH_SHORT).show())
                .addOnFailureListener(error ->
                        Toast.makeText(requireContext(),
                                error.getLocalizedMessage(),
                                Toast.LENGTH_LONG).show());
    }

    private void setText(int id, String value) {
        TextView text = root.findViewById(id);
        if (text != null && value != null) text.setText(value);
    }
}