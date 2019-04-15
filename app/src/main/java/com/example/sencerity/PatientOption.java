package com.example.sencerity;



import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class PatientOption extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseUser currentUser;
    String userId;
    ArrayList<String> patientArray;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    private TextView textview;
    public static final String TAG = "data retrieval";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_option);

        textview = findViewById(R.id.TestingText);
        spinner = new Spinner(this);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        else{
            userId="kM2gyYrk5MeLlLFKoZdNOViGwJI2";}
        loadDataFromFirebase();
       // addDataToSpinner();
    }

    private void loadDataFromFirebase() {
        db.collection("users").document(userId).collection("patient")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                             for (DocumentSnapshot querySnapshot : task.getResult()) {
                                  patientArray.add(querySnapshot.getString("patientId"));
                                  textview.append("data" + querySnapshot.getString("patientId"));

                            }
                        }
                        else{
                            Log.d(TAG,"ERROR IN DATABASE CALL",task.getException());
                        }

                    }
                });
    }

    private void addDataToSpinner(){

        for(int i =0; i <2;i++){
            Spinner s = findViewById(R.id.spinnerItem);
            adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_item, patientArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s.setAdapter(adapter);
        }
    }
}