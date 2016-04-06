package project.mspos.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import project.mspos.R;
import project.mspos.adapter.ListProductBoughtAdapter;
import project.mspos.entity.CustomSale;
import project.mspos.entity.CustomerEntity;
import project.mspos.entity.DiscountEntity;
import project.mspos.entity.ProductInCartItem;
import project.mspos.fragments.CustomDiscountDialogFragment;
import project.mspos.fragments.CustomSaleFragment;
import project.mspos.fragments.CustomerInfoDialogFragment;
import project.mspos.fragments.FragmentDrawer;
import project.mspos.fragments.FragmentGiridViewProduct;
import project.mspos.fragments.Fragment_Order_Cart;
import project.mspos.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener,Fragment_Order_Cart.OpenDialogCustomerInteface,
FragmentGiridViewProduct.AddProductInCartInterface,ListProductBoughtAdapter.DeleteProductInCartInterface,FragmentGiridViewProduct.AddCustomsaleInterface,
CustomSaleFragment.AddCustomSaleToCartInterface,Fragment_Order_Cart.OpenDialogCustomDiscountInterface{
    public static final int NO_CUSTOMER=-1;
    SessionManager session;
    FrameLayout layoutLeft,layoutRight;
    private FragmentManager fragmentManager;
    private FragmentGiridViewProduct fragmentGiridViewProduct;
    private Fragment_Order_Cart fragment_order_cart;
    CustomSaleFragment customSaleFragment;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    public static ArrayList<CustomerEntity> listCustomer;
    public static ArrayList<ProductInCartItem> listProductInCart;
    private boolean doubleBackToExitPressedOnce=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session=new SessionManager(MainActivity.this);
        session.checkLogin();
        setupView();
        setDefaultData();
        setInitialLayout();
        addListCategoryFragment();
        addOrderCartFragment();
    }

    private void setDefaultData() {
        CustomerEntity customer1=new CustomerEntity("Jack","Ma","1234","jackma@gmail.com","","","Beijing","","China","","Alibaba","","","");
        listCustomer.add(customer1);
        CustomerEntity customer2=new CustomerEntity("Steve","Jobs","1234","steve@gmail.com","","","Whashington","","USA","","Apple","","","");
        listCustomer.add(customer2);
    }

    private void addOrderCartFragment() {
        fragment_order_cart=new Fragment_Order_Cart();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.frame_right,
                fragment_order_cart);
        fragmentTransaction.commit();
    }

    private void addListCategoryFragment() {
        fragmentGiridViewProduct=new FragmentGiridViewProduct();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.add(R.id.frame_left,
                fragmentGiridViewProduct);
        fragmentTransaction.commit();
    }

    private void setInitialLayout() {
        layoutLeft.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 1f));
        layoutRight.setLayoutParams(new LinearLayout.LayoutParams(0,
                MATCH_PARENT, 1f));

    }

    private void setupView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        layoutLeft=(FrameLayout)findViewById(R.id.frame_left);
        layoutRight=(FrameLayout)findViewById(R.id.frame_right);
        fragmentManager=getFragmentManager();
        listCustomer=new ArrayList<CustomerEntity>();
        listProductInCart=new ArrayList<ProductInCartItem>();
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        switch (position) {
            case 0:
                Toast.makeText(this,"Account is cliked",Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this,"Checkout is cliked",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this,"Order is cliked",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this,"On-holder order is cliked",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void openDialogCustomerInfo(int positionCustomer) {
        FragmentManager fm = getFragmentManager();
        CustomerInfoDialogFragment customerInfoDialogFragment = CustomerInfoDialogFragment.newInstance(positionCustomer);
        customerInfoDialogFragment.show(fm,"");

    }

    @Override
    public void addProduct(ProductInCartItem productInCartItem) {
        fragment_order_cart.addProductInCart(productInCartItem);
    }

    @Override
    public void deleteProductInCart(String productName) {
        fragment_order_cart.deleteProductInCart(productName);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void addCustomSale() {
        FragmentManager fm = getFragmentManager();
        customSaleFragment = new CustomSaleFragment();
        customSaleFragment.show(fm,"");
    }

    @Override
    public void addCustomSaleToCart(CustomSale customSale) {
        ProductInCartItem productInCartItem=new ProductInCartItem(R.drawable.thumbnail,customSale.getName(),customSale.getQuantity(),customSale.getPrice()*customSale.getQuantity());
        fragment_order_cart.addProductInCart(productInCartItem);
        customSaleFragment.dismiss();

    }

    @Override
    public void openDialogCustomDiscount(DiscountEntity discountEntity) {
        if(discountEntity.getNameDiscount().equals("")){
            FragmentManager fm = getFragmentManager();
            CustomDiscountDialogFragment customDiscountDialogFragment = CustomDiscountDialogFragment.newInstance(discountEntity);
            customDiscountDialogFragment.show(fm,"");
        }
    }
}
