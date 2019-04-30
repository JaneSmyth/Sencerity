package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import adapter.SleepAdapter;;
import models.RecyclerViewItem;

public class FoodActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    public static String barcodeValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        db = FirebaseFirestore.getInstance();


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

                    barcodeValue = barcode.displayValue;
                   // barcodeTextView.setText("Barcode value: " +barcode.displayValue);

                    Intent nutritionalActivity = new Intent(FoodActivity.this,NutrionalActivity.class);
                    FoodActivity.this.startActivity(nutritionalActivity);

                } else{
                    //add barcode info
                }

            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }


    private void setUpRecyclerView() {
        mRecyclerView=findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

