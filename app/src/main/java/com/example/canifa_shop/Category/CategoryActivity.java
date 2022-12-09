package com.example.canifa_shop.Category;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canifa_shop.Category.Adapter.CategoryAdapter;
import com.example.canifa_shop.Category.Object.Category;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    ImageView btnBack, btnAdd;
    TextView tvTitile, tvDelete;
    List<Category> categoryList;
    SQLHelper sqlHelper;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category);
        findByViewID();
        // thêm mới một danh mục
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), CategoryDetail.class));
        });
        // click quây lại
        btnBack.setOnClickListener(v -> {
            finish();
        });
        // Xóa item khi dùng sự kiện long click
        binding.lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext())
                        .setTitle("Bạn có muốn xóa không ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqlHelper.deleteCategory(categoryList.get(position).getIdCategory());
                                categoryList.remove(position);
                                categoryAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();

                return false;
            }
        });
    }
    // khởi tạo giá trị cho bảng neus không có dữ liệu trong db
    public void initialization() {
        categoryList = new ArrayList<>();
        sqlHelper = new SQLHelper(getApplicationContext());
        categoryList = sqlHelper.getAllCategory();
        if (categoryList.size() == 0) {
            sqlHelper.insertCategory(new Category(0, "Mặc đinh", 0, "Mặc định"));
            categoryList.clear();
            categoryList = sqlHelper.getAllCategory();
        }
    }
    // get widget
    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        tvDelete.setVisibility(View.INVISIBLE);
        btnAdd = findViewById(R.id.btnAdd);
        tvTitile.setText("Danh mục");
    }
    // set adapter cho list view
    public void setAdapter() {
        categoryAdapter = new CategoryAdapter(categoryList);
        binding.lvCategory.setAdapter(categoryAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialization();
        setAdapter();
    }
}