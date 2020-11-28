package com.example.tracktrigger32;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

import static com.google.android.material.internal.ContextUtils.getActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static int AUTH_REQUEST_CODE = 1001;
    private FirebaseAuth firebaseAuth;
    private List<AuthUI.IdpConfig> providers;
    DrawerLayout drawerLayout;
    String uId;
    User user1 = new User();
    private Dialog dialog;


    //private DocumentReference mDocRef = FirebaseFirestore.getInstance().collection("Users").document("User_1");
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Navigation Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Firebase Auth Initialization
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null)
        {
            showSignInOptions();
        }


        firebaseFirestore = FirebaseFirestore.getInstance();


    }



    /**-------------------------------------------------------Firestore--------------------------------------------------------------------
     *
     */


/*
    public void addUser()
    {
        String name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString().trim();
        String telNum = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().toString().trim();
        String mailID = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString().trim();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        User user = new User(name,telNum,mailID,userID);

        usersRef.add(user);
    }

    public void loadUsers()
    {
        usersRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        
                    }
                });
    }
*/



    /**-------------------------------------------------------------FirebaseUI-------------------------------------------------------------------
     *
     */

    private void showSignInOptions() {
        //login activity
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo)
                        .setTheme(R.style.MyTheme)
                        .build(),AUTH_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if (requestCode == AUTH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                // ...
                Toast.makeText(this, "Signed in as "+user.getDisplayName(), Toast.LENGTH_SHORT).show();


                /*uId = user.getUid();
                DocumentReference documentReference = firebaseFirestore.collection("Users").document(uId);
                if((user.getDisplayName()!= null) && (user.getPhoneNumber()!=null) && (user.getEmail()!=null) ) {
                    user1.setName(user.getDisplayName().toString());
                    user1.setTelNum(user.getPhoneNumber().toString());
                    user1.setMailID(user.getEmail().toString());
                    documentReference.set(user1);
                }
                else{
                    dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.firestore_update_popup);

                    final EditText etUName = dialog.findViewById(R.id.etUName);
                    final EditText etUTelNum = dialog.findViewById(R.id.etUTelNum);
                    final EditText etUMailID = dialog.findViewById(R.id.etUMailID);
                    Button btnSave = dialog.findViewById(R.id.btnSave);

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            user1.setName(etUName.getText().toString());
                            user1.setTelNum(etUTelNum.getText().toString());
                            user1.setMailID(etUMailID.getText().toString());
                            documentReference.set(user1);
                        }
                    });
                }*/



            } else {
                if(response != null)
                {
                    Toast.makeText(this,""+response.getError().getErrorCode(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }





    /**----------------------------------------------------------NavigationDrawer--------------------------------------------------------------
     *
     * @param activity
     * @param aClass
     */

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
        //open drawer
        openDrawer(drawerLayout);
    }

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
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {

                                showSignInOptions();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}