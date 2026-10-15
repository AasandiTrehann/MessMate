package com.example.messmate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.messmate.R;
import com.example.messmate.activities.MealDetailActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Locale;

public class MenuFragment extends Fragment {

    private View root;

    public MenuFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(R.layout.fragment_menu, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);

        loadMeal("Breakfast", R.id.txtBreakfastMeal,
                R.id.txtBreakfastDescription, R.id.txtBreakfastRating,
                R.id.imgBreakfast, R.id.breakfastMealCard,
                "meal_breakfast_1",
                "Aloo Paratha & Curd",
                "Stuffed golden wheat parathas served hot with fresh curd.",
                4.7,
                "https://images.unsplash.com/photo-1626777552726-4a6b54c97e46?w=600&auto=format&fit=crop");

        loadMeal("Lunch", R.id.txtLunchMeal,
                R.id.txtLunchDescription, R.id.txtLunchRating,
                R.id.imgLunch, R.id.lunchMealCard,
                "meal_lunch_1",
                "Paneer Butter Masala & Naan",
                "Rich and creamy paneer curry cooked in butter tomato gravy.",
                4.8,
                "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=600&auto=format&fit=crop");

        loadMeal("Snacks", R.id.txtSnacksMeal,
                R.id.txtSnacksDescription, R.id.txtSnacksRating,
                R.id.imgSnacks, R.id.snacksMealCard,
                "meal_snacks_1",
                "Samosa & Masala Chai",
                "Crispy golden samosas with spiced potato filling & hot tea.",
                4.5,
                "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=600&auto=format&fit=crop");

        loadMeal("Dinner", R.id.txtDinnerMeal,
                R.id.txtDinnerDescription, R.id.txtDinnerRating,
                R.id.imgDinner, R.id.dinnerMealCard,
                "meal_dinner_1",
                "Dal Makhani & Jeera Rice",
                "Slow-cooked black lentils in butter & cream with cumin rice.",
                4.6,
                "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=600&auto=format&fit=crop");
    }

    private void loadMeal(String category, int nameId, int descId,
                          int ratingId, int imageId, int cardId,
                          String defaultId, String defaultName,
                          String defaultDesc, double defaultRating,
                          String defaultImgUrl) {

        // Set default values immediately so UI is never empty
        setText(nameId, defaultName);
        setText(descId, defaultDesc);
        setText(ratingId, String.format(Locale.getDefault(), "%.1f rating", defaultRating));
        loadImage(imageId, defaultImgUrl);
        setupCardClick(cardId, defaultId, defaultName);

        // Fetch Firestore updates
        FirebaseFirestore.getInstance().collection("meals")
                .whereEqualTo("category", category)
                .limit(1).get()
                .addOnSuccessListener(snapshot -> {
                    if (!isAdded() || snapshot.isEmpty()) return;

                    QueryDocumentSnapshot meal =
                            (QueryDocumentSnapshot) snapshot.getDocuments().get(0);

                    String name = meal.getString("name");
                    String description = meal.getString("description");
                    Double rating = meal.getDouble("rating");
                    String imageUrl = meal.getString("imageUrl");

                    if (name != null) setText(nameId, name);
                    if (description != null) setText(descId, description);
                    if (rating != null) {
                        setText(ratingId, String.format(Locale.getDefault(), "%.1f rating", rating));
                    }
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        loadImage(imageId, imageUrl);
                    }

                    setupCardClick(cardId, meal.getId(), name != null ? name : defaultName);
                });
    }

    private void loadImage(int viewId, String url) {
        ImageView img = root.findViewById(viewId);
        if (img != null && isAdded() && getContext() != null) {
            Glide.with(this)
                    .load(url)
                    .centerCrop()
                    .into(img);
        }
    }

    private void setupCardClick(int cardId, String mealId, String mealName) {
        View card = root.findViewById(cardId);
        if (card != null) {
            card.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), MealDetailActivity.class);
                intent.putExtra("mealId", mealId);
                intent.putExtra("mealName", mealName);
                startActivity(intent);
            });
        }
    }

    private void setText(int id, String value) {
        TextView text = root.findViewById(id);
        if (text != null && value != null) text.setText(value);
    }
}
