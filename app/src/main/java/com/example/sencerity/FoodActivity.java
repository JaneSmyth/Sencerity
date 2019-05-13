package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;

import adapter.FoodAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import models.FoodHeaderRow;
import models.FoodNormalRow;
import models.RecyclerViewItem;
import utils.FoodGradeCalculations;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class FoodActivity extends AppCompatActivity {


    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    public static String barcodeValue;
    private FirebaseUser currentUser;
    private String userId;
    private ArrayList<RecyclerViewItem> recyclerViewItems;
    private int countB;
    private int countL;
    private int countD;
    private int countS;
    private FoodHeaderRow foodHeader;
    private FoodAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        recyclerViewItems = new ArrayList<>();
        countB=0;
        countL=0;
        countD=0;
        countS=0;
        setUpRecyclerView();
        setUpFirebase();
        loadDataFromFirebase();



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

                    Intent nutritionalActivity = new Intent(FoodActivity.this, NutritionalActivity.class);
                    FoodActivity.this.startActivity(nutritionalActivity);

                } else{
                    //add barcode info
                }

            }
        } else {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }



    private void setUpFirebase() {
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        } else {
            userId = "kM2gyYrk5MeLlLFKoZdNOViGwJI2";
        }
    }

    private void setUpRecyclerView() {
        mRecyclerView = findViewById(R.id.foodRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadDataFromFirebase(){
        db.collection("users").document(userId).collection("nutrition")
                .orderBy("mealTypeValue", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnapshot : task.getResult()) {
                           String mealType = querySnapshot.getString("mealType");
                            setHeader(mealType);
                            FoodNormalRow normalRow = new FoodNormalRow(
                                    querySnapshot.getString("productName"),
                                    querySnapshot.getDouble("caloriesForThisServing"),
                                    querySnapshot.getDouble("servingSizeEntered"),
                                    querySnapshot.getString("foodGrade"));
                            recyclerViewItems.add(normalRow);


                        }

                        adapter = new FoodAdapter(FoodActivity.this, recyclerViewItems);

                        mRecyclerView.setAdapter(adapter);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodActivity.this, "Problem ---1---", Toast.LENGTH_SHORT).show();
                        Log.w("!---1---", e.getMessage());
                    }
                });
    }


    public void setHeader(String mealType){
        if (mealType.equals("Breakfast") && countB ==0)
        {
            foodHeader = new FoodHeaderRow(mealType);
            recyclerViewItems.add(foodHeader);
            countB=1;
        }
        else if(mealType.equals("Lunch") && countL==0)
        {
            foodHeader = new FoodHeaderRow(mealType);
            recyclerViewItems.add(foodHeader);
            countL=1;
        }
        else if(mealType.equals("Dinner") && countD==0)
        {
            foodHeader = new FoodHeaderRow(mealType);
            recyclerViewItems.add(foodHeader);
            countD=1;
        }
        else if(mealType.equals("Snack") && countS==0)
        {
            foodHeader = new FoodHeaderRow(mealType);
            recyclerViewItems.add(foodHeader);
            countS=1;
        }

    }

}

