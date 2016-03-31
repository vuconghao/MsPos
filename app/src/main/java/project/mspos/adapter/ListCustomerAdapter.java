package project.mspos.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import project.mspos.R;
import project.mspos.entity.CustomerEntity;

/**
 * Created by SON on 3/30/2016.
 */
public class ListCustomerAdapter extends ArrayAdapter<CustomerEntity> {
    private Activity context;
    private ArrayList<CustomerEntity> listCustomer;
    public ListCustomerAdapter(Activity context, int resource, ArrayList<CustomerEntity> objects) {
        super(context, resource, objects);
        this.context=context;
        this.listCustomer=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=context.getLayoutInflater();
            convertView=inflater.inflate(R.layout.list_customer_item,parent,false);
        }
        final TextView tvName=(TextView)convertView.findViewById(R.id.text_view_name_customer);
        final TextView tvEmail=(TextView)convertView.findViewById(R.id.text_view_email_customer);
        final TextView tvPhone=(TextView)convertView.findViewById(R.id.text_view_phone);
        tvName.setText(listCustomer.get(position).getFirstName()+" "+listCustomer.get(position).getLastName());
        tvEmail.setText(listCustomer.get(position).getEmail());
        tvPhone.setText(listCustomer.get(position).getPhone());

        return convertView;
    }
}
