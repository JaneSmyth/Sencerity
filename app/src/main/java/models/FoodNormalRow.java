package models;

public class FoodNormalRow extends RecyclerViewItem{

    String mProduct;
    Double mCalories;
    Double mServing;
    String mGrade;

    public FoodNormalRow(String mProduct, Double mCalories, Double mServing, String mGrade) {
        this.mProduct = mProduct;
        this.mCalories = mCalories;
        this.mServing = mServing;
        this.mGrade = mGrade;
    }

    public String getmProduct() {
        return mProduct;
    }

    public void setmProduct(String mProduct) {
        this.mProduct = mProduct;
    }

    public Double getmCalories() {
        return mCalories;
    }

    public void setmCalories(Double mCalories) {
        this.mCalories = mCalories;
    }

    public Double getmServing() {
        return mServing;
    }

    public void setmServing(Double mServing) {
        this.mServing = mServing;
    }

    public String getmGrade() {
        return mGrade;
    }

    public void setmGrade(String mGrade) {
        this.mGrade = mGrade;
    }
}
