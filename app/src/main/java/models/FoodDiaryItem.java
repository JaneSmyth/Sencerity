package models;

import java.util.Map;

public class FoodDiaryItem {


    private String product;
    private String brand;
    private String barcode;
    private Double calories;
    private Double inputServSize;
    private String mealType;
    private Map<String, Object> timestamp;


    public FoodDiaryItem(String product, String brand, String barcode, Double calories, Double inputServSize, String mealType, Map<String, Object> timestamp) {
        this.product = product;
        this.brand = brand;
        this.barcode = barcode;
        this.calories = calories;
        this.inputServSize = inputServSize;
        this.mealType = mealType;
        this.timestamp = timestamp;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getInputServSize() {
        return inputServSize;
    }

    public void setInputServSize(Double inputServSize) {
        this.inputServSize = inputServSize;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public Map<String, Object> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Map<String, Object> timestamp) {
        this.timestamp = timestamp;
    }
}
