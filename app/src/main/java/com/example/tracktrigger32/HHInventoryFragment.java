package com.example.tracktrigger32;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;

public class HHInventoryFragment extends Fragment {

    ListView lvInventory;
    ArrayList<productHHInv> list;
    Button btnAdd,btnSub;
    FloatingActionButton ibAdd;
    final int EDITACTIVITY=9;
    final int ADDACTIVITY=7;
    static int k = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference documentReference = db.document("Households/"+HouseholdActivity.hhID);
    CollectionReference cr = db.collection("Households/"+HouseholdActivity.hhID+"/Products");

    public void addItem(String Name, String Description, String Category, String Id, int Quantity, int pos){
        productHHInv product = new productHHInv(Name,Id,Description,Category,Quantity, pos);
        addProduct(product);
        list.add(product);

        productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list);
        lvInventory.setAdapter(adapter);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if  (requestCode==EDITACTIVITY){
            if (resultCode==RESULT_OK){
                int pos=data.getIntExtra("Position",0);
                list.get(pos).setName(data.getStringExtra("upName"));
                list.get(pos).setDescription(data.getStringExtra("upDescription"));
                list.get(pos).setCategory(data.getStringExtra("upCategory"));
                list.get(pos).setId(data.getStringExtra("upId"));
                list.get(pos).setQuantity(data.getIntExtra("upQuantity",0));

                cr.whereEqualTo("pos", pos).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int i =0;
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    productHHInv product = documentSnapshot.toObject(productHHInv.class);
                                    db.document("Households/"+HouseholdActivity.hhID+"/Products/"+documentSnapshot.getId()).delete();
                                    addProduct(product);
                                }
                            }
                        });



                productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list);
                lvInventory.setAdapter(adapter);
            }else if (resultCode==RESULT_FIRST_USER){
                int pos=data.getIntExtra("Position",0);


                cr.whereEqualTo("pos", pos).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                int i =0;
                                for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                                {
                                    productHHInv product = documentSnapshot.toObject(productHHInv.class);
                                    db.document("Households/"+HouseholdActivity.hhID+"/Products/"+documentSnapshot.getId()).delete();
                                }
                            }
                        });
                list.remove(pos);

                productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list);
                lvInventory.setAdapter(adapter);

            }
        }else if (requestCode==ADDACTIVITY){

            if (resultCode==RESULT_OK){
                addItem(data.getStringExtra("newName"),data.getStringExtra("newDescription"),
                        data.getStringExtra("newCategory"),data.getStringExtra("newId"),data.getIntExtra("newQuantity",1), k);
            }
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v2 = inflater.inflate(R.layout.fragment_hh_inventory,container,false);




        ibAdd=v2.findViewById(R.id.ibAdd);


        lvInventory=(ListView) v2.findViewById(R.id.lvInventory);

        list = new ArrayList<productHHInv>();



        cr.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        int i =0;
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots)
                        {
                            productHHInv product = documentSnapshot.toObject(productHHInv.class);
                            list.add(product);
                            productAdapterHHInv adapter = new productAdapterHHInv(getContext(),list); //caution
                            lvInventory.setAdapter(adapter);
                        }
                    }
                });





        lvInventory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),com.example.tracktrigger32.editActivityHHInv.class);
                intent.putExtra("Name",list.get(position).getName());
                intent.putExtra("Description",list.get(position).getDescription());
                intent.putExtra("Category",list.get(position).getCategory());
                intent.putExtra("Quantity",list.get(position).getQuantity());
                intent.putExtra("Id",list.get(position).getId());
                intent.putExtra("Position",position);
                startActivityForResult(intent,EDITACTIVITY);
            }
        });


        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),com.example.tracktrigger32.addActivityHHInv.class);
                startActivityForResult(intent,ADDACTIVITY);
            }
        });

        createNotificationChannel();
        sendNotif();

        return v2;
    }

    /**-------------------------------------------------Firestore----------------------------------------------
     *
     */

    public void addProduct(productHHInv prod)
    {
        k++;
        documentReference.collection("Products")
                .add(prod)
        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getContext(), "Product added.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendNotif() {
        Toast.makeText(getActivity(), "notif2", Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 57);
        calendar.set(Calendar.SECOND, 30);
        Intent intent = new Intent(getActivity(), HHReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 500, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            CharSequence name = "UserReminderChannel";
            String description = "Channel for TrackTrigger Users";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyUser", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }

}
