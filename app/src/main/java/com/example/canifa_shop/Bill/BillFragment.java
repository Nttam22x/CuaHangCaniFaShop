package com.example.canifa_shop.Bill;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.canifa_shop.Bill.Adapter.BillListAdapter;
import com.example.canifa_shop.Bill.Object.Bill;
import com.example.canifa_shop.Helper.Function;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.FragmentBillBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BillFragment extends Fragment {
    FragmentBillBinding binding;
    List<Bill> billList;
    List<Bill> billListSearch;
    BillListAdapter adapter;
    SQLHelper sqlHelper;

    public static BillFragment newInstance() {

        Bundle args = new Bundle();

        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill, container, false);
        initialization();
        setAdapter(billList);
        binding.rvBill.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), BillDetailActivity.class);
                intent.putExtra("control", "update");
                intent.putExtra("ID", billList.get(position).getIDBill());
                startActivity(intent);
            }
        });
        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addListSear(binding.edtSearch.getText().toString());
                setAdapter(billListSearch);
                if (binding.edtSearch.getText().toString().equals("") || binding.edtSearch.getText().toString() == null) {
                    setAdapter(billList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return binding.getRoot();
    }

    public void initialization() {
        billList = new ArrayList<>();
        billListSearch = new ArrayList<>();
        sqlHelper = new SQLHelper(getContext());
        billList = sqlHelper.getAllBill();
    }

    public void setAdapter(List<Bill> billList) {
        Collections.reverse(billList);
        adapter = new BillListAdapter(getContext(), R.layout.item_list_bill, billList);
        binding.rvBill.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addListSear(String text) {
        billListSearch.clear();
        if (text.equals("")) {
            return;
        } else {
            for (Bill bill : billList) {
                if (text.contains(String.valueOf(bill.getIDBill())) || sqlHelper.getNameCustomer(bill.getIDCustomer()).contains(text)) {
                    billListSearch.add(bill);
                }
            }
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        initialization();
        setAdapter(billList);
    }
}
