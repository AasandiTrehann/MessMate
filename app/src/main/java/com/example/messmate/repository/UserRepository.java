package com.example.messmate.repository;

import com.example.messmate.models.UserProfile;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public interface ProfileCallback {
        void onSuccess(UserProfile profile);
        void onError(Exception error);
    }

    public void getProfile(String uid, ProfileCallback callback) {
        db.collection("users").document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) {
                        callback.onError(
                                new Exception("User profile not found."));
                        return;
                    }

                    UserProfile profile = doc.toObject(UserProfile.class);
                    if (profile != null) {
                        profile.setUid(uid);
                        callback.onSuccess(profile);
                    } else {
                        callback.onError(
                                new Exception("Could not read user profile."));
                    }
                })
                .addOnFailureListener(callback::onError);
    }

    public void saveProfile(String uid, UserProfile profile,
                            com.google.android.gms.tasks.OnCompleteListener<Void> callback) {
        db.collection("users").document(uid)
                .set(profile).addOnCompleteListener(callback);
    }
}