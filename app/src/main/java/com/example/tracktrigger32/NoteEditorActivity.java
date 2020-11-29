package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        EditText editText = (EditText)  findViewById(R.id.editText) ;
        Intent  intent = getIntent() ;
          noteId = intent.getIntExtra("noteId" ,-1) ;
        if(noteId !=-1 )
        {
            editText.setText(NotesActivity.notes.get(noteId)) ;
        }
        else
        {
            NotesActivity.notes.add("") ;
            noteId = NotesActivity.notes.size() - 1 ;
            NotesActivity.arrayAdapter.notifyDataSetChanged();

        }


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                NotesActivity.notes.set(noteId,String.valueOf(charSequence)) ;
                NotesActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.tracktrigger32", Context.MODE_PRIVATE);

                HashSet<String> set = new HashSet(NotesActivity.notes);

                sharedPreferences.edit().putStringSet("notes", set).apply();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void GoBack(View view){
        NoteEditorActivity.this.finish();
    }

    public void addNote(String text){
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Note note = new Note(text, userID);

        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                    }
                });
    }


}