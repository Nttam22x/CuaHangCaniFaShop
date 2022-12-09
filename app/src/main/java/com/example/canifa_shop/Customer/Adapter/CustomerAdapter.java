package com.example.canifa_shop.Customer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.canifa_shop.Customer.Object.Customer;
import com.example.canifa_shop.R;

import java.util.List;

public class CustomerAdapter extends ArrayAdapter<Customer> {
    Context context;
    List<Customer> customerList=null;
    int id;
    public CustomerAdapter(@NonNull Context context, int resource, List<Customer> list) {
        super(context, resource, list);
        this.context=context;
        this.id=resource;
        this.customerList=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Viewholer view;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(id, parent, false);
            view=new Viewholer();
            view.tvName=convertView.findViewById(R.id.tvCustomerName);
            view.tvPoint=convertView.findViewById(R.id.tvCustomerPoint);
            view.tvPhone=convertView.findViewById(R.id.tvCustomerPhone);
            view.tvType=convertView.findViewById(R.id.tvCustomerType);
            convertView.setTag(view);
        }else{
            view=(Viewholer) convertView.getTag();
        }
        Customer c=customerList.get(position);
        view.tvType.setText(c.getCustomerType());
        view.tvPoint.setText(c.getCustomerPoints()+"");
        view.tvPhone.setText(c.getCustomerPhone());
        view.tvName.setText(c.getCustomerName()+"");
        return convertView;
    }
    class Viewholer{
        TextView tvName, tvPhone, tvPoint, tvType;
    }
}
