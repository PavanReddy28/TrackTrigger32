package com.example.tracktrigger32;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AddSchedulesActivity extends AppCompatActivity {

    //TextView textView;
    Button select,add;

    //EditText message;
    //Calendar newCalender;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedules);


        final TextView textView = findViewById(R.id.date);
        select = findViewById(R.id.selectDate);
        add = findViewById(R.id.addButton);
        final EditText etTitle = findViewById(R.id.etTitle);
        final EditText message = findViewById(R.id.message);
        final Calendar newCalender = Calendar.getInstance();

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(AddSchedulesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {

                        final Calendar newDate = Calendar.getInstance();
                        Calendar newTime = Calendar.getInstance();
                        TimePickerDialog time = new TimePickerDialog(AddSchedulesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                newDate.set(year,month,dayOfMonth,hourOfDay,minute,0);
                                Calendar tem = Calendar.getInstance();
                                //Log.w("TIME",System.currentTimeMillis()+"");
                                if(newDate.getTimeInMillis()-tem.getTimeInMillis()>0)
                                    textView.setText(newDate.getTime().toString());
                                else
                                    Toast.makeText(AddSchedulesActivity.this,"Invalid time",Toast.LENGTH_SHORT).show();

                            }
                        },newTime.get(Calendar.HOUR_OF_DAY),newTime.get(Calendar.MINUTE),true);
                        time.show();

                    }
                },newCalender.get(Calendar.YEAR),newCalender.get(Calendar.MONTH),newCalender.get(Calendar.DAY_OF_MONTH));

                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //RoomDAO roomDAO = appDatabase.getRoomDAO();
                //Reminder reminders = new Reminder();
                //reminders.setMessage(message.getText().toString().trim());
                //Date remind = new Date(textView.getText().toString().trim());
                //reminders.setRemindDate(remind);
                //roomDAO.Insert(reminders);
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                String userID = fAuth.getCurrentUser().getUid();
                CollectionReference collectionReference = FirebaseFirestore.getInstance()
                        .collection("Households").document(HouseholdActivity.hhID).collection("Household Reminders");
                collectionReference.add(new Reminder(etTitle.getText().toString().trim(), message.getText().toString().trim(), new Date(textView.getText().toString().trim())));

                /*Intent intentAdd = new Intent();
                intentAdd.putExtra("msg", message.getText().toString().trim());
                intentAdd.putExtra("dt", textView.getText().toString().trim());
                setResult(RESULT_OK, intentAdd);*/

                AddSchedulesActivity.this.finish();

                //List<Reminder> l = roomDAO.getAll();
                //reminders = l.get(l.size()-1);
                //Log.e("ID chahiye",reminders.getId()+"");

                /*Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                calendar.setTime(remind);
                calendar.set(Calendar.SECOND,0);
                Intent intent = new Intent(AddScheduleActivity.this,NotifierAlarm.class);
                intent.putExtra("Message",reminders.getMessage());
                intent.putExtra("RemindDate",reminders.getRemindDate().toString());
                intent.putExtra("id",reminders.getId());
                PendingIntent intent1 = PendingIntent.getBroadcast(AddScheduleActivity.this,reminders.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),intent1);

                Toast.makeText(AddScheduleActivity.this,"Inserted Successfully",Toast.LENGTH_SHORT).show();
                setItemsInRecyclerView();*/
                //AppDatabase.destroyInstance();


            }
        });

    }

    public void GoBack(View view){
        AddSchedulesActivity.this.finish();
    }
}