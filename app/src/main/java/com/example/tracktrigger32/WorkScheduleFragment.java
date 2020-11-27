package com.example.tracktrigger32;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Date;

public class WorkScheduleFragment extends Fragment {
    private FloatingActionButton add;
    //private Dialog dialog;
    //private AppDatabase appDatabase;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    RecyclerView recyclerView;
    private ReminderAdapterWork  adapter;
    RecyclerView.LayoutManager layoutManager;
    //public ArrayList<Reminder> temp;     //can come from database, hardcode instead
    private TextView empty;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Work").document(userID).collection("Work Reminders");
    //private LinearLayoutManager linearLayoutManager;
    final int WORKSCHEDULE = 3;
    //public Reminder reminders = new Reminder();

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_page);
        appDatabase = AppDatabase.geAppdatabase(MainPage.this);
        add = findViewById(R.id.floatingButton);
        empty = findViewById(R.id.empty);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainPage.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setItemsInRecyclerView();
    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v2 =  inflater.inflate(R.layout.fragment_work_schedule,container,false);
        //appDatabase = AppDatabase.geAppdatabase(MainPage.this);

        add = v2.findViewById(R.id.floatingButton);
        empty =  v2.findViewById(R.id.empty);

        //setItemsInRecyclerView();

        //temp = new ArrayList<Reminder>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), com.example.tracktrigger32.AddSchedWorkActivity.class);
                //startActivityForResult(intent3, WORKSCHEDULE);
                startActivity(intent3);
            }

        });
        recyclerView = v2.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        setItemsInRecyclerView();
        return v2;
    }


    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Reminder reminders = new Reminder();
        //ArrayList<Reminder> temp = null;
        String dtStr;
        if (requestCode == WORKSCHEDULE){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                reminders.setMessage(data.getStringExtra("msg"));
                dtStr = data.getStringExtra("dt");
                Date remind = new Date(dtStr);
                reminders.setRemindDate(remind);
                temp.add(reminders);
                empty.setText(temp.get(0).getMessage());

                *//*Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                calendar.setTime(remind);
                calendar.set(Calendar.SECOND,0);
                Intent intent = new Intent(getActivity(),NotifierAlarm.class);
                intent.putExtra("Message",reminders.getMessage());
                intent.putExtra("RemindDate",reminders.getRemindDate().toString());
                intent.putExtra("id",reminders.getId());
                PendingIntent intent1 = PendingIntent.getBroadcast(getActivity(),reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);*//*

                //Toast.makeText(getActivity(),"Inserted Successfully",Toast.LENGTH_SHORT).show();
                setItemsInRecyclerView();
            }
            if(resultCode == Activity.RESULT_CANCELED){
            }
        }

    }*/

    private void setItemsInRecyclerView(){

        //RoomDAO dao = appDatabase.getRoomDAO();
        //temp = dao.orderThetable();
        Query query = collectionReference.orderBy("remindDate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ReminderWork> options = new FirestoreRecyclerOptions.Builder<ReminderWork>()
                .setQuery(query, ReminderWork.class)
                .build();

        //if(temp.size()>0) {
            empty.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        //}
        adapter = new ReminderAdapterWork(options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}

