package com.example.canifa_shop.Customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canifa_shop.Bill.BillDetailActivity;
import com.example.canifa_shop.Customer.Adapter.CustomerAdapter;
import com.example.canifa_shop.Customer.Object.Customer;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityCustomerBinding;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {
    ActivityCustomerBinding binding;
    SQLHelper sqlHelper;
    List<Customer> customerList;
    List<Customer> customerListSearch;
    ImageView btnAdd;
    String control = "";
    CustomerAdapter adapter = null;
    ImageView btnBack;
    TextView tvTitile, tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        initialization();
        findByViewID();
        getInten();
        setAdapter(customerList);
        btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.rvCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (control != null && control.equals("getCustomer")) {
                    Intent intentData = new Intent(CustomerActivity.this, BillDetailActivity.class);
                    intentData.putExtra("ID", customerList.get(position).getIDCustomer());
                    setResult(BillDetailActivity.REQUEST_CODE, intentData);
                    finish();
                } else {
                    int ID = customerList.get(position).getIDCustomer();
                    Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                    intent.putExtra("control", "update");
                    intent.putExtra("ID", ID);
                    startActivity(intent);
                }

            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, CustomerDetailActivity.class);
                intent.putExtra("control", "create");
                startActivity(intent);
            }
        });

        // th???c thi  ch???c n??ng t??m ki???m
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addListSearch(binding.edtSearch.getText().toString());
                if (binding.edtSearch.getText().toString().equals("")) {
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                    setAdapter(customerList);
                } else binding.btnDelete.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        tvDelete.setVisibility(View.INVISIBLE);
        btnAdd = findViewById(R.id.btnAdd);
        tvTitile.setText("Kh??ch h??ng");
    }

    public void getInten() {
        Intent intent = getIntent();
        control += intent.getStringExtra("control");
        if (control != null) {
            if (control.equals("getCustomer")) {

            } else
                tvDelete.setVisibility(View.INVISIBLE);

        }
    }

    // ????y l?? h??m t??m ki???m th??ng tin
    public void addListSearch(String text) {
        customerListSearch.clear();
        // customerListSearch ch???a danh s??ch kh??ch h??ng ???????c t??m th???y
        // customerList hi???n th??? danh s??ch kh??ch h??ng ???? t???n t???i tr?????c ????
        for (Customer customer : customerList) {
            if (String.valueOf(customer.getIDCustomer()).contains(text) || customer.getCustomerName().contains(text)) {
                // n???u ID ho???c t??n kh??ch h??ng tr??ng v???i chu???i nh???p th?? add n?? v??o 1 list v?? cho hi???n th??? ra m??n h??nh
                customerListSearch.add(customer);
            }
        }
        setAdapter(customerListSearch);
    }

    public void initialization() {
        sqlHelper = new SQLHelper(getApplicationContext());
        customerList = new ArrayList<>();
        customerListSearch = new ArrayList<>();
        customerList = sqlHelper.getAllCustomer();
    }

    public void setAdapter(List<Customer> customerList) {
        adapter = new CustomerAdapter(this, R.layout.item_customer, customerList);
        binding.rvCustomer.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialization();
        setAdapter(customerList);
    }
}