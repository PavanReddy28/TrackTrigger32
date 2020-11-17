package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ClickMenu(View view) {
        //open drawer
        openDrawer(drawerLayout);
    }

    public void ClickProfile(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickHouse(View view){
        redirectActivity(this, HouseholdActivity.class);
    }

    public void ClickWork(View view){
        redirectActivity(this, WorkActivity.class);
    }

    public void ClickNotes(View view){
        redirectActivity(this, NotesActivity.class);
    }

    public void ClickSettings(View view){
        redirectActivity(this, SettingsActivity.class);
    }

    //Navigation Functionality ends




}