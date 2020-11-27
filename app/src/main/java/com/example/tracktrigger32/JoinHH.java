package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class JoinHH extends AppCompatActivity {

    ImageView hhPic;
    EditText etHHName, etPassword;
    Button btnJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_h_h);

        etHHName = findViewById(R.id.etHHName);
        etPassword = findViewById(R.id.etPassword);
        btnJoin = findViewById(R.id.btnJoin);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hhname = etHHName.getText().toString().trim();
                String hhpwd = etPassword.getText().toString().trim();
            }
        });

        HouseholdActivity.loggedin = true;
    }


}