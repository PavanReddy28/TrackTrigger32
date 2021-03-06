package com.example.tracktrigger32;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashSet;

public class NotesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    static ArrayList<String> notes = new ArrayList<>() ;
    static ArrayAdapter arrayAdapter ;
    private FloatingActionButton add ;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("Note");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        add = findViewById(R.id.ibAdd) ;
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.drawer_notes);


        ListView listView = (ListView) findViewById(R.id.listView) ;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.tracktrigger32", Context.MODE_PRIVATE);

        HashSet<String> set = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if(set==null) {
            notes.add("Example note");
        }
        else
        {
            notes = new ArrayList(set);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class) ;
                startActivity(intent) ;

            }
        }) ;

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, notes) ;
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(),NoteEditorActivity.class) ;
                intent.putExtra("noteId" , i ) ;
                startActivity(intent) ;


            }
        } );

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int itemToDelete = i ;

                new AlertDialog.Builder(NotesActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to delete this note ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notes.remove(itemToDelete) ;
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.tracktrigger32", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet(NotesActivity.notes);

                                sharedPreferences.edit().putStringSet("notes", set).apply();

                            }
                        }
                        )
                 .setNegativeButton("No" , null)
                 .show() ;
                return true;
            }
        });
    }

    public void addNote(String text){

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.drawer_home:
                redirectActivity(this, MainActivity.class);
                break;
            case R.id.drawer_house:
                redirectActivity(this, HouseholdActivity.class);
                break;
            case R.id.drawer_work:
                redirectActivity(this, WorkActivity.class);
                break;
            case R.id.drawer_notes:
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
