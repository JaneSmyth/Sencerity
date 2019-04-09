package com.example.sencerity;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SleepActivity extends AppCompatActivity {


    private static final String TAG = "DocSnippets";
    private static final String DATETIME="Date and Time";
    private static final String SENSOR="Sensor";
    private static final String PRESSURE="Pressure On Sensor";
    private Timestamp dateTime;
    private String sensor;
    private Boolean sensorPressure;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String userId;
    private TextView displayData;
    private static final String KEY = "dateTime";
    CollectionReference sleepCollection;
    DocumentReference userDocument;
    private CustomAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        displayData = findViewById(R.id.dataTextView);
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        sleepCollection = db.collection("users").document(userId).collection("sleep");

       // getDataFromSleepDocs();
        getDataForRecyclerView();

    }

    private void getDataForRecyclerView() {
        Query query = sleepCollection;

        FirestoreRecyclerOptions<DataModel> options = new FirestoreRecyclerOptions.Builder<DataModel>()
                .setQuery(query, DataModel.class)
                .build();
        adapter = new CustomAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
/*
    public void getDataFromSleepDocs(){

          db.collection("users").document(userId).collection("sleep")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                displayData.append(DATETIME + " : " +document.get("dateTime").toString());
                                displayData.append("\n");
                                displayData.append(SENSOR + " : "+ document.get("sensor").toString());
                                displayData.append("\n");
                                displayData.append(PRESSURE + " : " +document.get("sensorPressure").toString());
                                displayData.append("\n\n");
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                DataModel data = document.toObject(DataModel.class);
                        }
                        } else{
                            displayData.append("Error getting documents");}
                    }
                });
    }
*/
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}