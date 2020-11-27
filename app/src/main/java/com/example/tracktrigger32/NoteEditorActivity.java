package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class NoteEditorActivity extends AppCompatActivity {
    int noteId ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        EditText editText = (EditText)  findViewById(R.id.editText) ;
        EditText etTitle = (EditText) findViewById(R.id.etTitle);
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




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void GoBack(View view){
        NoteEditorActivity.this.finish();
    }


}