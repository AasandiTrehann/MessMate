package com.example.messmate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.messmate.R;
import com.example.messmate.activities.LoginActivity;
import com.example.messmate.activities.PreferenceActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private View root;

    public ProfileFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle state) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle state) {
        super.onViewCreated(view, state);

        view.findViewById(R.id.btnEditPreferences)
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                requireContext(), PreferenceActivity.class)));

        view.findViewById(R.id.txtEditPreferences)
                .setOnClickListener(v ->
                        startActivity(new Intent(
                                requireContext(), PreferenceActivity.class)));

        view.findViewById(R.id.txtSignOut).setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        loadProfile();
    }

    private void loadProfile() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) return;

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseFirestore.getInstance().collection("users")
                .document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (!isAdded() || !doc.exists()) return;

                    setText(R.id.txtName, doc.getString("name"));
                    setText(R.id.txtEmail, doc.getString("email"));
                    setText(R.id.txtHostel, doc.getString("hostel"));
                    setText(R.id.txtRoom, doc.getString("room"));
                    setText(R.id.txtFoodType, doc.getString("foodType"));
                    setText(R.id.txtSpiceLevel, doc.getString("spiceLevel"));
                    setText(R.id.txtFavoriteFoods,
                            doc.getString("favoriteFoods"));
                });
    }

    private void setText(int id, String value) {
        TextView text = root.findViewById(id);
        if (text != null && value != null) text.setText(value);
    }
}