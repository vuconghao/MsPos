package project.mspos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
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
    PopupWindow popupWindowOne;
    PopupWindow popupWindowTwo;
    Button buttonCheckOut;
    OpenDialogCustomerInteface mCallback;
    OpenDialogCustomDiscountInterface callBackOpenDiscountDialog;
    Boolean isDollar=false;
    Boolean isCustomPrice=false;
    TextView tvCustomPriceOrDiscount;
    Switch switchCustomPriceDiscount;
    TextView tvDiscountAmountInput;
    Button btNumberOne;
    Button btNumberTwo;
    Button btNumberThree;
    Button btNumberFour;
    Button btNumberFive;
    Button btNumberSix;
    Button btNumberSeven;
    Button btNumberEight;
    Button btNumberNine;
    Button btNumberZero;
    Button btNumberDoubleZero;
    private Button btDoneChangeDiscountAmount;
    private ImageView btBackChangeDiscountAmount;
    private Button btBackDiscountOrCustomPrice;
    private TextView tvDiscountAmount;
    ImageButton btBackspace;
    EditText editNumberProduct;
    float totalPrice=0;
    float discount=0;
    float tax=0;
    float realPrice=0;
    float selectionProductPrice=0;
    float selectionProductDiscount=0;
    float selectionProductCustomPrice=0;
    int numberSelectionProduct=0;
    int positionSelectionProduct=0;
    boolean isFirstChangeAmountSelectionProduct=true;
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
                if(popupWindowTwo!=null){
                    popupWindowTwo.dismiss();
                }
                if(popupWindowOne!=null){
                    popupWindowOne.dismiss();
                }
                addPopUpWindowCustomPriceDiscount(position);
            }
        });
    }

    private void addPopUpWindowCustomPriceDiscount(int positionProduct) {
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.dialog_custom_price_product_bought, null);
        popupWindowOne = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tvDiscountAmount=(TextView)popupView.findViewById(R.id.amount_discount_or_custom_price);
        editNumberProduct=(EditText)popupView.findViewById(R.id.edit_number_product_bought);
        TextView tvProductName=(TextView)popupView.findViewById(R.id.tv_prouduct_bought_name_custom_price);
        Button btSubtractProduct=(Button)popupView.findViewById(R.id.bt_substract_product_bought);
        Button btAddProduct=(Button)popupView.findViewById(R.id.bt_add_product_bought);
        Button btCustomPrice=(Button)popupView.findViewById(R.id.button_custom_price);
        Button btDiscount=(Button)popupView.findViewById(R.id.button_custom_discount);
        RelativeLayout layoutDiscountOrCustomPrice=(RelativeLayout)popupView.findViewById(R.id.layout_discount_or_custom_price);
        ProductInCartItem currentProduct=MainActivity.listProductInCart.get(positionProduct);
        selectionProductPrice=currentProduct.getPriceProduct();
        numberSelectionProduct=currentProduct.getNumberProduct();
        positionSelectionProduct=positionProduct;
        editNumberProduct.setText(currentProduct.getNumberProduct() + "");
        tvProductName.setText(currentProduct.getNameProduct());
        tvDiscountAmount.setText(currentProduct.getPriceProduct()+"$");
        popupWindowOne.showAtLocation(layout_customer_cart, Gravity.CENTER, 0, positionProduct * (-2));
        btCustomPrice.setOnClickListener(this);
        btDiscount.setOnClickListener(this);
        btAddProduct.setOnClickListener(this);
        btSubtractProduct.setOnClickListener(this);
        layoutDiscountOrCustomPrice.setOnClickListener(this);
    }

    public void addPopupWindowCustomDiscountAmount(boolean priceOrDiscount,int positonProduct){
        LayoutInflater layoutInflater
                = (LayoutInflater)getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.dialog_custom_price_or_discount_input, null);
        popupWindowTwo = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        isDollar=false;
        tvCustomPriceOrDiscount=(TextView)popupView.findViewById(R.id.tv_discount_custom_price);
        switchCustomPriceDiscount=(Switch) popupView.findViewById(R.id.switch_type_custom_price_discount);
        tvDiscountAmountInput=(TextView)popupView.findViewById(R.id.textview_amount_custom_price_discount);
        btNumberOne=(Button)popupView.findViewById(R.id.button_number_one_custom_price_discount);
        btNumberTwo=(Button)popupView.findViewById(R.id.button_number_two_custom_price_discount);
        btNumberThree=(Button)popupView.findViewById(R.id.button_number_three_custom_price_discount);
        btNumberFour=(Button)popupView.findViewById(R.id.button_number_four_custom_price_discount);
        btNumberFive=(Button)popupView.findViewById(R.id.button_number_five_custom_price_discount);
        btNumberSix=(Button)popupView.findViewById(R.id.button_number_six_custom_price_discount);
        btNumberSeven=(Button)popupView.findViewById(R.id.button_number_seven_custom_price_discount);
        btNumberEight=(Button)popupView.findViewById(R.id.button_number_eight_custom_price_discount);
        btNumberNine=(Button)popupView.findViewById(R.id.button_number_nine_custom_price_discount);
        btNumberZero=(Button)popupView.findViewById(R.id.button_number_zero_custom_price_discount);
        btNumberDoubleZero=(Button)popupView.findViewById(R.id.button_number_double_zero_custom_price_discount);
        btBackspace=(ImageButton)popupView.findViewById(R.id.button_backspace_custom_price_discount);
        btDoneChangeDiscountAmount=(Button)popupView.findViewById(R.id.button_done_custom_price_discount);
        btBackChangeDiscountAmount=(ImageView)popupView.findViewById(R.id.button_back_custom_price_discount_amount);

        if(priceOrDiscount){
            tvCustomPriceOrDiscount.setText("Custom Price");
            isCustomPrice=true;
        }else {
            tvCustomPriceOrDiscount.setText("Discount");
            isCustomPrice=false;
        }
        if(switchCustomPriceDiscount.isChecked()){
            isDollar=true;
        }else {
            isDollar=false;
        }
        ProductInCartItem productInCartItem=MainActivity.listProductInCart.get(positonProduct);
        btNumberOne.setOnClickListener(this);
        btNumberTwo.setOnClickListener(this);
        btNumberThree.setOnClickListener(this);
        btNumberFour.setOnClickListener(this);
        btNumberFive.setOnClickListener(this);
        btNumberSix.setOnClickListener(this);
        btNumberSeven.setOnClickListener(this);
        btNumberEight.setOnClickListener(this);
        btNumberNine.setOnClickListener(this);
        btNumberZero.setOnClickListener(this);
        btNumberDoubleZero.setOnClickListener(this);
        btBackspace.setOnClickListener(this);
        btDoneChangeDiscountAmount.setOnClickListener(this);
        btBackChangeDiscountAmount.setOnClickListener(this);
        switchCustomPriceDiscount.setOnClickListener(this);

        popupWindowTwo.showAtLocation(layout_customer_cart, Gravity.CENTER, 0, positonProduct * (-2));
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
        if(popupWindowTwo!=null){
            popupWindowTwo.dismiss();
        }
        if(popupWindowOne!=null){
            popupWindowOne.dismiss();
        }
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
            case R.id.button_number_one_custom_price_discount:
                addPrice(1, tvDiscountAmountInput);
                break;
            case R.id.button_number_two_custom_price_discount:
                addPrice(2,tvDiscountAmountInput);
                break;
            case R.id.button_number_three_custom_price_discount:
                addPrice(3,tvDiscountAmountInput);
                break;
            case R.id.button_number_four_custom_price_discount:
                addPrice(4,tvDiscountAmountInput);
                break;
            case R.id.button_number_five_custom_price_discount:
                addPrice(5,tvDiscountAmountInput);
                break;
            case R.id.button_number_six_custom_price_discount:
                addPrice(6,tvDiscountAmountInput);
                break;
            case R.id.button_number_seven_custom_price_discount:
                addPrice(7,tvDiscountAmountInput);
                break;
            case R.id.button_number_eight_custom_price_discount:
                addPrice(8,tvDiscountAmountInput);
                break;
            case R.id.button_number_nine_custom_price_discount:
                addPrice(9,tvDiscountAmountInput);
                break;
            case R.id.button_number_zero_custom_price_discount:
                multiplePrice(10, tvDiscountAmountInput);
                break;
            case R.id.button_number_double_zero_custom_price_discount:
                multiplePrice(100, tvDiscountAmountInput);
                break;
            case R.id.button_backspace_custom_price_discount:
                backSpace(tvDiscountAmountInput);
                break;
            case R.id.button_custom_price:
                addPopupWindowCustomDiscountAmount(true, positionSelectionProduct);
                break;
            case R.id.button_custom_discount:
                addPopupWindowCustomDiscountAmount(false, positionSelectionProduct);
                break;
            case R.id.button_done_custom_price_discount:
                doneCustomPriceOrDiscount();
                break;
            case R.id.button_back_custom_price_discount_amount:
                popupWindowTwo.dismiss();
                break;
            case R.id.layout_discount_or_custom_price:
                addPopupWindowCustomDiscountAmount(false, positionSelectionProduct);
                break;
            case R.id.switch_type_custom_price_discount:
                isDollar=!isDollar;
                break;
            case R.id.bt_add_product_bought:
                addCurrentProduct();
                break;
            case R.id.bt_substract_product_bought:
                substractCurrentProduct();
        }
        
    }

    private void substractCurrentProduct() {
        int numberCurrentProduct=MainActivity.listProductInCart.get(positionSelectionProduct).getNumberProduct();
        if(numberCurrentProduct>=1) {
            float newTotalPrice = MainActivity.listProductInCart.get(positionSelectionProduct).getPriceProduct() / numberCurrentProduct * (numberCurrentProduct -1);
            numberCurrentProduct--;
            editNumberProduct.setText(numberCurrentProduct+"");
            selectionProductPrice=newTotalPrice;
            MainActivity.listProductInCart.get(positionSelectionProduct).setPriceProduct(newTotalPrice);
            MainActivity.listProductInCart.get(positionSelectionProduct).setNumberProduct(numberCurrentProduct);
            if(numberCurrentProduct==0){
                MainActivity.listProductInCart.remove(positionSelectionProduct);
            }
            listProductBoughtAdapter.notifyDataSetChanged();
        }
    }

    private void addCurrentProduct() {
        int numberCurrentProduct=MainActivity.listProductInCart.get(positionSelectionProduct).getNumberProduct();
        if(numberCurrentProduct!=0) {
            float newTotalPrice = MainActivity.listProductInCart.get(positionSelectionProduct).getPriceProduct() / numberCurrentProduct * (numberCurrentProduct + 1);
            numberCurrentProduct++;
            editNumberProduct.setText(numberCurrentProduct + "");
            selectionProductPrice = newTotalPrice;
            MainActivity.listProductInCart.get(positionSelectionProduct).setPriceProduct(newTotalPrice);
            MainActivity.listProductInCart.get(positionSelectionProduct).setNumberProduct(numberCurrentProduct);
            listProductBoughtAdapter.notifyDataSetChanged();
        }
    }

    private void doneCustomPriceOrDiscount() {
        if(isCustomPrice){
            if(isDollar)
                selectionProductPrice=selectionProductCustomPrice;
            else
                selectionProductPrice=selectionProductPrice*selectionProductCustomPrice/100;
        }else {
            if(isDollar){
                if(selectionProductDiscount<selectionProductPrice){
                    selectionProductPrice-=selectionProductDiscount;
                }else {
                    Toast.makeText(getActivity(),"The discount is bigger than current price",Toast.LENGTH_SHORT).show();
                    return;
                }
            }else {
                if(selectionProductDiscount<100){
                    selectionProductPrice=selectionProductPrice*(100-selectionProductDiscount)/100;
                }else {
                    Toast.makeText(getActivity(),"The discount percent is bigger than 100 percent",Toast.LENGTH_SHORT).show();
                    return;
                }
            }

        }
        selectionProductDiscount=0;
        selectionProductCustomPrice=0;
        MainActivity.listProductInCart.get(positionSelectionProduct).setPriceProduct(selectionProductPrice);
        listProductBoughtAdapter.notifyDataSetChanged();
        popupWindowTwo.dismiss();
        popupWindowOne.dismiss();
    }

    private void addPrice(int number,TextView tvDiscountOrCustomPrice){
        if(!isCustomPrice) {
            selectionProductDiscount = (Float) selectionProductDiscount * 10 + number;
            if(isDollar) {
                tvDiscountOrCustomPrice.setText(selectionProductDiscount + "$" + "");
            }else {
                tvDiscountOrCustomPrice.setText(selectionProductDiscount + "%" + "");
            }
        }else {
            selectionProductCustomPrice=(Float)selectionProductCustomPrice*10+number;
            if(isDollar){
                tvDiscountOrCustomPrice.setText(selectionProductCustomPrice+"$");
            }else {
                tvDiscountOrCustomPrice.setText(selectionProductCustomPrice+"%");
            }
        }

    }
    private void backSpace(TextView tvDiscountOrCustomPrice){
//        float priceSubstract=selectionProductDiscount%10;
//        selectionProductDiscount=(selectionProductDiscount-priceSubstract)/10;
//        tvPrice.setText(selectionProductDiscount+"$"+"");
        if(!isCustomPrice) {
            float discountSubstract=selectionProductDiscount%10;
            selectionProductDiscount=(selectionProductDiscount-discountSubstract)/10;
            if(isDollar) {
                tvDiscountOrCustomPrice.setText(selectionProductDiscount + "$" + "");
            }else {
                tvDiscountOrCustomPrice.setText(selectionProductDiscount + "%" + "");
            }
        }else {
            float priceSubstract;
            priceSubstract=selectionProductCustomPrice%10;
            selectionProductCustomPrice=(selectionProductCustomPrice-priceSubstract)/10;
            if(isDollar) {
                tvDiscountOrCustomPrice.setText(selectionProductCustomPrice + "$" + "");
            }else {
                tvDiscountOrCustomPrice.setText(selectionProductCustomPrice + "%" + "");
            }
        }
    }
    private void multiplePrice(int number,TextView tvDiscountOrCustomPrice){
//        selectionProductDiscount=selectionProductDiscount*number;
//        tvPrice.setText(selectionProductDiscount+"$"+"");
        if(!isCustomPrice) {
            selectionProductDiscount=selectionProductDiscount*number;
            if(isDollar) {
                tvDiscountOrCustomPrice.setText(selectionProductDiscount + "$" + "");
            }else {
                tvDiscountOrCustomPrice.setText(selectionProductDiscount + "%" + "");
            }
        }else {
            selectionProductCustomPrice=selectionProductCustomPrice*number;
            if(isDollar) {
                tvDiscountOrCustomPrice.setText(selectionProductCustomPrice + "$" + "");
            }else {
                tvDiscountOrCustomPrice.setText(selectionProductCustomPrice + "%" + "");
            }
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
        popupWindowOne = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btSave=(Button)popupView.findViewById(R.id.button_save_comment_order);
        EditText editComment=(EditText)popupView.findViewById(R.id.edittext_comment_order);
        ImageView imgBack=(ImageView)popupView.findViewById(R.id.button_Comment_Order_Back);
        popupView.setFocusable(true);
        popupWindowOne.setFocusable(true);
        popupWindowOne.showAsDropDown(imageCommentCart);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowOne.setFocusable(false);
                popupWindowOne.dismiss();
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
        popupWindowOne = new PopupWindow(
                popupView,
                800,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        Button btCreateCustomer=(Button)popupView.findViewById(R.id.button_create_customer);
        EditText editSearchCustomer=(EditText)popupView.findViewById(R.id.edit_text_search_customer);
        ImageView imgBack=(ImageView)popupView.findViewById(R.id.img_list_customer_back);
        popupView.setFocusable(true);
        popupWindowOne.setFocusable(true);
        ListView listCustomerView=(ListView)popupView.findViewById(R.id.list_customer);
        ListCustomerAdapter listCustomerAdapter=new ListCustomerAdapter(getActivity(),R.layout.list_customer_item,MainActivity.listCustomer);
        listCustomerView.setAdapter(listCustomerAdapter);
        popupWindowOne.showAsDropDown(layout_customer_cart);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowOne.setFocusable(false);
                popupWindowOne.dismiss();
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
