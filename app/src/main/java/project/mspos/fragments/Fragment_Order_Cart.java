package project.mspos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import project.mspos.R;
import project.mspos.activity.MainActivity;
import project.mspos.adapter.ListCustomerAdapter;
import project.mspos.adapter.ListProductBoughtAdapter;
import project.mspos.entity.ProductInCartItem;

/**
 * Created by SON on 3/25/2016.
 */
public class Fragment_Order_Cart extends Fragment implements View.OnClickListener {
    ListView listViewProductInCart;
    ListProductBoughtAdapter listProductBoughtAdapter;
    RelativeLayout layout_customer_cart;
    PopupWindow popupWindow;
    OpenDialogCustomerInteface mCallback;
    boolean popupWindowAlready=false;
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
        setAdapterForRecyclerView();
        registerForEvent();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallback=(OpenDialogCustomerInteface)activity;
    }

    private void registerForEvent() {
        layout_customer_cart.setOnClickListener(this);
    }

    private void setAdapterForRecyclerView() {
        listProductBoughtAdapter=new ListProductBoughtAdapter(getActivity(),R.layout.product_in_cart_item,MainActivity.listProductInCart);
        listViewProductInCart.setAdapter(listProductBoughtAdapter);
    }


    private void setupView() {
        listViewProductInCart=(ListView)getActivity().findViewById(R.id.recycle_view_product_in_cart);
        layout_customer_cart=(RelativeLayout)getActivity().findViewById(R.id.layout_customer_cart);
    }

    @Override
    public void onClick(View v) {
        if(popupWindowAlready){
            popupWindow.setFocusable(false);
            popupWindow.dismiss();
        }
        switch (v.getId()){
            case R.id.layout_customer_cart:
                addListCustomerDiaLog();
        }
        
    }

    private void addListCustomerDiaLog() {
        popupWindowAlready=true;
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.layout_create_customer, null);
        popupWindow = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.MATCH_PARENT);
        Button btCreateCustomer=(Button)popupView.findViewById(R.id.button_create_customer);
        EditText editSearchCustomer=(EditText)popupView.findViewById(R.id.edit_text_search_customer);
        ImageView imgBack=(ImageView)popupView.findViewById(R.id.img_list_customer_back);
        popupView.setFocusable(true);
        popupWindow.setFocusable(true);
        ListView listCustomerView=(ListView)popupView.findViewById(R.id.list_customer);
        ListCustomerAdapter listCustomerAdapter=new ListCustomerAdapter(getActivity(),R.layout.list_customer_item,MainActivity.listCustomer);
        listCustomerView.setAdapter(listCustomerAdapter);
        popupWindow.showAsDropDown(layout_customer_cart, -800, -30);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.setFocusable(false);
                popupWindow.dismiss();
            }
        });
        listCustomerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.openDialogCustomerInfo(position);
            }
        });


    }

    public interface OpenDialogCustomerInteface{
        public void openDialogCustomerInfo(int positionCustomer);
    }

    public void addProductInCart(ProductInCartItem productInCartItem){
        boolean isNewProduct=true;
        for(int i=0;i<MainActivity.listProductInCart.size();i++){
            if(MainActivity.listProductInCart.get(i).getNameProduct().equals(productInCartItem.getNameProduct())){
                MainActivity.listProductInCart.get(i).setNumberProduct(MainActivity.listProductInCart.get(i).getNumberProduct()+1);
                MainActivity.listProductInCart.get(i).setPriceProduct(MainActivity.listProductInCart.get(i).getPriceProduct()+productInCartItem.getPriceProduct());
                isNewProduct=false;
            }
        }
        if(isNewProduct){
            MainActivity.listProductInCart.add(productInCartItem);
        }
        listProductBoughtAdapter.notifyDataSetChanged();
    }

    public void deleteProductInCart(String productInCartName){
        for(int i=0;i<MainActivity.listProductInCart.size();i++){
            if(MainActivity.listProductInCart.get(i).getNameProduct().equals(productInCartName)){
                MainActivity.listProductInCart.remove(i);
            }
        }
        listProductBoughtAdapter.notifyDataSetChanged();
    }


}
