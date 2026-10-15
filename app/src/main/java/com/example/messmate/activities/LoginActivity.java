package com.example.messmate.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messmate.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtPassword);
        login = findViewById(R.id.btnLogin);

        login.setOnClickListener(v -> signIn());

        findViewById(R.id.txtSignUp).setOnClickListener(v ->
                startActivity(new Intent(this, SignupActivity.class)));

        findViewById(R.id.txtAdminLogin).setOnClickListener(v ->
                startActivity(new Intent(this, AdminLoginActivity.class)));

        findViewById(R.id.txtForgotPassword).setOnClickListener(v ->
                resetPassword());
    }

    private void signIn() {
        String e = email.getText().toString().trim();
        String p = password.getText().toString();

        if (TextUtils.isEmpty(e) || TextUtils.isEmpty(p)) {
            Toast.makeText(this, "Enter email and password.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        login.setEnabled(false);

        auth.signInWithEmailAndPassword(e, p).addOnCompleteListener(task -> {
            login.setEnabled(true);

            if (task.isSuccessful()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                String error = task.getException() == null
                        ? "Login failed."
                        : task.getException().getLocalizedMessage();

                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resetPassword() {
        String e = email.getText().toString().trim();

        if (e.isEmpty()) {
            email.setError("Enter your email first");
            return;
        }

        auth.sendPasswordResetEmail(e).addOnCompleteListener(task ->
                Toast.makeText(this,
                        task.isSuccessful()
                                ? "Password reset email sent."
                                : "Could not send reset email.",
                        Toast.LENGTH_LONG).show());
    }
}