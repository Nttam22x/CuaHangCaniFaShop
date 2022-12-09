package com.example.canifa_shop.Category.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.canifa_shop.Category.Object.Category;
import com.example.canifa_shop.R;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // custom list view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false);
        TextView tvName,tvTotalProduct,tvDescribe;
        tvName = view.findViewById(R.id.tvNameCategory);
        tvDescribe = view.findViewById(R.id.tvDescribe);
        tvTotalProduct = view.findViewById(R.id.tvTotalProduct);
        Category category = categories.get(position);
        tvDescribe.setText("Mô tả : "+category.getDescribe());
        tvName.setText(category.getNameCategory());
        tvTotalProduct.setText("Số lượng : "+category.getAmountCategory()+"");
        return view;
    }
}
