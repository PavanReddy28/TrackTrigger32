package com.example.tracktrigger32;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static androidx.core.content.ContextCompat.getSystemService;

public class HHScheduleFragment extends Fragment {

    private FloatingActionButton add;
    //private Dialog dialog;
    //private AppDatabase appDatabase;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    RecyclerView recyclerView;
    private ReminderAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    //public ArrayList<Reminder> temp;     //can come from database, hardcode instead
    private TextView empty;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Households").document(HouseholdActivity.hhID).collection("Household Reminders");
    //private LinearLayoutManager linearLayoutManager;
    final int HHSCHEDULE = 3;
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
        View v1 =  inflater.inflate(R.layout.fragment_hh_schedule,container,false);
        //appDatabase = AppDatabase.geAppdatabase(MainPage.this);

        add = v1.findViewById(R.id.floatingButton);
        empty =  v1.findViewById(R.id.empty);

        //temp = new ArrayList<Reminder>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), com.example.tracktrigger32.AddSchedulesActivity.class);
                //startActivityForResult(intent3, HHSCHEDULE);
                startActivity(intent3);
            }

        });
        recyclerView = v1.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        setItemsInRecyclerView();

        return v1;
    }


  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Reminder reminders = new Reminder();
        //ArrayList<Reminder> temp = null;
        String dtStr;
        if (requestCode == HHSCHEDULE){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                reminders.setMessage(data.getStringExtra("msg"));
                dtStr = data.getStringExtra("dt");
                Date remind = new Date(dtStr);
                reminders.setRemindDate(remind);
                temp.add(reminders);
                //empty.setText(temp.get(0).getMessage());

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

    }
*/
  private void setItemsInRecyclerView() {

      //RoomDAO dao = appDatabase.getRoomDAO();
      //temp = dao.orderThetable();
      Query query = collectionReference.orderBy("remindDate", Query.Direction.ASCENDING);
      FirestoreRecyclerOptions<Reminder> options = new FirestoreRecyclerOptions.Builder<Reminder>()
              .setQuery(query, Reminder.class)
              .build();

      //if(temp.size()>0) {
      empty.setVisibility(View.INVISIBLE);
      recyclerView.setVisibility(View.VISIBLE);
      //}
      adapter = new ReminderAdapter(options);
      recyclerView.setAdapter(adapter);

      new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
              ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
          @Override
          public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
              return false;
          }

          @Override
          public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
              adapter.deleteReminder(viewHolder.getAbsoluteAdapterPosition());
          }
      }).attachToRecyclerView(recyclerView);
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