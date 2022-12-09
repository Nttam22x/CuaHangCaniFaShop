package com.example.canifa_shop.Manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canifa_shop.Bill.Adapter.BillAdapter;
import com.example.canifa_shop.Login.Object.Accounts;
import com.example.canifa_shop.Manager.Adapter.ProductImportListAdapter;
import com.example.canifa_shop.Manager.Adapter.ProductListAdapter;
import com.example.canifa_shop.Manager.Object.Receipt;
import com.example.canifa_shop.Product.Adapter.ProductAdapter;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityImprodProductBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ImportProductActivity extends AppCompatActivity {
    ActivityImprodProductBinding binding;
    private List<Product> productList;
    SQLHelper sqlHelper;
    private List<Product> productListSearch;
    ProductListAdapter productListAdapter;
    List<Product> productImportList;
    ProductImportListAdapter productImportListAdapter;
    SharedPreferences sharedPreferences;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_improd_product);
        initialization();
        getIntents();
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productListSearch.clear();
                binding.btnDelete.setVisibility(View.VISIBLE);
               addListSearch(binding.edtSearch.getText().toString());
                if (binding.edtSearch.getText().toString().equals("")) {
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                    productListSearch = productList;
                }
                setAdapter(productListSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        binding.lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product product = new Product(productListSearch.get(position).getID(),productListSearch.get(position).getNameProduct(),
                        productListSearch.get(position).getImportprice(),productListSearch.get(position).getPrice(),
                        productListSearch.get(position).getAmount(),productListSearch.get(position).getType(),productListSearch.get(position).getDescribe(),
                        productListSearch.get(position).getImage(),productListSearch.get(position).getBardCode());
                showDialogAmount(product);

            }
        });
        binding.btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReceipt(productImportList);
                updateProduct();
                finish();
                Toast.makeText(getApplicationContext(), "Nhập hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
//        binding.btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }
    public void addListSearch(String text) {
        // tạo 1 list có tên là "accountsListSearch", kiểm tra trong danh sách nhân viên accountList,
        // nếu tồn tại tên hoặc ID trùng với từ tìm kiếm vừa nhập là "text" thì add nhân viên đó vào accountsListSearch
        // "accountList" chứa danh sách nhân viên trong bảng Accounts
        productListSearch.clear();
        for (Product product : productList) {
            if (product.getNameProduct().equals(text)) {
                productListSearch.add(product);
            }
        }
        setAdapter(productListSearch);
    }

    public void showDialogAmount(Product product) {
        Dialog dialog = new Dialog(ImportProductActivity.this);
        dialog.setContentView(R.layout.dialog_update_amount);
        EditText etAmount = dialog.findViewById(R.id.etAmountt);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            // Khi click nút "Nhập", tiến hành add sản phẩm vừa click vào productImportList - đây là danh sách chứa các sản phẩm cần import
            // tiếp theo là gán số lượng của nó bằng số lượng vừa nhập rồi set lại list
            @Override
            public void onClick(View v) {
                productImportList.add(product);
                productImportList.get(productImportList.size()-1).setAmount(Integer.valueOf(etAmount.getText().toString()));
                productImportListAdapter.notifyDataSetChanged();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void getIntents() {
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getStringExtra("control").equals("show")) {
                int ID = intent.getIntExtra("ID", 0);
                addProduct(ID);
                binding.llChooseProduct.setVisibility(View.GONE);
            }
        }
        else {
            setAdapter(productListSearch);
        }

    }

    public void initialization() {
        sqlHelper = new SQLHelper(getApplicationContext());
        productList = new ArrayList<>();
        productListSearch = new ArrayList<>();
        productImportList = new ArrayList<>();
        productList = sqlHelper.getAllPrduct();
        productListSearch = productList;
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        ID = sharedPreferences.getInt("ID", 0);
        setAdapterImport();
    }

    public void addProduct(int ID) {
        List<Product> products = sqlHelper.getAllPrduct();
        productListSearch.clear();
        Receipt receipt = sqlHelper.getReceipt(ID);
        Toast.makeText(getBaseContext(), receipt.getIDReceipt()+"", Toast.LENGTH_LONG).show();
        String[] IDProduct = receipt.getIDProduct().split(";");
        for (int i = 0; i < IDProduct.length; i++) {
            if (IDProduct[i].equals("")) break;
            else
                for (Product product : products) {
                    if (String.valueOf(product.getID()).equals(IDProduct[i])) {
                        productList.add(product);
                    }
                }
        }
        productListSearch = productList;
        setAdapter(productListSearch);
    }

    public void setAdapter(List<Product> productList) {
        productListAdapter = new ProductListAdapter(productListSearch, getApplicationContext());
        binding.lvProduct.setAdapter(productListAdapter);
    }
    public void setAdapterImport(){
        productImportListAdapter = new ProductImportListAdapter(productImportList, getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvProductImport.setLayoutManager(layoutManager);
        binding.rvProductImport.setAdapter(productImportListAdapter);
    }
// đây là hàm update sản phẩm sau khi click nút "Nhập hàng"
    public void updateProduct() {
        productList=sqlHelper.getAllPrduct();
        // productList chứa danh sách các mặt hàng trong cửa hàng
        // productImportList chứa danh sách các sản phẩm muốn import
        for (Product product : productList) {
            for ( Product productImport : productImportList) {
                if (product.getBardCode().equals(productImport.getBardCode())) {
                    int amountHas =product.getAmount(); // số lượng sản phẩm hiện tại
                    int amountImport = productImport.getAmount() ; // Số lượng import
                    product.setAmount(amountHas+amountImport); // set SL cho sản phẩm
                    sqlHelper.updateProduct(product);  // update sản phẩm
                }
            }
        }
    }
// Đây là hàm thêm hóa đơn sau khi thực hiện thêm mới SL sản phẩm
// Danh sách này được hiển thị ở màn hình Báo cáo
    public void addReceipt(List<Product> productList) {
        if (productList.size() != 0 || productList != null) {
            String IDProduct = "";
            int totalProduct = 0;
            for (Product product : productList) {
                IDProduct = product.getID() + "";
                totalProduct = product.getAmount();
            }
            Receipt receipt = new Receipt(0, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()), IDProduct, totalProduct, ID);
            sqlHelper.insertReceipt(receipt);
        }

    }
}