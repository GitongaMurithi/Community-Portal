package com.example.communityportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    String[] options = {"Select", "Resident", "Authority", "Third-party"};
    String selected;
    TextView textView;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/**
 CardView resident = findViewById(R.id.cardView1);
 CardView authority = findViewById(R.id.cardView2);
 CardView thirdParty = findViewById(R.id.cardView3); **/
        Button login = findViewById(R.id.button1);
        spinner = findViewById(R.id.register);
        textView = findViewById(R.id.selectedText);
        cardView = findViewById(R.id.ok);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /**
         resident.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ResidentRegistration.class);
        startActivity(intent);
        }
        });
         authority.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, AuthorityRegistration.class);
        startActivity(intent);
        }
        });
         thirdParty.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, ThirdPartyRegistration.class);
        startActivity(intent);
        }
        }); **/

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Login.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selected = spinner.getSelectedItem().toString();
        if (selected.equals("Select")) {
            Toast.makeText(this, "Select registration mode!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selected.equals("Authority")) {
            textView.setText(selected);
            textView.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, AuthorityRegistration.class);
                    startActivity(intent);
                    textView.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                }
            });
            return;
        }
        if (selected.equals("Resident")) {
            textView.setText(selected);
            textView.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ResidentRegistration.class);
                    startActivity(intent);
                    textView.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                }
            });

            return;
        }
        if (selected.equals("Third-party")) {
            textView.setText(selected);
            textView.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ThirdPartyRegistration.class);
                    startActivity(intent);
                    textView.setVisibility(View.GONE);
                    cardView.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}