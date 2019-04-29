package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

import adapter.SleepAdapter;
import models.Header;
import models.NormalRow;
import models.RecyclerViewItem;

public class FoodActivity extends AppCompatActivity {
    private TextView barcodeTextView;

    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    private ArrayList<RecyclerViewItem> recyclerViewItems;
    private SleepAdapter adapter;
    private String barcodeValue;
    private static final String TAG="product info";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        db = FirebaseFirestore.getInstance();
        barcodeTextView = findViewById(R.id.barcodeInfoTextView);

    }


    public void scanBarcode(View v) { //barcode click event
        Intent intent = new Intent(this, BarcodeScannerActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        if(requestCode == 0){
            if(resultCode == CommonStatusCodes.SUCCESS){
                if(data!=null){
                    //HERE
                    Barcode barcode = data.getParcelableExtra("barcode");
                   // barcodeTextView.setText("Barcode value: " +barcode.displayValue);
                    barcodeValue = barcode.displayValue;
                    loadDataFromFirebase();

                } else{
                    barcodeTextView.setText("No barcode found");
                }

            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void firestoreDB(){

    }

    private void loadDataFromFirebase() {

        db.collection("products")
                .whereEqualTo("barcodeNumber",barcodeValue)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for(QueryDocumentSnapshot querySnapshot: task.getResult()){
                            Log.d(TAG, querySnapshot.getId() + " => " + querySnapshot.getData());
                            barcodeTextView.setText(querySnapshot.getData().toString());
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodActivity.this,"Problem ---1---",Toast.LENGTH_SHORT).show();
                        Log.w("---1---",e.getMessage());
                    }
                });
    }



    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

