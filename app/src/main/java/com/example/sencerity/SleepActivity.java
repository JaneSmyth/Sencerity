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

    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    FirebaseUser currentUser;
    String userId;
    ArrayList<SleepDataModel> sleepArrayList;
    RecyclerViewAdapter adapter;
    Date timestampToDate;
    PieChart pieChart;
    long timeElapsed;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        sleepArrayList = new ArrayList<>();
        //setUpRecyclerView();
       // setUpFirebase();
        //loadDataFromFirebase();
       // Time();
        DrawPie();
      /*  ArrayList<PieEntry> values = new ArrayList<>();
        pieChart = findViewById(R.id.sleepPieChart);
        pieChart.setUsePercentValues(true);
        values.add(new PieEntry(45,"A"));
        values.add(new PieEntry(45,"B"));
        values.add(new PieEntry(45,"C"));
        values.add(new PieEntry(45,"D"));
        values.add(new PieEntry(45,"E"));
        values.add(new PieEntry(45,"F"));
        PieDataSet pDataSet= new PieDataSet(values, "letters");
        PieData pData = new PieData(pDataSet);
        pDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(pData);
        //pieChart.animateXY(5000, 5000);
        */
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
        //86,400 seconds in 24hr




    }

    private void DrawPie(){
        pieChart = findViewById(R.id.sleepPieChart);
        long t2 = 1548579660L; //seconds
        long t1 = 1548535800L; //seconds
        timeElapsed = t2-t1;
        float timeElap = timeElapsed;
        float totalTime = (float)86400L;
        float timeLeft = totalTime- timeElap;
        ArrayList<PieEntry> yvalues = new ArrayList<>();

        yvalues.add(new PieEntry(timeElap, "sleep"));
        yvalues.add(new PieEntry(timeLeft, "Awake"));


        PieDataSet pDataSet= new PieDataSet(yvalues, "Time asleep");
        PieData pData = new PieData(pDataSet);
        pDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
       // pData.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setUsePercentValues(true);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setData(pData);

       // data.setValueFormatter(new DefaultValueFormatter(0));

    }

}