package com.example.sencerity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import adapter.PatientAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import models.PatientInfo;

public class PatientSelectActivity extends AppCompatActivity {

    String userId;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    PatientAdapter adapter;
    List<PatientInfo> patientList = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_select);
        mFirestore=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        userId=mAuth.getUid();
        setUpRecyclerView();
        loadDataFromFirebase();
    }




    private void loadDataFromFirebase() {
        mFirestore.collection("users").document(userId).collection("patient")
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                PatientInfo patientInfo = new PatientInfo(
                                        doc.getString("name"),
                                        doc.getString("patientId"),
                                        doc.getString("tokenId")
                                );
                                patientList.add(patientInfo);
                            }
                            adapter = new PatientAdapter(PatientSelectActivity.this, patientList);
                            recyclerView.setAdapter(adapter);
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("patient name error: ", e.getMessage());
                    }
                });
    }


    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.patientRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
