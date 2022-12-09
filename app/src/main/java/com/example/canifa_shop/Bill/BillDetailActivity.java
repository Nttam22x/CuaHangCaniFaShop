package com.example.canifa_shop.Bill;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canifa_shop.Bill.Adapter.BillAdapter;
import com.example.canifa_shop.Bill.Object.Bill;
import com.example.canifa_shop.Customer.CustomerActivity;
import com.example.canifa_shop.Helper.Function;
import com.example.canifa_shop.Product.Object.Product;
import com.example.canifa_shop.R;
import com.example.canifa_shop.Report.Objcet.Report;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.ActivityBillDetailBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BillDetailActivity extends AppCompatActivity {
    private ActivityBillDetailBinding binding;
    private SQLHelper sqlHelper;
    private BillAdapter adapter;
    private List<Product> productOrderList;
    private List<Product> productList;
    private List<Bill> billList;
    int IDCustomer = 0;
    public final static int REQUEST_CODE = 1;
    private ImageView btnBack, btnAdd;
    private TextView tvTitile, tvDelete;
    private String control = "";
    private Bill billChoose;
    private int IDEmployee;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bill_detail);
        findByViewID();
        initialization();
        getInten();
        binding.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (control.equals("update")) {
                    updateBill(billChoose);
                } else {
                    addNewBill();
                    updateProduct();
                    sqlHelper.deleteOrderProduct();
                    finish();
                }


            }
        });
        binding.tvCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BillDetailActivity.this, CustomerActivity.class);
                intent.putExtra("control", "getCustomer");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(BillDetailActivity.this)
                        .setTitle("Bạn có muốn xóa không ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sqlHelper.deleteOrderProduct();
                                finish();
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
            }
        });
    }

    public void findByViewID() {
        btnAdd = findViewById(R.id.btnAdd);
        btnBack = findViewById(R.id.btnBack);
        tvTitile = findViewById(R.id.tvTitle);
        tvDelete = findViewById(R.id.tvDelete);
        btnAdd.setVisibility(View.INVISIBLE);
        tvTitile.setText("Hóa đơn chi tiết");

    }

    public void initialization() {
        productList = new ArrayList<>();
        sqlHelper = new SQLHelper(getApplicationContext());
        productOrderList = new ArrayList<>();
        billList = new ArrayList<>();
        productList = sqlHelper.getAllPrduct();
        productOrderList = sqlHelper.getAllOrderPrduct();
        billList = sqlHelper.getAllBill();
        setTotalBill();
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        IDEmployee = sharedPreferences.getInt("ID", 0);
    }

    public void getInten() {
        Intent intent = getIntent();
        control += intent.getStringExtra("control");
        if (control.equals("update")) {
            binding.btnThanhToan.setText("Cập nhật");
            btnAdd.setVisibility(View.INVISIBLE);
            tvDelete.setVisibility(View.INVISIBLE);
            setDataEditText(intent.getIntExtra("ID", 0));
            for (Bill bill : billList) {
                if (bill.getIDBill() == intent.getIntExtra("ID", 0))
                    billChoose = bill;
            }
        } else {
            binding.tvBill.setText("DH." + (billList.size() + 1) + "");
            setAdapter(productOrderList);
        }
    }

    public void setTotalBill() {
        long total = 0;
        for (Product product : productOrderList) {
            total += (product.getPrice() * product.getAmount());
        }
        binding.tvPointPlus.setText(Function.pointsPlus((int) total) + "");

        binding.tvTotalPrice.setText(Function.decimalFormatMoney(total));
    }

    public void addNewBill() {
        try {
            String IDProduct = "";
            String amount = "";
            long total = 0;
            for (Product product : productOrderList) {
                IDProduct += product.getBardCode() + ";";
                amount += product.getAmount() + ";";
                total += (product.getPrice() * product.getAmount());
            }
            if(productOrderList.size()==0)
            {
                Toast.makeText(getBaseContext(), "Không thể thanh toán. ", Toast.LENGTH_SHORT).show();

            }else{
                Bill bill = new Bill(0, new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime()), IDProduct, amount, "0", total, IDCustomer, IDEmployee);
                sqlHelper.insertBill(bill);
                addReport(productOrderList,IDCustomer);
            }

        }catch (Exception e)
        {
            Toast.makeText(getBaseContext(), "Không thể thanh toán. "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBill(Bill bill) {
        String IDProduct = "";
        String amount = "";
        long total = 0;
        for (Product product : productOrderList) {
            IDProduct += product.getBardCode() + ";";
            amount += product.getAmount() + ";";
            total += (product.getPrice() * product.getAmount());
        }
        bill.setAmount(amount);
        bill.setNames(IDProduct);
        bill.setTotal(total);
        bill.setIDCustomer(IDCustomer);
        sqlHelper.updateBill(bill);
        finish();
    }

    public void getOrderProductList(Bill bill) {
        productOrderList.clear();
        String id[] = bill.getNames().split(";");
        String amount[] = bill.getAmount().split(";");
        for (int i = 0; i < id.length; i++) {
            if (id[i].equals("")) break;
            else
                for (Product product : productList) {
                    if (product.getBardCode().equals(id[i])) {
                        product.setAmount(Integer.valueOf(amount[i]));
                        productOrderList.add(product);
                    }
                }
        }
        setAdapter(productOrderList);

    }

    public void setAdapter(List<Product> productOrderList) {
        adapter = new BillAdapter(productOrderList, getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvOrderProduct.setLayoutManager(layoutManager);
        binding.rvOrderProduct.setAdapter(adapter);
    }

    public void setDataEditText(int ID) {
        List<Bill> billList = new ArrayList<>();
        billList = sqlHelper.getAllBill();
        for (Bill bill : billList) {
            if (bill.getIDBill() == ID) {
                binding.tvBill.setText("DH." + bill.getIDBill());
                String customerName = sqlHelper.getNameCustomer(bill.getIDCustomer());
                if (customerName.equals("")) {
                    binding.tvCustomer.setText("Khách lẻ");
                } else
                    binding.tvCustomer.setText(customerName);
                binding.tvPointPlus.setText(Function.pointsPlus((int) bill.getTotal()) + "");
                binding.tvTotalPrice.setText(bill.getTotal() + "");
                binding.tvVoucher.setText(sqlHelper.getVoucher(bill.getIDCustomer()));
                getOrderProductList(bill);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REQUEST_CODE) {
            IDCustomer = data.getIntExtra("ID", 0);
            binding.tvCustomer.setText(sqlHelper.getNameCustomer(IDCustomer));
            binding.tvVoucher.setText(sqlHelper.getVoucher(IDCustomer));
        }
    }

    public void updateProduct() {
        for (Product product : productList) {
            updateAmountProduct(product);
        }
    }

    public void updateAmountProduct(Product product) {
        for (Product productItem : productOrderList) {
            if (productItem.getBardCode().equals(product.getBardCode())) {
                int hienCo = product.getAmount();
                int order = productItem.getAmount();
                product.setAmount(hienCo - order);
                sqlHelper.updateProduct(product);
            }
        }
    }

    // đây là hàm thêm hóa đơn
    public void addReport(List<Product> productList, int ID) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        long totalImport = 0;
        long totalSale = 0;
        long salemoney = 0;
        for (Product product : productList) {
            totalImport += (product.getImportprice() * product.getAmount());
            totalSale += (product.getPrice() * product.getAmount());
        }
        salemoney = totalSale * 10 /100;
        Report report = new Report(0,date,totalImport,totalSale,salemoney);
        sqlHelper.insertReport(report);
    }
}