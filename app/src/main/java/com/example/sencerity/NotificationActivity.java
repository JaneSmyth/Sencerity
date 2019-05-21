package com.example.sencerity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private TextView mNotifData;
    Button reply;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNotifData=findViewById(R.id.notifData);
        reply=findViewById(R.id.replyBtn);


        String dataMessage = getIntent().getStringExtra("dataMessage");
        String dataFrom  = getIntent().getStringExtra("dataFrom");

        mNotifData.setText("Message From: " + dataFrom+ "\n\n"+ "Message: "+ dataMessage);

    }

    public void onClickReply(View v){
        Intent intent = new Intent(this, SendActivity.class);
        startActivity(intent);

    }
}
