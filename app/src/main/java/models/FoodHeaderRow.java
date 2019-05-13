package models;

public class FoodHeaderRow extends RecyclerViewItem{

    String mealType;

    public FoodHeaderRow(String mealType) {
        this.mealType = mealType;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
