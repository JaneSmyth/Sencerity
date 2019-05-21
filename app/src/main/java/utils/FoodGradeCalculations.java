package utils;

import android.util.Log;

public class FoodGradeCalculations{

    // so lets say upon scanning a barcode for cheese, the nutritional info that is
    // displayed is for 100g of cheese-as stated on the back of the product.
    //However, a person would not usually be eating that amount, lets say they're eating 10g of cheese..
    //this converts the nutritional info for 100g to 10g (or whatever value the user enters as serving size)


    //original nutritional info , i.e for 100g, stored in products database

     Double totalCarbs;
     Double sugarCarbs;
     Double totalFat;
     Double satFat;
     Double servingSizeGrams;
     Double protein;
     Double salt;
     Double fibre;


    //nutritional info calculated based on the serving size entered by the user


     Double totalCarbsResult;
     Double sugarCarbsResult;
     Double totalFatResult;
     Double satFatResult;
     Double servingSizeGramsResult;
     Double proteinResult;
     Double saltResult;
     Double fibreResult;

    public double inputtedServingSize; //user entered serving size

    //score of products once calculations have begun on product with entered serving size

     Double foodScoreStart=0.710; // food grade originally starts at 0.710 (refer to documentation)
     Double foodScore;
     Double totalCarbsScore;
     Double sugarCarbsScore;
     Double totalFatScore;
     Double satFatScore;
     Double servingSizeGramsScore;
     Double proteinScore;
     Double saltScore;
     Double fibreScore;

     String foodGrade;


   public FoodGradeCalculations(Double totalCarbs, Double sugarCarbs, Double totalFat, Double satFat, Double servingSizeGrams,Double protein, Double salt, Double fibre) {

        this.totalCarbs = totalCarbs;
        this.sugarCarbs = sugarCarbs;
        this.totalFat = totalFat;
        this.satFat = satFat;
        this.servingSizeGrams = servingSizeGrams;
        this.protein = protein;
        this.salt = salt;
        this.fibre = fibre;
    }

    ///////////////////////////////////////////////////

    public void nutritionalInfoForServingInputted(){


        totalCarbsResult =(totalCarbs/servingSizeGrams)* inputtedServingSize;
        sugarCarbsResult =(sugarCarbs/servingSizeGrams)* inputtedServingSize;
        totalFatResult=(totalFat/servingSizeGrams)* inputtedServingSize;
        satFatResult=(satFat/servingSizeGrams)* inputtedServingSize;
        proteinResult=(protein/servingSizeGrams)* inputtedServingSize;
        saltResult=(salt/servingSizeGrams)* inputtedServingSize;
        fibreResult=(fibre/servingSizeGrams)* inputtedServingSize;

   }

    public void foodScoreCalculation(){

        totalFatScore =  0.0538 * totalFatResult; //neg
        satFatScore =  0.423 * satFatResult;//neg
        saltScore = 0.00254 * saltResult;//neg
        totalCarbsScore= 0.0300 * totalCarbsResult;//neg
        fibreScore= 0.561 * fibreResult;//p
        sugarCarbsScore=0.0245 * sugarCarbsResult;//neg
        proteinScore= 0.123 * proteinResult;//p

        saltScore=0.2;
        try {
           foodScore=foodScoreStart - totalFatScore - satFatScore - saltScore - totalCarbsScore + fibreScore - sugarCarbsScore + proteinScore;
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public String getFoodGrade(){
       if(foodScore >1.5)
           foodGrade= "A+";
       else if(foodScore > 1 && foodScore< 1.5)
           foodGrade="A-";
       else if(foodScore > 0.5 && foodScore <1)
           foodGrade="B+";
       else if(foodScore > 0 && foodScore < 0.5)
           foodGrade="B";
       else if(foodScore > -0.5 && foodScore < 0)
           foodGrade="B-";
       else if(foodScore > -1 && foodScore < -0.5)
           foodGrade="C+";
       else if(foodScore > -1.5 && foodScore < -1)
           foodGrade="C";
       else if(foodScore > -2 && foodScore < -1.5)
           foodGrade="C-";
       else if(foodScore > -2.5 && foodScore < -2)
           foodGrade="D+";
       else if(foodScore < -2.5)
           foodGrade="D";
       else
           foodGrade="NG";//no grade


        return foodGrade;
    }

    public Double getInputtedServingSize() {
        return inputtedServingSize;
    }

    public void setInputtedServingSize(Double inputtedServingSize) {
        this.inputtedServingSize = inputtedServingSize;

    }
    public Double getServ(){
       return servingSizeGrams;
    }
}
