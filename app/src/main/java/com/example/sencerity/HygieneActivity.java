package com.example.sencerity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class HygieneActivity extends AppCompatActivity {


    private static final String TAG = "docData";
    private static final String DATETIME="Date and Time";
    private static final String SENSOR="Sensor";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String userId;
    private TextView displayData;
    CollectionReference userCollection;
    DocumentReference userDocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hygiene);
        displayData = findViewById(R.id.dataTextView);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        userCollection = db.collection("users");
        userDocument = userCollection.document(userId);
        getDataFromHygieneDocs();


    }


    public void getDataFromHygieneDocs(){

        db.collection("users").document(userId).collection("hygiene")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                displayData.append(DATETIME + " : " +document.get("dateTime").toString());
                                displayData.append("\n");
                                displayData.append(SENSOR + " : "+ document.get("sensor").toString());
                                displayData.append("\n\n");

                                Log.d(TAG, document.getId() + " => " + document.getData());                            }
                        } else{
                            displayData.append("Error getting documents");}
                    }
                });
    }


}
