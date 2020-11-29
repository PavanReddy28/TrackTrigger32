package com.example.tracktrigger32;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.rpc.Code;

import java.util.HashMap;
import java.util.Map;

public class HHHomeFragment extends Fragment{

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference documentReference = db.document("Households/"+HouseholdActivity.hhID);
    private CollectionReference cr1 = db.collection("Households/"+HouseholdActivity.hhID+"/Products");
    private CollectionReference cr2 = db.collection("Households/"+HouseholdActivity.hhID+"/Household Reminders");

    private HHHomeAdapter adapter1;
    private ReminderAdapter adapter2;
    RecyclerView recyclerView1, recyclerView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_hh_home,container,false);

        recyclerView1 = v.findViewById(R.id.invRec);
        //recyclerView1.setHasFixedSize(false);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        setUpRecycler1();

        recyclerView2 = v.findViewById(R.id.remRec);
        //recyclerView2.setHasFixedSize(false);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false));
        setUpRecycler2();

        return v;

    }

    private void setUpRecycler2() {
        Query query = cr2.orderBy("remindDate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Reminder> options1 = new FirestoreRecyclerOptions.Builder<Reminder>()
                .setQuery(query, Reminder.class)
                .build();


        adapter2 = new ReminderAdapter(options1);
        recyclerView2.setAdapter(adapter2);
    }

    public void setUpRecycler1(){
        Query query = cr1.orderBy("quantity", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<productHHInv> options = new FirestoreRecyclerOptions.Builder<productHHInv>()
                .setQuery(query, productHHInv.class)
                .build();


        adapter1 = new HHHomeAdapter(options);
        recyclerView1.setAdapter(adapter1);

    }


    @Override
    public void onStart() {
        super.onStart();
        adapter1.startListening();
        adapter2.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter1.stopListening();
        adapter2.stopListening();
    }
}
