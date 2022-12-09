package com.example.canifa_shop.Customer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canifa_shop.Customer.Object.Customer;
import com.example.canifa_shop.Login.Object.Accounts;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.Product.ProductDetailActivity;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityCustomerDetailBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerDetailActivity extends AppCompatActivity {
    ActivityCustomerDetailBinding binding;
    SQLHelper sqlHelper;
    List<Customer> customerArrayList;
    private String control;
    private Customer customerChoose;
    int ID;
    ImageView btnBack, btnAdd;
    TextView tvTitile, tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_detail);
        initialization();
        findByViewID();
        getIntents();
        binding.btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (control.equals("create")) {
                    addCustomer();
                } else {
                    updateCustomer(customerChoose);
                }
            }
        });

        // Chức năng xóa khách hàng
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog builder = new AlertDialog.Builder(CustomerDetailActivity.this)
                        .setTitle("Bạn có muốn xóa")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqlHelper.deleteCustomer(ID);
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // đây là hàm thêm khách hàng mới
    public void addCustomer() {
        try {
            String name = binding.etFulName.getText().toString().trim();
            String phone = binding.etPhoneNumber.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String points = binding.etPoint.getText().toString().trim();
            String type = binding.etType.getText().toString().trim();
            String voucher = binding.etVoucher.getText().toString().trim();
            if (name.equals("") || phone.equals("") || email.equals("") || address.equals("")) {
                Toast.makeText(getBaseContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (checkEmail() == true) {
                    if (phone.length() == 10) {
                        Customer customer = new Customer(0, name, phone, email, address, points, type, voucher);
                        sqlHelper.insertCustomer(customer);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Số điện thoại phải đủ 10 chữ số", Toast.LENGTH_SHORT).show();
                    }
                }
            }


        } catch (Exception e) {
            Log.e("Lỗi", e.getMessage());
            Toast.makeText(getApplicationContext(), "Lỗi nhập liệu (Không được để trông)", Toast.LENGTH_SHORT).show();
        }
    }

    // đây là hàm update thông tin khách hàng
    public void updateCustomer(Customer customer) {
        try {
            String name = binding.etFulName.getText().toString().trim();
            String phone = binding.etPhoneNumber.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String points = binding.etPoint.getText().toString().trim();
            String type = binding.etType.getText().toString().trim();
            String voucher = binding.etVoucher.getText().toString().trim();
            if (name.equals("") || phone.equals("") || email.equals("") || address.equals("")) {
                Toast.makeText(getBaseContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (checkEmail() == true) {
                    if (phone.length() == 10) {
                        customer.setCustomerName(name);
                        customer.setCustomerPhone(phone);
                        customer.setCustomerEmail(email);
                        customer.setCustomerAddress(address);
                        customer.setCustomerPoints(points);
                        customer.setCustomerType(type);
                        customer.setCustomerVoucher(voucher);
                        sqlHelper.updateCustomer(customer);
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Số điện thoại phải đủ 10 chữ số", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        } catch (Exception e) {
            Log.e("Lỗi", e.getMessage());
            Toast.makeText(getApplicationContext(), "Lỗi nhập liệu (Không được để trông)", Toast.LENGTH_SHORT).show();
        }
    }

    // hàm check mail, mail phải có định dạng "@gmail.com"
    public boolean checkEmail() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (Pattern.matches(emailPattern, binding.etEmail.getText().toString())) { // đây là câu lệnh kiểm tra định dạng email
            return true;
        } else {
            Toast.makeText(getBaseContext(), "Email không đúng định dạng '@gmail.com'", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        btnAdd.setVisibility(View.INVISIBLE);
        tvTitile.setText("Khách hàng");

    }

    public void getIntents() {
        Intent intent = getIntent();
        control = intent.getStringExtra("control");
        if (control.equals("create")) {
            binding.btnControl.setText("Thêm mới");
            tvDelete.setVisibility(View.INVISIBLE);
        } else {
            binding.btnControl.setText("Cập nhật");
            tvDelete.setVisibility(View.VISIBLE);
            ID = intent.getIntExtra("ID", 0);
            for (Customer customer : customerArrayList) {
                if (customer.getIDCustomer() == ID) {
                    customerChoose = customer;
                    binding.etVoucher.setText(customer.getCustomerVoucher());
                    binding.etType.setText(customer.getCustomerType());
                    binding.etPoint.setText(customer.getCustomerPoints());
                    binding.etAddress.setText(customer.getCustomerAddress());
                    binding.etPhoneNumber.setText(customer.getCustomerPhone());
                    binding.etEmail.setText(customer.getCustomerEmail());
                    binding.etFulName.setText(customer.getCustomerName());
                }
            }
        }

    }

    public void initialization() {
        sqlHelper = new SQLHelper(getApplicationContext());
        customerArrayList = new ArrayList<>();
        customerArrayList = sqlHelper.getAllCustomer();
    }
}