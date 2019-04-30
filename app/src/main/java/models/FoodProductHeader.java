package models;

public class FoodProductHeader {

    private String barcodeNum;
    private String productName;
    private String brandName;

    public FoodProductHeader(String productName, String brandName, String barcodeNum){
        this.barcodeNum=barcodeNum;
        this.brandName=brandName;
        this.productName=productName;

    }

    public String getBarcodeNum() {
        return barcodeNum;
    }

    public void setBarcodeNum(String barcodeNum) {
        this.barcodeNum = barcodeNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
