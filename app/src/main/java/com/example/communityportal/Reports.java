package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityReportsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reports extends AppCompatActivity {

    RecyclerView cases;
    ArrayList<Message> messages;
    ActivityReportsBinding binding;
    ReportsAdapter.OnMessageClicked onMessageClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onMessageClicked = new ReportsAdapter.OnMessageClicked() {
            @Override
            public void onMessageClicked(int position) {
                //   Toast.makeText(Reports.this, "Message selected " + messages.get(position).getContent(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Reports.this, ActualMessageSent.class)
                        .putExtra("message_content", messages.get(position).getContent())
                        .putExtra("sender", messages.get(position).getSender()));

            }
        };
        cases = findViewById(R.id.reportsList);
        cases.setHasFixedSize(true);
        cases.setLayoutManager(new LinearLayoutManager(this));
        messages = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Cases");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = dataSnapshot.child("sender").getValue(String.class);
                    String content = dataSnapshot.child("content").getValue(String.class);
                    Message message = new Message(name, content);
                    messages.add(message);
                    cases.setVisibility(View.VISIBLE);
                    binding.load.setVisibility(View.GONE);
                }
                cases.setAdapter(new ReportsAdapter(messages, Reports.this, onMessageClicked));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}