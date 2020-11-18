package com.example.tracktrigger32;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HouseholdActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household);
        drawerLayout = findViewById(R.id.drawer_layout);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HHHomeFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HHHomeFragment();
                            break;
                        case R.id.nav_schedule:
                            selectedFragment = new HHScheduleFragment();
                            break;
                        case R.id.nav_inventory:
                            selectedFragment = new HHInventoryFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                return true;
                }
            };


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