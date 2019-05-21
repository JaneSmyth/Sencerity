package com.example.sencerity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView userIdView;
    private String patientId;//the patient ID of the person we want to send the notification to
    private String msgReceiverUserId;//the patients user login ID
    private String msgReceiverUserName;//the name of the person we want to send the notification to
    private String userId;

    String docId;

    private EditText mMessageView;
    private Button mSubmitBtn;
    private ProgressBar mMessageProgress;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mMessageView = findViewById(R.id.messageView);
        mSubmitBtn = findViewById(R.id.sendButton);
        mMessageProgress = findViewById(R.id.messageProgress);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        userId = mAuth.getUid();//the id of the current user logged in

        //userIdView.setText(userId);
        mSubmitBtn.setOnClickListener(this);
        msgReceiverUserName = MainMenuActivity.patientSelectName;
        Log.d("msgReceiverName",msgReceiverUserName);
        patientId=MainMenuActivity.patientSelectId;
        getDocId();


    }


    public void getDocId(){
        mFirestore.collection("users")
                .whereEqualTo("patientId", patientId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> thisTask) {
                        if(thisTask.isSuccessful()){
                            for(QueryDocumentSnapshot doc: thisTask.getResult())
                            {
                                docId = doc.getId();
                            }
                        }
                        msgReceiverUserId = docId;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("docid firebase Error:", ""+e);
                    }
                });

    }

    public void onClick(View v) {
        if (v.getId() == R.id.sendButton) {
            sendNotification();
        }


    }

    public void sendNotification(){

        String message = mMessageView.getText().toString();
                if(!TextUtils.isEmpty(message))//if message isnt blank
                {
                    mMessageProgress.setVisibility(View.VISIBLE);
                    Map<String,Object> notificationMessage =new HashMap<>();
                    notificationMessage.put("message",message);
                    notificationMessage.put("from", userId);

                    mFirestore.collection("users").document(msgReceiverUserId).collection("notifications")
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
                            Log.d("sendNotification()ErrorMsg: ",e.getMessage());
                            mMessageProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }



}
