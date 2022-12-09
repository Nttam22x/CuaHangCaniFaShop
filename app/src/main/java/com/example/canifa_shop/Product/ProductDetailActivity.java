package com.example.canifa_shop.Product;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.canifa_shop.Category.Object.Category;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityProductDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailActivity extends AppCompatActivity {
    public static final int PICK_IMAGE = 1;
    ActivityProductDetailBinding binding;
    List<Category> categories;
    ArrayAdapter<Category> categoryArrayAdapter;
    SQLHelper sqlHelper;
    List<Product> productList;
    ImageView btnBack, btnAdd;
    TextView tvTitile, tvDelete;
    String control;
    Product productUpdate;
    int ID;
    String linkImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail);
        findByViewID();
        initialization();
        getIntents();
        setAdapterSpiner();
        //gọi chức năng
        binding.btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (control.equals("create")) {
                    addProduct();
                } else {
                    updateProduct();
                    finish();
                }
            }
        });
        //chức năng thêm ảnh cho sản phẩm
        //khi kích vào mục chọn ảnh sẽ tạo 1 intent mới
        //khi chọn 1 ảnh sẽ hiển thị ảnh đã chọn
        binding.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });
        //khi kích vào nút back sẽ đóng ứng dựng
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //khi nhấn nút xóa sẽ hiển thị thông báo bạn có muốn xóa không
        //kích vào nút "có" thì sẽ gọi hàm xóa trong csdl và xóa trên màn hình list sản phẩm
        //kích vào nút "không" thì sẽ ở nguyên màn hình
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog builder = new AlertDialog.Builder(ProductDetailActivity.this)
                        .setTitle("Bạn có muốn xóa")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqlHelper.deleteItemProduct(ID);
                                finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                builder.show();
            }
        });

    }
    //phương thức getIntent
    public void getIntents() {
        //tạo intent mới
        Intent intent = getIntent();
        //gọi đến điều khiển control
        control = intent.getStringExtra("control");
        //nếu control = create
        if (control.equals("create")) {
            //gọi đến nút thêm mới
            binding.btnControl.setText("Thêm mới");
            //ẩn nút xóa sản phẩm
            tvDelete.setVisibility(View.INVISIBLE);
        } else {
            //gọi đến nút cập nhật
            binding.btnControl.setText("Cập nhật");
            //gọi nút xóa
            tvDelete.setVisibility(View.VISIBLE);
            //đóng gói dữ liệu ID theo kiểu Int
            ID = intent.getIntExtra("ID", 0);
            //hiển thị danh sách các sản phẩm
            for (Product product : productList
            ) {
                if (product.getID() == ID) {
                    productUpdate = product;
                    binding.etNameProduct.setText(product.getNameProduct()+"");
                    binding.etAmountProduct.setText(product.getAmount() + "");
                    binding.etBardCodeProduct.setText(product.getBardCode()+"");
                    binding.etNote.setText(product.getDescribe()+"");
                    Picasso.with(getApplicationContext()).load("file://" + product.getImage()).into(binding.ivProduct);
                    binding.etPriceImport.setText(product.getImportprice() + "");
                    binding.etPriceSell.setText(product.getPrice() + "");
                }
            }
        }

    }
    //chức năng add sản phẩm
    public void addProduct() {
        try {
            //hàm gọi các thuộc tính
            String name = binding.etNameProduct.getText().toString().trim();
            //ép kiểu giá nhập và giá bán
            long priceImport = Long.valueOf(binding.etPriceImport.getText().toString().trim());
            long priceSell = Long.valueOf(binding.etPriceSell.getText().toString().trim());
            String code = binding.etBardCodeProduct.getText().toString().trim();
            String note = binding.etNote.getText().toString().trim();
            //ép kiểu số lượng theo kiểu int
            int amount = Integer.valueOf(binding.etAmountProduct.getText().toString().trim());
            //chọn loại sản phẩm
            String type = binding.spType.getSelectedItem().toString();
             {
                 //kiểm tra giá nhập, giá bán
                if(checkPrice(priceImport,priceSell)){
                    //tạo với 1 sản phẩm
                    Product product = new Product(0, name, priceImport, priceSell, amount, type, note, linkImage, code);
                    //gọi hàm insert sản phẩm trong file SQLHelper
                    sqlHelper.insertProduct(product);
                    //gọi danh mục
                    Category category = categories.get(binding.spType.getSelectedItemPosition());
                    //khi chọn danh mục thì số lượng sẽ được thêm ở số lượng sản phẩm có trong danh mục
                    category.setAmountCategory(category.getAmountCategory() + 1);
                    //gọi hàm update lại csdl
                    sqlHelper.updateCategory(category);
                    finish();
                }else Toast.makeText(getApplicationContext(),"Tiền bán phải lớn hơn tiền nhập",Toast.LENGTH_SHORT).show();
                //nếu tiền nhập > tiền bán thì sẽ hiển thị thông báo "tiền bán phải lớn hơn tiền nhập
           }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Lỗi nhập liệu", Toast.LENGTH_SHORT).show();
        }

    }
    // phương thức gọi tới các giá trị trong file activity_product_detail.xml
    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        btnAdd.setVisibility(View.INVISIBLE);
        tvTitile.setText("Sản phẩm");

    }
    //phương thức khởi tạo
    public void initialization() {

        sqlHelper = new SQLHelper(this);
        productList = new ArrayList<>();
        categories = new ArrayList<>();
        productList = sqlHelper.getAllPrduct();
        categories = sqlHelper.getAllCategory();
    }
    //Phương thức tạo spinner cho danh mục
    public void setAdapterSpiner() {
        //gọi đến danh sách các thư mục
        categoryArrayAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, categories);
        //lấy tất cả danh sách thư mục vào spinner
        categoryArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //hiển thị danh sách thư mục spinner lên màn hình
        binding.spType.setAdapter(categoryArrayAdapter);
    }
    //phương thức nhận kết quả trả về từ 1 activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            //lấy 1 image trong dữ liệu
            Uri selectedImageUri = data.getData();
            if (null != selectedImageUri) {
                // gán ảnh được chọn lên ivProduct
                binding.ivProduct.setImageURI(selectedImageUri);
                //gán cho linkImage một giá trị kết nối
                linkImage += convertMediaUriToPath(selectedImageUri);
                Toast.makeText(getBaseContext(), linkImage+"", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String convertMediaUriToPath(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        //Cursor để đóng gói giá trị tạm thời tương tự bundle
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        //trả về chỉ số của cột và tên chỉ định
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //chỉ con trỏ đến hàng đầu tiên
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
    //Phương thức cập nhật sản phẩm
    public void updateProduct() {
        try {
            //ép kiểu cho các thuộc tính
            String name = binding.etNameProduct.getText().toString().trim();
            long priceImport = Long.valueOf(binding.etPriceImport.getText().toString().trim());
            long priceSell = Long.valueOf(binding.etPriceSell.getText().toString().trim());
            String code = binding.etBardCodeProduct.getText().toString().trim();
            String note = binding.etNote.getText().toString().trim();
            int amount = Integer.valueOf(binding.etAmountProduct.getText().toString().trim());
            String type = binding.spType.getSelectedItem().toString();
            //gán giá trị cho các thuộc tính
            productUpdate.setNameProduct(name);
            productUpdate.setPrice(priceSell);
            productUpdate.setImportprice(priceImport);
            productUpdate.setAmount(amount);
            productUpdate.setDescribe(note);
            productUpdate.setType(type);
            productUpdate.setImage(linkImage);
            //kiểm tra mã barCode đã tồn tại chưa
            if (checkHasBardCode(code)) {
               Toast.makeText(getApplicationContext(), "Mã sản phẩm đã tồn tại", Toast.LENGTH_SHORT).show();
           } else {
                //kiểm tra giá nhập < giá bán
                if(checkPrice(priceImport,priceSell)){
                    productUpdate.setBardCode(code);
                    sqlHelper.updateProduct(productUpdate);
                }else Toast.makeText(getApplicationContext(),"Tiền bán phải lớn hơn tiền nhập",Toast.LENGTH_SHORT).show();

            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Lỗi nhập liệu", Toast.LENGTH_SHORT).show();
        }
    }
    //phương thức dùng để lắng nghe sự kiện back khi cần
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    // phương thức kiểm tra BardCade
    public boolean checkHasBardCode(String bardCode) {
        boolean has = true;
        for (Product product : productList) {
            if (product.getBardCode().equals(bardCode)) {
               has = true;
               break;
            } else
            {
                has = false;
            }
        }
        return has;
    }
    //Phương thức kiểm tra giá nhập và giá bán
    public boolean checkPrice(long importPrice, long sellPrice){
        if(importPrice>sellPrice){
            return false;
        }
        else return true;
    }
}