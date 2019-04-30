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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Date;


import adapter.SleepAdapter;
import models.SleepHeader;
import models.NormalRow;
import models.RecyclerViewItem;

public class SleepActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    private FirebaseUser currentUser;
    private String userId;
    private ArrayList<RecyclerViewItem> recyclerViewItems;
    public ArrayList<Date> dateList;
   // private List<RecyclerViewItem> sleepArrayList;
    private SleepAdapter adapter;
    private Date timestampToDate;
    private PieChart pieChart;
    private long timeElapsed;
    private float timeElap;
    private float timeLeft;
    private float totalTime;
    private int increment;
    String dayDate;
    int index;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        index =0;
        recyclerViewItems = new ArrayList<>();
        setUpRecyclerView();
        setUpFirebase();
        loadDataFromFirebase();
        for(Object i: recyclerViewItems)
        {
            System.out.println(i);
            System.out.println("===========");
        }
       // Time();
       // DrawPie();

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

                        for(DocumentSnapshot querySnapshot: task.getResult()){

                            String s="hello "+increment;
                            timestampToDate = querySnapshot.getTimestamp("dateTime").toDate();
                            SleepHeader header = new SleepHeader(s);
                            recyclerViewItems.add(header);
                            NormalRow normalRow = new NormalRow(timestampToDate,querySnapshot.getString("sensor"),
                                   querySnapshot.getString("patientId"));

                             recyclerViewItems.add(normalRow);

                        }
                        adapter = new SleepAdapter (SleepActivity.this, recyclerViewItems);

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
        else{
            userId = "kM2gyYrk5MeLlLFKoZdNOViGwJI2";
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
    private void headerDates(LocalDateTime dateTime,int i){

        if(!(dateList.get(i) == dateList.get(i - 1)) |i==0 ) {
            //so if the dates are not the same or its the first date
            SleepHeader header = new SleepHeader(dateTime.toString());
            recyclerViewItems.add(header);
        }
        else{
            //no header
        }

        /*
        if(dayDate == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy ");
            dayDate = sdf.format(timestamp);
        }
        else{
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy ");
            String dayDate2 = sdf.format(timestamp);
            //Continue from here !!!!!!!
        }
        */


    }

}
