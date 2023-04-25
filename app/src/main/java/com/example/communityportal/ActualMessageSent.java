package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActualMessageSent extends AppCompatActivity {
    TextView textView1, textView2;
    String content, sender;
    Button button;
    EditText editText;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_message_sent);

        textView1 = findViewById(R.id.sender);
        textView2 = findViewById(R.id.reportedCase);
        editText = findViewById(R.id.reply);
        button = findViewById(R.id.caseResponse);
        progressBar = findViewById(R.id.responsePB);

        sender = getIntent().getStringExtra("sender");
        content = getIntent().getStringExtra("message_content");
        textView1.setText(sender);
        textView2.setText(content);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textResponse = editText.getText().toString();
                if (textResponse.isEmpty()) {
                    editText.setError("Required!");
                    editText.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                Response response = new Response(textResponse);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Responses");
                reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(response).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            editText.setText("");
                            Toast.makeText(ActualMessageSent.this, "Sent", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(ActualMessageSent.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

}