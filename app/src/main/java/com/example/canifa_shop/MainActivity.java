package com.example.canifa_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canifa_shop.Bill.BillFragment;
import com.example.canifa_shop.More.MoreFragment;
import com.example.canifa_shop.Report.ReportFragment;
import com.example.canifa_shop.SQLHelper.SQLHelper;
import com.example.canifa_shop.Selling.SellingFragment;
import com.example.canifa_shop.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SQLHelper sqlHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        requestContactsPermissions();
        getFragment(SellingFragment.newInstance());
        requestContactsPermissions();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.selling:{
                        getFragment(SellingFragment.newInstance());
                        return true;
                    }
                    case R.id.bill:{
                        getFragment(BillFragment.newInstance());
                        return true;
                    }
                    case R.id.report:{
                        getFragment(ReportFragment.newInstance());
                        return true;
                    }
                    case R.id.more:{
                        getFragment(MoreFragment.newInstance());
                        return true;
                    }
                }
                return false;
            }
        });
    }
    public void getFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.flMain,fragment).commit();
    }
    private void requestContactsPermissions() {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  )
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}