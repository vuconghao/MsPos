package project.mspos.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import project.mspos.R;
import project.mspos.activity.MainActivity;
import project.mspos.entity.CustomerEntity;

/**
 * Created by SON on 3/30/2016.
 */
public class CustomerInfoDialogFragment extends DialogFragment implements View.OnClickListener{
    public static final String POSITION_CUSTOMER="position_customer";
    Button btSaveCustomer;
    Button btCancel;
    EditText editFirstName,editLastName,editEmail,editPhone,editAddressLine1,editAddressLine2;
    EditText editCity,editZip,editCompany,editFax,editVatId;
    Spinner spinCountry,spinProvice,spinTypeCustomer;
    ArrayList<CustomerEntity> listCustomer;

    public static CustomerInfoDialogFragment newInstance(int positionCustomer){
        CustomerInfoDialogFragment customerInfoDialogFragment=new CustomerInfoDialogFragment();
        Bundle argument=new Bundle();
        argument.putInt(POSITION_CUSTOMER, positionCustomer);
        customerInfoDialogFragment.setArguments(argument);
        return customerInfoDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.layout_information_customer, container, false);
        setUpView(rootView);
        displayInfomationCustomer();
        return rootView;
    }

    private void displayInfomationCustomer() {
        int positionCustomer=getArguments().getInt(POSITION_CUSTOMER,0);
        if(positionCustomer!=MainActivity.NO_CUSTOMER) {
            editFirstName.setText(listCustomer.get(positionCustomer).getFirstName());
            editLastName.setText(listCustomer.get(positionCustomer).getLastName());
            editEmail.setText(listCustomer.get(positionCustomer).getEmail());
            editPhone.setText(listCustomer.get(positionCustomer).getPhone());
            editAddressLine1.setText(listCustomer.get(positionCustomer).getAddressLine1());
            editAddressLine2.setText(listCustomer.get(positionCustomer).getAddressLine2());
            editCity.setText(listCustomer.get(positionCustomer).getCity());
            editZip.setText(listCustomer.get(positionCustomer).getZipId());
            editCompany.setText(listCustomer.get(positionCustomer).getCompany());
            editFax.setText(listCustomer.get(positionCustomer).getFax());
            editVatId.setText(listCustomer.get(positionCustomer).getVATId());
        }

    }

    private void setUpView(View rootView) {
        btCancel=(Button)rootView.findViewById(R.id.btCancelInfoCustomer);
        btSaveCustomer=(Button)rootView.findViewById(R.id.btSaveInfoCustomer);
        editFirstName=(EditText)rootView.findViewById(R.id.editFirstname);
        editLastName=(EditText)rootView.findViewById(R.id.editLastName);
        editEmail=(EditText)rootView.findViewById(R.id.editEmail);
        editPhone=(EditText)rootView.findViewById(R.id.editPhone);
        editCity=(EditText)rootView.findViewById(R.id.editCity);
        editAddressLine1=(EditText)rootView.findViewById(R.id.editAddressLine1);
        editAddressLine2=(EditText)rootView.findViewById(R.id.editAddressLine2);
        editZip=(EditText)rootView.findViewById(R.id.editZip);
        editCompany=(EditText)rootView.findViewById(R.id.editCompany);
        editFax=(EditText)rootView.findViewById(R.id.editFax);
        editVatId=(EditText)rootView.findViewById(R.id.editVatID);
        spinCountry=(Spinner)rootView.findViewById(R.id.spinnerCountry);
        spinProvice=(Spinner)rootView.findViewById(R.id.spinner_Province);
        spinTypeCustomer=(Spinner)rootView.findViewById(R.id.spinner_type_customer);
        listCustomer=new ArrayList<CustomerEntity>();
        listCustomer=MainActivity.listCustomer;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        registerEvent();
    }

    private void registerEvent() {
        btSaveCustomer.setOnClickListener(this);
        btCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btCancelInfoCustomer:
                dismiss();
                break;
            case R.id.btSaveInfoCustomer:
                break;
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog= super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
