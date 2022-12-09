package com.example.canifa_shop.Helper;

import java.text.DecimalFormat;

public class Function {
    public  static String permissionAdmin = "Admin";
    public  static String permissionEmployee = "Employee";
    public static String decimalFormatMoney(long money){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        return decimalFormat.format(money);
    }
    public static int pointsPlus(int money){
        return money*10/100;
    }
}
