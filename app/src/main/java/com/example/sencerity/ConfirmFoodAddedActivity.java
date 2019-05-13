package com.example.sencerity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmFoodAddedActivity extends AppCompatActivity {

    String successOrFail;
    TextView messageToUser;
    Button returnToFoodPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_food_added);
        messageToUser = findViewById(R.id.successOrFailTextView);
        returnToFoodPageBtn = findViewById(R.id.returnToFoodPageBtn);
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent i =getIntent();
        successOrFail= i.getStringExtra("successOrFailMessage");
        fillTextBox();
    }

    private void fillTextBox() {
       messageToUser.setText(successOrFail);
    }

    public void onClickReturn(View v){
        Intent intent = new Intent(this,FoodActivity.class);
        startActivity(intent);
    }
}
