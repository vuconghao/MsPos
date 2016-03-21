package project.mspos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import project.mspos.R;
import project.mspos.entity.ProductEntity;
import project.mspos.views.RecyclerViewHolders;

/**
 * Created by SON on 3/21/2016.
 */
public class GridViewProductAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<ProductEntity> itemList;
    private Context context;

    public GridViewProductAdapter(Context context, List<ProductEntity> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_product_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.imageProduct.setImageResource(itemList.get(position).getList_image().get(0).getmProductImage());
        holder.textViewProductId.setText(itemList.get(position).getmProductID()+"");
        holder.textViewProductPrice.setText(itemList.get(position).getmProductPrice()+"$");
        holder.textViewProductName.setText(itemList.get(position).getmProductName());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}