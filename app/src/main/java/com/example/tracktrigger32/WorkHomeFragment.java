package com.example.tracktrigger32;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WorkHomeFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore firebaseFirestore;
    String userID;
    Boolean exists = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v3 =  inflater.inflate(R.layout.fragment_work_home, container, false);
        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Work").document(userID);
        Map<String, Object> workUser = new HashMap<>();
        workUser.put("exists", exists);
        documentReference.set(workUser);

     return v3;
    }
}