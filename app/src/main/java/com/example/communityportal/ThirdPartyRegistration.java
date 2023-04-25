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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ThirdPartyRegistration extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editText1, editText2;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_registration);
        mAuth = FirebaseAuth.getInstance();
        editText1 = findViewById(R.id.editText9);
        editText2 = findViewById(R.id.editText10);
        progressBar = findViewById(R.id.progressBar1);
        Button button = findViewById(R.id.button3);

        /** if (mAuth.getCurrentUser() != null) {
         startActivity(new Intent(getApplicationContext(), ThirdPartyLogin.class));
         finish();
         } **/

        button.setOnClickListener(view -> {

            String email = editText1.getText().toString().trim();
            String userPassword = editText2.getText().toString().trim();


            if (email.isEmpty()) {
                editText1.setError("Please enter your email!");
                editText1.requestFocus();
                return;
            }
            if (userPassword.isEmpty() || userPassword.length() < 4) {
                editText2.setError("Please enter user password containing at least 4 characters!");
                editText2.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editText1.setError("Please enter a valid email address!");
                editText1.requestFocus();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            mAuth.createUserWithEmailAndPassword(email, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        User user = new User(email, userPassword, "");

                        FirebaseDatabase.getInstance().getReference("Third-parties").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(ThirdPartyRegistration.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(ThirdPartyRegistration.this, ThirdPartyDashboard.class));
                                            progressBar.setVisibility(View.GONE);
                                            startActivity(new Intent(ThirdPartyRegistration.this,ThirdPartyLogin.class));
                                            editText1.setText("");
                                            editText2.setText("");
                                        } else {
                                            Toast.makeText(ThirdPartyRegistration.this, "Failed to register,check your credentials or network!", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }

                                });
                    } else {
                        Toast.makeText(ThirdPartyRegistration.this, "Failed to register,check your credentials or network!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }


            });
        });


    }
}