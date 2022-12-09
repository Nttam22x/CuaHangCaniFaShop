package com.example.canifa_shop.Report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.canifa_shop.Bill.Object.Bill;
import com.example.canifa_shop.R;
import com.example.canifa_shop.Report.Objcet.Report;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.FragmentReportBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ReportFragment extends Fragment {
    FragmentReportBinding binding;
    SQLHelper sqlHelper;
    List<Report> reportList;
    List<Bill> billList;
    private long totalPrice = 0;
    private long totalImportPrice = 0;
    private long totalSaleMoney = 0;
    private long totalProfit = 0;

    public static ReportFragment newInstance() {

        Bundle args = new Bundle();

        ReportFragment fragment = new ReportFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_report, container, false);
        return binding.getRoot();
    }


    // hàm này set dữ liệu cho các trường trong giao diện bao gồm:
    public void setData(){
        binding.tvProfit.setText(totalProfit+""); // lợi nhuận
        binding.tvSales.setText(totalPrice+"");  // doanh thu
        binding.tvDiscount.setText(totalSaleMoney+""); // giảm giá
        binding.tvInvoiceValue.setText(totalPrice+""); // giá trị hóa đơn
        binding.tvInvoiceNumber.setText(billList.size()+""); //  số hóa đơn
        binding.tvSellAmount.setText(totalPrice+""); // tiền bán
        binding.tvFundAmount.setText(totalImportPrice+""); // tiền vốn
        binding.tvBankAmount.setText("0"); // ngân hàng
        binding.tvBluntAmount.setText(totalPrice+"");  // tiền mặt
        binding.tvDebtAmount.setText("0"); // khách nợ
        binding.tvTaxAmount.setText("0");  // tiền thuế
    }
    public void initialization() {
        sqlHelper = new SQLHelper(getContext());
        reportList = new ArrayList<>();
        billList = new ArrayList<>();
        billList = sqlHelper.getAllBill(); // chứa danh sách hóa đơn
        reportList = sqlHelper.getAllReport();
        // reportList chứa danh sách các hóa đơn đã thanh toán
        for (Report report : reportList) {
            totalPrice += report.getTotalSale();
            totalImportPrice +=report.getTotalImport();
            totalSaleMoney +=report.getSaleMoney();
        }
        totalProfit = totalPrice-totalImportPrice;
    }

    @Override
    public void onStart() {
        super.onStart();
        initialization();
        setData();
    }
}
