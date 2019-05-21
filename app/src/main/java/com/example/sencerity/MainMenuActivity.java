package com.example.sencerity;


import android.Manifest;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainMenuActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CAMERA = 200;
    private static final String TAG="id error";
    private String token;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String mUserId;
    private Button logOutBtn;
    public static String patientSelectName;
    public static String patientSelectId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        logOutBtn=findViewById(R.id.logOutBtn);
        mAuth = FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();
        patientSelectName=getIntent().getStringExtra("pName");
        getPatientInfo();

    }


    protected  void onStart(){
        super.onStart();
        camPermission();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser ==null){
            sendToSignIn();
        }
    }

    private void sendToSignIn() {
        Intent changeToSignInActivity = new Intent(MainMenuActivity.this, SignInActivity.class);
        startActivity(changeToSignInActivity);
        Toast.makeText(this, "Signed Out Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void camPermission(){
        ActivityCompat.requestPermissions(MainMenuActivity.this, new String[]{Manifest.permission.CAMERA},
                MY_PERMISSION_REQUEST_CAMERA);
    }

    public void getPatientInfo(){

        mFirestore.collection("users").document(mUserId).collection("patient")
                .whereEqualTo("name",patientSelectName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot doc: task.getResult())
                            {
                                patientSelectId = doc.getString("patientId");
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("patient firebase Error:",""+ e);
                    }
                });



    }
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.foodButton) {
            Intent changeToFoodActivity = new Intent(MainMenuActivity.this, FoodActivity.class);
            MainMenuActivity.this.startActivity(changeToFoodActivity);
        }
        if (i == R.id.sleepButton) {
            Intent changeToSleepActivity = new Intent(MainMenuActivity.this, SleepActivity.class);
            MainMenuActivity.this.startActivity(changeToSleepActivity);
        }
        if (i == R.id.hygieneButton) {
            Intent changeToHygieneActivity = new Intent(MainMenuActivity.this, HygieneActivity.class);
            MainMenuActivity.this.startActivity(changeToHygieneActivity);
        }
        if (i == R.id.continenceButton) {
            Intent changeToContinenceActivity = new Intent(MainMenuActivity.this, ContinenceActivity.class);
            MainMenuActivity.this.startActivity(changeToContinenceActivity);
        }
        if (i == R.id.mobilityButton) {
            Intent changeToMobilityActivity = new Intent(MainMenuActivity.this, MobilityActivity.class);
            MainMenuActivity.this.startActivity(changeToMobilityActivity);
        }
        if (i == R.id.dressingButton) {
            Intent changeToDressingActivity = new Intent(MainMenuActivity.this, DressingActivity.class);
            MainMenuActivity.this.startActivity(changeToDressingActivity);
        }
        if (i == R.id.sendNotif) {
            Intent sendNotif = new Intent(MainMenuActivity.this, SendActivity.class);
            MainMenuActivity.this.startActivity(sendNotif);
        }
        if (i == R.id.logOutBtn) {
            mAuth.signOut();
            sendToSignIn();
        }
    }


    }





