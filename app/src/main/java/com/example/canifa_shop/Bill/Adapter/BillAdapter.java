package com.example.canifa_shop.Bill.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    List<Product> productList;

    Context context;

    public BillAdapter(List<Product> productList, Context context) {
        this.productList = productList;

        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BillAdapter.ViewHolder holder, int position) {
        Product product = productList.get(position);
        String link = product.getImage();
        String name = product.getNameProduct();
        String price = product.getPrice() + "";
        String amount = product.getAmount() + "";
        Picasso.with(context).load("file://" + link).into(holder.ivProduct);
        holder.tvPriceProduct.setText(price);
        holder.tvNameProduct.setText(name);
        holder.tvAmountProduct.setText(amount);
        holder.ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                product.setAmount(product.getAmount() + 1);
                holder.sqlHelper.updateOrderProduct(product);
                notifyDataSetChanged();
            }
        });
        holder.ivMinus.setOnClickListener(v -> {
            if (product.getAmount() > 1) {
                product.setAmount(product.getAmount() - 1);
                holder.sqlHelper.updateOrderProduct(product);
            } else {
                productList.remove(position);
                holder.sqlHelper.deleteItemOrderProduct(product.getID());
            }
            notifyDataSetChanged();
        });
        holder.tvDelete.setOnClickListener(v -> {
            productList.remove(position);
            holder.sqlHelper.deleteItemOrderProduct(product.getID());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct, ivMinus, ivPlus;
        TextView tvNameProduct, tvAmountProduct, tvPriceProduct, tvDelete;
        LinearLayout llProduct;
        SQLHelper sqlHelper;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivMinus = itemView.findViewById(R.id.btnMinus);
            ivPlus = itemView.findViewById(R.id.btnPlus);
            ivProduct = itemView.findViewById(R.id.ivProducts);
            tvNameProduct = itemView.findViewById(R.id.tvNameProduct);
            tvAmountProduct = itemView.findViewById(R.id.tvAmount);
            tvPriceProduct = itemView.findViewById(R.id.tvPrice);
            llProduct = itemView.findViewById(R.id.llProduct);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            sqlHelper = new SQLHelper(context);
        }
    }
}
