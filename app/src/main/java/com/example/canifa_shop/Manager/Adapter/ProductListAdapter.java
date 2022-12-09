package com.example.canifa_shop.Manager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    List<Product> productList;
    Context context;

    public ProductListAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        ImageView ivProduct;
        TextView tvNameProduct,tvAmountProduct,tvPriceProduct;
        ivProduct = view.findViewById(R.id.ivProducts);
        tvNameProduct = view.findViewById(R.id.tvNameProduct);
        tvAmountProduct = view.findViewById(R.id.tvAmountProduct);
        tvPriceProduct = view.findViewById(R.id.tvPriceProduct);
        Product product = productList.get(position);
        String link = product.getImage();
        String name = product.getNameProduct();
        String price = product.getPrice()+"";
        String amount = product.getAmount()+"";
        Picasso.with(context).load("file://"+link).into(ivProduct);
        tvPriceProduct.setText(price);
        tvNameProduct.setText(name);
       tvAmountProduct.setText(amount);
        return view;
    }
}
