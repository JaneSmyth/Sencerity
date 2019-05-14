package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;
//import com.google.firebase.database.DatabaseReference;


public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private TextView username;
    private TextView password;

    FirebaseAuth mAuth;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        // authentication instance



        username = findViewById(R.id.usernameTextView);
        password = findViewById(R.id.passwordTextView);

        findViewById(R.id.loginButton).setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        //check to see if user is already signed in, update UI
       // FirebaseUser currentUser = mAuth.getCurrentUser();

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

                                            // Get new Instance ID token
                                            String token = task.getResult().getToken();

                                            // Log and toast
                                            String msg = getString(R.string.tokenMsg, token);
                                            Log.d(TAG, msg);
                                            Toast.makeText(SignIn.this, msg, Toast.LENGTH_SHORT).show();
                                            sendToMainMenu();
                                        }
                                    });
                            //sign in success
                            /*
                             String tokenId = FirebaseInstanceId.getInstance().getToken();
                                    String currentId = mAuth.getCurrentUser().getUid();

                                    Map<String, Object> tokenMap= new HashMap<>();
                                    tokenMap.put("tokenId",tokenId);

                                    mFirestore.collection("users").document(currentId).update(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            sendToMainMenu();

                                        }
                                    });
                                }
                            });*/

                        } else {
                            Log.d(TAG,"sign in failed", task.getException());
                            Toast.makeText(SignIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }


    @Override
    public void onClick(View v){
        int i= v.getId();
        if(i == R.id.loginButton)
        {
            signInWithEmailAndPassword(username.getText().toString(), password.getText().toString());
        }
    }

    public void sendToMainMenu(){
        Intent activityChangeIntent = new Intent(SignIn.this, MainMenu.class);
        SignIn.this.startActivity(activityChangeIntent);
    }
}





