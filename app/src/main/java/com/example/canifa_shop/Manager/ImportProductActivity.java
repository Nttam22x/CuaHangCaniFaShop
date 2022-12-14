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
                Toast.makeText(getApplicationContext(), "Nh???p h??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
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
        // t???o 1 list c?? t??n l?? "accountsListSearch", ki???m tra trong danh s??ch nh??n vi??n accountList,
        // n???u t???n t???i t??n ho???c ID tr??ng v???i t??? t??m ki???m v???a nh???p l?? "text" th?? add nh??n vi??n ???? v??o accountsListSearch
        // "accountList" ch???a danh s??ch nh??n vi??n trong b???ng Accounts
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
            // Khi click n??t "Nh???p", ti???n h??nh add s???n ph???m v???a click v??o productImportList - ????y l?? danh s??ch ch???a c??c s???n ph???m c???n import
            // ti???p theo l?? g??n s??? l?????ng c???a n?? b???ng s??? l?????ng v???a nh???p r???i set l???i list
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
// ????y l?? h??m update s???n ph???m sau khi click n??t "Nh???p h??ng"
    public void updateProduct() {
        productList=sqlHelper.getAllPrduct();
        // productList ch???a danh s??ch c??c m???t h??ng trong c???a h??ng
        // productImportList ch???a danh s??ch c??c s???n ph???m mu???n import
        for (Product product : productList) {
            for ( Product productImport : productImportList) {
                if (product.getBardCode().equals(productImport.getBardCode())) {
                    int amountHas =product.getAmount(); // s??? l?????ng s???n ph???m hi???n t???i
                    int amountImport = productImport.getAmount() ; // S??? l?????ng import
                    product.setAmount(amountHas+amountImport); // set SL cho s???n ph???m
                    sqlHelper.updateProduct(product);  // update s???n ph???m
                }
            }
        }
    }
// ????y l?? h??m th??m h??a ????n sau khi th???c hi???n th??m m???i SL s???n ph???m
// Danh s??ch n??y ???????c hi???n th??? ??? m??n h??nh B??o c??o
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