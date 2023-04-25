package com.example.communityportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.communityportal.databinding.ActivityJobDetailsBinding;

public class JobDetails extends AppCompatActivity {
    ActivityJobDetailsBinding binding;

    String title,company,location,description,qualification;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        linearLayout = findViewById(R.id.applyJob);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(JobDetails.this,ApplicationWebsite.class));
            }
        });

        title=getIntent().getStringExtra("title");
        location=getIntent().getStringExtra("location");
        company=getIntent().getStringExtra("company");
        description=getIntent().getStringExtra("description");
        qualification=getIntent().getStringExtra("qualifications");

        binding.jobTitle.setText(title);
        binding.jobDescription.setText(description);
        binding.companyLocation.setText(location);
        binding.companyName.setText(company);
        binding.qualification.setText(qualification);
    }
}