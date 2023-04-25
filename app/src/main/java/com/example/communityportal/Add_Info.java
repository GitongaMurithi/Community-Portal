package com.example.communityportal;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.communityportal.databinding.ActivityAddInfoBinding;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;


public class Add_Info extends Drawer implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    ActivityAddInfoBinding activityAddInfoBinding;
    FirebaseDatabase db;
    DatabaseReference reference;
    String userName, birthday, phone, residence, skills, gender, employed;
    EditText editText;
    TextView textView;
    RadioButton male, female, other, hasJob, hasNoJob;
    UserData userData;
    String data;
    Uri profilePicture;
    String[] levelOfEducation = {"Choose..", "Primary", "Secondary", "Tertiary", "Undergraduate", "Postgraduate"};

    /**
    ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

        }
    }); **/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddInfoBinding = ActivityAddInfoBinding.inflate(getLayoutInflater());
        setContentView(activityAddInfoBinding.getRoot());
        allocateActivityTitle("Add Data");

        editText = findViewById(R.id.edittext18);
        male = activityAddInfoBinding.radio1;
        female = activityAddInfoBinding.radio2;
        other = activityAddInfoBinding.radio3;
        hasJob = activityAddInfoBinding.radio4;
        hasNoJob = activityAddInfoBinding.radio5;
        textView = activityAddInfoBinding.spinnerData;

        activityAddInfoBinding.linear5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open gallery
                Intent selectImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(selectImage,1);
               // activityResultLauncher.launch(selectImage);

            }
        });

        activityAddInfoBinding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(Add_Info.this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                FirebaseStorage.getInstance().getReference("images/" + UUID.randomUUID().toString()).putFile(profilePicture).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                            Toast.makeText(Add_Info.this, "Image uploaded", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Add_Info.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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

        activityAddInfoBinding.spinner1.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, levelOfEducation);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityAddInfoBinding.spinner1.setAdapter(adapter);

        activityAddInfoBinding.calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment fragment = new DatePickerFragment();
                fragment.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        activityAddInfoBinding.saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userName = editText.getText().toString().trim();

                birthday = activityAddInfoBinding.birthday1.getText().toString();
                phone = activityAddInfoBinding.edittext19.getText().toString();
                residence = activityAddInfoBinding.edittext20.getText().toString();
                skills = activityAddInfoBinding.edittext21.getText().toString();
                String maleRadio = male.getText().toString();
                String femaleRadio = female.getText().toString();
                String otherRadio = other.getText().toString();
                String emp = hasJob.getText().toString();
                String notEmp = hasNoJob.getText().toString();
                data = activityAddInfoBinding.spinner1.getSelectedItem().toString();


                if (userName.isEmpty()) {
                    editText.setError("This is a required field!");
                    editText.requestFocus();
                    return;
                }
                if (birthday.isEmpty()) {
                    activityAddInfoBinding.birthday1.setError("This is a required field!");
                    activityAddInfoBinding.birthday1.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    activityAddInfoBinding.edittext19.setError("This is a required field!");
                    activityAddInfoBinding.edittext19.requestFocus();
                    return;
                }
                if (residence.isEmpty()) {
                    activityAddInfoBinding.edittext20.setError("This is a required field!");
                    activityAddInfoBinding.edittext20.requestFocus();
                    return;
                }
                if (skills.isEmpty()) {
                    activityAddInfoBinding.edittext21.setError("This is a required field!");
                    activityAddInfoBinding.edittext21.requestFocus();
                    return;
                }
                if (data.equals("Choose..")) {
                    Toast.makeText(Add_Info.this, "Choose level", Toast.LENGTH_SHORT).show();
                    return;
                }


                activityAddInfoBinding.progressBar11.setVisibility(View.VISIBLE);
                userData = new UserData(userName, birthday, phone, residence, skills, gender, employed, data, "");
                if (male.isChecked()) {
                    userData.setGender(maleRadio);
                } else if (female.isChecked()) {
                    userData.setGender(femaleRadio);
                } else {
                    userData.setGender(otherRadio);
                }
                if (hasJob.isChecked()) {
                    userData.setEmployed(emp);
                } else {
                    userData.setEmployed(notEmp);
                }
                db = FirebaseDatabase.getInstance();
                reference = db.getReference("Users Data");
                reference.child(userName).setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            editText.setText("");
                            activityAddInfoBinding.birthday1.setText("");
                            activityAddInfoBinding.edittext19.setText("");
                            activityAddInfoBinding.edittext20.setText("");
                            activityAddInfoBinding.edittext21.setText("");
                            Toast.makeText(Add_Info.this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
                            activityAddInfoBinding.progressBar11.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(Add_Info.this, "Failed to save data!", Toast.LENGTH_SHORT).show();
                            activityAddInfoBinding.progressBar11.setVisibility(View.GONE);
                        }


                    }
                });

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                profilePicture = data.getData();
                activityAddInfoBinding.image1.setImageURI(profilePicture);
            }
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, i);
        calendar.set(Calendar.MONTH, i1);
        calendar.set(Calendar.DAY_OF_MONTH, i2);

        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        activityAddInfoBinding.birthday1.setText(currentDate);
    }

    private void updateProfilePic(String url) {
        FirebaseDatabase.getInstance().getReference("Users Data/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/image").setValue(url);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        data = activityAddInfoBinding.spinner1.getSelectedItem().toString();
        textView.setText(data);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}