package models;

import com.example.sencerity.FoodActivity;

public class FoodProductInfo {

    private Double calories;
    private Double totalCarbs;
    private Double sugarCarbs;
    private Double totalFat;
    private Double satFat;
    private Double servingSizeGrams;
    private Double protein;
    private Double salt;
    private Double fibre;
    //private Double gradeOverall; i.e. per 100g etc


    public FoodProductInfo(Double servingSizeGrams, Double calories, Double protein, Double totalCarbs, Double sugarCarbs, Double totalFat, Double satFat, Double fibre, Double salt) {
        this.servingSizeGrams = servingSizeGrams;
        this.calories = calories;
        this.protein = protein;
        this.totalCarbs = totalCarbs;
        this.sugarCarbs = sugarCarbs;
        this.totalFat = totalFat;
        this.satFat = satFat;
        this.fibre = fibre;
        this.salt=salt;

    }
///////////////////////////////



    ////////////////////////////////
    public String getCalories() {
        return calories.toString();
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public String getTotalCarbs() {
        return totalCarbs.toString();
    }

    public void setTotalCarbs(Double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public String getSugarCarbs() {
        return sugarCarbs.toString();
    }

    public void setSugarCarbs(Double sugarCarbs) {
        this.sugarCarbs = sugarCarbs;
    }

    public String getTotalFat() {
        return totalFat.toString();
    }

    public void setTotalFat(Double totalFat) {
        this.totalFat = totalFat;
    }

    public String getSatFat() {
        return satFat.toString();
    }

    public void setSatFat(Double satFat) {
        this.satFat = satFat;
    }

    public String getServingSizeGrams() {
        return servingSizeGrams.toString();
    }

    public void setServingSizeGrams(Double servingSizeGrams) {
        this.servingSizeGrams = servingSizeGrams;
    }

    public String getProtein() {
        return protein.toString();
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public String getSalt() {
        return salt.toString();
    }

    public void setSalt(Double salt) {
        this.salt = salt;
    }

    public String getFibre() {
        return fibre.toString();
    }

    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }
}
