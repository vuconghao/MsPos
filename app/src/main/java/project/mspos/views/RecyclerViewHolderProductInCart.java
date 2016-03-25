package project.mspos.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import project.mspos.R;

/**
 * Created by SON on 3/25/2016.
 */
public class RecyclerViewHolderProductInCart extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ImageView imgProductIncart;
    public TextView tvProductInCart;
    public ImageView imgDeleteProductInCart;
    public RecyclerViewHolderProductInCart(View itemView) {
        super(itemView);
        imgProductIncart=(ImageView)itemView.findViewById(R.id.img_item_product_cart);
        tvProductInCart=(TextView)itemView.findViewById(R.id.tv_name_product_in_cart);
        imgDeleteProductInCart=(ImageView)itemView.findViewById(R.id.img_delete_product_item);
    }

    @Override
    public void onClick(View v) {

    }
}
