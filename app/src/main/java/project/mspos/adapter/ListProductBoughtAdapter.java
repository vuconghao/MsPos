package project.mspos.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.mspos.R;
import project.mspos.entity.ProductEntity;
import project.mspos.entity.ProductInCartItem;

/**
 * Created by SON on 3/30/2016.
 */
public class ListProductBoughtAdapter extends ArrayAdapter<ProductInCartItem> {
    private Activity activity;
    private ArrayList<ProductInCartItem> listProductBought;
    DeleteProductInCartInterface mCallBack;
    public ListProductBoughtAdapter(Activity context, int resource, ArrayList<ProductInCartItem> objects) {
        super(context, resource, objects);
        this.activity=context;
        this.listProductBought=objects;
        mCallBack=(DeleteProductInCartInterface)activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=activity.getLayoutInflater();
            convertView=inflater.inflate(R.layout.product_in_cart_item,parent,false);
        }
        final ImageView imgProduct=(ImageView)convertView.findViewById(R.id.img_item_product_cart);
        final TextView tvNameProductIncart=(TextView)convertView.findViewById(R.id.tv_name_product_in_cart);
        final ImageView imgDeleteProductInCart=(ImageView)convertView.findViewById(R.id.img_delete_product_item);
        final TextView tvPriceProduct=(TextView)convertView.findViewById(R.id.tv_price_product_in_cart);
        final TextView tvNumberProduct=(TextView)convertView.findViewById(R.id.tv_number_product_in_cart);

        imgProduct.setImageResource(listProductBought.get(position).getImgProduct());
        tvNameProductIncart.setText(listProductBought.get(position).getNameProduct());
        tvPriceProduct.setText(listProductBought.get(position).getPriceProduct()+"$");
        tvNumberProduct.setText(listProductBought.get(position).getNumberProduct()+"");
        imgDeleteProductInCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.deleteProductInCart(listProductBought.get(position).getNameProduct());
            }
        });

        return convertView;

    }

    public interface DeleteProductInCartInterface{
        public void deleteProductInCart(String productName);
    }
}
