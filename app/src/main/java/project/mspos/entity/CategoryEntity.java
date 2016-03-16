package project.mspos.entity;

import java.util.ArrayList;

/**
 * Created by CONGHAO on 3/16/2016.
 */
public class CategoryEntity {

    private int mCategoryID;

    private String mCategoryName;

    private int mCategoryImage;

    private ArrayList<ProductEntity> list_product;

    public int getmCategoryID() {
        return mCategoryID;
    }

    public void setmCategoryID(int mCategoryID) {
        this.mCategoryID = mCategoryID;
    }

    public ArrayList<ProductEntity> getList_product() {
        return list_product;
    }

    public void setList_product(ArrayList<ProductEntity> list_product) {
        this.list_product = list_product;
    }

    public int getmCategoryImage() {
        return mCategoryImage;
    }

    public void setmCategoryImage(int mCategoryImage) {
        this.mCategoryImage = mCategoryImage;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }
}
