package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.database.DatabaseReference;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private TextView username;
    private TextView password;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;
    private String currentId;
    private String mToken;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        username = findViewById(R.id.usernameTextView);
        password = findViewById(R.id.passwordTextView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        findViewById(R.id.loginButton).setOnClickListener(this);
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }
                                    // Get new Instance ID mToken
                                    mToken = Objects.requireNonNull(task.getResult()).getToken();
                                   // mToken = task.getResult().getToken();
                                    Map<String, Object> tokenMap= new HashMap<>();
                                    tokenMap.put("tokenId", mToken);
                                    currentId = FirebaseAuth.getInstance().getUid();
                                    mFirestore.collection("users").document(currentId).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        sendToPatientSelection();

                                    }
                                });

                            }
                        });

                    } else {
                        Log.d(TAG,"sign in failed", task.getException());
                        Toast.makeText(SignInActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }

                    }

                });
    }

    @Override
    public void onClick(View v){
        int i= v.getId();
        if(i == R.id.loginButton)
        {
            progressBar.setVisibility(View.VISIBLE);

            signInWithEmailAndPassword(username.getText().toString(), password.getText().toString());
        }
    }

    public void sendToPatientSelection(){

            Intent activityChangeIntent = new Intent(this, PatientSelectActivity.class);
            startActivity(activityChangeIntent);

    }
}





