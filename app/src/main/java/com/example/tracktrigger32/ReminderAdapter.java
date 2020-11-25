package com.example.tracktrigger32;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.MyViewHolder>{

    private  ArrayList<Reminder> allReminders;  //arraylist
    private TextView message,time;

    public ReminderAdapter( ArrayList<Reminder> list) {   //context
        allReminders = list;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {  //adaptereminders.viewholder

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.schedule_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Reminder reminders = allReminders.get(i); // myViewHolder.itemView.settag(allreminders.get(i))
        if(!reminders.getMessage().equals(""))
            message.setText(reminders.getMessage());
        else
            message.setHint("No Message");
        time.setText(reminders.getRemindDate().toString());

    }

    @Override
    public int getItemCount() {
        return allReminders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
        }
    }

}