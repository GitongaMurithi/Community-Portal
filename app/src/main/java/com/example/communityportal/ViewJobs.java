package com.example.communityportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityViewJobsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewJobs extends AppCompatActivity {
    ActivityViewJobsBinding binding;
    RecyclerView recyclerView;
    ArrayList<JobsModel> jobs;
    JobsAdapter.OnJobClicked onJobClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       onJobClicked=new JobsAdapter.OnJobClicked() {
           @Override
           public void onJobClicked(int position) {
               startActivity(new Intent(ViewJobs.this,JobDetails.class)
                               .putExtra("title", jobs.get(position).getJobTitle())
                               .putExtra("location", jobs.get(position).getCompanyLocation())
                               .putExtra("company", jobs.get(position).getCompanyName()));
                       //.putExtra("description", jobs.get(position).getDescription())
                       //.putExtra("qualifications", jobs.get(position).getQualification()););

           }
       };
        recyclerView = binding.jobsRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobs = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Jobs");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobs.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String jobTitle = ds.child("title").getValue(String.class);
                    String companyName = ds.child("employer").getValue(String.class);
                    String companyLocation = ds.child("location").getValue(String.class);

                    JobsModel model = new JobsModel(jobTitle, companyName, companyLocation);
                    jobs.add(model);
                    binding.jobsRecyclerView.setVisibility(View.VISIBLE);
                    binding.jobsProgress.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(new JobsAdapter(jobs, ViewJobs.this, onJobClicked));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}