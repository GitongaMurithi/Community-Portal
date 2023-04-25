package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityPracticeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Practice extends AppCompatActivity {
    ActivityPracticeBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    Example example;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPracticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.editExample.getText().toString().isEmpty()) {
                    example = new Example(binding.editExample.getText().toString());
                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("Example");
                    reference.child(binding.editExample.getText().toString()).setValue(example).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                binding.editExample.setText("");
                                Toast.makeText(Practice.this, "Saved!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}