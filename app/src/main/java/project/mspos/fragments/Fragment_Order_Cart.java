package project.mspos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import project.mspos.R;
import project.mspos.adapter.RecyclerViewProductInCartAdapter;
import project.mspos.entity.RecyclerViewProductInCartItem;
import project.mspos.views.RecyclerViewHolderProductInCart;

/**
 * Created by SON on 3/25/2016.
 */
public class Fragment_Order_Cart extends Fragment {
    RecyclerView recyclerViewProductInCart;
    RecyclerViewProductInCartAdapter recyclerViewProductInCartAdapter;
    ArrayList<RecyclerViewProductInCartItem> listProductInCart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_order_cart,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupView();
        setDefaultProductInCart();
        setAdapterForRecyclerView();
    }

    private void setAdapterForRecyclerView() {
        recyclerViewProductInCartAdapter=new RecyclerViewProductInCartAdapter(listProductInCart,getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewProductInCart.setLayoutManager(mLayoutManager);
        recyclerViewProductInCart.setItemAnimator(new DefaultItemAnimator());
        recyclerViewProductInCart.setAdapter(recyclerViewProductInCartAdapter);
    }

    private void setDefaultProductInCart() {
        RecyclerViewProductInCartItem glass=new RecyclerViewProductInCartItem(R.drawable.glass,"Sun Glass",1);
        listProductInCart.add(glass);
    }

    private void setupView() {
        listProductInCart=new ArrayList<RecyclerViewProductInCartItem>();
        recyclerViewProductInCart=(RecyclerView)getActivity().findViewById(R.id.recycle_view_product_in_cart);
    }
}
