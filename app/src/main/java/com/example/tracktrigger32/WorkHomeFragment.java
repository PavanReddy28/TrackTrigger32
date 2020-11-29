package com.example.tracktrigger32;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class WorkHomeFragment extends Fragment {

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    Boolean exists = true;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    //private DocumentReference documentReference = db.document("Households/"+HouseholdActivity.hhID);
    private CollectionReference cr1 = db.collection("Work/"+userID+"/Products");
    private CollectionReference cr2 = db.collection("Work/"+userID+"/Work Reminders");

    private WorkHomeAdapter adapter1;
    private ReminderAdapterWork adapter2;
    RecyclerView recyclerView1, recyclerView2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v3 =  inflater.inflate(R.layout.fragment_work_home, container, false);

        DocumentReference documentReference = firebaseFirestore.collection("Work").document(userID);
        Map<String, Object> workUser = new HashMap<>();
        workUser.put("exists", exists);
        documentReference.set(workUser);

        recyclerView1 = v3.findViewById(R.id.invRec1);
        //recyclerView1.setHasFixedSize(false);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        setUpRecycler1();

        recyclerView2 = v3.findViewById(R.id.remRec1);
        //recyclerView2.setHasFixedSize(false);
        recyclerView2.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.HORIZONTAL,false));
        setUpRecycler2();


     return v3;
    }

    private void setUpRecycler2() {
        Query query = cr2.orderBy("remindDate", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<ReminderWork> options1 = new FirestoreRecyclerOptions.Builder<ReminderWork>()
                .setQuery(query, ReminderWork.class)
                .build();


        adapter2 = new ReminderAdapterWork(options1);
        recyclerView2.setAdapter(adapter2);
    }

    public void setUpRecycler1(){
        Query query = cr1.orderBy("quantity", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<productHHInv> options = new FirestoreRecyclerOptions.Builder<productHHInv>()
                .setQuery(query, productHHInv.class)
                .build();


        adapter1 = new WorkHomeAdapter(options);
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