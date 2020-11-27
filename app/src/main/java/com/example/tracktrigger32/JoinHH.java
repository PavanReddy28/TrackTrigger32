package com.example.tracktrigger32;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.tracktrigger32.HouseholdActivity.redirectActivity;

public class JoinHH extends AppCompatActivity {

    ImageView hhPic;
    EditText etHHName, etPassword;
    Button btnJoin;
    Household hh1;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hhRef = db.collection("Households");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_h_h);

        etHHName = findViewById(R.id.etHHName);
        etPassword = findViewById(R.id.etPassword);
        btnJoin = findViewById(R.id.btnJoin);

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

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hhname = etHHName.getText().toString().trim();
                String hhpwd = etPassword.getText().toString().trim();
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim();

                hhRef.whereEqualTo("hhName", hhname)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    hh1 = documentSnapshot.toObject(Household.class);

                                        if(hh1.getHhpwd().equals(hhpwd)){
                                                HouseholdActivity.loggedin = true;
                                                hh1.setMem1ID(userID);
                                                db.collection("Households").document(documentSnapshot.getId()).update("memID", userID);
                                                redirectActivity(JoinHH.this, HouseholdActivity.class);
                                            Toast.makeText(JoinHH.this, "Joined"+hhname+" Household", Toast.LENGTH_SHORT).show();
                                                finish();
                                                break;
                                        }
                                        else {
                                                Toast.makeText(JoinHH.this, "Please enter the correct Username or password.", Toast.LENGTH_SHORT).show();
                                        }


                                   
                                }
                            }
                        });


            }
        });



    }

}