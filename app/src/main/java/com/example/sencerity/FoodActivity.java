package com.example.sencerity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector;
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetectorOptions;

import java.io.IOException;
import java.util.List;

public class FoodActivity extends AppCompatActivity {


    private static final String TAG = "DocData";
    private static final String DATETIME="Date and Time";
    private static final String SENSOR="Sensor";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String userId;
    private TextView displayData;
    private FirebaseVisionBarcodeDetector detector;
    // private static final String KEY = "dateTime";
    CollectionReference userCollection;
    DocumentReference userDocument;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        displayData = findViewById(R.id.dataTextView);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        userCollection = db.collection("users");
        userDocument = userCollection.document(userId);
        getDataFromFoodDocs();


    }


    public void getDataFromFoodDocs(){

        db.collection("users").document(userId).collection("food")
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