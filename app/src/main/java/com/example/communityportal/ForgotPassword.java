package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText editText;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        editText = findViewById(R.id.edittext17);
        progressBar = findViewById(R.id.progressBar7);

        Button button = findViewById(R.id.button9);

        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = editText.getText().toString().trim();

                if (userEmail.isEmpty()) {
                    editText.setError("Enter your email!");
                    editText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    editText.setError("Enter a valid email address!");
                    editText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgotPassword.this, "Check your email to reset password", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ForgotPassword.this, Login.class);
                            startActivity(intent);
                            editText.setText("");
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ForgotPassword.this, "Failed to reset the password!", Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });
    }
}