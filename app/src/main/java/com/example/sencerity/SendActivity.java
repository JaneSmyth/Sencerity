package com.example.sencerity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userIdView;
    private String userId;
    private String mUserName;
    private String mCurrentId;

    private EditText mMessageView;
    private Button mSubmitBtn;
    private ProgressBar mMessageProgress;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        userIdView = findViewById(R.id.userIdView);
        mMessageView = findViewById(R.id.messageView);
        mSubmitBtn = findViewById(R.id.sendButton);
        mMessageProgress = findViewById(R.id.messageProgress);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        //TODO: check next 2 lines
        userId = mAuth.getUid(); //technically this should be the user ID of the person we want to send the notification to
        mCurrentId = mAuth.getUid();//the id of the current user logged in
        userIdView.setText(userId);
        mSubmitBtn.setOnClickListener(this);


    }

    public void onClick(View v) {
        if (v.getId() == R.id.sendButton) {
            sendNotification();
        }


    }
/*
    public void notifBuilder() {
    }

        String textTitle = "Sencerity Notification";
        String textContent ="You have one new message";
    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "CHANNEL_01")
                .setSmallIcon(R.drawable.fui_ic_github_white_24dp)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }


*/

    public void sendNotification(){

        String message = mMessageView.getText().toString();
                if(!TextUtils.isEmpty(message))//if message isnt blank
                {
                    mMessageProgress.setVisibility(View.VISIBLE);
                    Map<String,Object> notificationMessage =new HashMap<>();
                    notificationMessage.put("message",message);
                    notificationMessage.put("from", userId);

                    mFirestore.collection("users").document(userId).collection("notifications")
                            .add(notificationMessage)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(SendActivity.this, "Notification Sent.", Toast.LENGTH_SHORT).show();
                                   mMessageView.setText("");
                                    mMessageProgress.setVisibility(View.INVISIBLE);
                                }
                            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SendActivity.this, "Notification Error."+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("!DEV WARNING!!",e.getMessage());
                            mMessageProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }



}
