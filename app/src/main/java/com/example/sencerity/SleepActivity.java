package com.example.sencerity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class SleepActivity extends AppCompatActivity {


    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    FirebaseUser currentUser;
    String userId;
    ArrayList<SleepDataModel> sleepArrayList;
    RecyclerViewAdapter adapter;
    Date timestampToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        sleepArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadDataFromFirebase();
    }

    private void loadDataFromFirebase() {
        if (sleepArrayList.size() > 0)
            sleepArrayList.clear();

        db.collection("users").document(userId).collection("sleep")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()){

                            timestampToDate = querySnapshot.getTimestamp("dateTime").toDate();

                          SleepDataModel sleepDataM = new SleepDataModel(timestampToDate,querySnapshot.getString("sensor"),
                                   querySnapshot.getString("patientId"));
                            sleepArrayList.add(sleepDataM);
                        }
                        adapter = new RecyclerViewAdapter(SleepActivity.this, sleepArrayList);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SleepActivity.this,"Problem ---1---",Toast.LENGTH_SHORT).show();
                Log.w("---1---",e.getMessage());
            }
        });
    }

    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}