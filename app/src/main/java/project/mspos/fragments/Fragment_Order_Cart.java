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
import android.widget.TextView;
import android.widget.Toast;

import project.mspos.R;
import project.mspos.activity.MainActivity;
import project.mspos.adapter.ListCustomerAdapter;
import project.mspos.adapter.ListProductBoughtAdapter;
import project.mspos.entity.DiscountEntity;
import project.mspos.entity.DiscountType;
import project.mspos.entity.ProductInCartItem;

/**
 * Created by SON on 3/25/2016.
 */
public class Fragment_Order_Cart extends Fragment implements View.OnClickListener {
    ImageView imgAddDiscount;
    TextView tvDiscountName;
    TextView tvAmountDiscount;
    ImageView imageDeleteAllProduct;
    ImageView imageCommentCart;
    TextView tvTotalPrice;
    ListView listViewProductInCart;
    ListProductBoughtAdapter listProductBoughtAdapter;
    RelativeLayout layout_customer_cart;
    RelativeLayout layout_add_discount;
    RelativeLayout layout_tax;
    TextView tvAmountTax;
    PopupWindow popupWindow;
    Button buttonCheckOut;
    OpenDialogCustomerInteface mCallback;
    OpenDialogCustomDiscountInterface callBackOpenDiscountDialog;
    float totalPrice=0;
    float discount=0;
    float tax=0;
    float realPrice=0;
    boolean popupWindowAlready=false;
    boolean popupCommentOrderAlready=false;
    boolean discountAlready=false;
    boolean productAlready=false;
    DiscountEntity currentDiscount=new DiscountEntity();
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
        callBackOpenDiscountDialog=(OpenDialogCustomDiscountInterface)activity;
    }

    private void registerForEvent() {
        layout_customer_cart.setOnClickListener(this);
        imageDeleteAllProduct.setOnClickListener(this);
        imageCommentCart.setOnClickListener(this);
        layout_add_discount.setOnClickListener(this);
        listViewProductInCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addPopUpWindowCustomPriceDiscount(position);
            }
        });
    }

    private void addPopUpWindowCustomPriceDiscount(int positionProduct) {
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.dialog_custom_price_product_bought, null);
        popupWindow = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        EditText editNumberProduct=(EditText)popupView.findViewById(R.id.edit_number_product_bought);
        TextView tvProductName=(TextView)popupView.findViewById(R.id.tv_prouduct_bought_name_custom_price);
        Button btSubtractProduct=(Button)popupView.findViewById(R.id.bt_substract_product_bought);
        Button btAddProduct=(Button)popupView.findViewById(R.id.bt_add_product_bought);
        Button btCustomPrice=(Button)popupView.findViewById(R.id.button_custom_price);
        Button btDiscount=(Button)popupView.findViewById(R.id.button_custom_discount);
        RelativeLayout layoutDiscountOrCustomPrice=(RelativeLayout)popupView.findViewById(R.id.layout_discount_or_custom_price);
        ProductInCartItem currentProduct=MainActivity.listProductInCart.get(positionProduct);

        editNumberProduct.setText(currentProduct.getNumberProduct()+"");
        tvProductName.setText(currentProduct.getNameProduct());
        popupWindow.showAsDropDown(listViewProductInCart);

    }

    private void setAdapterForRecyclerView() {
        listProductBoughtAdapter=new ListProductBoughtAdapter(getActivity(),R.layout.product_in_cart_item,MainActivity.listProductInCart);
        listViewProductInCart.setAdapter(listProductBoughtAdapter);
    }


    private void setupView() {
        listViewProductInCart=(ListView)getActivity().findViewById(R.id.recycle_view_product_in_cart);
        layout_customer_cart=(RelativeLayout)getActivity().findViewById(R.id.layout_customer_cart);
        imageDeleteAllProduct=(ImageView)getActivity().findViewById(R.id.img_delete_cart);
        imageCommentCart=(ImageView)getActivity().findViewById(R.id.img_comment_cart);
        layout_add_discount=(RelativeLayout)getActivity().findViewById(R.id.layout_add_cart_discount);
        imgAddDiscount=(ImageView)getActivity().findViewById(R.id.button_add_cart_discount);
        tvDiscountName=(TextView)getActivity().findViewById(R.id.tv_name_discount_cart);
        tvAmountDiscount=(TextView)getActivity().findViewById(R.id.tv_Discount_Amount_Cart);
        tvTotalPrice=(TextView)getActivity().findViewById(R.id.tv_total_price);
        layout_tax=(RelativeLayout)getActivity().findViewById(R.id.layout_tax);
        tvAmountTax=(TextView)getActivity().findViewById(R.id.tv_Amount_Tax);
        buttonCheckOut=(Button)getActivity().findViewById(R.id.btCheckoutCart);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_customer_cart:
                addListCustomerDiaLog();
                break;
            case R.id.layout_add_cart_discount:
                addOrEditDiscount(discountAlready);
                break;
            case R.id.button_create_customer:
                mCallback.openDialogCustomerInfo(MainActivity.NO_CUSTOMER);
                break;
            case R.id.img_delete_cart:
                MainActivity.listProductInCart.clear();
                listProductBoughtAdapter.notifyDataSetChanged();
                break;
            case R.id.img_comment_cart:
                showCommentDialog();
                break;
            case R.id.button_save_comment_order:
                Toast.makeText(getActivity(),"Order comment saved",Toast.LENGTH_SHORT).show();
                break;

        }
        
    }

    private void addOrEditDiscount(boolean discountAlready) {
        if(!discountAlready){
            callBackOpenDiscountDialog.openDialogCustomDiscount(new DiscountEntity("", DiscountType.MONEY,0.0f,""));
        }else {
            callBackOpenDiscountDialog.openDialogCustomDiscount(currentDiscount);
        }
    }

    private void showCommentDialog() {
        popupCommentOrderAlready=true;
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.layout_comment_order, null);
        popupWindow = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btSave=(Button)popupView.findViewById(R.id.button_save_comment_order);
        EditText editComment=(EditText)popupView.findViewById(R.id.edittext_comment_order);
        ImageView imgBack=(ImageView)popupView.findViewById(R.id.button_Comment_Order_Back);
        popupView.setFocusable(true);
        popupWindow.setFocusable(true);
        popupWindow.showAsDropDown(imageCommentCart);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.setFocusable(false);
                popupWindow.dismiss();
            }
        });
        btSave.setOnClickListener(this);
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
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btCreateCustomer=(Button)popupView.findViewById(R.id.button_create_customer);
        EditText editSearchCustomer=(EditText)popupView.findViewById(R.id.edit_text_search_customer);
        ImageView imgBack=(ImageView)popupView.findViewById(R.id.img_list_customer_back);
        popupView.setFocusable(true);
        popupWindow.setFocusable(true);
        ListView listCustomerView=(ListView)popupView.findViewById(R.id.list_customer);
        ListCustomerAdapter listCustomerAdapter=new ListCustomerAdapter(getActivity(),R.layout.list_customer_item,MainActivity.listCustomer);
        listCustomerView.setAdapter(listCustomerAdapter);
        popupWindow.showAsDropDown(layout_customer_cart);
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
        btCreateCustomer.setOnClickListener(this);


    }

    public void addDiscount(DiscountEntity discount) {
        discountAlready=true;
        imgAddDiscount.setVisibility(View.GONE);
        tvAmountDiscount.setVisibility(View.VISIBLE);
        tvDiscountName.setText(discount.getNameDiscount());
        if(discount.getDiscountType()==DiscountType.MONEY) {
            tvAmountDiscount.setText(discount.getAmount() + "$");
            this.discount=discount.getAmount();
        }
        else{
            float discountAmount=discount.getAmount()*totalPrice/100;
            tvAmountDiscount.setText(discountAmount+"$");
            this.discount=discountAmount;
        }
        currentDiscount=discount;

    }

    public void removeDiscount() {
        if(discountAlready){
            discountAlready=false;
            imgAddDiscount.setVisibility(View.VISIBLE);
            tvAmountDiscount.setVisibility(View.GONE);
            tvAmountDiscount.setText("");
            tvDiscountName.setText("Add Discount");
            currentDiscount=new DiscountEntity();
        }
    }

    public interface OpenDialogCustomerInteface{
        public void openDialogCustomerInfo(int positionCustomer);
    }

    public interface OpenDialogCustomDiscountInterface{
        public void openDialogCustomDiscount(DiscountEntity discountEntity);
    }

    public void addProductInCart(ProductInCartItem productInCartItem){
        productAlready=true;
        totalPrice+=productInCartItem.getPriceProduct();
        tvTotalPrice.setText(totalPrice+"$");
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
        updateLayoutTax();
        updateRealPrice();
    }

    private void updateRealPrice() {
        realPrice=totalPrice-discount+tax;
        buttonCheckOut.setText("Checkout "+realPrice+"$");

    }

    private void updateLayoutTax() {
        if(productAlready){
            layout_tax.setVisibility(View.VISIBLE);
            tax=(totalPrice-discount)*0.1f;
            tvAmountTax.setText(tax+"$");
        }else {
            tax=0;
            layout_tax.setVisibility(View.GONE);
            tvAmountTax.setText("");
        }
    }

    public void deleteProductInCart(String productInCartName){
        for(int i=0;i<MainActivity.listProductInCart.size();i++){
            if(MainActivity.listProductInCart.get(i).getNameProduct().equals(productInCartName)){
                totalPrice-=MainActivity.listProductInCart.get(i).getPriceProduct();
                MainActivity.listProductInCart.remove(i);
                }
        }
        listProductBoughtAdapter.notifyDataSetChanged();
        if(MainActivity.listProductInCart.isEmpty())
            productAlready=false;
        tvTotalPrice.setText(totalPrice + "$");
        updateLayoutTax();
        updateRealPrice();
    }


}
