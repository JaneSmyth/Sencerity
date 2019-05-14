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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;

public class MainMenu extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_CAMERA = 200;
    private static final String TAG="id error";
    private String token;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String mUserId;
    private Button logOutBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        logOutBtn=findViewById(R.id.logOutBtn);
        mAuth = FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();

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
        Intent changeToSignInActivity = new Intent(MainMenu.this, SignIn.class);
        MainMenu.this.startActivity(changeToSignInActivity);
        finish();
    }

    public void camPermission(){
        ActivityCompat.requestPermissions(MainMenu.this, new String[]{Manifest.permission.CAMERA},
                MY_PERMISSION_REQUEST_CAMERA);
    }



    public void onClick(View v){
        int i=v.getId();
        if(i==R.id.foodButton)
        {
            Intent changeToFoodActivity = new Intent(MainMenu.this, FoodActivity.class);
            MainMenu.this.startActivity(changeToFoodActivity);
        }
        if(i==R.id.sleepButton)
        {
            Intent changeToSleepActivity = new Intent(MainMenu.this,SleepActivity.class);
            MainMenu.this.startActivity(changeToSleepActivity);
        }
        if(i==R.id.hygieneButton)
        {
            Intent changeToHygieneActivity = new Intent(MainMenu.this, HygieneActivity.class);
            MainMenu.this.startActivity(changeToHygieneActivity);
        }
        if(i==R.id.continenceButton)
        {
            Intent changeToContinenceActivity = new Intent(MainMenu.this,ContinenceActivity.class);
            MainMenu.this.startActivity(changeToContinenceActivity);
        }
        if(i==R.id.mobilityButton)
        {
            Intent changeToMobilityActivity = new Intent(MainMenu.this, MobilityActivity.class);
            MainMenu.this.startActivity(changeToMobilityActivity);
        }
        if(i==R.id.dressingButton)
        {
            Intent changeToDressingActivity = new Intent(MainMenu.this,DressingActivity.class);
            MainMenu.this.startActivity(changeToDressingActivity);
        }
        if(i==R.id.sendNotif)
        {
            Intent sendNotif = new Intent(MainMenu.this, SendActivity.class);
            MainMenu.this.startActivity(sendNotif);
        }
        if(i==R.id.logOutBtn)
        {
            Map<String,Object> tokenMapRemove = new HashMap<>();
            tokenMapRemove.put("tokenId", FieldValue.delete());
            mFirestore.collection("users").document(mUserId).update(tokenMapRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    mAuth.signOut();
                    sendToSignIn();
                }
            });

        }

    }


}



