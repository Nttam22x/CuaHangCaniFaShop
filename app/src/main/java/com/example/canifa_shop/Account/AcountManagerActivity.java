package com.example.canifa_shop.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canifa_shop.Helper.Function;
import com.example.canifa_shop.Login.Object.Accounts;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityAcountManagerBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

public class AcountManagerActivity extends AppCompatActivity {
    ActivityAcountManagerBinding binding;
    private ImageView btnBack, btnAdd;
    private TextView tvTitile, tvDelete;
    SQLHelper sqlHelper;
    List<Accounts> accountsList;
    SharedPreferences sharedPreferences;
    private int ID;
    private Accounts accountsChoose;
    private String control = "";
    int cDay = 0, cMonth = 0, cYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_acount_manager);
        findByViewID();
        initialization();
        getInten();
        setData();
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (control.equals("create")) {
                    if (createAccount() == true)
                        finish();
                } else {
                    if (updateAccout(accountsChoose) == true)
                        finish();
                }

            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processBirthday();
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlHelper.deleteAccount(ID);
                finish();
            }
        });
    }

    // hàm này để xử lý sự kiện click vào button để hiển thị DatePicker để chọn ngày sinh
    public void processBirthday() {
        Calendar c = Calendar.getInstance();
        this.cDay = c.get(Calendar.DAY_OF_MONTH);
        this.cMonth = c.get(Calendar.MONTH);
        this.cYear = c.get(Calendar.YEAR);
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                binding.etDateOfBird.setText(arg3 + "/" + (arg2 + 1) + "/" + arg1);
            }
        };
        DatePickerDialog dateDialog = new DatePickerDialog(this, callBack, cYear, cMonth, cDay);
        dateDialog.setTitle("Choose the Birthday");
        dateDialog.show();
    }

    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        btnAdd.setVisibility(View.INVISIBLE);
        tvDelete.setVisibility(View.INVISIBLE);
        tvTitile.setText("Tài khoản");

    }

    public void initialization() {
        sqlHelper = new SQLHelper(getApplicationContext());
        accountsList = new ArrayList<>();
        accountsList = sqlHelper.getAllAccounts();
    }

    // hàm này get intent được gửi đến từ EmployeeManagerActivity.java
    public void getInten() {
        Intent intent = getIntent();
        control += intent.getStringExtra("control");
        if (control != null && !control.equals("")) {
            if (control.equals("create")) {   // nếu nội dung intent là "create" thì hiển thị giao diện thêm mới
                binding.btnUpdate.setText("Thêm mới");
                binding.etUserName.setEnabled(true);
            } else if (control.equals("update")) {   // nếu nội dung intent là "update" thì hiển thị giao diện cập nhật
                ID = intent.getIntExtra("ID", 0);
                tvDelete.setVisibility(View.INVISIBLE);
            }else {
                sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                ID = sharedPreferences.getInt("ID", 0);
            }
        }

    }

    // hàm này sử dụng để set data giao diện thêm hoặc update nhân viên
    //sử dụng foreach để kiểm tra ID có trùng với id trong bảng Accounts không,
    // nếu trùng thì setText cho các EditText của giao diện thêm hoặc update nhân viên
    public void setData() {
        for (Accounts accounts : accountsList) {
            if (accounts.getAccountID() == ID) {
                accountsChoose = accounts;
                binding.etDateOfBird.setText(accounts.getDateOfBirth());
                binding.etEmail.setText(accounts.getEmail());
                binding.etFullName.setText(accounts.getFullName());
                binding.etPassword.setText(accounts.getPassword());
                binding.etPhoneNumber.setText(accounts.getPhone());
                binding.etAddress.setText(accounts.getHomeTown());
                binding.etUserName.setText(accounts.getUserName());
            }
        }
    }

    // đây là hàm update Thông tin nhân viên
    public boolean updateAccout(Accounts accounts) {
        try {
            if (binding.etPhoneNumber.length() == 10) {
                if (checkEmail() == true) {
                    accounts.setDateOfBirth(binding.etDateOfBird.getText().toString());
                    accounts.setEmail(binding.etEmail.getText().toString());
                    accounts.setFullName(binding.etFullName.getText().toString());
                    accounts.setHomeTown(binding.etAddress.getText().toString());
                    accounts.setPassword(binding.etPassword.getText().toString());
                    accounts.setPhone(binding.etPhoneNumber.getText().toString());
                    sqlHelper.updateAccount(accounts);
                    return true;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Số điện thoại phải đủ 10 chữ số", Toast.LENGTH_SHORT).show();
            }
            return false;
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Có lỗi nhập liệu", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // đây là hàm thêm nhân viên
    public boolean createAccount() {
        try {
            String userName = binding.etUserName.getText().toString();
            String password = binding.etPassword.getText().toString();
            String fullName = binding.etFullName.getText().toString();
            String dateOfBirth = binding.etDateOfBird.getText().toString();
            String phone = binding.etPhoneNumber.getText().toString();
            String email = binding.etEmail.getText().toString();
            String homeTow = binding.etAddress.getText().toString();
            String avatar = "";
            String permission = "employee";
            if (userName.equals("") || password.equals("") || fullName.equals("") || dateOfBirth.equals("") || phone.equals("") || email.equals("") || homeTow.equals("")) {
                Toast.makeText(getBaseContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if (checkAccount() == true) {
                    if (checkPassword() == true) {
                        if (phone.length() == 10) {
                            if (checkEmail() == true) {
                                Accounts accounts = new Accounts(0, userName, password, fullName, dateOfBirth, phone, email, homeTow, avatar, permission);
                                sqlHelper.insertAccount(accounts);
                                return true;
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Số điện thoại phải đủ 10 chữ số", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Có lỗi nhập liệu", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    // đây là hàm kiểm tra password, password phải có từ 6 ký tự bao gồm chữ hoa, chữ thường và số
    public boolean checkPassword() {
        String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})";
        if (binding.etPassword.getText().toString().isEmpty()) {      // đây là câu lệnh kiểm tra định dạng password
            Toast.makeText(this, "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        }
        if (Pattern.matches(passPattern, binding.etPassword.getText().toString())) {
            return true;
        } else {
            Toast.makeText(getBaseContext(), "Mật khẩu có từ 6 ký tự bao gồm chữ hoa, chữ thường và số", Toast.LENGTH_SHORT).show();
            return false;
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

    // hàm kiểm tra tài khoản xem có trùng với tài khoản trong bảng Accounts không
    public boolean checkAccount() {
        // đầu tiên sẽ tạo 1 list để get danh sách các account trong bảng Accounts
        // sử dùng vòng foreach để kiểm tra xem username nhập vào có trùng với username trong bảng Accounts không,
        // nếu có thì hiển thị thông báo
        List<Accounts> accountsArrayList = sqlHelper.getAllAccounts();
        for (Accounts acc : accountsArrayList) {
            if (acc.getUserName().equalsIgnoreCase(binding.etUserName.getText().toString())) {
                Toast.makeText(getBaseContext(), "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
