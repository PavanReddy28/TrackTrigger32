package com.example.tracktrigger32;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvName;
    private ImageView pic;
    private Uri profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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

/*
        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()!=null)
        {
            profilePic = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
            pic.setImageURI(profilePic);
        }
        else {
            pic = findViewById(R.id.pic);
        }*/

        tvName = (TextView) findViewById(R.id.etName);
        tvName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString().trim());

    }

    public static void redirectActivity(Activity activity, Class aClass) {

        //Initialize intent
        Intent intent = new Intent(activity, aClass);

        //Set flag
        intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));

        //Start activity
        activity.startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ClickPic(View view) {

    }

    public void ClickEditProfile(View view) {
        redirectActivity(this, EditProfile.class);
    }

    public void ClickLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        redirectActivity(this, MainActivity.class);
        finish();
    }

    public void CLickReport(View view) {

    }

    public void ClickHelp(View view) {

    }

    public void ClickSystem(View view) {

    }


}