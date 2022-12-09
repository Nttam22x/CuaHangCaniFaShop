package com.example.canifa_shop.Selling.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.Product.ProductDetailActivity;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SellingAdapterGrid extends RecyclerView.Adapter<SellingAdapterGrid.ViewHolder> {
    List<Product> productList;
    Context context;
    OnClickItem onClickItem;

    public SellingAdapterGrid(List<Product> productList, Context context, OnClickItem onClickItem) {
        this.productList = productList;
        this.context = context;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prodcut_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SellingAdapterGrid.ViewHolder holder, int position) {
        Product product = productList.get(position);
        String link = product.getImage();
        String name = product.getNameProduct();
        String price = product.getPrice()+"";
        String amount = "Còn: "+product.getAmount() ;
        if(product.getAmount()==0){
            holder.ivSold.setVisibility(View.VISIBLE);
        }
        Picasso.with(context).load("file://"+link).into(holder.ivProduct);
        holder.tvPriceProduct.setText(price);
        holder.tvNameProduct.setText(name);
        holder.tvAmountProduct.setText(amount);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLHelper sqlHelper = new SQLHelper(context);
                product.setAmount(1);
                sqlHelper.insertOrderProduct(product);
                onClickItem.onClickItem();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct,ivSold;
        TextView tvNameProduct,tvAmountProduct,tvPriceProduct;
        RelativeLayout llProduct;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProduct);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvAmountProduct = itemView.findViewById(R.id.tvAmountProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            llProduct = itemView.findViewById(R.id.llProduct);
            ivSold = itemView.findViewById(R.id.ivSold);
        }
    }

}
