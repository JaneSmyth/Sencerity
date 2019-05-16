package com.example.sencerity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import models.FoodProductHeader;
import models.FoodProductInfo;
import utils.FoodGradeCalculations;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewBarcodeActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private Map<String, Object> foodEntry;
    private static final String TAG="product info";
    private EditText caloriesTextView;
    private EditText proteinTextView;
    private EditText totalCarbTextView;
    private EditText sugarCarbTextView;
    private EditText totalFatTextView;
    private EditText satFatTextView;
    private EditText fibreTextView;
    private EditText saltTextView;
    private EditText servingSize;

    private EditText productNameTextView;
    private EditText brandNameTextView;
    private String barcodeVal;


    Double carbs,cals,protein,fat,satFat,sugar,salt,servSize,fibre;
    String product,brand;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_barcode);
        db = FirebaseFirestore.getInstance();
        barcodeVal = BarcodeScannerActivity.barcodeNumber;
        setTextViews();
    }

    private void setTextViews(){
        caloriesTextView=findViewById(R.id.kcalsTextView);
        proteinTextView=findViewById(R.id.proteinTextView);
        totalFatTextView=findViewById(R.id.fatsTotalTextView);
        satFatTextView=findViewById(R.id.satFatTextView);
        totalCarbTextView=findViewById(R.id.carbsTotalTextView);
        sugarCarbTextView=findViewById(R.id.sugarCarbsTextView);
        fibreTextView=findViewById(R.id.fibreTextView);
        saltTextView=findViewById(R.id.saltTextView);
        servingSize=findViewById(R.id.servingSizeTextView);

        productNameTextView=findViewById(R.id.productNameTextView);
        brandNameTextView=findViewById(R.id.brandTextView);

    }

    public void createMap(){

           carbs = Double.valueOf(totalCarbTextView.getText().toString());
           fibre = Double.valueOf(fibreTextView.getText().toString());
           cals = Double.valueOf(caloriesTextView.getText().toString());
           protein = Double.valueOf(proteinTextView.getText().toString());
           salt = Double.valueOf(saltTextView.getText().toString());
           satFat = Double.valueOf(satFatTextView.getText().toString());
           servSize = Double.valueOf(servingSize.getText().toString());
           sugar = Double.valueOf(sugarCarbTextView.getText().toString());
           fat = Double.valueOf(totalFatTextView.getText().toString());
           product = productNameTextView.getText().toString();
           brand = brandNameTextView.getText().toString();

         foodEntry = new HashMap<>();
        foodEntry.put("barcodeNumber", FoodActivity.barcodeValue);
        foodEntry.put("brandName", brandNameTextView.getText().toString());
        foodEntry.put("carbsPerServ", carbs);
        foodEntry.put("fibrePerServ", fibre);
        foodEntry.put("kcalsPerServ", cals);
        foodEntry.put("productName", productNameTextView.getText().toString());
        foodEntry.put("proteinPerServ", protein);
        foodEntry.put("saltPerServ", salt);
        foodEntry.put("satFatPerServ", satFat);
        foodEntry.put("servingInGrams",servSize);
        foodEntry.put("sugarCarbsPerServ", sugar);
        foodEntry.put("totalFatPerServ", fat);
        addDataToFireBase();

    }

    public void addDataToFireBase(){
        db.collection("products")
                .add(foodEntry)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //success
                        changeIntent();
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("addDataToFb","error",e);
                    }
                });
    }
public void changeIntent(){
    Intent addFoodToDiary = new Intent(this, AddFoodToDiaryActivity.class);
    addFoodToDiary.putExtra("calories",cals.toString());
    addFoodToDiary.putExtra("servingSize",servSize.toString());
    addFoodToDiary.putExtra("product",product);
    addFoodToDiary.putExtra("barcode",barcodeVal);
    addFoodToDiary.putExtra("brand",brand);

    AddNewBarcodeActivity.this.startActivity(addFoodToDiary);
}

    public void onClickAddFood(View v){
           try {
               createMap();
           }catch (IllegalStateException ise)
           {
               Toast.makeText(this, "Please ensure all fields have values. Enter 0 if you are unsure.", Toast.LENGTH_LONG).show();
           }

    }
}


