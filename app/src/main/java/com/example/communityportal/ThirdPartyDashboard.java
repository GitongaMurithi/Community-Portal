package com.example.communityportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.communityportal.databinding.ActivityThirdPartyDashboardBinding;

public class ThirdPartyDashboard extends Drawer2 {
    ActivityThirdPartyDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityThirdPartyDashboardBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        setTitle("Third-party Dashboard");
        binding.jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ThirdPartyDashboard.this, AddJobs.class));
            }
        });
    }
}