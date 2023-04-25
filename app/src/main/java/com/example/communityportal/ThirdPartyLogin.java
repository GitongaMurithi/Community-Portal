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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThirdPartyLogin extends AppCompatActivity {
    FirebaseAuth mAuth;
    private EditText userEmail, password;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_party_login);

        TextView forgotPassword = findViewById(R.id.textview11);
        TextView register = findViewById(R.id.textview12);
        Button button = findViewById(R.id.button6);

        progressBar=findViewById(R.id.progressBar5);

        userEmail = findViewById(R.id.editText11);
        password = findViewById(R.id.edittext12);

        mAuth = FirebaseAuth.getInstance();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Gitonga Murithi codes!");

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThirdPartyLogin.this, ForgotPassword.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThirdPartyLogin.this, ThirdPartyRegistration.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString().trim();
                String pass = password.getText().toString().trim();


                if(email.isEmpty()){
                    userEmail.setError("Enter your email");
                    userEmail.requestFocus();
                    return;
                }

                if(pass.isEmpty()){
                    password.setError("Enter your email");
                    password.requestFocus();
                    return;
                }

                if(pass.length()<4){
                    password.setError("Enter a password with at leas 4 characters");
                    password.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.setError("Enter a valid  email");
                    userEmail.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(ThirdPartyLogin.this,ThirdPartyDashboard.class));
                            Toast.makeText(ThirdPartyLogin.this,"Logged in successfully!",Toast.LENGTH_LONG).show();
                            userEmail.setText("");
                            password.setText("");
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ThirdPartyLogin.this,"Failed to login,check your credentials or network!",Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }


            });
        }
    }
