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

public class AuthorityRegistration extends AppCompatActivity {
    private EditText username, phoneNo, email, password, area;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_registration);

        username = findViewById(R.id.editText5);
        phoneNo = findViewById(R.id.editText6);
        email = findViewById(R.id.editText7);
        password = findViewById(R.id.editText8);
        area = findViewById(R.id.editText9);

        Button button = findViewById(R.id.button5);
        progressBar = findViewById(R.id.progressBar3);

        mAuth = FirebaseAuth.getInstance();

        /**  if (mAuth.getCurrentUser() != null){
         startActivity(new Intent(getApplicationContext(),AuthorityLogin.class));
         finish();
         }
         **/
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String names = username.getText().toString().trim();
                String phone = phoneNo.getText().toString().trim();
                String userMail = email.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String admArea = area.getText().toString().trim();

                if (names.isEmpty()) {
                    username.setError("Enter your name!");
                    username.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    phoneNo.setError("Enter your phone number!");
                    phoneNo.requestFocus();
                    return;
                }
                if (userMail.isEmpty()) {
                    email.setError("Enter your email!");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(userMail).matches()) {
                    email.setError("Enter a valid email address!");
                    email.requestFocus();
                    return;
                }
                if (pass.isEmpty() || pass.length() < 4) {
                    password.setError("Enter password of at least 4 characters!");
                    password.requestFocus();
                    return;
                }
                if (admArea.isEmpty()) {
                    area.setError("Enter your administration area!");
                    area.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(userMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            User_Authority user_authority = new User_Authority(names, phone, pass, userMail, admArea);

                            FirebaseDatabase.getInstance().getReference("Authorities").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user_authority)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(AuthorityRegistration.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                                startActivity(new Intent(AuthorityRegistration.this,AuthorityLogin.class));
                                                username.setText("");
                                                phoneNo.setText("");
                                                email.setText("");
                                                password.setText("");
                                                area.setText("");
                                            } else {
                                                Toast.makeText(AuthorityRegistration.this, "Failed to register,check your credentials or network!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }

                                    });
                        } else {
                            Toast.makeText(AuthorityRegistration.this, "Failed to register,check your credentials or network!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }


                });
            }

        });
    }
}