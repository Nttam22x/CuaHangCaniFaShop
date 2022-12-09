package com.example.canifa_shop.More;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.canifa_shop.Category.CategoryActivity;
import com.example.canifa_shop.Customer.CustomerActivity;
import com.example.canifa_shop.Account.AcountManagerActivity;
import com.example.canifa_shop.Emplyee.EmployeeManagerActivity;
import com.example.canifa_shop.Helper.Function;
import com.example.canifa_shop.Login.LoginActivity;
import com.example.canifa_shop.Manager.WarehouseManagerActivity;
import com.example.canifa_shop.Product.ProductActivity;
import com.example.canifa_shop.R;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.databinding.FragmentMoreBinding;

import static android.content.Context.MODE_PRIVATE;

public class MoreFragment extends Fragment {
    FragmentMoreBinding binding;
    SharedPreferences sharedPreferences;
    int ID;
    SQLHelper sqlHelper;

    public static MoreFragment newInstance() {

        Bundle args = new Bundle();

        MoreFragment fragment = new MoreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false);
        initialization();
        binding.btnAccountManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AcountManagerActivity.class));

            }
        });
        binding.btnCategoryManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAdmin())
                    startActivity(new Intent(getContext(), CategoryActivity.class));
                else
                    Toast.makeText(getContext(), "Bạn không phải là admin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnCustomerManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAdmin())
                    startActivity(new Intent(getContext(), CustomerActivity.class));
                else
                    Toast.makeText(getContext(), "Bạn không phải là admin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnEmployeeManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAdmin())
                    startActivity(new Intent(getContext(), EmployeeManagerActivity.class));
                else
                    Toast.makeText(getContext(), "Bạn không phải là admin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnProdutManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAdmin())
                    startActivity(new Intent(getContext(), ProductActivity.class));
                else
                    Toast.makeText(getContext(), "Bạn không phải là admin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnWearHouseManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAdmin())
                    startActivity(new Intent(getContext(), WarehouseManagerActivity.class));
                else
                    Toast.makeText(getContext(), "Bạn không phải là admin", Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        return binding.getRoot();

    }

    public void initialization() {
        sharedPreferences = getActivity().getSharedPreferences("account", MODE_PRIVATE);
        ID = sharedPreferences.getInt("ID", 0);
        sqlHelper = new SQLHelper(getContext());
    }

    public boolean checkAdmin() {
        String permission = sqlHelper.checkPermission(ID);
        if (permission.equals(Function.permissionAdmin)) {
            return true;
        } else return false;
    }
}
