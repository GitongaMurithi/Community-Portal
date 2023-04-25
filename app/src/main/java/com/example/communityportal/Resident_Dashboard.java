package com.example.communityportal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.example.communityportal.databinding.ActivityResidentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class Resident_Dashboard extends Drawer {

    ActivityResidentDashboardBinding activityResidentDashboardBinding;
    String uid;
    Uri profilePic;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResidentDashboardBinding = ActivityResidentDashboardBinding.inflate(getLayoutInflater());
        setContentView(activityResidentDashboardBinding.getRoot());
        allocateActivityTitle("Resident Dashboard");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        uid = user.getUid();


        activityResidentDashboardBinding.cardView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Resident_Dashboard.this, Add_Info.class));
            }
        });
        /**
         activityResidentDashboardBinding.cardView7.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        startActivity(new Intent(Resident_Dashboard.this, Demographics.class));
        }
        }); **/

        activityResidentDashboardBinding.imageHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectImage = new Intent(Intent.ACTION_PICK);
                selectImage.setType("image/*");
                activityResultLauncher.launch(selectImage);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Residents").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String activeUser = snapshot.child(uid).child("username").getValue(String.class);
                activityResidentDashboardBinding.user.setText(activeUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                profilePic = data.getData();
                activityResidentDashboardBinding.userImage.setImageURI(profilePic);

                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profilePic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                activityResidentDashboardBinding.userImage.setImageBitmap(bitmap);
            }
        }
    }
}