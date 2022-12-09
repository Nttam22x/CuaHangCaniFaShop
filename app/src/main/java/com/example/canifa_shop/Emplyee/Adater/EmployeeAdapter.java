package com.example.canifa_shop.Emplyee.Adater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.canifa_shop.Login.Object.Accounts;
import com.example.canifa_shop.R;

import java.util.List;

public class EmployeeAdapter extends ArrayAdapter<Accounts> {
    Context context;
    List<Accounts> accountsList;
    int id;
    public EmployeeAdapter(@NonNull Context context, int resource, List<Accounts> list) {
        super(context, resource, list);
        this.context=context;
        this.id=resource;
        this.accountsList=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Viewholer view;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(id, parent, false);
            view=new Viewholer();
            view.tvName=convertView.findViewById(R.id.tvEmployeeName);
            view.tvBirth=convertView.findViewById(R.id.tvEmployeeBirth);
            view.tvPhone=convertView.findViewById(R.id.tvEmployeePhone);
            view.tvEmail=convertView.findViewById(R.id.tvEmployeeEmail);
            convertView.setTag(view);
        }else{
            view=(Viewholer) convertView.getTag();
        }
        Accounts a=accountsList.get(position);
        view.tvBirth.setText(a.getDateOfBirth());
        view.tvEmail.setText(a.getEmail());
        view.tvPhone.setText(a.getPhone());
        view.tvName.setText(a.getFullName());
        return convertView;
    }
    class Viewholer{
        TextView tvName, tvPhone, tvBirth, tvEmail;
    }
}
