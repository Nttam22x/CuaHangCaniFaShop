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

    // h??m n??y ????? x??? l?? s??? ki???n click v??o button ????? hi???n th??? DatePicker ????? ch???n ng??y sinh
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
        tvTitile.setText("T??i kho???n");

    }

    public void initialization() {
        sqlHelper = new SQLHelper(getApplicationContext());
        accountsList = new ArrayList<>();
        accountsList = sqlHelper.getAllAccounts();
    }

    // h??m n??y get intent ???????c g???i ?????n t??? EmployeeManagerActivity.java
    public void getInten() {
        Intent intent = getIntent();
        control += intent.getStringExtra("control");
        if (control != null && !control.equals("")) {
            if (control.equals("create")) {   // n???u n???i dung intent l?? "create" th?? hi???n th??? giao di???n th??m m???i
                binding.btnUpdate.setText("Th??m m???i");
                binding.etUserName.setEnabled(true);
            } else if (control.equals("update")) {   // n???u n???i dung intent l?? "update" th?? hi???n th??? giao di???n c???p nh???t
                ID = intent.getIntExtra("ID", 0);
                tvDelete.setVisibility(View.INVISIBLE);
            }else {
                sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                ID = sharedPreferences.getInt("ID", 0);
            }
        }

    }

    // h??m n??y s??? d???ng ????? set data giao di???n th??m ho???c update nh??n vi??n
    //s??? d???ng foreach ????? ki???m tra ID c?? tr??ng v???i id trong b???ng Accounts kh??ng,
    // n???u tr??ng th?? setText cho c??c EditText c???a giao di???n th??m ho???c update nh??n vi??n
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

    // ????y l?? h??m update Th??ng tin nh??n vi??n
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
                Toast.makeText(getApplicationContext(), "S??? ??i???n tho???i ph???i ????? 10 ch??? s???", Toast.LENGTH_SHORT).show();
            }
            return false;
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "C?? l???i nh???p li???u", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // ????y l?? h??m th??m nh??n vi??n
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
                Toast.makeText(getBaseContext(), "Vui l??ng nh???p ????? th??ng tin", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "S??? ??i???n tho???i ph???i ????? 10 ch??? s???", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "C?? l???i nh???p li???u", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    // ????y l?? h??m ki???m tra password, password ph???i c?? t??? 6 k?? t??? bao g???m ch??? hoa, ch??? th?????ng v?? s???
    public boolean checkPassword() {
        String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})";
        if (binding.etPassword.getText().toString().isEmpty()) {      // ????y l?? c??u l???nh ki???m tra ?????nh d???ng password
            Toast.makeText(this, "M???t kh???u kh??ng ???????c b??? tr???ng", Toast.LENGTH_SHORT).show();
        }
        if (Pattern.matches(passPattern, binding.etPassword.getText().toString())) {
            return true;
        } else {
            Toast.makeText(getBaseContext(), "M???t kh???u c?? t??? 6 k?? t??? bao g???m ch??? hoa, ch??? th?????ng v?? s???", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // h??m check mail, mail ph???i c?? ?????nh d???ng "@gmail.com"
    public boolean checkEmail() {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        if (Pattern.matches(emailPattern, binding.etEmail.getText().toString())) { // ????y l?? c??u l???nh ki???m tra ?????nh d???ng email
            return true;
        } else {
            Toast.makeText(getBaseContext(), "Email kh??ng ????ng ?????nh d???ng '@gmail.com'", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // h??m ki???m tra t??i kho???n xem c?? tr??ng v???i t??i kho???n trong b???ng Accounts kh??ng
    public boolean checkAccount() {
        // ?????u ti??n s??? t???o 1 list ????? get danh s??ch c??c account trong b???ng Accounts
        // s??? d??ng v??ng foreach ????? ki???m tra xem username nh???p v??o c?? tr??ng v???i username trong b???ng Accounts kh??ng,
        // n???u c?? th?? hi???n th??? th??ng b??o
        List<Accounts> accountsArrayList = sqlHelper.getAllAccounts();
        for (Accounts acc : accountsArrayList) {
            if (acc.getUserName().equalsIgnoreCase(binding.etUserName.getText().toString())) {
                Toast.makeText(getBaseContext(), "T??n t??i kho???n ???? t???n t???i", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }
}
