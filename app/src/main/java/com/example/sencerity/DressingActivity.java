package com.example.sencerity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import models.DressedModel;
import utils.DateAndTimeConversions;

public class DressingActivity extends AppCompatActivity {


    private static final String TAG = "docData";
    private static final String DATETIME = "Date and Time";
    private static final String SENSOR = "Sensor";
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String userId;
    private TextView displayData;
    private TextView displayWashData;
    // private static final String KEY = "dateTime";
    CollectionReference userCollection;
    DocumentReference userDocument;
    String id;
    String dateTimeStr;
    Date dateTime;
    DateAndTimeConversions dDateTimeConvert;
    DateAndTimeConversions wDateTimeConvert;
    Calendar cal=Calendar.getInstance();
    String patientId;
    Date yesterday;
    Date today;
    Date tomorrow;
    String patientName;
    String displayMessage;
    String displayReminderMsg;
    Resources res;
    String todayStr;
    List<Date> timesListDress = new ArrayList<>();
    int count;
    String timeStringDressRecent;
    String timeStringDressFirst;
    String timeStringWashRecent;
    String timeStringWashFirst;
    String displayWashMessage;
    private Date dateTimeWash;
    private String washTimeString;
    private String washDateString;
    TextView washReminderBtn;
    TextView dressReminderBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dressing);
        displayData = findViewById(R.id.dressedTextView);
        displayWashData = findViewById(R.id.washingTextView);
        washReminderBtn = findViewById(R.id.washReminderBtn);
        dressReminderBtn = findViewById(R.id.dressReminderBtn);
        today=new Date();
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        res = getResources();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
        else
        {
            userId="kM2gyYrk5MeLlLFKoZdNOViGwJI2";
        }
        wDateTimeConvert = new DateAndTimeConversions(today);

        userCollection = db.collection("users");
        userDocument = userCollection.document(userId);
        cal.add(Calendar.DATE,-1);
        yesterday = cal.getTime();
        cal.add(Calendar.DATE,+1);
        tomorrow =cal.getTime();
        patientId="1"; //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        setPatientName();




    }

    public void setPatientName(){
        db.collection("users").document(userId).collection("patient")
                .whereEqualTo("patientId",patientId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (@NonNull QueryDocumentSnapshot document : task.getResult()) {
                                patientName = document.getString("name");

                            }
                        }
                        else {

                        }
                        getDressingData();
                        getWashingData();

                    }
                });
    }


    public void getDressingData() {

        db.collection("users").document(userId).collection("dressing")
                .whereGreaterThan("dateTime",yesterday)
                .whereLessThan("dateTime",tomorrow)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }
                        if(snapshots.isEmpty())
                        {
                            dressSnapshotEmpty();
                            return;

                        }

                            for (DocumentChange dc : snapshots.getDocumentChanges()) {
                                Log.d("!3:", patientName);
                                dateTime = dc.getDocument().getTimestamp("dateTime").toDate();
                                //dateTimeConvert = new DateAndTimeConversions(dateTime);
                                timesListDress.add(dateTime);
                                dressSnapshotResult();

                            }
                            displayData.setText(displayMessage);

                    }
                });
    }


    public void dressSnapshotEmpty() {
        displayMessage = String.format(res.getString(R.string.dressingIsEmptyMsg),patientName);
        displayData.setText(displayMessage);

    }

//this function could be tidied up like the washing is
    public void dressSnapshotResult(){
        dDateTimeConvert = new DateAndTimeConversions(timesListDress.get(0));
        timeStringDressFirst = dDateTimeConvert.getHourMinuteFormat();

        if(timesListDress.size()==1)
        {
            displayMessage = String.format(res.getString(R.string.dressingResult), patientName, timeStringDressFirst);
        }
        else if(timesListDress.isEmpty())
        {

        }
        else{
            dDateTimeConvert = new DateAndTimeConversions(timesListDress.get(timesListDress.size()-1));
            timeStringDressRecent= dDateTimeConvert.getHourMinuteFormat();
            displayMessage = String.format(res.getString(R.string.dressingMultiResult), patientName, timeStringDressFirst,timeStringDressRecent);
        }

    }



    public void getWashingData() {

        db.collection("dressing")
                .whereEqualTo("userId", "123")
                .whereEqualTo("patientId", "1")
                .whereEqualTo("sensor","washingMachine")

                .addSnapshotListener(new EventListener<QuerySnapshot>() {

                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {

                        if (e != null) {
                            Log.w(TAG, "listen:error", e);
                            return;
                        }
                        if(snapshots.isEmpty()){
                            washingSnapshotEmpty();
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            dateTimeWash = dc.getDocument().getTimestamp("dateTime").toDate();
                            wDateTimeConvert = new DateAndTimeConversions(dateTimeWash);
                            washTimeString = wDateTimeConvert.getHourMinuteFormat();
                            washDateString = wDateTimeConvert.getDayDateMonthFormat();
                            if(washDateString == todayStr){
                                washingDoneToday();
                            }
                            else{
                                washingPreviouslyDone();
                            }
                        }
                        displayWashData.setText(displayWashMessage);
                    }

                });
    }

    public void washingSnapshotEmpty() {
        displayWashMessage = String.format(res.getString(R.string.dressingWashingIsEmpty),patientName);


    }

    public void washingDoneToday(){
        displayWashMessage= String.format(res.getString(R.string.dressingWashingToday),patientName,washTimeString);

    }

    public void washingPreviouslyDone(){
        displayWashMessage= String.format(res.getString(R.string.dressingWashingPrevious),patientName,washDateString,washTimeString);


    }


    public void onClickReminder(View v){
        if(v.getId() == R.id.dressReminderBtn)
        {
            Toast.makeText(this, "A reminder to get dressed has been sent.", Toast.LENGTH_SHORT).show();
        }
        if(v.getId() == R.id.washReminderBtn)
        {
            Toast.makeText(this, "A reminder to put on a wash has been sent.", Toast.LENGTH_SHORT).show();
        }
    }
}