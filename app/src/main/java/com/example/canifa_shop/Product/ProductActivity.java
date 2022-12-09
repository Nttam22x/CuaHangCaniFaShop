package com.example.canifa_shop.Product;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canifa_shop.Product.Adapter.ProductAdapter;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityProductBinding;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ActivityProductBinding binding;
    ImageView btnBack, btnAdd;
    TextView tvTitile,tvDelete;
    SQLHelper sqlHelper;
    ProductAdapter productAdapter;
    List<Product> productList;
    List<Product> productListSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product);
        findByViewID();
        //lắng nghe sự kiện thêm
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //khi nhấn vào nút thêm sẽ gọi đến một intent ProductDetailActivity.class
                Intent intent = new Intent(ProductActivity.this,ProductDetailActivity.class);
                //gọi đến điều khiển create
                intent.putExtra("control","create");
                //xác định intent vừa gửi chạy trên activity này
                startActivity(intent);
            }
        });
        //lắng nghe sự kiện trở về
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Lắng nghe sự kiện nút search
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //làm mới danh sách productListSearch
                productListSearch.clear();
                //gọi đến button xóa
                binding.btnDelete.setVisibility(View.VISIBLE);
                //gọi danh sách các sản phẩm
                for (Product product : productList) {
                    if (product.getBardCode().contains(binding.edtSearch.getText().toString()) || product.getNameProduct().contains(binding.edtSearch.getText().toString())) {
                        productListSearch.add(product);
                    }
                }

                setAdapter(productListSearch);

                if(binding.edtSearch.getText().toString().equals("")){
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //lắng nghe sự kiện xóa
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.edtSearch.setText("");
                setAdapter(productList);
                binding.btnDelete.setVisibility(View.INVISIBLE);
            }
        });


    }
    //phương thức gọi tới các giá trị trong file activity_product.xml
    public void findByViewID(){
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        tvDelete.setVisibility(View.INVISIBLE);
        tvTitile.setText("Sản phẩm");
    }
    //Phương thúc khởi tạo
    public void initialization(){
        sqlHelper = new SQLHelper(getApplicationContext());
        productList = new ArrayList<>();
        productListSearch = new ArrayList<>();
        productList=sqlHelper.getAllPrduct();
    }
    //gán giá trị adapter
    public void setAdapter(List<Product>productList){
            productAdapter=new ProductAdapter(productList,getApplicationContext());
            GridLayoutManager gridLayoutManager =new GridLayoutManager(getApplicationContext(),1, RecyclerView.VERTICAL,false);
            binding.rvProduct.setLayoutManager(gridLayoutManager);
            binding.rvProduct.setAdapter(productAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialization();
        setAdapter(productList);
    }
}