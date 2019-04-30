package com.example.sencerity;

import adapter.SleepAdapter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import models.FoodProductHeader;
import models.FoodProductInfo;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.example.sencerity.BarcodeScannerActivity.barcodeNum;
import static com.example.sencerity.FoodActivity.barcodeValue;

public class NutrionalActivity extends AppCompatActivity{

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

    private FoodProductHeader foodHeader;
    private FoodProductInfo foodInfo;


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


                            String carbs = querySnapshot.getDouble("carbsPerServ").toString();
                            String fibre = querySnapshot.getDouble("fibrePerServ").toString();
                            String cals = querySnapshot.getDouble("kcalsPerServ").toString();
                            String protein = querySnapshot.getDouble("proteinPerServ").toString();
                            String salt = querySnapshot.getDouble("saltPerServ").toString();
                            String satFat = querySnapshot.getDouble("satFatPerServ").toString();
                            String servSize = querySnapshot.getDouble("servingInGrams").toString();
                            String sugar = querySnapshot.getDouble("sugarCarbsPerServ").toString();
                            String fat = querySnapshot.getDouble("totalFatPerServ").toString();


                            foodHeader = new FoodProductHeader(product,brand,barcode);
                            foodInfo = new FoodProductInfo(servSize,cals,protein,carbs,sugar,fat,satFat,fibre,salt);
                            retrieveProudctInfo();
                            retieveNutrionalData();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NutrionalActivity.this, "Problem ---1---", Toast.LENGTH_SHORT).show();
                        Log.w("---1---", e.getMessage());
                    }
                });

    }


    public void retieveNutrionalData(){
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
    public void retrieveProudctInfo(){
        productNameTextView.setText(foodHeader.getProductName());
        brandNameTextView.setText(foodHeader.getBrandName());
        barcodeNumberTextView.setText(foodHeader.getBarcodeNum());


    }
}
