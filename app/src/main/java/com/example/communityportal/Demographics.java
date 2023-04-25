package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityDemographicsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Demographics extends AppCompatActivity {
    ActivityDemographicsBinding binding;
    RecyclerView recyclerView;
    ArrayList<UserData> names;
    UsersAdapter.OnUserClicked onUserClicked;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDemographicsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        onUserClicked = new UsersAdapter.OnUserClicked() {
            @Override
            public void onUserClicked(int position) {
                //  Toast.makeText(Demographics.this, "" + names.get(position).getUserName(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Demographics.this, UserDetails.class)
                        .putExtra("userName", names.get(position).getUserName())
                        .putExtra("residence", names.get(position).getResidence())
                        .putExtra("skills", names.get(position).getSkills())
                        .putExtra("gender", names.get(position).getGender())
                        .putExtra("employed", names.get(position).getEmployed())
                        .putExtra("birthday", names.get(position).getBirthday())
                        .putExtra("phone", names.get(position).getPhone())
                        .putExtra("level", names.get(position).getLevel()));
            }
        };
        recyclerView = binding.listView;
        names = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users Data");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                names.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String name = ds.child("userName").getValue(String.class);
                    String birthday = ds.child("birthday").getValue(String.class);
                    String residence = ds.child("residence").getValue(String.class);
                    String phone = ds.child("phone").getValue(String.class);
                    String skills = ds.child("skills").getValue(String.class);
                    String gender = ds.child("gender").getValue(String.class);
                    String employed = ds.child("employed").getValue(String.class);
                    String data = ds.child("level").getValue(String.class);
                    String image=ds.child("image").getValue(String.class);
                    UserData userData = new UserData(name, birthday, residence, phone, skills, gender, employed, data,image);
                    names.add(userData);
                    binding.progressbar12.setVisibility(View.GONE);
                    binding.listView.setVisibility(View.VISIBLE);

                }
                recyclerView.setAdapter(new UsersAdapter(names, Demographics.this, onUserClicked));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users Data");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    count = (int) snapshot.getChildrenCount();
                    binding.textview25.setText(Integer.toString(count));
                } else {
                    binding.textview25.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Residents");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    count = (int) snapshot.getChildrenCount();
                    binding.textview23.setText(Integer.toString(count));
                } else {
                    binding.textview23.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
