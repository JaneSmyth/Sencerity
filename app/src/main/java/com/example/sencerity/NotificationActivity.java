package com.example.sencerity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private TextView mNotifData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNotifData=findViewById(R.id.notifData);

        String dataMessage = getIntent().getStringExtra("dataMessage");
        String dataFrom  = getIntent().getStringExtra("dataFrom");

        mNotifData.setText("FROM: "+dataFrom + " | MESSAGE: "+dataMessage);

    }
}
