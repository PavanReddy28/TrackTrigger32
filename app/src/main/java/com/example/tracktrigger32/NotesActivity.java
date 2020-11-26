package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    static ArrayList<String> notes = new ArrayList<>() ;
    static ArrayAdapter arrayAdapter ;
private FloatingActionButton add ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        add = findViewById(R.id.ibAdd) ;
        drawerLayout = findViewById(R.id.drawer_layout);
        ListView listView = (ListView) findViewById(R.id.listView) ;
        notes.add("Example note") ;
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

                            }
                        }
                        )
                 .setNegativeButton("No" , null)
                 .show() ;
                return true;
            }
        });
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
        redirectActivity(this,MainActivity.class);
    }

    public void ClickHouse(View view){
        redirectActivity(this, HouseholdActivity.class);
    }

    public void ClickWork(View view){
        redirectActivity(this, WorkActivity.class);
    }

    public void ClickNotes(View view){
        closeDrawer(drawerLayout);
    }

    public void ClickSettings(View view){
        redirectActivity(this, SettingsActivity.class);
    }


    //Navigation Functionality ends

}
