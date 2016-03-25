package project.mspos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import project.mspos.R;
import project.mspos.entity.ProductEntity;
import project.mspos.entity.RecyclerViewProductInCartItem;
import project.mspos.views.RecyclerViewHolderProductInCart;
import project.mspos.views.RecyclerViewHolders;

/**
 * Created by SON on 3/25/2016.
 */
public class RecyclerViewProductInCartAdapter extends RecyclerView.Adapter<RecyclerViewHolderProductInCart> {
    private List<RecyclerViewProductInCartItem> itemList;
    private Context context;

    public RecyclerViewProductInCartAdapter(List<RecyclerViewProductInCartItem> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolderProductInCart onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_in_cart_item, parent,false);
        RecyclerViewHolderProductInCart rcv = new RecyclerViewHolderProductInCart(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderProductInCart holder, int position) {
        holder.imgDeleteProductInCart.setImageResource(itemList.get(position).getImgProduct());
        holder.tvProductInCart.setText(itemList.get(position).getNameProduct());
        holder.imgDeleteProductInCart.setImageResource(R.drawable.delete_product);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
