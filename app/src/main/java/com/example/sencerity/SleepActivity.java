package com.example.sencerity;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SleepActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    private FirebaseUser currentUser;
    private String userId;
    private ArrayList<SleepDataModel> sleepArrayList;
    private RecyclerViewAdapter adapter;
    private Date timestampToDate;
    private PieChart pieChart;
    private long timeElapsed;
    private float timeElap;
    private float timeLeft;
    private float totalTime;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        sleepArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadDataFromFirebase();
        Time();
        DrawPie();

    }

    private void loadDataFromFirebase() {
        if (sleepArrayList.size() > 0)
            sleepArrayList.clear();

        db.collection("users").document(userId).collection("sleep")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(DocumentSnapshot querySnapshot: task.getResult()){

                            timestampToDate = querySnapshot.getTimestamp("dateTime").toDate();

                          SleepDataModel sleepDataM = new SleepDataModel(timestampToDate,querySnapshot.getString("sensor"),
                                   querySnapshot.getString("patientId"));
                            sleepArrayList.add(sleepDataM);

                        }
                        adapter = new RecyclerViewAdapter (SleepActivity.this, sleepArrayList);
                        mRecyclerView.setAdapter(adapter);

                    }
                })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SleepActivity.this,"Problem ---1---",Toast.LENGTH_SHORT).show();
                Log.w("---1---",e.getMessage());
            }
        });
    }

    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
    }

    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void Time(){
        long t2 = 1548579660L; //seconds
        long t1 = 1548535800L; //seconds
        timeElapsed = t2-t1;
        timeElap = timeElapsed;
        totalTime = (float)86400L;//86,400 seconds in 24hr
        timeLeft = totalTime- timeElap;


    }

    private void DrawPie(){
        pieChart = findViewById(R.id.sleepPieChart);
        ArrayList<PieEntry> yvalues = new ArrayList<>();

        yvalues.add(new PieEntry(timeElap, "sleep"));
        yvalues.add(new PieEntry(timeLeft, "Awake"));


        PieDataSet pDataSet= new PieDataSet(yvalues, "Time asleep");
        PieData pData = new PieData(pDataSet);
        pDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
       // pData.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setData(pData);

       // data.setValueFormatter(new DefaultValueFormatter(0));

    }

}