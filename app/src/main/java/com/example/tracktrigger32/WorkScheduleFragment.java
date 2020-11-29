package com.example.tracktrigger32;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/*import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;*/

public class WorkScheduleFragment extends Fragment {
    private FloatingActionButton add;
    //private Dialog dialog;
    //private AppDatabase appDatabase;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    RecyclerView recyclerView;
    private ReminderAdapterWork adapter;
    RecyclerView.LayoutManager layoutManager;
    //public ArrayList<Reminder> temp;     //can come from database, hardcode instead
    private TextView empty;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Work").document(userID).collection("Work Reminders");
    private DocumentReference docRef;
    //private LinearLayoutManager linearLayoutManager;
    final int WORKSCHEDULE = 3;
    Date rDate;
    String rMessage, rTitle;

    //String sEmail = "donotreply.tt32@gmail.com";
    //String sPassword = "trackTrigger32";
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
        View v2 = inflater.inflate(R.layout.fragment_work_schedule, container, false);
        //appDatabase = AppDatabase.geAppdatabase(MainPage.this);

        add = v2.findViewById(R.id.floatingButton);
        empty = v2.findViewById(R.id.empty);

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
        createNotificationChannel();
        //sendNotif();
        //remind();
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


    private void setItemsInRecyclerView() {

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.deleteReminder(viewHolder.getAbsoluteAdapterPosition()); //delete from recyclerview
            }
        }).attachToRecyclerView(recyclerView);

        collectionReference.orderBy("remindDate", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle error
                    //...
                    return;
                }
                List<ReminderWork> reminders = value.toObjects(ReminderWork.class);
                if(!reminders.isEmpty()) {
                    ReminderWork r = reminders.get(0);
                    rTitle = r.title;
                    rDate = r.remindDate;
                    rMessage = r.message;
                    String rDateStr = rDate.toString();
                    //Toast.makeText(getActivity(), rDateStr, Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();
                    //calendar.set(Calendar.YEAR, rDate.getYear());
                    //calendar.set(Calendar.MONTH, rDate.getMonth());
                    calendar.set(Calendar.DATE, rDate.getDate());
                    calendar.set(Calendar.HOUR_OF_DAY, rDate.getHours());
                    calendar.set(Calendar.MINUTE, rDate.getMinutes());
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 1);

                    Intent intent = new Intent(getActivity(), WorkScheduleBroadcast.class);
                    intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                    intent.putExtra("message", rMessage);
                    intent.putExtra("title", rTitle);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 300, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    long systemTime = System.currentTimeMillis();
                    if (systemTime <= calendar.getTimeInMillis()) {
                        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        //Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                        //adapter.deleteReminder(0);
                    }
                }
            }
        });

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


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "WorkSchReminderChannel";
            String description = "Channel for Work TrackTrigger Users";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel2 = new NotificationChannel("notify2User", name, importance);
            channel2.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel2);
        }

    }




    /*public void remind(){
        if(!fAuth.getCurrentUser().getEmail().isEmpty()) {
            DateFormat df = new SimpleDateFormat("EEE dd MM yyyy HH:mm");
            String currentTime = df.format(Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30")).getTime());
            //long cTime = System.currentTimeMillis();

            //Date currentTime = Calendar.getInstance().getTime();
            //String id;
            collectionReference.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Timestamp timestamp = (Timestamp) documentSnapshot.getData().get("remindDate");
                                //df.format(new Date(timestamp));
                                //String remTime = df.format(documentSnapshot.getDate("remindDate"));
                                // remTime = documentSnapshot.getData().get("remindDate");
                                Date date = timestamp.toDate();
                                String remTime = df.format(date);
                                if (currentTime == remTime){
                                    Toast.makeText(getActivity(), "It's time.", Toast.LENGTH_SHORT).show();
                                    Properties properties = new Properties();
                                    properties.put("mail.smtp.auth", "true");
                                    properties.put("mail.smtp.starttls.enable", "true");
                                    properties.put("mail.smtp.host", "smtp.gmail.com");
                                    properties.put("mail.smtp.port", "587");

                                    Session session = Session.getInstance(properties, new Authenticator() {
                                        @Override
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(sEmail, sPassword);
                                        }
                                    });


                                    try {
                                        Message message = new MimeMessage(session);
                                        message.setFrom(new InternetAddress(sEmail));
                                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(fAuth.getCurrentUser().getEmail()));
                                        message.setSubject("Reminder for TrackTrigger");
                                        message.setText(documentSnapshot.getData().get("message").toString());

                                        new SendMail().execute(message);

                                    } catch (MessagingException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                        }
                    });*/
//}
    }

    /*private class SendMail  extends AsyncTask<Message, String, String> {
        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                //return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }*/


//}


