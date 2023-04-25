package com.example.communityportal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    String[] options = {"Select", "Resident", "Authority", "Third-party"};
    String selected;
    TextView textView;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinner = findViewById(R.id.loginSpinner);
        textView=findViewById(R.id.selectedText1);
        cardView=findViewById(R.id.Ok);

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        /**
         CardView resident = findViewById(R.id.cardView4);
         CardView authority = findViewById(R.id.cardView5);
         CardView thirdParty = findViewById(R.id.cardView6);
         btnLogin = findViewById(R.id.button2);

         resident.setOnClickListener(view -> {
         btnLogin.setVisibility(View.VISIBLE);
         selected=0;
         });
         authority.setOnClickListener(view -> {
         btnLogin.setVisibility(View.VISIBLE);
         selected=1;
         });

         thirdParty.setOnClickListener(view -> {
         btnLogin.setVisibility(View.VISIBLE);
         selected=2;
         });

         btnLogin.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        if (selected==0){
        startActivity(new Intent(Login.this,ResidentLogin.class));
        btnLogin.setVisibility(View.GONE);
        return;
        }
        else if (selected==1){
        startActivity(new Intent(Login.this,AuthorityLogin.class));
        btnLogin.setVisibility(View.GONE);
        return;

        }
        else if (selected==2){
        startActivity(new Intent(Login.this,ThirdPartyLogin.class));
        btnLogin.setVisibility(View.GONE);
        return;

        };

        }
        }); **/

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selected = spinner.getSelectedItem().toString();
        if (selected.equals("Select")) {
            Toast.makeText(this, "Select login mode!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selected.equals("Authority")) {
            textView.setText(selected);
            textView.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.VISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, AuthorityLogin.class);
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
                    Intent intent = new Intent(Login.this, ResidentLogin.class);
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
                    Intent intent = new Intent(Login.this, ThirdPartyLogin.class);
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
