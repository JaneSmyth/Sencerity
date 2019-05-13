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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NutritionalActivity extends AppCompatActivity{

    private FirebaseFirestore db;
    private static final String TAG="product info";
    private TextView caloriesTextView;
    private TextView proteinTextView;
    private TextView totalCarbTextView;
    private TextView sugarCarbTextView;
    private TextView totalFatTextView;
    private TextView satFatTextView;
    private TextView fibreTextView;
    private TextView saltTextView;
    private TextView servingSize;

    private TextView productNameTextView;
    private TextView brandNameTextView;
    private TextView barcodeNumberTextView;

    public static FoodGradeCalculations foodGradeCalc;
    FoodProductHeader foodHeader;
    FoodProductInfo foodInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrional_info);
        db = FirebaseFirestore.getInstance();
        setTextViews();
        loadDataFromFirebase();
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
        barcodeNumberTextView=findViewById(R.id.barcodeNumTextView);





    }


    private void loadDataFromFirebase() {

        db.collection("products")
               // .whereEqualTo("barcodeNumber", barcodeNum )
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (QueryDocumentSnapshot querySnapshot : task.getResult()) {
                            Log.d(TAG, querySnapshot.getId() + " => " + querySnapshot.getData());
                            String barcode = querySnapshot.getString("barcodeNumber");
                            String brand = querySnapshot.getString("brandName");
                            String product = querySnapshot.getString("productName");


                            Double carbs = querySnapshot.getDouble("carbsPerServ");
                            Double fibre = querySnapshot.getDouble("fibrePerServ");
                            Double cals = querySnapshot.getDouble("kcalsPerServ");
                            Double protein = querySnapshot.getDouble("proteinPerServ");
                            Double salt = querySnapshot.getDouble("saltPerServ");
                            Double satFat = querySnapshot.getDouble("satFatPerServ");
                            Double servSize = querySnapshot.getDouble("servingInGrams");
                            Double sugar = querySnapshot.getDouble("sugarCarbsPerServ");
                            Double fat = querySnapshot.getDouble("totalFatPerServ");

                            foodGradeCalc = new FoodGradeCalculations(carbs,sugar,fat,satFat,servSize,protein,salt,fibre);

                            //should be able to get grade, stored in db, for 100 grams of the food (or whatever the
                            //weight of the data being stored)..should be added
                            foodHeader = new FoodProductHeader(product,brand,barcode);
                            foodInfo = new FoodProductInfo(servSize,cals,protein,carbs,sugar,fat,satFat,fibre,salt);

                            retrieveProductInfo();
                            retrieveNutritionalData();


                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NutritionalActivity.this, "Problem ---1---", Toast.LENGTH_SHORT).show();
                        Log.w("---1---", e.getMessage());
                    }
                });

    }



    public static String getFoodGradeCalc(Double inputServ){
        foodGradeCalc.setInputtedServingSize(inputServ);
        foodGradeCalc.nutritionalInfoForServingInputted();
        foodGradeCalc.foodScoreCalculation();


        return foodGradeCalc.getFoodGrade();
    }

    public void retrieveNutritionalData(){
        servingSize.setText(foodInfo.getServingSizeGrams() +"g");

        caloriesTextView.setText(foodInfo.getCalories());
        proteinTextView.setText(foodInfo.getProtein());
        totalFatTextView.setText(foodInfo.getTotalFat());
        satFatTextView.setText(foodInfo.getSatFat());
        totalCarbTextView.setText(foodInfo.getTotalCarbs());
        sugarCarbTextView.setText(foodInfo.getSugarCarbs());
        fibreTextView.setText(foodInfo.getFibre());
        saltTextView.setText(foodInfo.getSalt());

    }
    public void retrieveProductInfo(){
        productNameTextView.setText(foodHeader.getProductName());
        brandNameTextView.setText(foodHeader.getBrandName());
        barcodeNumberTextView.setText(foodHeader.getBarcodeNum());


    }
    /*public void addFood(View v) { //barcode click event
         Intent intent = new Intent(this, BarcodeScannerActivity.class);
         startActivityForResult(intent, 0);
      }*/
    public void onClickAddFood(View v){
            Intent addFoodToDiary = new Intent(this,AddFoodToDiary.class);
            addFoodToDiary.putExtra("calories",foodInfo.getCalories());
            addFoodToDiary.putExtra("servingSize",foodInfo.getServingSizeGrams());
            addFoodToDiary.putExtra("product",foodHeader.getProductName());
            addFoodToDiary.putExtra("barcode",foodHeader.getBarcodeNum());
            addFoodToDiary.putExtra("brand",foodHeader.getBrandName());

        startActivity(addFoodToDiary);

    }

}
