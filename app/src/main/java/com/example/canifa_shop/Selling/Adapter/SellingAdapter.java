package com.example.canifa_shop.Selling.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Helper.Function;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.Product.ProductDetailActivity;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class SellingAdapter extends RecyclerView.Adapter<SellingAdapter.ViewHolder> {
    List<Product> productList;
    Context context;
    OnClickItem onClickItem;

    public SellingAdapter(List<Product> productList, Context context, OnClickItem onClickItem) {
        this.productList = productList;
        this.context = context;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SellingAdapter.ViewHolder holder, int position) {

        Product product = productList.get(position);
        String link = product.getImage();
        String name = product.getNameProduct();
        long price = product.getPrice();
        String amount = "Còn: "+product.getAmount() ;
        if (product.getAmount() == 0) {
            holder.ivSold.setVisibility(View.VISIBLE);
        }
        Picasso.with(context).load("file://" + link).into(holder.ivProduct);
        holder.tvPriceProduct.setText(Function.decimalFormatMoney(price));
        holder.tvNameProduct.setText(name);
        holder.tvAmountProduct.setText(amount);
        holder.llProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.getAmount() == 0) {
                    Toast.makeText(context, "Đã hết hàng", Toast.LENGTH_LONG).show();
                } else {
                    SQLHelper sqlHelper = new SQLHelper(context);
                    product.setAmount(1);
                    sqlHelper.insertOrderProduct(product);
                    onClickItem.onClickItem();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, ivSold;
        TextView tvNameProduct, tvAmountProduct, tvPriceProduct;
        RelativeLayout llProduct;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivProducts);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvAmountProduct = itemView.findViewById(R.id.tvAmountProduct);
            tvPriceProduct = itemView.findViewById(R.id.tvPriceProduct);
            llProduct = itemView.findViewById(R.id.llProduct);
            ivSold = itemView.findViewById(R.id.ivSold);
        }
    }
}
