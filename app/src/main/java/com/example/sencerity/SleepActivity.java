package com.example.sencerity;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import adapter.SleepAdapter;
import models.SleepDataPopulated;
import models.SleepDateTimeData;
import models.RecyclerViewItem;
import models.SleepNormalRow;
import utils.SleepDuration;

public class SleepActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    private FirebaseUser currentUser;
    private String userId;
    private ArrayList<RecyclerViewItem> recyclerViewItems;
    public ArrayList<Date> dateList;
    // private List<RecyclerViewItem> sleepArrayList;
    List<SleepDataPopulated> finalList;
    private SleepAdapter adapter;
    private Date timestampToDate;
    private PieChart pieChart;
    private long timeElapsed;
    private float timeElap;
    private float timeLeft;
    private float totalTime;
    private int increment;

    String dayDate;
    private List<SleepDateTimeData> sleepDateTimeData = new ArrayList<>();
    private Boolean sensorPressure;
    private String sleepDurationClassReturn;
    private SleepDuration sleepDuration;
    //  private String sleepDuration;
    int index;
    int i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        index = 0;
        i=0;
        recyclerViewItems = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadDataFromFirebase();

    }

    private void loadDataFromFirebase() {

        // if (recyclerViewItems.size() > 0)
        //    recyclerViewItems.clear();

        db.collection("users").document(userId).collection("sleep")
                .orderBy("dateTime", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot querySnapshot : task.getResult()) {

                            timestampToDate = querySnapshot.getTimestamp("dateTime").toDate();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                            dayDate = sdf.format(timestampToDate);
                            sensorPressure = querySnapshot.getBoolean("sensorPressure");
                            sleepDateTimeData.add(new SleepDateTimeData(dayDate, sensorPressure, timestampToDate));
                            i++;
                        }

                        accumulateData();

                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SleepActivity.this, "Problem ---1---", Toast.LENGTH_SHORT).show();
                        Log.w("!---1---", e.getMessage());
                    }
                });
    }

    private void accumulateData() {
        sleepDuration = new SleepDuration(sleepDateTimeData);

        for (index = 0; index < sleepDateTimeData.size(); index++) {
            sleepDuration.getEligibleDates(index);
        }
        finalList = sleepDuration.returnedData();
        drawPie();
        displayData();
    }

    private void displayData() {

//        finalList = sleepDuration.returnedData();
  //      drawPie();
        int j = finalList.size() - 1;

        while (j >= 0) {
            Log.d("j", "" + j);
            SleepNormalRow normalRow = new SleepNormalRow(
                    finalList.get(j).getDateOfSleep(),
                    finalList.get(j).getDurationString(),
                    finalList.get(j).getSleepTimeToWakeTime());
            recyclerViewItems.add(normalRow);
            j--;
        }

        adapter = new SleepAdapter(SleepActivity.this, recyclerViewItems);
        mRecyclerView.setAdapter(adapter);

    }

    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void drawPie() {


        long timeElap = finalList.get(finalList.size()-1).durationSeconds();


        totalTime = (float)86400L;//86,400 seconds in 24hr
        timeLeft = totalTime- timeElap;
        pieChart = findViewById(R.id.sleepPieChart);
        ArrayList<PieEntry> yvalues = new ArrayList<>();

        yvalues.add(new PieEntry(timeElap, "Asleep"));
        yvalues.add(new PieEntry(timeLeft, "Awake"));


        PieDataSet pDataSet = new PieDataSet(yvalues, "Time asleep");
        PieData pData = new PieData(pDataSet);
        pDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        // pData.setValueFormatter(new DefaultValueFormatter(0));
        pieChart.setUsePercentValues(true);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTextSize(16f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setData(pData);
        pieChart.getDescription().setText("% of time asleep/awake in 24 hours");
        pieChart.invalidate();

        // data.setValueFormatter(new DefaultValueFormatter(0));

    }


}