package com.example.tracktrigger32;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class ReminderAdapterWork extends FirestoreRecyclerAdapter<ReminderWork, ReminderAdapterWork.MyViewHolder> {

    //private List<Reminder> allReminders;  //arraylist

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView message,time;
        TextView etTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etTitle = itemView.findViewById(R.id.textView);
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

    public void deleteReminder(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i, @NonNull ReminderWork model) {

        //Reminder reminders = allReminders.get(i); // myViewHolder.itemView.settag(allreminders.get(i))
        if(model.getTitle()!=null) {
            myViewHolder.etTitle.setText(model.getTitle());
        }
        else
        {
            myViewHolder.etTitle.setText("Title");
        }
        if(!model.getMessage().equals(""))
            myViewHolder.message.setText(model.getMessage());
        else
            myViewHolder.message.setHint("No Message");
        myViewHolder.time.setText(model.getRemindDate().toString());

    }

  /*  @NonNull
    @Override
    public ReminderWork getItem(int position) {
        return getSnapshots().getSnapshot(position).getReference().getId();
    }*/
    /*@Override
    public int getItemCount() {
        return allReminders.size();
    }*/

  /*  public DocumentSnapshot getReminder(int position){
        return getSnapshots().getSnapshot(position);//.getData();//.get("remindDate").toString();
    }*/




}

