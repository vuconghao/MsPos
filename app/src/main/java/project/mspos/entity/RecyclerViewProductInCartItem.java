package project.mspos.entity;

import project.mspos.views.RecyclerViewHolderProductInCart;

/**
 * Created by SON on 3/25/2016.
 */
public class RecyclerViewProductInCartItem {
    private int imgProduct;
    private String nameProduct;
    private int numberProduct;

    public RecyclerViewProductInCartItem(){

    }

    public RecyclerViewProductInCartItem(int imgProduct, String nameProduct, int numberProduct) {
        this.imgProduct = imgProduct;
        this.nameProduct = nameProduct;
        this.numberProduct = numberProduct;
    }

    public int getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(int imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public int getNumberProduct() {
        return numberProduct;
    }

    public void setNumberProduct(int numberProduct) {
        this.numberProduct = numberProduct;
    }
}
