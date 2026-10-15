
package com.example.messmate.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public final class FirebaseHelper {

    private static final FirebaseAuth AUTH =
            FirebaseAuth.getInstance();

    private static final FirebaseFirestore DB =
            FirebaseFirestore.getInstance();

    private FirebaseHelper() {}

    public static FirebaseAuth getAuth() {
        return AUTH;
    }

    public static FirebaseFirestore getDatabase() {
        return DB;
    }

    public static String getCurrentUserId() {
        if (AUTH.getCurrentUser() == null) {
            return null;
        }

        return AUTH.getCurrentUser().getUid();
    }

    public static boolean isUserLoggedIn() {
        return AUTH.getCurrentUser() != null;
    }

    public static void signOut() {
        AUTH.signOut();
    }
}
