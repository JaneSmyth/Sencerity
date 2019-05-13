package com.example.sencerity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ServerTimestamp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import models.FoodDiaryItem;
import models.FoodProductHeader;
import models.FoodProductInfo;
import utils.FoodGradeCalculations;

import static com.example.sencerity.NutritionalActivity.foodGradeCalc;
import static com.example.sencerity.NutritionalActivity.getFoodGradeCalc;


public class AddFoodToDiary extends AppCompatActivity {

    public static final String TAG = "SuccessDB";
    private TextView productTextView;
    private TextView brandTextView;
    private TextView barcodeTextView;
    private Button submitBtn;
    FoodProductHeader foodHeader;
    FoodProductInfo foodInfo;
    private String spinnerSelection;
    private Spinner spinner;
    private EditText servingEntered;
    private TextView calculatedCalsTextView;
    Double calsDouble;
    Double servDouble;
    Double calsInOneGram;
    Double calsCalc;
    final Calendar calendar = Calendar.getInstance();
    final Date date = calendar.getTime();
    private String today;
    private String product;
    private String brand;
    private String barcode;
    private String serv;
    private String cals;
    private Double inputtedServingSize;
    private String dbAddSuccess;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String userId;
    private int mealTypeValue;
    String foodGrade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);
        productTextView = findViewById(R.id.productNameTextView2);
        brandTextView = findViewById(R.id.brandTextView2);
        barcodeTextView = findViewById(R.id.barcodeNumTextView2);
        spinner = findViewById(R.id.spinnerFood);
        servingEntered = findViewById(R.id.servInputEditTextView);
        calculatedCalsTextView = findViewById(R.id.calculatedCalsPerServ);
        submitBtn = findViewById(R.id.addFoodToDiaryBtn);
        today = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).format(date);
        getDataFromIntent();
        setUpFirebase();
        setProductHeader();
        textChanged();

    }


    private void getDataFromIntent() {
        Intent i = getIntent();
        product = i.getStringExtra("product");
        brand = i.getStringExtra("brand");
        barcode = i.getStringExtra("barcode");
        cals = i.getStringExtra("calories");
        serv = i.getStringExtra("servingSize");
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

    private void setProductHeader() {
        productTextView.setText(product);
        brandTextView.setText(brand);
        barcodeTextView.setText(barcode);
    }

    public void textChanged() {
        servingEntered.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String input = s.toString();
                if (input.isEmpty()) {
                    calculatedCalsTextView.setText("0");
                } else {
                    calculatedCalsTextView.setText(calculateCals(input));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public String calculateCals(String input) {
        calsDouble = Double.parseDouble(cals);
        servDouble = Double.parseDouble(serv);
        inputtedServingSize = Double.parseDouble(input);
        calsInOneGram = calsDouble / servDouble;
        calsCalc = calsInOneGram * inputtedServingSize;

        //e.g. 40 cals in 10g so 40/10 = 4 cals in 1g
        //so 4x20 = 80 so 80cals in 20g
        return String.valueOf(calsCalc);
    }

    public void onClickConfirmFoodAdd(View v) {

        getSelection();
        addDataIntoDatabase();
    }

    public void getSelection() {
        spinnerSelection = String.valueOf(spinner.getSelectedItem());
        switch (spinnerSelection) {
            case "Breakfast":
                mealTypeValue = 1;
                break;
            case "Lunch":
                mealTypeValue = 2;
                break;
            case "Dinner":
                mealTypeValue = 3;
                break;
            case "Snack":
                mealTypeValue = 4;
                break;

            default:
                mealTypeValue = 0;

        }


    }

    public void changeIntent(String message) {

        Intent intent = new Intent(this, ConfirmFoodAddedActivity.class);
        intent.putExtra("successOrFailMessage", message);
        startActivity(intent);


    }

    public void addDataIntoDatabase() {
        foodGrade = getFoodGradeCalc(inputtedServingSize);

         Map<String, Object> foodItem = new HashMap<>();
        foodItem.put("productName", product);
        foodItem.put("brand", brand);
        foodItem.put("barcodeNumber", barcode);
        foodItem.put("caloriesForThisServing", calsCalc);
        foodItem.put("servingSizeEntered", inputtedServingSize);
        foodItem.put("mealType", spinnerSelection);
        foodItem.put("mealTypeValue", mealTypeValue);
        foodItem.put("date", today);
        foodItem.put("foodGrade",foodGrade);

        db.collection("users").document(userId).collection("nutrition")
                .add(foodItem)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        changeIntent("The item was added to your food diary!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        changeIntent("There was a problem adding the item to your food diary. Please try again.");
                    }
                });

    }



}
