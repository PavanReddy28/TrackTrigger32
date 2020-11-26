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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    public ArrayList<Reminder> temp;     //can come from database, hardcode instead
    private TextView empty;
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
        recyclerView = v1.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //setItemsInRecyclerView();

        temp = new ArrayList<Reminder>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getActivity(), com.example.tracktrigger32.AddSchedulesActivity.class);
                startActivityForResult(intent3, HHSCHEDULE);
                //startActivity(intent3);
            }

        });
        //setItemsInRecyclerView();
        return v1;
    }


    @Override
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
                empty.setText(temp.get(0).getMessage());

                /*Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                calendar.setTime(remind);
                calendar.set(Calendar.SECOND,0);
                Intent intent = new Intent(getActivity(),NotifierAlarm.class);
                intent.putExtra("Message",reminders.getMessage());
                intent.putExtra("RemindDate",reminders.getRemindDate().toString());
                intent.putExtra("id",reminders.getId());
                PendingIntent intent1 = PendingIntent.getBroadcast(getActivity(),reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(getActivity().ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);*/

                //Toast.makeText(getActivity(),"Inserted Successfully",Toast.LENGTH_SHORT).show();
                setItemsInRecyclerView();
            }
            if(resultCode == Activity.RESULT_CANCELED){
            }
        }

    }

    public void setItemsInRecyclerView(){

        //RoomDAO dao = appDatabase.getRoomDAO();
        //temp = dao.orderThetable();

        if(temp.size()>0) {
            empty.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        adapter = new ReminderAdapter(temp);
        recyclerView.setAdapter(adapter);

    }
    /* public void addReminder(){    // should be in other activity....
     *//*dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.activity_addschedule);
        final TextView textView = dialog.findViewById(R.id.date);
        Button select,add;
        select = dialog.findViewById(R.id.selectDate);
        add = dialog.findViewById(R.id.addButton);
        final EditText message = dialog.findViewById(R.id.message);
        final Calendar newCalender = Calendar.getInstance();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                                Calendar tem = Calendar.getInstance();
                                Log.w("TIME",System.currentTimeMillis()+"");
                                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0)
                                    textView.setText(newDate.getTime().toString());
                                else
                                    Toast.makeText(getActivity(),"Invalid time",Toast.LENGTH_SHORT).show();
                            }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                        time.show();
                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
            }
        });
*//*
     *//*add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RoomDAO roomDAO = appDatabase.getRoomDAO();
                Reminder reminders = new Reminder();
                reminders.setMessage(message.getText().toString().trim());
                Date remind = new Date(textView.getText().toString().trim());
                reminders.setRemindDate(remind);
                roomDAO.Insert(reminders);
                List<Reminder> l = roomDAO.getAll();
                reminders = l.get(l.size()-1);
                Log.e("ID chahiye",reminders.getId()+"");
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                calendar.setTime(remind);
                calendar.set(Calendar.SECOND,0);
                Intent intent = new Intent(MainPage.this,NotifierAlarm.class);
                intent.putExtra("Message",reminders.getMessage());
                intent.putExtra("RemindDate",reminders.getRemindDate().toString());
                intent.putExtra("id",reminders.getId());
                PendingIntent intent1 = PendingIntent.getBroadcast(MainPage.this,reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);
                Toast.makeText(getActivity(),"Inserted Successfully",Toast.LENGTH_SHORT).show();
                setItemsInRecyclerView();
                AppDatabase.destroyInstance();
                dialog.dismiss();
            }
        });*//*
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }*/


}