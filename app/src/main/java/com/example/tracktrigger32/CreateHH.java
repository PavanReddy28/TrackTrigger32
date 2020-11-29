package com.example.tracktrigger32;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import static com.example.tracktrigger32.HouseholdActivity.redirectActivity;

public class CreateHH extends AppCompatActivity {

    ImageView hhPic;
    EditText etHHName, etPassword;
    Button btnCreate;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference hhRef = db.collection("Households");
    Household hh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_h_h);


        etHHName = findViewById(R.id.etHHName);
        etPassword = findViewById(R.id.etPassword);
        btnCreate = findViewById(R.id.btnCreate);

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

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hhname = etHHName.getText().toString().trim();
                String hhpwd = etPassword.getText().toString().trim();
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString().trim();


                HouseholdActivity.bool = true;
                Household hh = new Household(hhname, hhpwd, userID, true);
                addHH(hh);
                /*hhRef.whereEqualTo("hhName", hhname)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    hh1 = documentSnapshot.toObject(Household.class);


                                }
                            }
                        });*/
                redirectActivity(CreateHH.this, HouseholdActivity.class);
                finish();



               /* hhRef.get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    Household hh1 = documentSnapshot.toObject(Household.class);
                                    if(!hhname.equals("")) {
                                        if (hh1.getHhName().equals(hhname)) {
                                            Toast.makeText(CreateHH.this, "This Household already exists.", Toast.LENGTH_SHORT).show();
                                            etHHName.setText("");
                                            etPassword.setText("");
                                        }
                                        else
                                        {
                                            HouseholdActivity.loggedin = true;
                                            Household hh = new Household(hhname, hhpwd, userID, true);
                                            addHH(hh);
                                            redirectActivity(CreateHH.this, HouseholdActivity.class);
                                            finish();
                                            break;
                                        }
                                    }
                                }
                            }
                        });*/
            }
        });


    }

    public void addHH(Household hh)
    {
        Household hhFire = hh;

        FirebaseFirestore.getInstance()
                .collection("Households")
                .add(hhFire)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateHH.this, "Household Created", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}