package project.mspos.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import project.mspos.R;
import project.mspos.entity.DiscountEntity;
import project.mspos.entity.DiscountType;

/**
 * Created by SON on 4/6/2016.
 */
public class CustomDiscountDialogFragment extends DialogFragment {
    public static final String DISCOUNT_NAME="DISCOUNT_NAME";
    public static final String DISCOUNT_CODE="DISCOUNT_CODE";
    public static final String DISCOUNT_TYPE="DISCOUNT_TYPE";
    public static final String DISCOUNT_AMOUNT="DISCOUNT_AMOUNT";
    TextView tvDiscountMax;
    Button btRemoveDiscount;
    Button btApplyDiscount;

    public static CustomDiscountDialogFragment newInstance(DiscountEntity discountEntity){
        CustomDiscountDialogFragment customDiscountDialogFragment=new CustomDiscountDialogFragment();
        Bundle argument=new Bundle();
        argument.putString(DISCOUNT_NAME, discountEntity.getNameDiscount());
        if(discountEntity.getDiscountType()== DiscountType.MONEY)
            argument.putInt(DISCOUNT_TYPE,0);
        else
            argument.putInt(DISCOUNT_TYPE,1);
        argument.putFloat(DISCOUNT_AMOUNT,discountEntity.getAmount());
        argument.putString(DISCOUNT_CODE,discountEntity.getDiscountCode());
        customDiscountDialogFragment.setArguments(argument);
        return customDiscountDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.dialog_discount,container,false);
        setUpView(rootView);
        return rootView;
    }

    private void setUpView(View rootView) {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
