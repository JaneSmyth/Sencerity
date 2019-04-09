package com.example.sencerity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

    }

    public void onClick(View v){
        int i=v.getId();
        if(i==R.id.foodButton)
        {
            Intent changeToFoodActivity = new Intent(MainMenu.this, FoodActivity.class);
            MainMenu.this.startActivity(changeToFoodActivity);
        }
        if(i==R.id.sleepButton)
        {
            Intent changeToSleepActivity = new Intent(MainMenu.this,SleepActivity.class);
            MainMenu.this.startActivity(changeToSleepActivity);
        }
        if(i==R.id.hygieneButton)
        {
            Intent changeToHygieneActivity = new Intent(MainMenu.this, HygieneActivity.class);
            MainMenu.this.startActivity(changeToHygieneActivity);
        }
        if(i==R.id.continenceButton)
        {
            Intent changeToContinenceActivity = new Intent(MainMenu.this,ContinenceActivity.class);
            MainMenu.this.startActivity(changeToContinenceActivity);
        }
        if(i==R.id.mobilityButton)
        {
            Intent changeToMobilityActivity = new Intent(MainMenu.this, MobilityActivity.class);
            MainMenu.this.startActivity(changeToMobilityActivity);
        }
        if(i==R.id.dressingButton)
        {
            Intent changeToDressingActivity = new Intent(MainMenu.this,DressingActivity.class);
            MainMenu.this.startActivity(changeToDressingActivity);
        }
    }


}



