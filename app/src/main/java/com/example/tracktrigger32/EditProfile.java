package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {

    private TextView tvName;
    private ImageView pic;
    private Uri profilePic;
    private String name;
    private EditText etEditName, etTelNum, etMailID;
    private Button btnUpdate;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("Users");

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

        name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString().trim();
        tvName = (TextView) findViewById(R.id.etName);
        etEditName = findViewById(R.id.etEditName);
        etTelNum = findViewById(R.id.etTelNum);
        etMailID = findViewById(R.id.etMailID);
        btnUpdate = findViewById(R.id.btnUpdate);
        loadUsers();
    }

    /**-------------------------------------------------------Firestore--------------------------------------------------------------------
     *
     */

    public void updateUser()
    {

    }

    public void loadUsers()
    {
        usersRef.whereEqualTo("name", name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            User user = documentSnapshot.toObject(User.class);

                            etEditName.setText(user.getName());
                            etTelNum.setText(user.getTelNum());
                            etMailID.setText(user.getMailID());

                        }
                    }
                });
    }
}