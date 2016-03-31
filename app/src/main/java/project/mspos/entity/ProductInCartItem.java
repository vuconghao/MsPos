package project.mspos.entity;


/**
 * Created by SON on 3/25/2016.
 */
public class ProductInCartItem {
    private int imgProduct;
    private String nameProduct;
    private int numberProduct;
    private float priceProduct;

    public ProductInCartItem(){

    }

    public ProductInCartItem(int imgProduct, String nameProduct, int numberProduct,float priceProduct) {
        this.imgProduct = imgProduct;
        this.nameProduct = nameProduct;
        this.numberProduct = numberProduct;
        this.priceProduct=priceProduct;
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

    public float getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(float priceProduct) {
        this.priceProduct = priceProduct;
    }
}
