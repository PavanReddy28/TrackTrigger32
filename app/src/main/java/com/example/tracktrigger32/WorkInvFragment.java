package com.example.tracktrigger32;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;


public class WorkInvFragment extends Fragment {

    //private static final String ALARM_SERVICE = "alarm";
    String defaultString="android.resource://com.example.tracktrigger32/mipmap/ic_launcher_foreground";
    Uri uriDefault = Uri.parse("android.resource://com.example.tracktrigger32/mipmap/ic_launcher_foreground");
    ListView lvInventory;
    public ArrayList<productHHInv> list;
    FloatingActionButton ibAdd;
    final int EDITACTIVITY = 9;
    final int ADDACTIVITY = 7;
    static int k = 0;
    private String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.document("Work/" + uID);
    private CollectionReference cr = db.collection("Work/" + uID + "/Products");

    public void addItem(String Name, String Description, String Category, String Id, int Quantity, String ur, int pos) {
        productHHInv product = new productHHInv(Name, Id, Description, Category, Quantity,ur, pos);
        addProduct(product);
        list.add(product);

        productAdapterHHInv adapter = new productAdapterHHInv(getContext(), list);
        lvInventory.setAdapter(adapter);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDITACTIVITY) {
            if (resultCode == RESULT_OK) {
                int pos = data.getIntExtra("Position", 0);
                list.get(pos).setName(data.getStringExtra("upName"));
                list.get(pos).setDescription(data.getStringExtra("upDescription"));
                list.get(pos).setCategory(data.getStringExtra("upCategory"));
                list.get(pos).setId(data.getStringExtra("upId"));
                list.get(pos).setQuantity(data.getIntExtra("upQuantity", 0));
                list.get(pos).setUri(data.getParcelableExtra("uriFinal").toString());

                cr.whereEqualTo("pos", pos).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int i = 0;
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    productHHInv product = documentSnapshot.toObject(productHHInv.class);
                                    db.document("Work/" + uID + "/Products/" + documentSnapshot.getId()).delete();
                                    addProduct(product);
                                }
                            }
                        });

                productAdapterHHInv adapter = new productAdapterHHInv(getContext(), list);
                lvInventory.setAdapter(adapter);
            } else if (resultCode == RESULT_FIRST_USER) {
                int pos = data.getIntExtra("Position", 0);
                list.remove(pos);

                cr.whereEqualTo("pos", pos).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int i = 0;
                                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                    productHHInv product = documentSnapshot.toObject(productHHInv.class);
                                    db.document("Work/" + uID + "/Products/" + documentSnapshot.getId()).delete();
                                }
                            }
                        });

                productAdapterHHInv adapter = new productAdapterHHInv(getContext(), list);
                lvInventory.setAdapter(adapter);

            }
        } else if (requestCode == ADDACTIVITY) {

            if (resultCode == RESULT_OK) {
                addItem(data.getStringExtra("newName"), data.getStringExtra("newDescription"),
                        data.getStringExtra("newCategory"), data.getStringExtra("newId"), data.getIntExtra("newQuantity", 1), defaultString,k);
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v3 = inflater.inflate(R.layout.fragment_work_inv, container, false);


        ibAdd = v3.findViewById(R.id.ibAdd);


        lvInventory = (ListView) v3.findViewById(R.id.lvInventory);

        list = new ArrayList<productHHInv>();

        cr.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            productHHInv product = documentSnapshot.toObject(productHHInv.class);
                            list.add(product);
                            productAdapterHHInv adapter = new productAdapterHHInv(getContext(), list); //caution
                            lvInventory.setAdapter(adapter);
                        }
                    }
                });


        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), com.example.tracktrigger32.editActivityHHInv.class);
                intent.putExtra("Name", list.get(position).getName());
                intent.putExtra("Description", list.get(position).getDescription());
                intent.putExtra("Category", list.get(position).getCategory());
                intent.putExtra("Quantity", list.get(position).getQuantity());
                intent.putExtra("Id", list.get(position).getId());
                intent.putExtra("Position", position);
                intent.putExtra("Uri",list.get(position).getUri());
                startActivityForResult(intent, EDITACTIVITY);
            }
        });


        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.tracktrigger32.addActivityHHInv.class);
                startActivityForResult(intent, ADDACTIVITY);
            }
        });
        createNotificationChannel();
        sendNotif();


        return v3;
    }

    private void addProduct(productHHInv prod) {
        k++;
        db.collection("Work/" + uID + "/Products")
                .add(prod)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Product added.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendNotif() {
        //Toast.makeText(getActivity(), "notif", Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 48);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,1);

        Intent intent = new Intent(getActivity(),WorkReminderBroadcast.class);
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),500,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        long systemTime = System.currentTimeMillis();
        if(systemTime <= calendar.getTimeInMillis()) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }else{
            //hope to hell this works
        }
    }

    private void createNotificationChannel(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notif);
            String description = getString(R.string.sendNotif);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("sendUser", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}