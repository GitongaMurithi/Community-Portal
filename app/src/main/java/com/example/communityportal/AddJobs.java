package com.example.communityportal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityAddJobsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddJobs extends AppCompatActivity {
    ActivityAddJobsBinding binding;
    AddJob addJob;
    String employer, description, qualification, title, location;
    Uri logo;

   /** ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

        }
    }); **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddJobsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.companyLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             //   activityResultLauncher.launch(intent);
                startActivityForResult(intent,500);
                binding.setLogo.setVisibility(View.VISIBLE);
            }
        });

        binding.setLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(AddJobs.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                FirebaseStorage.getInstance().getReference("images/" + UUID.randomUUID().toString()).putFile(logo).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        updateProfilePic(task.getResult().toString());
                                    }
                                }
                            });
                            Toast.makeText(AddJobs.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddJobs.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progress = 100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded " + (int) progress + " %");
                    }
                });

            }
        });
        binding.btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employer = binding.company.getText().toString();
                description = binding.jobDescription.getText().toString();
                qualification = binding.jobQualifications.getText().toString();
                title = binding.edtTitle.getText().toString();
                location = binding.edtLocation.getText().toString();

                if (employer.isEmpty()) {
                    binding.company.setError("This field is required");
                    binding.company.requestFocus();
                    return;
                }
                if (description.isEmpty()) {
                    binding.jobDescription.setError("This field is required");
                    binding.jobDescription.requestFocus();
                    return;

                }
                if (qualification.isEmpty()) {
                    binding.jobQualifications.setError("This field is required");
                    binding.jobQualifications.requestFocus();
                    return;
                }
                if (title.isEmpty()) {
                    binding.edtTitle.setError("This field is required");
                    binding.edtTitle.requestFocus();
                    return;
                }
                if (location.isEmpty()) {
                    binding.edtLocation.setError("This field is required");
                    binding.edtLocation.requestFocus();
                    return;
                }
                binding.jobProgress.setVisibility(View.VISIBLE);

                addJob = new AddJob(employer, description, qualification, title, location, "");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("Jobs");
                reference.child(employer).setValue(addJob).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            binding.company.setText("");
                            binding.jobDescription.setText("");
                            binding.jobQualifications.setText("");
                            binding.edtLocation.setText("");
                            binding.edtTitle.setText("");
                            binding.jobProgress.setVisibility(View.GONE);
                            Toast.makeText(AddJobs.this, "Job posted", Toast.LENGTH_SHORT).show();
                        } else {
                            binding.jobProgress.setVisibility(View.GONE);
                            Toast.makeText(AddJobs.this, "Failed to post!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    logo = data.getData();
                     binding.companyLogo.setImageURI(logo);
                }
            }

        }
    }

    private void updateProfilePic(String url) {
        FirebaseDatabase.getInstance().getReference("Third-parties/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/logo").setValue(url);
        Picasso.get().load(url).into(binding.companyLogo);
        binding.setLogo.setVisibility(View.GONE);
    }
}