package com.example.sencerity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
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

public class SendActivity extends AppCompatActivity {

    private TextView userIdView;
    private String mUserId;
    private String mUserName;
    private String mCurrentId;

    private EditText mMessageView;
    private Button mSubmitBtn;
    private ProgressBar mMessageProgress;

    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        userIdView=findViewById(R.id.userIdView);
        mMessageView = findViewById(R.id.messageView);
        mSubmitBtn = findViewById(R.id.sendButton);
        mMessageProgress = findViewById(R.id.messageProgress);
        mFirestore = FirebaseFirestore.getInstance();
        //hardcoded for now!! TODO: hardcoded text needs to be changed
        mUserId = FirebaseAuth.getInstance().getUid();
        userIdView.setText(mUserId);


        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessageView.getText().toString();

                if(!TextUtils.isEmpty(message))//if message isnt blank
                {
                    mMessageProgress.setVisibility(View.VISIBLE);
                    Map<String,Object> notificationMessage =new HashMap<>();
                    notificationMessage.put("message",message);
                    notificationMessage.put("from",mUserId);

                    mFirestore.collection("users").document(mUserId).collection("notifications")
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

                            mMessageProgress.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }
}
