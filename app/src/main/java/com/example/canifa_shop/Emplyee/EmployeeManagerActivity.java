package com.example.canifa_shop.Emplyee;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.Toast;

import com.example.canifa_shop.Account.AcountManagerActivity;
import com.example.canifa_shop.Customer.Object.Customer;
import com.example.canifa_shop.Emplyee.Adater.EmployeeAdapter;
import com.example.canifa_shop.Login.Object.Accounts;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityEmployeeManagerBinding;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManagerActivity extends AppCompatActivity {
    ActivityEmployeeManagerBinding binding;
    List<Accounts> accountsList = null;
    List<Accounts> accountsListSearch;
    EmployeeAdapter adapter;
    SQLHelper sqlHelper;
    ImageView btnBack, btnAdd;
    TextView tvTitile, tvDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_manager);
        initialization();
        findByViewID();
        setAdapter(accountsList);
        btnBack.setOnClickListener(v -> {
            finish();
        });

        // chức năng tìm kiếm
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addListSearch(binding.edtSearch.getText().toString());
                if (binding.edtSearch.getText().toString().equals("")) {
                    binding.btnDelete.setVisibility(View.INVISIBLE);
                    setAdapter(accountsList);
                }
                else binding.btnDelete.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        // khi click vào biểu tượng add ở góc trên cùng màn hình, hệ thống sẽ hiển thị giao diện của hàm AccountManagerActivity.java
        // gửi đến AcountManagerActivity.class 1 intent tên là "control"
        // và value là "create". Khi AccountManagerActivity get intent có value là "create" thì sẽ tiến hành thực thi hàm thêm nhân viên
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AcountManagerActivity.class);
                intent.putExtra("control", "create");
                startActivity(intent);
            }
        });
        // khi click chọn 1 item trong list nhân viên, hệ thống sẽ hiển thị giao diện của hàm AccountManagerActivity.java
        // gửi đến AcountManagerActivity.class 1 intent tên là "control" đồng thời cũng gửi đi 1 ID là id của account tại vị trí được chọn
        // Khi AccountManagerActivity get intent có value là "update" thì sẽ tiến hành thực thi hàm update
        binding.rvEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), AcountManagerActivity.class);
                intent.putExtra("control", "update");
                intent.putExtra("ID", accountsList.get(position).getAccountID());
                startActivity(intent);
            }
        });
        // Xóa nhân viên ra khỏi danh sách khi click lâu vào 1 item trong List view
        binding.rvEmployee.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Tạo 1 dialog để hiển thị thông báo xác nhận xóa, nếu chọn Yes thì xóa, chọn No dialog đóng
                AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeManagerActivity.this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có chắc muốn xóa");
                builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
                builder.setPositiveButton("Yes", (dialog, which) ->
                        {
                            if (position == 0)  // tại vị trí position=0, đây là vị trí account của admin nên không thể xóa
                            {
                                Toast.makeText(getBaseContext(), "Bạn không thể xóa tài khoản này", Toast.LENGTH_SHORT).show();
                            } else {
                                sqlHelper.deleteAccount(accountsList.get(position).getAccountID());
                                accountsList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }
                );
                builder.show();

                return false;
            }
        });

    }

    public void initialization() {
        sqlHelper = new SQLHelper(getApplicationContext());
        accountsList = new ArrayList<>();
        accountsListSearch = new ArrayList<>();
        accountsList = sqlHelper.getAllAccounts();
    }

    public void setAdapter(List<Accounts> accountsList) {
        adapter = new EmployeeAdapter(this, R.layout.item_employee, accountsList);
        binding.rvEmployee.setAdapter(adapter);
    }

    // đây là hàm tìm kiếm nhân viên
    public void addListSearch(String text) {
        // tạo 1 list có tên là "accountsListSearch", kiểm tra trong danh sách nhân viên accountList,
        // nếu tồn tại tên hoặc ID trùng với từ tìm kiếm vừa nhập là "text" thì add nhân viên đó vào accountsListSearch
        // "accountList" chứa danh sách nhân viên trong bảng Accounts
        accountsListSearch.clear();
        for (Accounts accounts : accountsList) {
            if (String.valueOf(accounts.getAccountID()).contains(text) || accounts.getFullName().contains(text)) {
                accountsListSearch.add(accounts);
            }
        }
        setAdapter(accountsListSearch);
    }

    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        tvDelete.setVisibility(View.INVISIBLE);
        btnAdd = findViewById(R.id.btnAdd);
        tvTitile.setText("Nhân viên");
    }

    @Override
    protected void onStart() {
        super.onStart();
        initialization();
        setAdapter(accountsList);
    }
}
