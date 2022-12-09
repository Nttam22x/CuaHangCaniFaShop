package com.example.canifa_shop.Selling;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Bill.BillDetailActivity;
import com.example.canifa_shop.Product.Object.Product;

import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;

import com.example.canifa_shop.Selling.Adapter.OnClickItem;
import com.example.canifa_shop.Selling.Adapter.SellingAdapter;
import com.example.canifa_shop.Selling.Adapter.SellingAdapterGrid;
import com.example.canifa_shop.databinding.FragmentSellingBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellingFragment extends Fragment {
    FragmentSellingBinding binding;
    SQLHelper sqlHelper;
    List<Product> productList;
    SellingAdapter sellingAdapter;
    SellingAdapterGrid sellingAdapterGrid;
    List<Product> productListSearch;
    List<String> typeProductList;
    ArrayAdapter<String> adapterSpinner;
    public static SellingFragment newInstance() {

        Bundle args = new Bundle();

        SellingFragment fragment = new SellingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_selling, container, false);
        initialization();
        setAdapter(productList);
        setAdapterSpinner();
        binding.imvList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imvGrid.setVisibility(View.VISIBLE);
                binding.imvList.setVisibility(View.INVISIBLE);
                setAdapter(productList);
            }
        });
        binding.imvGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imvGrid.setVisibility(View.INVISIBLE);
                binding.imvList.setVisibility(View.VISIBLE);
                setAdapter(productList);
            }
        });
        binding.btnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtSearch.setText("");
                setAdapter(productList);
                binding.btnDelete.setVisibility(View.INVISIBLE);
            }
        });


        // tìm kiếm sản phẩm theo input nhập vào
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            // sự kiện thay đổi text ô input
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productListSearch.clear();
                binding.btnDelete.setVisibility(View.VISIBLE);
                // lọc các kết quả tìm kiếm từ danh sách product
                for (Product product : productList) {
                    // thêm tìm kiếm không phân biệt ký tự hoa thường và dấu
                    if (VNCharacterUtils.removeAccent(product.getBardCode().toLowerCase()).contains(VNCharacterUtils.removeAccent(binding.edtSearch.getText().toString().toLowerCase())) ||
                            VNCharacterUtils.removeAccent(product.getNameProduct().toLowerCase()).contains(VNCharacterUtils.removeAccent(binding.edtSearch.getText().toString().toLowerCase()))) {
                        productListSearch.add(product);
                    }
                }
                // kết quả tìm kiếm được
                setAdapter(productListSearch);
                if(binding.edtSearch.getText().toString().equals("")){
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.sprCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(position==0){
                  setAdapter(productList);
              }else {
                  productListSearch.clear();
                  for (Product product : productList) {
                      if (product.getType().contains(binding.sprCategory.getSelectedItem().toString())) {
                          productListSearch.add(product);
                      }
                  }
                  setAdapter(productListSearch);
              }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.imvCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BillDetailActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    public void initialization() {
        sqlHelper = new SQLHelper(getContext());
        productList = new ArrayList<>();
        productListSearch = new ArrayList<>();
        typeProductList = new ArrayList<>();
        productList = sqlHelper.getAllPrduct();
        typeProductList.add("Tất cả");
        typeProductList.add("Quần");
        typeProductList.add("Áo");
        typeProductList.add("Váy");
    }
    // tìm kiếm nhanh theo danh mục
    public void setAdapterSpinner(){
        adapterSpinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,typeProductList);
        adapterSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        binding.sprCategory.setAdapter(adapterSpinner);
    }
    public void setAdapter(List<Product> productList) {
        sellingAdapter = new SellingAdapter(productList, getContext(), new OnClickItem() {
            @Override
            public void onClickItem() {
                binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
            }
        });
        sellingAdapterGrid = new SellingAdapterGrid(productList, getContext(), new OnClickItem() {
            @Override
            public void onClickItem() {
                binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
            }
        });
        binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
        if (binding.imvGrid.getVisibility() == View.VISIBLE) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, RecyclerView.VERTICAL, false);
            binding.rvProduct.setLayoutManager(gridLayoutManager);
            binding.rvProduct.setAdapter(sellingAdapterGrid);

        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
            binding.rvProduct.setLayoutManager(gridLayoutManager);
            binding.rvProduct.setAdapter(sellingAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initialization();
        binding.tvAmount.setText(sqlHelper.getAllOrderPrduct().size() + "");
        setAdapter(productList);
    }
}
