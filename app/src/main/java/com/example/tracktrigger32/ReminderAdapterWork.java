package com.example.tracktrigger32;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

public class ReminderAdapterWork extends FirestoreRecyclerAdapter<ReminderWork, ReminderAdapterWork.MyViewHolder> {

    //private List<Reminder> allReminders;  //arraylist

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView message,time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textView1);
            time = itemView.findViewById(R.id.textView2);
        }
    }



    public ReminderAdapterWork(@NonNull FirestoreRecyclerOptions<ReminderWork> options) {
        super(options);
    }

    /*public ReminderAdapterWork(List<Reminder> allReminders) {   //context
        this.allReminders = allReminders;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {  //adaptereminders.viewholder

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sched_item_work, viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull ReminderWork model) {

        //Reminder reminders = allReminders.get(i); // myViewHolder.itemView.settag(allreminders.get(i))
        if(!model.getMessage().equals(""))
            myViewHolder.message.setText(model.getMessage());
        else
            myViewHolder.message.setHint("No Message");
        myViewHolder.time.setText(model.getRemindDate().toString());

    }

    /*@Override
    public int getItemCount() {
        return allReminders.size();
    }*/




}

