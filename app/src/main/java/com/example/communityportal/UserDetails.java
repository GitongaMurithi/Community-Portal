package com.example.communityportal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserDetails extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7,textView8;
    String name, birthday, phone, employed, skills, gender, residence,level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        textView1 = findViewById(R.id.detailsName);
        textView2 = findViewById(R.id.detailsGender);
        textView3 = findViewById(R.id.detailsPhone);
        textView4 = findViewById(R.id.detailsResidence);
        textView5 = findViewById(R.id.detailsSkills);
        textView6 = findViewById(R.id.detailsDOB);
        textView7 = findViewById(R.id.detailsJob);
        textView8=findViewById(R.id.detailsLevel);

        name = getIntent().getStringExtra("userName");
        birthday = getIntent().getStringExtra("birthday");
        phone = getIntent().getStringExtra("phone");
        employed = getIntent().getStringExtra("employed");
        skills = getIntent().getStringExtra("skills");
        gender = getIntent().getStringExtra("gender");
        residence = getIntent().getStringExtra("residence");
        level=getIntent().getStringExtra("level");

        textView1.setText(name);
        textView2.setText(gender);
        textView3.setText(phone);
        textView4.setText(residence);
        textView5.setText(skills);
        textView6.setText(birthday);
        textView7.setText(employed);
        textView8.setText(level);
    }
}