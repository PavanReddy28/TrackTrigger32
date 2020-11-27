package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class FirestoreUpdate extends AppCompatActivity {

    EditText etUName,etUTelNum, etUMailID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firestore_update);

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

        etUName = findViewById(R.id.etUName);
        etUTelNum = findViewById(R.id.etUTelNum);
        etUMailID = findViewById(R.id.etUMailID);

        Intent intent = new Intent();
        intent.putExtra("Name", etUName.getText().toString().trim());
        intent.putExtra("Tel", etUTelNum.getText().toString().trim());
        intent.putExtra("Mail", etUMailID.getText().toString().trim());
        setResult(RESULT_OK, intent);
        FirestoreUpdate.this.finish();


    }


}