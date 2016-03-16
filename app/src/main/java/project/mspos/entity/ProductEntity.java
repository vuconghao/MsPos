package project.mspos.entity;

import java.util.ArrayList;

/**
 * Created by CONGHAO on 3/16/2016.
 */
public class ProductEntity {
    private int mProductID;
    private String mProductName;
    private Boolean mProductStock;
    private String mProductSymboy;
    private float mProductPrice;
    private String mProductDesciption;
    private ArrayList<ProductImage> list_image;
    private ArrayList<ProductEntity> list_productRelated;

    public int getmProductID() {
        return mProductID;
    }

    public void setmProductID(int mProductID) {
        this.mProductID = mProductID;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public Boolean getmProductStock() {
        return mProductStock;
    }

    public void setmProductStock(Boolean mProductStock) {
        this.mProductStock = mProductStock;
    }

    public String getmProductSymboy() {
        return mProductSymboy;
    }

    public void setmProductSymboy(String mProductSymboy) {
        this.mProductSymboy = mProductSymboy;
    }

    public String getmProductDesciption() {
        return mProductDesciption;
    }

    public void setmProductDesciption(String mProductDesciption) {
        this.mProductDesciption = mProductDesciption;
    }

    public float getmProductPrice() {
        return mProductPrice;
    }

    public void setmProductPrice(float mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public ArrayList<ProductImage> getList_image() {
        return list_image;
    }

    public void setList_image(ArrayList<ProductImage> list_image) {
        this.list_image = list_image;
    }

    public ArrayList<ProductEntity> getList_productRelated() {
        return list_productRelated;
    }

    public void setList_productRelated(ArrayList<ProductEntity> list_productRelated) {
        this.list_productRelated = list_productRelated;
    }
}
