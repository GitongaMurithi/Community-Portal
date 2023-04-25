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

public class ResidentLogin extends AppCompatActivity {

    private EditText editText1,editText2;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_login);


        TextView forgotPassword = findViewById(R.id.textview13);
        TextView register = findViewById(R.id.textview14); 

        editText1=findViewById(R.id.edittext13);
        editText2=findViewById(R.id.edittext14);

        progressBar=findViewById(R.id.progressBar10);

        Button button=findViewById(R.id.button7);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResidentLogin.this, ForgotPassword.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResidentLogin.this, ResidentRegistration.class));
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editText1.getText().toString().trim();
                String pass = editText2.getText().toString().trim();


                if(email.isEmpty()){
                    editText1.setError("Enter your email");
                    editText1.requestFocus();
                    return;
                }

                if(pass.isEmpty()){
                    editText2.setError("Enter your email");
                    editText2.requestFocus();
                    return;
                }

                if(pass.length()<4){
                    editText2.setError("Enter a password with at leas 4 characters");
                    editText2.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    editText1.setError("Enter a valid  email");
                    editText1.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(ResidentLogin.this,Resident_Dashboard.class));
                            Toast.makeText(ResidentLogin.this,"Logged in successfully!",Toast.LENGTH_LONG).show();
                            editText1.setText("");
                            editText2.setText("");
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(ResidentLogin.this,"Failed to login,check your credentials or network!",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}