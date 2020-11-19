package com.example.tracktrigger32;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class WorkActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.drawer_work);
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

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
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
        openDrawer(drawerLayout);
    }

    /*



    public void ClickProfile(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        redirectActivity(this, MainActivity.class);
    }

    public void ClickHouse(View view){
        redirectActivity(this, HouseholdActivity.class);
    }

    public void ClickWork(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickNotes(View view){
        redirectActivity(this, NotesActivity.class);
    }

    public void ClickSettings(View view){
        redirectActivity(this, SettingsActivity.class);
    }

    public void ClickLogout(View view){
        FirebaseAuth.getInstance().signOut();
        redirectActivity(this, MainActivity.class);
        finish();
    }
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.drawer_home:
                break;
            case R.id.drawer_house:
                redirectActivity(this, HouseholdActivity.class);
                break;
            case R.id.drawer_work:
                redirectActivity(this, WorkActivity.class);
                break;
            case R.id.drawer_notes:
                redirectActivity(this, NotesActivity.class);
                break;
            case R.id.drawer_settings:
                redirectActivity(this, SettingsActivity.class);
                break;
            case R.id.drawer_logout:
                FirebaseAuth.getInstance().signOut();
                redirectActivity(this, MainActivity.class);
                finish();
                break;
        }

        drawerLayout.closeDrawers();
        return true;
    }

    //Navigation Functionality ends

}