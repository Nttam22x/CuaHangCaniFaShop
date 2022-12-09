package com.example.canifa_shop.Manager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.canifa_shop.Manager.Object.Receipt;
import com.example.canifa_shop.R;

import java.util.List;

public class ReceiptAdapter extends BaseAdapter {
    List<Receipt>receiptList;

    public ReceiptAdapter(List<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    @Override
    public int getCount() {
        return receiptList.size();
    }

    @Override
    public Object getItem(int position) {
        return receiptList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receipt,parent,false);
        TextView tvID, tvTotalProduct, tvDate;
        tvDate = view.findViewById(R.id.tvDateTime);
        tvID = view.findViewById(R.id.tvIDReceipt);
        tvTotalProduct = view.findViewById(R.id.tvTotalProduct);
        tvID.setText("PN."+receiptList.get(position).getIDReceipt()+"");
        tvDate.setText(receiptList.get(position).getDateCreate());
        tvTotalProduct.setText(receiptList.get(position).getTotalProduct()+"");
        return view;
    }
}
