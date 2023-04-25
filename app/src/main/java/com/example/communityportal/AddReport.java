package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityAddReportBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddReport extends AppCompatActivity {

    ActivityAddReportBinding binding;
    String message, sender;
    FirebaseDatabase database, firebaseDatabase;
    DatabaseReference databaseReference;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.sendCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                assert user != null;
                uid = user.getUid();
                message = binding.reportCase.getText().toString();

                if (message.isEmpty()) {
                    binding.reportCase.setError("Fill this field");
                    binding.reportCase.requestFocus();
                    return;
                }
                binding.progress.setVisibility(View.VISIBLE);
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference().child("Residents");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        sender = snapshot.child(uid).child("username").getValue(String.class);

                        CasesReported casesReported = new CasesReported(sender, message);
                        database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("Cases");
                        reference.child(sender).setValue(casesReported).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    binding.reportCase.setText("");
                                    binding.progress.setVisibility(View.GONE);
                                    Toast.makeText(AddReport.this, "Sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    binding.progress.setVisibility(View.GONE);
                                    Toast.makeText(AddReport.this, "Failed", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }
}