package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    private TextView tvName;
    private ImageView pic;
    private Uri profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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


        /*profilePic = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        if(profilePic!=null)
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
}