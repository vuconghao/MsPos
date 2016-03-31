package project.mspos.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import project.mspos.R;

/**
 * Created by SON on 3/21/2016.
 */
public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView imageProduct;
    public TextView textViewProductId;
    public TextView textViewProductPrice;
    public TextView textViewProductName;
    public RecyclerViewHolders(View itemView) {
        super(itemView);
        imageProduct=(ImageView)itemView.findViewById(R.id.image_product);
        textViewProductId=(TextView)itemView.findViewById(R.id.textview_product_id);
        textViewProductName=(TextView)itemView.findViewById(R.id.textview_prduct_name);
        textViewProductPrice=(TextView)itemView.findViewById(R.id.textview_prduct_price);
    }
    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Product Position = " + getPosition(), Toast.LENGTH_SHORT).show();
    }
}
