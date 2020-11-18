package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class HouseholdActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    //Navigation Drawer Functionality

    public static void redirectActivity(Activity activity, Class aClass) {

        //Initialize intent
        Intent intent = new Intent(activity, aClass);

        //Set flag
        intent.setFlags((Intent.FLAG_ACTIVITY_NEW_TASK));

        //Start activity
        activity.startActivity(intent);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMenu(View view) {
        //open drawer
        openDrawer(drawerLayout);
    }

    public void ClickProfile(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        redirectActivity(this, MainActivity.class);
    }

    public void ClickHouse(View view){
        closeDrawer(drawerLayout);
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