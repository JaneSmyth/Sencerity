package models;

import com.example.sencerity.FoodActivity;

public class FoodProductInfo {

    private String calories;
    private String totalCarbs;
    private String sugarCarbs;
    private String totalFat;
    private String satFat;
    private String servingSizeGrams;
    private String protein;
    private String salt;
    private String fibre;


    public FoodProductInfo(String servingSizeGrams, String calories, String protein, String totalCarbs, String sugarCarbs, String totalFat, String satFat, String fibre, String salt) {
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

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(String totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public String getSugarCarbs() {
        return sugarCarbs;
    }

    public void setSugarCarbs(String sugarCarbs) {
        this.sugarCarbs = sugarCarbs;
    }

    public String getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(String totalFat) {
        this.totalFat = totalFat;
    }

    public String getSatFat() {
        return satFat;
    }

    public void setSatFat(String satFat) {
        this.satFat = satFat;
    }

    public String getServingSizeGrams() {
        return servingSizeGrams;
    }

    public void setServingSizeGrams(String servingSizeGrams) {
        this.servingSizeGrams = servingSizeGrams;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFibre() {
        return fibre;
    }

    public void setFibre(String fibre) {
        this.fibre = fibre;
    }
}
