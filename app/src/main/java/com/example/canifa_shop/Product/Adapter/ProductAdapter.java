package com.example.canifa_shop.Product.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.Product.ProductDetailActivity;
import com.example.canifa_shop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> productList;
    Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        String link = product.getImage();
        String name = product.getNameProduct();
        String price = product.getPrice()+"";
        String amount = product.getAmount()+"";
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
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("control","update");
                intent.putExtra("ID",product.getID());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
        public ViewHolder(@NonNull View itemView) {
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
