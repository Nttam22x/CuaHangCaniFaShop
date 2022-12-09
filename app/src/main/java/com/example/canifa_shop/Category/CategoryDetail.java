package com.example.canifa_shop.Category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canifa_shop.Category.Object.Category;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityCategoryDetailBinding;

public class CategoryDetail extends AppCompatActivity {
    ActivityCategoryDetailBinding binding;
    ImageView btnBack,btnAdd;
    TextView tvTitile, tvDelete;
    SQLHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_category_detail);
        findByViewID();
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDanhMuc();
            }
        });
    }
    // get widget
    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        tvDelete.setVisibility(View.INVISIBLE);
        btnAdd = findViewById(R.id.btnAdd);
        tvTitile.setText("Chi tiết danh mục");
        sqlHelper = new SQLHelper(getApplicationContext());
    }
    // thêm một danh mục mới
    public void createDanhMuc(){
        String name = binding.etNameCategory.getText().toString();
        String discrible = binding.etDescrible.getText().toString();
        Category category = new Category(0,name,0,discrible);
        sqlHelper.insertCategory(category);
        finish();
    }
}